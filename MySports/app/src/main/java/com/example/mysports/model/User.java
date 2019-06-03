package com.example.mysports.model;

import java.util.Date;

public class User {
    private int userId;
    private String userAccount;
    private String userPassword;
    private String userName;
    private boolean userSex;
    private String userPhone;
    private String userEmail;
    private Date userRegtime;

    public User(){}
    public User(String userAccount, String userPassword){
        this.userAccount = userAccount;
        this.userPassword = userPassword;
    }
    public User(String userAccount,String userPassword,String userName,boolean userSex,String userPhone,String userEmail,Date userRegtime) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userRegtime = userRegtime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getUserSex() {
        return userSex;
    }

    public void setUserSex(boolean userSex) {
        this.userSex = userSex;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserRegtime() {
        return userRegtime;
    }

    public void setUserRegtime(Date userRegtime) {
        this.userRegtime = userRegtime;
    }


}

