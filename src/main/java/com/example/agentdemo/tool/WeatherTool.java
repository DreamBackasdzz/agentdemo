package com.example.agentdemo.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    @Tool(description = "获取某个城市的天气")
    public String getWeather(String city){
        return city + "今天是25°";
    }
}
