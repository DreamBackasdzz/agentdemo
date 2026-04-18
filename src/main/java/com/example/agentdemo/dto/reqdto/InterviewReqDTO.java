package com.example.agentdemo.dto.reqdto;

import jakarta.validation.constraints.NotBlank;

public class InterviewReqDTO {
    @NotBlank(message = "Message cannot be blank")
    private String msg;

    @NotBlank(message = "Conversation ID cannot be blank")
    private String cid;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
