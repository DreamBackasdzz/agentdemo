package com.example.agentdemo.service;

import com.example.agentdemo.tool.WeatherTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class WeatherAgentService {

    private final ChatClient chatClient;
    private final WeatherTool weatherTool;

    public WeatherAgentService(ChatClient.Builder builder, WeatherTool weatherTool){
        this.chatClient = builder.build();
        this.weatherTool = weatherTool;
    }

    public String chat(String message){
        return chatClient.prompt()
                .user(message)
                .tools(weatherTool)
                .call()
                .content();
    }
}
