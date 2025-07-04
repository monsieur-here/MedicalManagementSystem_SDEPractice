package com.mms.model;

import java.util.Map;

public class DoctorApiResponse {

    private String msg;
    private int code;
    private Map<String, Object> data;

    // The constructor name must also match the new class name.
    public DoctorApiResponse(String msg, int code, Map<String, Object> data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    // Getters and Setters (these do not need to change)

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
