package com.example.uuun.cbyapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 *
 */

public class SPUtil {
    private static SharedPreferences sp = null;
    private static void init(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences("user_message", Context.MODE_PRIVATE);
    }
    //存取用户基本信息
    public static void setUserName(Context context, String name) {
        init(context);
        sp.edit().putString("name", name).commit();
    }

    public static String getUserName(Context context) {
        init(context);
        return sp.getString("name", "");
    }
    public static void setUserPassWord(Context context, String password) {
        init(context);
        sp.edit().putString("password", password).commit();
    }

    public static String getUserPassWord(Context context) {
        init(context);
        return sp.getString("password", "");
    }

    public static void setToken(Context context,String token){
        init(context);
        sp.edit().putString("token",token).commit();
    }

    public static String getToken(Context context){
        init(context);
        return sp.getString("token",null);
    }
    //存取用户积分
    public static void setIntegration(Context context,int integration){
        init(context);
        sp.edit().putInt("integration",integration).commit();
    }

    public static int getIntegration(Context context){
        init(context);
        return sp.getInt("integration",0);
    }



    //存取是否点击退出登录的标记
    public static void setExitFlag(Context context,boolean flag){
        init(context);
        sp.edit().putBoolean("exitFlag",flag).commit();
    }

    public static boolean getExitFlag(Context context){
        init(context);
        return sp.getBoolean("exitFlag",false);
    }

    //存取是否有token的标记
    public static void setTokenFlag(Context context,boolean flag){
        init(context);
        sp.edit().putBoolean("tokenFlag",flag).commit();
    }

    public static boolean getTokenFlag(Context context){
        init(context);
        return sp.getBoolean("tokenFlag",false);
    }
    //存取是否是从添加地址进入到积分兑换页面
    public static void setAddressFlag(Context context,boolean flag){
        init(context);
        sp.edit().putBoolean("addressFlag",flag).commit();
    }

    public static boolean getAddressFlag(Context context){
        init(context);
        return sp.getBoolean("addressFlag",false);
    }
    //判断用户是不是第一次进入此app
    public static void setFirstFlag(Context context,boolean flag){
        init(context);
        sp.edit().putBoolean("firstFlag",flag).commit();
    }

    public static boolean getFirstFlag(Context context){
        init(context);
        return sp.getBoolean("firstFlag",false);
    }
    //存取ShopBean的信息,name
    public static void setShopName(Context context,String name){
        init(context);
        sp.edit().putString("shopName",name).commit();
    }

    public static String getShopName(Context context){
        init(context);
        return sp.getString("shopName",null);
    }
    //存取ShopBean的信息,color
    public static void setShopColor(Context context,String color){
        init(context);
        sp.edit().putString("shopColor",color).commit();
    }

    public static String getShopColor(Context context){
        init(context);
        return sp.getString("shopColor",null);
    }
    //存取ShopBean的信息,color
    public static void setShopNumber(Context context,String number){
        init(context);
        sp.edit().putString("shopNumber",number).commit();
    }

    public static String getShopNumber(Context context){
        init(context);
        return sp.getString("shopNumber",null);
    }
    //存取ShopBean的信息,img
    public static void setShopImg(Context context,String img){
        init(context);
        sp.edit().putString("shopImg",img).commit();
    }

    public static String getShopImg(Context context){
        init(context);
        return sp.getString("shopImg",null);
    }


}
