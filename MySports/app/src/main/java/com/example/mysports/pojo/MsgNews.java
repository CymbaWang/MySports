package com.example.mysports.pojo;


import com.example.mysports.model.News;

public class MsgNews {
    private News news;
    private String sendName;
    private String receiveName;

    public MsgNews(News news, String sendName, String receiveName) {
        this.news = news;
        this.sendName = sendName;
        this.receiveName = receiveName;
    }

    public MsgNews(){}


    public News getNews() {
        return news;
    }

    public void setNews(News news) {
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
