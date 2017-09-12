/**
  * Copyright 2017 bejson.com 
  */
package com.example.uuun.cbyapplication.bean;

import java.io.Serializable;

/**
 * Auto-generated: 2017-06-08 16:35:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class User implements Serializable {

    private String id;
    private String token;
    private long phone;
    private String password;
    private Long time;
    private Long birthday;
    private String location;
    private String occupation;
    private String income;
    private String hobby;
    private String sex;

    public User() {
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                ", time=" + time +
                ", birthday=" + birthday +
                ", location='" + location + '\'' +
                ", occupation='" + occupation + '\'' +
                ", income='" + income + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}