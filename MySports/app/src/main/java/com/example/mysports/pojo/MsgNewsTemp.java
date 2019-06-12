package com.example.mysports.pojo;

public class MsgNewsTemp {
    private NewsTemp news;
    private String sendName;
    private String receiveName;

    public MsgNewsTemp(NewsTemp news, String sendName, String receiveName) {
        this.news = news;
        this.sendName = sendName;
        this.receiveName = receiveName;
    }


    public NewsTemp getNews() {
        return news;
    }

    public void setNews(NewsTemp news) {
        this.news = news;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }




}
