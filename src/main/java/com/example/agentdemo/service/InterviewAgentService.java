package com.example.agentdemo.service;

import com.example.agentdemo.tool.QuestionTool;
import com.example.agentdemo.tool.ScoreTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;

@Service
public class InterviewAgentService {

    private final ChatClient chatClient;
    private final QuestionTool questionTool;
    private final ScoreTool scoreTool;

    public InterviewAgentService(ChatClient.Builder builder, QuestionTool questionTool, ScoreTool scoreTool){

        // 加Memory
        ChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                        .build();
        this.questionTool = questionTool;
        this.scoreTool = scoreTool;
    }

    public String chat(String message, String conversationId){
        return chatClient.prompt()
                .system("""
                    你是一名严格的技术面试官:
                    规则：
                    1. 用户说“开始面试” → 出题
                    2. 用户回答问题 → 评分
                    3. 评分后继续追问
                    4. 问题逐渐深入
                    保持专业、简洁、真实面试风格
                """)
                .user(message)
                .advisors(a -> a.param("conversationId", conversationId))
                .tools(questionTool, scoreTool)
                .call()
                .content();
    }
}
