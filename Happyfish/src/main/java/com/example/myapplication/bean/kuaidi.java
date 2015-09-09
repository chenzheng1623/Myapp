package com.example.myapplication.bean;

/**
 * Created by cz on 2015/9/4 0004.
 */
public class kuaidi {
    private String loc;
    private String time;


    public kuaidi(String loc, String time) {
        this.loc = loc;
        this.time = time;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLoc() {
        return loc;
    }

    public String getTime() {
        return time;
    }
}
