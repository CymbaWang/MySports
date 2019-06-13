package com.example.mysports.pojo;

import java.util.Date;

public class MessageContent {
    private int headSculpture;
    private String nickName;
    private String content;
    private Date time;
    private int senderId;
    private int receiveId;
    private int type;

    public MessageContent(int headSculpture, String nickName, String content, Date time,int senderId,int receiveId,int type) {
        this.headSculpture = headSculpture;
        this.nickName = nickName;
        this.content = content;
        this.time = time;
        this.senderId=senderId;
        this.receiveId=receiveId;
        this.type=type;
    }

    public MessageContent(){}

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }


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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
