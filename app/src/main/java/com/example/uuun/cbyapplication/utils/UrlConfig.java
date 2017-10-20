package com.example.uuun.cbyapplication.utils;

/**
 * Created by Ma on 2017/6/5.
 */

public class UrlConfig {

    //服务器地址
    public final static String URL_BASE = "http://101.200.53.112:8080";
//    public final static String URL_BASE = "http://192.168.3.27:8080";

    //注册接口
    public final static String URL_REGISIT = URL_BASE+"/regisit";

    //登录接口
    public final static String URL_LOGIN = URL_BASE+"/login";

    //通过手机验证登录
    public final static String URL_LOGINBYPHONE = URL_BASE+"/loginByPhone";

    //获取所有商品接口
    public final static String URL_GETALLGOODS = URL_BASE+"/getAllGoods";

    //兑换商品接口
    public final static String URL_EXCHANGEGOODS = URL_BASE+"/shop/exchangeGoods";

    //获取兑换记录接口
    public final static String URL_GETEXCHANGERECORD = URL_BASE+"/shop/getExchangeRecord";

    //删除兑换记录接口
    public final static String URL_DELETEEXCHANGERECORD = URL_BASE+"/shop/deleteExchangeRecord";

    //获取所有的收货地址
    public final static String URL_GETALLADDRESS = URL_BASE+"/getAllAddress";

    //添加收获地址
    public final static String URL_ADDADDRESS = URL_BASE+"/addAddress";

    //删除收货地址
    public final static String URL_DELETEADDRESS = URL_BASE+"/deleteAddress";

    //更新收货地址
    public final static String URL_UPDATEADDRESS = URL_BASE+"/updateAddress";

    //检查登录状态
    public final static String URL_CHECKSTATUS = URL_BASE+"/checkStatus";

    //获取所有问卷
    public final static String URL_GETSURVEY = URL_BASE+"/getAllSurvey";

    //获取问卷的问题
    public final static String URL_GETQUESTION = URL_BASE+"/getQuestion";

    //提交问卷
    public final static String URL_COMMITSURVEY = URL_BASE+"/commitSurvey";

    //获取用户提交的问卷
    public final static String URL_GETUSERSURVEY = URL_BASE+"/getUserSurvey";

    //获取我的积分
    public final static String URL_GETINTEGRATION = URL_BASE+"/getMyPoint";

    //获取我的奖金
    public final static String URL_GETREWARD = URL_BASE+"/getMyBonus";

    //支付宝提现
    public final static String URL_WITHDRAW = URL_BASE+"/withdraw";

    //设置默认地址
    public final static String URL_SETDEFAULTADD = URL_BASE+"/setDefaultAddress";

    //合作申请
    public final static String URL_APPLY = URL_BASE+"/apply";

    //忘记密码
    public final static String URL_FORGETPASS = URL_BASE+"/forgetPass";

    //修改密码
    public final static String URL_UPDATEPASS = URL_BASE+"/updatePass";

    //获取消息
    public final static String URL_GETMESSAGE = URL_BASE+"/getMessage";

    //阅读消息
    public final static String URL_UPDATEMESSAGE = URL_BASE+"/updateMessage";

    //阅读消息
    public final static String URL_SUBMITINFO = URL_BASE+"/submitInfo";

}
