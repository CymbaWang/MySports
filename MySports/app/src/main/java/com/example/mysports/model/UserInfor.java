package com.example.mysports.model;

import java.util.Date;

public class UserInfor {
    private int userId;	//用户id
    private float userHeight;	//身高
    private float userWeight;	//体重
    private String userHeadImage;	//头像
    private boolean userVip;	//是否是会员
    private float userTotalTime;	//总运动时间
    private int userTrends;	//动态总数
    private int userFollow;	//关注总数
    private int userFans;	//粉丝总数
    private String userBackgroundImage;	//个人背景主页
    private Date userBirth;	//生日
    private String userAddress;		//地区
    private String userUnivercity;	//大学
    private String userIntroduce;	//个人介绍
    private int userTotalScore;	//个人总积分

    public UserInfor() {}
    public UserInfor(float userHeight, float userWeight, String userHeadImage, boolean userVip, float userTotalTime, int userTrends, int userFollow, int userFans, int userTotalScore,
                     String userBackgroundImage, Date userBirth, String userAddress, String userUnivercity, String userIntroduce) {
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userHeadImage = userHeadImage;
        this.userVip = userVip;
        this.userTotalTime = userTotalTime;
        this.userTrends = userTrends;
        this.userFollow = userFollow;
        this.userFans = userFans;
        this.userBackgroundImage = userBackgroundImage;
        this.userBirth = userBirth;
        this.userAddress = userAddress;
        this.userUnivercity = userUnivercity;
        this.userIntroduce = userIntroduce;
        this.userTotalScore = userTotalScore;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public float getUserHeight() {
        return userHeight;
    }
    public void setUserHeight(float userHeight) {
        this.userHeight = userHeight;
    }
    public float getUserWeight() {
        return userWeight;
    }
    public void setUserWeight(float userWeight) {
        this.userWeight = userWeight;
    }
    public String getUserHeadImage() {
        return userHeadImage;
    }
    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }
    public boolean isUserVip() {
        return userVip;
    }
    public void setUserVip(boolean userVip) {
        this.userVip = userVip;
    }
    public float getUserTotalTime() {
        return userTotalTime;
    }
    public void setUserTotalTime(float userTotalTime) {
        this.userTotalTime = userTotalTime;
    }
    public int getUserTrends() {
        return userTrends;
    }
    public void setUserTrends(int userTrends) {
        this.userTrends = userTrends;
    }
    public int getUserFollow() {
        return userFollow;
    }
    public void setUserFollow(int userFollow) {
        this.userFollow = userFollow;
    }
    public int getUserFans() {
        return userFans;
    }
    public void setUserFans(int userFans) {
        this.userFans = userFans;
    }
    public String getUserBackgroundImage() {
        return userBackgroundImage;
    }
    public void setUserBackgroundImage(String userBackgroundImage) {
        this.userBackgroundImage = userBackgroundImage;
    }
    public Date getUserBirth() {
        return userBirth;
    }
    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }
    public String getUserAddress() {
        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public String getUserUnivercity() {
        return userUnivercity;
    }
    public void setUserUnivercity(String userUnivercity) {
        this.userUnivercity = userUnivercity;
    }
    public String getUserIntroduce() {
        return userIntroduce;
    }
    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce;
    }
    public int getUserTotalScore() {
        return userTotalScore;
    }
    public void setUserTotalScore(int userTotalScore) {
        this.userTotalScore = userTotalScore;
    }


}

