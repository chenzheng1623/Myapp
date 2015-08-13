package com.example.myapplication.bean;

/**
 * Created by cz on 2015/8/11.
 */
public class Xiaohau {

    private String time;
    private String img;
    private String title;
    private String type;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public Xiaohau() {
    }

    public Xiaohau(String time, String img, String title, String type) {
        this.time = time;
        this.img = img;
        this.title = title;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Xiaohau{" +
                "time='" + time + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
