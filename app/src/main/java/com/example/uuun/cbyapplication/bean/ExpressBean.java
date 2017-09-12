package com.example.uuun.cbyapplication.bean;

import java.util.List;

/**
 * Created by uuun on 2017/6/5.
 */

public class ExpressBean {
    private String state;
    private String company;
    private String number;
    private String phone;
    private List<ExpressDetailBean> expressDetail;

    public ExpressBean(String state, String company, String number, String phone, List<ExpressDetailBean> expressDetail) {
        this.state = state;
        this.company = company;
        this.number = number;
        this.phone = phone;
        this.expressDetail = expressDetail;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ExpressDetailBean> getExpressDetail() {
        return expressDetail;
    }

    public void setExpressDetail(List<ExpressDetailBean> expressDetail) {
        this.expressDetail = expressDetail;
    }
}