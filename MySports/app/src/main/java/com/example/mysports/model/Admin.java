package com.example.mysports.model;

public class Admin {
    private int adminId;	//管理员id
    private String account;	//账号
    private String password;	//密码

    public Admin(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
