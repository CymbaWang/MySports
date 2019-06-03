package com.example.mysports.model;

public class Music {
    private int musicId;	//歌曲id
    private String musicName;	//歌曲名
    private String singerName;	//歌手名
    private int musicLength;	//歌曲时长
    private String musicImage;	//歌曲图片
    private String musicSource;	//歌曲地址

    public Music() {}
    public Music(String musicName, String singerName, int musicLength, String musicImage, String musicSource) {
        this.musicName = musicName;
        this.singerName = singerName;
        this.musicLength = musicLength;
        this.musicImage = musicImage;
        this.musicSource = musicSource;
    }
    public int getMusicId() {
        return musicId;
    }
    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }
    public String getMusicName() {
        return musicName;
    }
    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
    public String getSingerName() {
        return singerName;
    }
    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
    public int getMusicLength() {
        return musicLength;
    }
    public void setMusicLength(int musicLength) {
        this.musicLength = musicLength;
    }
    public String getMusicImage() {
        return musicImage;
    }
    public void setMusicImage(String musicImage) {
        this.musicImage = musicImage;
    }
    public String getMusicSource() {
        return musicSource;
    }
    public void setMusicSource(String musicSource) {
        this.musicSource = musicSource;
    }


}

