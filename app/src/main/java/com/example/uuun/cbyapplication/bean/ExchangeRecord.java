package com.example.uuun.cbyapplication.bean;

import java.util.List;

/**
 * Created by Ma on 2017/7/4.
 */

public class ExchangeRecord {

    /**
     * code : 0
     * message : 成功
     * data : [{"exchangeRecord":{"id":1,"userId":"402894ef5cbe1f6d015cbe2002260000","goodsId":2,"status":"未发货","time":1499138319000},"goods":{"id":1,"img":"https://i1.mifile.cn/a1/pms_1490077058.71391368!220x220.png","name":"小米电视 4A 43英寸","time":"1498550453194","number":5,"price":5000}},{"exchangeRecord":{"id":2,"userId":"402894ef5cbe1f6d015cbe2002260000","goodsId":2,"status":"未发货","time":1499138325000},"goods":{"id":2,"img":"https://i1.mifile.cn/a1/pms_1490077058.71391368!220x220.png","name":"小米电视 4A 43英寸","time":"1498550453194","number":1,"price":10}},{"exchangeRecord":{"id":3,"userId":"402894ef5cbe1f6d015cbe2002260000","goodsId":2,"status":"未发货","time":1499138332000},"goods":{"id":3,"img":"https://i1.mifile.cn/a1/pms_1490077058.71391368!220x220.png","name":"小米电视 4A 43英寸","time":"1498550453194","number":2,"price":12000}},{"exchangeRecord":{"id":4,"userId":"402894ef5cbe1f6d015cbe2002260000","goodsId":2,"status":"未发货","time":1499138335000},"goods":{"id":4,"img":"https://i1.mifile.cn/a1/pms_1490077058.71391368!220x220.png","name":"小米电视 4A 43英寸","time":"1498550453194","number":0,"price":12000}}]
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

    public static class DataBean {
        /**
         * exchangeRecord : {"id":1,"userId":"402894ef5cbe1f6d015cbe2002260000","goodsId":2,"status":"未发货","time":1499138319000}
         * goods : {"id":1,"img":"https://i1.mifile.cn/a1/pms_1490077058.71391368!220x220.png","name":"小米电视 4A 43英寸","time":"1498550453194","number":5,"price":5000}
         */

        private ExchangeRecordBean exchangeRecord;
        private GoodsBean goods;

        public ExchangeRecordBean getExchangeRecord() {
            return exchangeRecord;
        }

        public void setExchangeRecord(ExchangeRecordBean exchangeRecord) {
            this.exchangeRecord = exchangeRecord;
        }

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public static class ExchangeRecordBean {
            /**
             * id : 1
             * userId : 402894ef5cbe1f6d015cbe2002260000
             * goodsId : 2
             * status : 未发货
             * time : 1499138319000
             */

            private int id;
            private String userId;
            private int goodsId;
            private String status;
            private long time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }
        }

        public static class GoodsBean {
            /**
             * id : 1
             * img : https://i1.mifile.cn/a1/pms_1490077058.71391368!220x220.png
             * name : 小米电视 4A 43英寸
             * time : 1498550453194
             * number : 5
             * price : 5000
             */

            private int id;
            private String img;
            private String name;
            private String time;
            private int number;
            private int price;

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
        }
    }
}
