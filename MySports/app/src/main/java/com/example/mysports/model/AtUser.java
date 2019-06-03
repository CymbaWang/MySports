package com.example.mysports.model;

import java.util.Date;

public class AtUser {
    private int atuserId;	//id
    private int trendId;	//动态id
    private int userId;		//被@用户id
    private Date atuserTime;	//被@时间

    public AtUser() {}
    public AtUser(int trendId, int userId, Date atuserTime) {
        this.trendId = trendId;
        this.userId = userId;
        this.atuserTime = atuserTime;
    }
    public int getAtuserId() {
        return atuserId;
    }
    public void setAtuserId(int atuserId) {
        this.atuserId = atuserId;
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
    public Date getAtuserTime() {
        return atuserTime;
    }
    public void setAtuserTime(Date atuserTime) {
        this.atuserTime = atuserTime;
    }


}
