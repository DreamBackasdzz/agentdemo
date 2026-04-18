package com.example.agentdemo.service;

import com.example.agentdemo.utils.ChunkUtil;
import com.example.agentdemo.utils.TikaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MilvusService {

    private static final Logger logger = LoggerFactory.getLogger(MilvusService.class);
    private static final int BATCH_SIZE = 10;
    private static final int CHUNK_SIZE = 500;
    private static final int OVERLAP_SIZE = 50;
    
    @Autowired
    private VectorStore vectorStore;

    public void saveToMilvus(String text) throws Exception {
        logger.info("Starting to save data to Milvus");
        
        ClassPathResource resource = new ClassPathResource("Java面试题大全.pdf");
        File file = resource.getFile();

        // 1.tika解析
        logger.info("Parsing PDF file: {}", file.getName());
        String fullText = TikaUtil.parseFile(file);
        logger.info("PDF parsing completed, text length: {}", fullText.length());

        // 2.chunk切块
        logger.info("Splitting text into chunks");
        List<String> chunks = ChunkUtil.splitText(fullText, CHUNK_SIZE, OVERLAP_SIZE);
        logger.info("Text splitting completed, total chunks: {}", chunks.size());

        // 3.分批处理并保存到Milvus
        logger.info("Saving chunks to Milvus in batches");
        List<Document> batchDocuments = new ArrayList<>();
        for (int i = 0; i < chunks.size(); i++) {
            Map<String, Object> meta = new HashMap<>();
            meta.put("source", file.getName());
            meta.put("chunk_index", i);
            batchDocuments.add(new Document(chunks.get(i), meta));
            
            // 达到批次大小或最后一批时保存
            if (batchDocuments.size() >= BATCH_SIZE || i == chunks.size() - 1) {
                logger.info("Saving batch: {}-{}", i - batchDocuments.size() + 1, i);
                vectorStore.add(batchDocuments);
                batchDocuments.clear();
                // 释放内存
                System.gc();
            }
        }
        
        logger.info("Data saved to Milvus successfully");
    }

    public String search(String question){
        try {
            logger.info("Searching for question: {}", question);
            List<Document> documents = vectorStore.similaritySearch(
                    SearchRequest.builder()
                            .query(question)
                            .topK(3)
                            .build()
            );
            logger.info("Search completed, found {} documents", documents.size());
            return documents.stream()
                    .map(Document::getText)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            logger.error("Error during search", e);
            return "搜索过程中出现错误，请稍后重试";
        }
    }
}
