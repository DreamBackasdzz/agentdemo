package com.example.agentdemo.controller;

import com.example.agentdemo.dto.reqdto.InterviewReqDTO;
import com.example.agentdemo.service.InterviewAgentService;
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
@RequestMapping("/interview")
public class InterviewChatController {

    private static final Logger logger = LoggerFactory.getLogger(InterviewChatController.class);
    private final InterviewAgentService interviewAgentService;

    public InterviewChatController(InterviewAgentService interviewAgentService){
        this.interviewAgentService = interviewAgentService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@Valid @RequestBody InterviewReqDTO request){
        try {
            logger.info("Received interview chat request: cid={}", request.getCid());
            String response = interviewAgentService.chat(request.getMsg(), request.getCid());
            logger.info("Interview chat response generated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing interview chat request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }
}
