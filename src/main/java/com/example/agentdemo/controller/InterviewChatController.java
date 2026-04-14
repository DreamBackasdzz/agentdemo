package com.example.agentdemo.controller;

import com.example.agentdemo.service.InterviewAgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interview")
public class InterviewChatController {

    private final InterviewAgentService interviewAgentService;

    public InterviewChatController(InterviewAgentService interviewAgentService){
        this.interviewAgentService = interviewAgentService;
    }

    @GetMapping
    public String chat(@RequestParam String msg,
                       @RequestParam String cid){
        return interviewAgentService.chat(msg,cid);
    }
}
