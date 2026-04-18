package com.example.agentdemo;

import com.example.agentdemo.service.MilvusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgentdemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(AgentdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AgentdemoApplication.class, args);
	}

    @Bean
    ApplicationRunner init(MilvusService milvusService) {
        return args -> {
            try {
                logger.info("Starting Milvus knowledge base initialization...");
                logger.info("Available memory: {} MB", Runtime.getRuntime().freeMemory() / (1024 * 1024));

                logger.info("Initializing Milvus knowledge base...");
                milvusService.saveToMilvus("Java面试题大全");
                logger.info("Milvus knowledge base initialized successfully");
                logger.info("Memory after initialization: {} MB", Runtime.getRuntime().freeMemory() / (1024 * 1024));
            } catch (OutOfMemoryError e) {
                logger.error("Out of memory error during Milvus initialization", e);
                logger.error("Please increase JVM memory allocation and try again");
            } catch (Exception e) {
                logger.error("Failed to initialize Milvus knowledge base", e);
            }
        };
    }

}
