package com.example.uuun.cbyapplication.bean;

/**
 * Created by Ma on 2017/8/2.
 */

public class Message {

    /**
     * id : 1
     * userId : 4028839b5d29fd3c015d2a062eba0000
     * state : 1
     * content : test1
     * date : 1501661880000
     */

    private int id;
    private String userId;
    private String state;
    private String content;
    private long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
