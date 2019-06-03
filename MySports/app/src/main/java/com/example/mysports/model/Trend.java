package com.example.mysports.model;
import java.util.Date;

public class Trend {
    private int trendId;	//动态id
    private int userId;	//用户id
    private String trendContent;	//动态内容
    private int readTimes;	//阅读次数
    private int forwardTimes;	//转发数
    private int commentTimes;	//评论数
    private int pointTimes;	//点赞数
    private Date sendTime;	//发布时间
    private String sendLocation;	//发布地点

    public Trend() {}
    public Trend(int userId, String trendContent, int readTimes, int forwardTimes, int commentTimes, int pointTimes, Date sendTime, String sendLocation) {
        this.userId = userId;
        this.trendContent = trendContent;
        this.readTimes = readTimes;
        this.forwardTimes = forwardTimes;
        this.commentTimes = commentTimes;
        this.pointTimes = pointTimes;
        this.sendTime = sendTime;
        this.sendLocation = sendLocation;
    }
    public int getTrendId() {
        return trendId;
    }
    public void setTrendId(int trendId) {
        this.trendId = trendId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getTrendContent() {
        return trendContent;
    }
    public void setTrendContent(String trendContent) {
        this.trendContent = trendContent;
    }
    public int getReadTimes() {
        return readTimes;
    }
    public void setReadTimes(int readTimes) {
        this.readTimes = readTimes;
    }
    public int getForwardTimes() {
        return forwardTimes;
    }
    public void setForwardTimes(int forwardTimes) {
        this.forwardTimes = forwardTimes;
    }
    public int getCommentTimes() {
        return commentTimes;
    }
    public void setCommentTimes(int commentTimes) {
        this.commentTimes = commentTimes;
    }
    public int getPointTimes() {
        return pointTimes;
    }
    public void setPointTimes(int pointTimes) {
        this.pointTimes = pointTimes;
    }
    public Date getSendTime() {
        return sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    public String getSendLocation() {
        return sendLocation;
    }
    public void setSendLocation(String sendLocation) {
        this.sendLocation = sendLocation;
    }


}

