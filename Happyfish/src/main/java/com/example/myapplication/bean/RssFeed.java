package com.example.myapplication.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by cz on 2015/9/7 0007.
 */
public class RssFeed {

    private String title = null;
    private String pubdate = null;
    private int itemcount = 0;
    private List<RssItem> itemList;

    public RssFeed() {
        itemList = new Vector(0);
    }

    public int additem(RssItem item) {
        itemList.add(item);
        itemcount++;
        return itemcount;
    }

    public RssItem getitem(int location) {
        return itemList.get(location);
    }

    public List getallitem() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        int size = itemList.size();
        for (int i = 0; i < size; i++) {
            HashMap<String,Object> item= new HashMap<String,Object>();
            item.put(RssItem.TITLE,itemList.get(i).getTitle());
            item.put(RssItem.PUBDATE,itemList.get(i).getPubdate());
            data.add(item);
        }
        return data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public int getItemcount() {
        return itemcount;
    }

    public void setItemcount(int itemcount) {
        this.itemcount = itemcount;
    }

    public List<RssItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<RssItem> itemList) {
        this.itemList = itemList;
    }
}
