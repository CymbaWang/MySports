package com.example.mysports.model;

public class TrendImage {
    private int imageId;	//图片id
    private int trendId;	//动态id
    private String trendImage;	//图片路径

    public TrendImage() {

    }
    public TrendImage(int trendId, String trendImage) {
        this.trendId = trendId;
        this.trendImage = trendImage;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public int getTrendId() {
        return trendId;
    }
    public void setTrendId(int trendId) {
        this.trendId = trendId;
    }
    public String getTrendImage() {
        return trendImage;
    }
    public void setTrendImage(String trendImage) {
        this.trendImage = trendImage;
    }


}
