package com.example.agentdemo.controller;

import com.example.agentdemo.service.WeatherAgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class WeatherChatController {

    private final WeatherAgentService weatherAgentService;

     public WeatherChatController(WeatherAgentService weatherAgentService){
         this.weatherAgentService = weatherAgentService;
     }

     @GetMapping
     public String chat(@RequestParam String msg){
         return weatherAgentService.chat(msg);
     }
}
