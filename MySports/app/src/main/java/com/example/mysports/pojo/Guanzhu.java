package com.example.mysports.pojo;

public class Guanzhu {
    private int touxiangImage;
    private int xingbieImage;
    private int diandiandianImage;
    private String nichengText;
    private String jieshaoText;

    public Guanzhu(int touxiangImage,int xingbieImage,int diandiandianImage,String nichengText,String jieshaoText){
        this.touxiangImage=touxiangImage;
        this.xingbieImage=xingbieImage;
        this.diandiandianImage=diandiandianImage;
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

    public int getDiandiandianImage() {
        return diandiandianImage;
    }

    public void setDiandiandianImage(int diandiandianImage) {
        this.diandiandianImage = diandiandianImage;
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
