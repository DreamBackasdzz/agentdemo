package com.example.agentdemo.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class QuestionTool {


    @Tool(description = "根据岗位生成技术面试问题")
    public String generateQuestion(String job){

        // 这里先模拟 后续可以自己设置问题啥的
        return "请解释 java 中线程的实现方式，并说明区别";
    }
}
