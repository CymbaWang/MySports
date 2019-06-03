package com.example.mysports.pojo;

public class Photo {
    private String photo_image;
    private String photo_text;

    public Photo(String photo_image, String photo_text){
        this.photo_image = photo_image;
        this.photo_text = photo_text;
    }

    public String getPhoto_image() {
        return photo_image;
    }

    public void setPhoto_image(String photo_image) {
        this.photo_image = photo_image;
    }

    public String getPhoto_text() {
        return photo_text;
    }

    public void setPhoto_text(String photo_text) {
        this.photo_text = photo_text;
    }
}
