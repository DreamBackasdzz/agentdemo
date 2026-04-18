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
        // 先手动强制查一遍
        String context = questionTool.generateQuestion(message);
        String sysPrompt = """
                    你是一名严格的技术面试官:
                    【参考问题】
                    %s
                    规则：
                    1. 用户说"开始面试" → 必须从参考问题中选择合适的问题出题
                    2. 用户回答问题 → 评分
                    3. 评分后继续追问，追问也必须基于参考问题
                    4. 问题逐渐深入
                    5. 绝对不能自己创造问题，必须严格基于参考问题
                    保持专业、简洁、真实面试风格
                    请严格基于参考知识进行提问，避免任何捏造
                """.formatted(context);

        return chatClient.prompt()
                .system(sysPrompt)
                .user(message)
                .advisors(a -> a.param("conversationId", conversationId))
                .tools(questionTool, scoreTool)
                .call()
                .content();
    }
}
