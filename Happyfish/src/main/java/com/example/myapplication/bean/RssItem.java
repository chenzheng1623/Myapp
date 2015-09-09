package com.example.myapplication.bean;

/**
 * Created by cz on 2015/9/7 0007.
 */
public class RssItem {
    public static final String TITLE = "title";
    public static final String PUBDATE = "pubdate";
    private String title = null;
    private String description = null;
    private String link = null;
    private String category = null;
    private String pubdate = null;

    public RssItem(String title, String description, String link, String category, String pubdate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.category = category;
        this.pubdate = pubdate;
    }

    public static String getTITLE() {
        return TITLE;
    }

    public static String getPUBDATE() {
        return PUBDATE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public RssItem() {
    }

    @Override
    public String toString() {
        return "RssItem{}";
    }
}
