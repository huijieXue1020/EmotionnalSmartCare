package com.example.demo.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResultReturn {
    private int code;
    private String msg;
    private Object data;

    public ResultReturn(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultReturn(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String toJsonString(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"code\":");
        stringBuilder.append(code);
        stringBuilder.append(", \"msg\":\"");
        stringBuilder.append(msg);
        stringBuilder.append("\", \"data\":");
        stringBuilder.append(data);
        stringBuilder.append("}");


        return stringBuilder.toString();
    }
}
