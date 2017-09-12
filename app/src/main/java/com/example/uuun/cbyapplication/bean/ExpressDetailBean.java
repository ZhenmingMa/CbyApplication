package com.example.uuun.cbyapplication.bean;

/**
 * Created by uuun on 2017/8/2.
 */

public class ExpressDetailBean {
    private String date;
    private String time;
    private String expressDetail;

    public ExpressDetailBean(String date, String time, String expressDetail) {
        this.date = date;
        this.time = time;
        this.expressDetail = expressDetail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExpressDetail() {
        return expressDetail;
    }

    public void setExpressDetail(String expressDetail) {
        this.expressDetail = expressDetail;
    }
}
