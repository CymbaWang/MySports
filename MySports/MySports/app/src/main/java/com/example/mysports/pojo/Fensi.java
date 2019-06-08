package com.example.mysports.pojo;

public class Fensi {
    private int touxiangImage;
    private int xingbieImage;
    private int guanzhuImage;
    private String nichengText;
    private String jieshaoText;

    public Fensi(int touxiangImage,int xingbieImage,int guanzhuImage,String nichengText,String jieshaoText){
        this.touxiangImage=touxiangImage;
        this.xingbieImage=xingbieImage;
        this.guanzhuImage=guanzhuImage;
        this.nichengText=nichengText;
        this.jieshaoText=jieshaoText;
    }

    public int getTouxiangImage() {
        return touxiangImage;
    }

    public void setTouxiangImage(int touxiangImage) {
        this.touxiangImage = touxiangImage;
    }

    public int getXingbieImage() {
        return xingbieImage;
    }

    public void setXingbieImage(int xingbieImage) {
        this.xingbieImage = xingbieImage;
    }

    public int getGuanzhuImage() {
        return guanzhuImage;
    }

    public void setGuanzhuImage(int guanzhuImage) {
        this.guanzhuImage = guanzhuImage;
    }

    public String getNichengText() {
        return nichengText;
    }

    public void setNichengText(String nichengText) {
        this.nichengText = nichengText;
    }

    public String getJieshaoText() {
        return jieshaoText;
    }

    public void setJieshaoText(String jieshaoText) {
        this.jieshaoText = jieshaoText;
    }
}
