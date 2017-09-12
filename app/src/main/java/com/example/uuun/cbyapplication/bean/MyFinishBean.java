package com.example.uuun.cbyapplication.bean;

/**
 * Created by uuun on 2017/6/29.
 */

public class MyFinishBean {
    private String name;
    private String money;

    public MyFinishBean(String name, String money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
