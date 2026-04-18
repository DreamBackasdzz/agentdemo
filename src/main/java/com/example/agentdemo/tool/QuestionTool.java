package com.example.agentdemo.tool;

import com.example.agentdemo.service.MilvusService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionTool {

    @Autowired
    private MilvusService milvusService;

    @Tool(description = "根据问题文档进行提问，避免捏造")
    public String generateQuestion(String job){
        try {
            // 从Milvus知识库中检索相关问题
            String context = milvusService.search(job);
            if (context != null && !context.isEmpty()) {
                return context;
            } else {
                return "知识库暂未有相关问题，抱歉";
            }
        } catch (Exception e) {
            return "查询知识库时出现错误，请稍后重试";
        }
    }
}
