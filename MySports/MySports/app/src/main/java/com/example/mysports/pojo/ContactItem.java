package com.example.mysports.pojo;

import java.util.Date;

public class ContactItem {
    private int headSculpture;
    private String nickName;
    private String content;
    private Date time;

    public ContactItem(int headSculpture, String nickName, String content, Date time) {
        this.headSculpture = headSculpture;
        this.nickName = nickName;
        this.content = content;
        this.time = time;
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
}
