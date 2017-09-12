package com.example.uuun.cbyapplication.bean;

/**
 * Created by Ma on 2017/8/8.
 */

public class APILocation {

    /**
     * msg : success
     * result : {"addr":"北京市朝阳区常营镇朝阳北路龙湖长楹天街购物中心,朝阳北路东约7米","cell":7994735,"googleLat":39.925778,"googleLng":116.604103,"id":"1_4111_7994735","lac":4111,"lat":39.92473042,"lng":116.59830631,"mcc":460,"mnc":1,"precision":564}
     * retCode : 200
     */

    private String msg;
    private ResultBean result;
    private String retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class ResultBean {
        /**
         * addr : 北京市朝阳区常营镇朝阳北路龙湖长楹天街购物中心,朝阳北路东约7米
         * cell : 7994735
         * googleLat : 39.925778
         * googleLng : 116.604103
         * id : 1_4111_7994735
         * lac : 4111
         * lat : 39.92473042
         * lng : 116.59830631
         * mcc : 460
         * mnc : 1
         * precision : 564
         */

        private String addr;
        private int cell;
        private double googleLat;
        private double googleLng;
        private String id;
        private int lac;
        private double lat;
        private double lng;
        private int mcc;
        private int mnc;
        private int precision;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getCell() {
            return cell;
        }

        public void setCell(int cell) {
            this.cell = cell;
        }

        public double getGoogleLat() {
            return googleLat;
        }

        public void setGoogleLat(double googleLat) {
            this.googleLat = googleLat;
        }

        public double getGoogleLng() {
            return googleLng;
        }

        public void setGoogleLng(double googleLng) {
            this.googleLng = googleLng;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLac() {
            return lac;
        }

        public void setLac(int lac) {
            this.lac = lac;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public int getMcc() {
            return mcc;
        }

        public void setMcc(int mcc) {
            this.mcc = mcc;
        }

        public int getMnc() {
            return mnc;
        }

        public void setMnc(int mnc) {
            this.mnc = mnc;
        }

        public int getPrecision() {
            return precision;
        }

        public void setPrecision(int precision) {
            this.precision = precision;
        }
    }
}
