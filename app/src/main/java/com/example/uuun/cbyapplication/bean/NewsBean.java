package com.example.uuun.cbyapplication.bean;

/**
 * Created by uuun on 2017/7/25.
 */

public class NewsBean {

    private String hello;//开头的几个字
    private String number;//支付宝账号
    private String money;
    private String result;//结果
    private String time;

    public NewsBean(String hello, String number, String money, String result, String time) {
        this.hello = hello;
        this.number = number;
        this.money = money;
        this.result = result;
        this.time = time;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
