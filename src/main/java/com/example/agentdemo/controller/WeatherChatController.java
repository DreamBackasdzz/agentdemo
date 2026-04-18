package com.example.agentdemo.controller;

import com.example.agentdemo.dto.reqdto.WeatherReqDTO;
import com.example.agentdemo.service.WeatherAgentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/chat")
public class WeatherChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherChatController.class);
    private final WeatherAgentService weatherAgentService;

    public WeatherChatController(WeatherAgentService weatherAgentService){
        this.weatherAgentService = weatherAgentService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@Valid @RequestBody WeatherReqDTO request){
        try {
            logger.info("Received weather chat request: msg={}", request.getMsg());
            String response = weatherAgentService.chat(request.getMsg());
            logger.info("Weather chat response generated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing weather chat request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }

}
