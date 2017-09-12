package com.example.uuun.cbyapplication.bean;

/**
 * Created by uuun on 2017/6/1.
 */

public class TopSurveyBean {
    private String name;
    private String time;
    private String require;
    private String money;

    public TopSurveyBean(String name, String time, String require, String money) {
        this.name = name;
        this.time = time;
        this.require = require;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
