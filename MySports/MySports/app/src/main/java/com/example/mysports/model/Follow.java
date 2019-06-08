package com.example.mysports.model;

import java.util.Date;

public class Follow {
    private int followId;	//关注id
    private int userId;	//被关注者id
    private int fansId;	//粉丝id
    private Date followTime;	//关注时间

    public Follow() {}
    public Follow(int userId,int fansId,Date followTime) {
        this.userId = userId;
        this.fansId = fansId;
        this.followTime = followTime;
    }
    public int getFollowId() {
        return followId;
    }
    public void setFollowId(int followId) {
        this.followId = followId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getFansId() {
        return fansId;
    }
    public void setFansId(int fansId) {
        this.fansId = fansId;
    }
    public Date getFollowTime() {
        return followTime;
    }
    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }


}

