package com.example.uuun.cbyapplication.bean;

/**
 * Created by uuun on 2017/5/19.
 */

public class ContactsBean {
    private String name;
    private String number;

    public ContactsBean(String name, String number) {
        this.name = name;
        this.number = number;
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
}
