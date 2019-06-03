package com.example.mysports.pojo;

import java.util.ArrayList;
import java.util.List;

public class Trend {
    private String touxiang_image;
    private String nicheng_text;
    private String sendtime_text;
    private String trendContent_text;
    private List<String>trend_image = new ArrayList<String>();
    private String zhuanfa_text;
    private String pinglun_text;
    private String zan_text;

    public Trend(String touxiang_image, String nicheng_text, String sendtime_text, String trendContent_text, List<String>trend_image, String zhuanfa_text, String pinglun_text, String zan_text){
        this.touxiang_image = touxiang_image;
        this.nicheng_text = nicheng_text;
        this.sendtime_text = sendtime_text;
        this.trendContent_text = trendContent_text;
        for(int i=0;i<trend_image.size();i++){
            this.trend_image.add(trend_image.get(i));
        }
        this.zhuanfa_text = zhuanfa_text;
        this.pinglun_text = pinglun_text;
        this.zan_text =zan_text;
    }

    public String getTouxiang_image() {
        return touxiang_image;
    }

    public void setTouxiang_image(String touxiang_image) {
        this.touxiang_image = touxiang_image;
    }

    public String getNicheng_text() {
        return nicheng_text;
    }

    public void setNicheng_text(String nicheng_text) {
        this.nicheng_text = nicheng_text;
    }

    public String getSendtime_text() {
        return sendtime_text;
    }

    public void setSendtime_text(String sendtime_text) {
        this.sendtime_text = sendtime_text;
    }

    public String getTrendContent_text() {
        return trendContent_text;
    }

    public void setTrendContent_text(String trendContent_text) {
        this.trendContent_text = trendContent_text;
    }

    public List<String> getTrend_image() {
        return trend_image;
    }

    public void setTrend_image(List<String> trend_image) {
        for(int i=0;i<trend_image.size();i++){
            this.trend_image.add(trend_image.get(i));
        }
    }

    public String getZhuanfa_text() {
        return zhuanfa_text;
    }

    public void setZhuanfa_text(String zhuanfa_text) {
        this.zhuanfa_text = zhuanfa_text;
    }

    public String getPinglun_text() {
        return pinglun_text;
    }

    public void setPinglun_text(String pinglun_text) {
        this.pinglun_text = pinglun_text;
    }

    public String getZan_text() {
        return zan_text;
    }

    public void setZan_text(String zan_text) {
        this.zan_text = zan_text;
    }
}
