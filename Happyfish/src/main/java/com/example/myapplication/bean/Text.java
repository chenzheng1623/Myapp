package com.example.myapplication.bean;

/**
 * Created by cz on 2015/8/11.
 */
public class Text {

    private String time;
    private String text;
    private  String title;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Text{" +
                "time='" + time + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
