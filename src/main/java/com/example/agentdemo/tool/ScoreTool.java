package com.example.agentdemo.tool;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class ScoreTool {

    private final ChatClient chatClient;

    public ScoreTool(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @Tool(description = "对候选人的回答评分，满分100，并给出建议")
    public String score(String answer){
        return chatClient.prompt()
                .user(answer)
                .system("你是技术面试官，请评分并给建议")
                .call()
                .content();
    }
}
