package com.example.mysports.pojo;

public class NewsTemp {
    private int newsId;	//消息id
    private int senduserId;	//发送者id
    private int receiveuserId;	//接受者id
    private String sendContent;	//发送内容
    private long sendTime;	//发送时间
    private boolean newsState;	//消息接受状态

    public NewsTemp() {

    }
    public NewsTemp(int senduserId, int receiveuserId, String sendContent, long sendTime, boolean newsState) {
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
    public long getSendTime() {
        return sendTime;
    }
    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
    public boolean isNewsState() {
        return newsState;
    }
    public void setNewsState(boolean newsState) {
        this.newsState = newsState;
    }


}

