package com.mms.model;

import java.util.Map;

public class ReceptionistApiResponse {

    private String msg;
    private int code;
    private Map<String, Object> data;

    // Constructor
    public ReceptionistApiResponse(String msg, int code, Map<String, Object> data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    // Getters and Setters for Gson

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
