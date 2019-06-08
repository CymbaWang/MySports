package com.example.mysports.model;

import java.util.Date;

public class Comment {
    private int commentId;	//评论id
    private int trendId;	//动态id
    private int userId;	//评论者id
    private String commentContent;	//评论内容
    private Date commentTime;	//评论时间

    public Comment() {}
    public Comment(int trendId, int userId, String commentContent, Date commentTime) {
        this.trendId = trendId;
        this.userId = userId;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }
    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int commentId) {
        this.commentId = commentId;
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
    public String getCommentContent() {
        return commentContent;
    }
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public Date getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }


}

