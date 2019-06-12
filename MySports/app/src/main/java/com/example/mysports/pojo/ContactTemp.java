package com.example.mysports.pojo;

public class ContactTemp {


    private int conId;
//    private int userId;
    private int headSculpture;
    private String nickName;
    private String content;
    private long time;

    public ContactTemp(int conid,int headSculpture, String nickName, String content, long time) {
        this.conId=conid;
        this.headSculpture = headSculpture;
        this.nickName = nickName;
        this.content = content;
        this.time = time;
//        this.userId=userId;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public int getHeadSculpture() {
        return headSculpture;
    }

    public void setHeadSculpture(int headSculpture) {
        this.headSculpture = headSculpture;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public int getConId() {
        return conId;
    }

    public void setConId(int conId) {
        this.conId = conId;
    }
    public void setTime(long time) {
        this.time = time;
    }
}
