package com.example.uuun.cbyapplication.bean;

import java.io.Serializable;

/**
 * Created by uuun on 2017/6/1.
 */

public class ShopBean implements Serializable{

    private Integer id; //商品ID

    private String img; //商品图片

    private String name;//商品名称

    private Long time;//开放购买时间

    private Integer number;//商品数量

    private Integer price;//商品价格

    public ShopBean(Integer id, String img, String name, Long time, Integer number, Integer price) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.time = time;
        this.number = number;
        this.price = price;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}