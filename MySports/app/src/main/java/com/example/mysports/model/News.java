package com.example.mysports.model;

import java.util.Date;

public class News {
    private int newsId;	//消息id
    private int senduserId;	//发送者id
    private int receiveuserId;	//接受者id
    private String sendContent;	//发送内容
    private Date sendTime;	//发送时间
    private boolean newsState;	//消息接受状态

    public News() {

    }
    public News(int senduserId, int receiveuserId, String sendContent, Date sendTime, boolean newsState) {
        this.senduserId = senduserId;
        this.receiveuserId = receiveuserId;
        this.sendContent = sendContent;
        this.sendTime = sendTime;
        this.newsState = newsState;
    }
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
    public int getSenduserId() {
        return senduserId;
    }
    public void setSenduserId(int senduserId) {
        this.senduserId = senduserId;
    }
    public int getReceiveuserId() {
        return receiveuserId;
    }
    public void setReceiveuserId(int receiveuserId) {
        this.receiveuserId = receiveuserId;
    }
    public String getSendContent() {
        return sendContent;
    }
    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }
    public Date getSendTime() {
        return sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    public boolean isNewsState() {
        return newsState;
    }
    public void setNewsState(boolean newsState) {
        this.newsState = newsState;
    }


}

