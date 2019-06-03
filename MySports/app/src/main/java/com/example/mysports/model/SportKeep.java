package com.example.mysports.model;

import java.util.Date;

public class SportKeep {
    private int keepId;	//记录id
    private int userId;	//用户id
    private float distance;	//运动距离
    private int stepNumber;	//运动步数
    private Date keepDate;	//日期
    private float keepHeat;	//消耗热量
    private Date keepStartTime;	//开始运动时间
    private Date keepStopTime;	//结束运动时间
    private float keepTotalTime;	//运动时长

    public SportKeep() {}
    public SportKeep(int userId, float distance, int stepNumber, Date keepDate, float keepHeat ,Date keepStartTime ,Date keepStopTime, float keepTotalTime) {
        this.userId = userId;
        this.distance = distance;
        this.stepNumber = stepNumber;
        this.keepDate = keepDate;
        this.keepHeat = keepHeat;
        this.keepStartTime = keepStartTime;
        this.keepStopTime = keepStopTime;
        this.keepTotalTime = keepTotalTime;
    }
    public int getKeepId() {
        return keepId;
    }
    public void setKeepId(int keepId) {
        this.keepId = keepId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public float getDistance() {
        return distance;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }
    public int getStepNumber() {
        return stepNumber;
    }
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }
    public Date getKeepDate() {
        return keepDate;
    }
    public void setKeepDate(Date keepDate) {
        this.keepDate = keepDate;
    }
    public float getKeepHeat() {
        return keepHeat;
    }
    public void setKeepHeat(float keepHeat) {
        this.keepHeat = keepHeat;
    }
    public Date getKeepStartTime() {
        return keepStartTime;
    }
    public void setKeepStartTime(Date keepStartTime) {
        this.keepStartTime = keepStartTime;
    }
    public Date getKeepStopTime() {
        return keepStopTime;
    }
    public void setKeepStopTime(Date keepStopTime) {
        this.keepStopTime = keepStopTime;
    }
    public float getKeepTotalTime() {
        return keepTotalTime;
    }
    public void setKeepTotalTime(float keepTotalTime) {
        this.keepTotalTime = keepTotalTime;
    }


}

