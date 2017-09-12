package com.example.uuun.cbyapplication.bean;

/**
 * Created by uuun on 2017/6/1.
 */

public class AddressBean {
    private String name;
    private String phone;
    private String province;
    private String city;
    private String detail;

    public AddressBean(String name, String phone, String province, String city, String detail) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
