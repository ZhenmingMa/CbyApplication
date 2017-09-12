package com.example.uuun.cbyapplication.bean;

/**
 * Created by uuun on 2017/6/2.
 */

public class RecordBean {

    private String name;
    private String number;
    private String color;
    private String state;
    private String time;
    private int img;

    public RecordBean(String name, String number, String color, String state, String time, int img) {
        this.name = name;
        this.number = number;
        this.color = color;
        this.state = state;
        this.time = time;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}