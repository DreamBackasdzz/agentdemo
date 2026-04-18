package com.example.agentdemo.dto.reqdto;

import jakarta.validation.constraints.NotBlank;

public class WeatherReqDTO {

    @NotBlank(message = "Message cannot be blank")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
