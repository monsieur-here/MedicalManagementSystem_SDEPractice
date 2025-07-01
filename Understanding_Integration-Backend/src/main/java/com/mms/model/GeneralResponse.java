package com.mms.model;

import java.util.HashMap;

public class GeneralResponse {
    private String msg;
    private Integer code;

    private HashMap<String, ?> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public HashMap<String, ?> getData() {
        return data;
    }

    public void setData(HashMap<String, ?> data) {
        this.data = data;
    }


}