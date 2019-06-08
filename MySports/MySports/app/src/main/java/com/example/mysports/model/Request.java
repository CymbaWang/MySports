package com.example.mysports.model;

public class Request {
    private int type;   //记录请求类型
    private String data;    //记录请求数据

    public Request(int type, String data){
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
