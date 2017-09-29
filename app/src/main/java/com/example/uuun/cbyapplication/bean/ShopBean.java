package com.example.uuun.cbyapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uuun on 2017/6/1.
 */

public class ShopBean implements Serializable{
    /**
     * code : 0
     * message : 成功
     * data : [{"id":1,"img":"images/TIM截图20170927162228.png","detailImg":"/images/微信截图_20170927162236.png","name":"test","time":1505962964000,"number":3,"price":5,"createTime":1506567765000},{"id":3,"img":"images/b_388426052.jpg","detailImg":"/images/b_388426052.jpg","name":"test2","time":1505279851000,"number":11,"price":11,"createTime":1506575853000},{"id":4,"img":"images/68976310798640530.jpg","detailImg":"/images/829853767642307624.png","name":"小米手机","time":1506663328000,"number":2,"price":1000,"createTime":1506576941000}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * img : images/TIM截图20170927162228.png
         * detailImg : /images/微信截图_20170927162236.png
         * name : test
         * time : 1505962964000
         * number : 3
         * price : 5
         * createTime : 1506567765000
         */

        private int id;
        private String img;
        private String detailImg;
        private String name;
        private long time;
        private int number;
        private int price;
        private long createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDetailImg() {
            return detailImg;
        }

        public void setDetailImg(String detailImg) {
            this.detailImg = detailImg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }

//    private Integer id; //商品ID
//
//    private String img; //商品图片
//
//    private String detailImg;//商品详情
//
//    private String name;//商品名称
//
//    private Long time;//开放购买时间
//
//    private Integer number;//商品数量
//
//    private Integer price;//商品价格
//
//    private String createTime;
//
//    public ShopBean(Integer id, String img, String detailImg, String name, Long time, Integer number, Integer price, String createTime) {
//        this.id = id;
//        this.img = img;
//        this.detailImg = detailImg;
//        this.name = name;
//        this.time = time;
//        this.number = number;
//        this.price = price;
//        this.createTime = createTime;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
//
//    public String getDetailImg() {
//        return detailImg;
//    }
//
//    public void setDetailImg(String detailImg) {
//        this.detailImg = detailImg;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Long getTime() {
//        return time;
//    }
//
//    public void setTime(Long time) {
//        this.time = time;
//    }
//
//    public Integer getNumber() {
//        return number;
//    }
//
//    public void setNumber(Integer number) {
//        this.number = number;
//    }
//
//    public Integer getPrice() {
//        return price;
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;
//    }
//
//    public String getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }

}