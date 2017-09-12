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


    //保存未提交的问卷
    public static void setSurvey(Context context,String survey){
        init(context);
        sp.edit().putString("unDoSurvey",survey).commit();
    }

    public static String getSurvey(Context context){
        init(context);
        return sp.getString("unDoSurvey",null);
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

    //存取是否是从完成答卷页面进入到查看奖励页面
    public static void setLookFlag(Context context,boolean flag){
        init(context);
        sp.edit().putBoolean("lookFlag",flag).commit();
    }

    public static boolean getLookFlag(Context context){
        init(context);
        return sp.getBoolean("lookFlag",false);
    }

    //存取是否是第一次进入消息页面
    public static void setNewsFlag(Context context,boolean flag){
        init(context);
        sp.edit().putBoolean("NewsFlag",flag).commit();
    }

    public static boolean getNewsFlag(Context context){
        init(context);
        return sp.getBoolean("NewsFlag",false);
    }
    /**
     * 针对复杂类型存储<对象>
     *
     */
    public static void setObject(Object object,Context context) {
        SharedPreferences sp = context.getSharedPreferences("unDoSurvey", Context.MODE_PRIVATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {

            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("unDoSurvey", objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static  <T> T getObject(Context context) {
        SharedPreferences sp = context.getSharedPreferences("unDoSurvey", Context.MODE_PRIVATE);
        if (sp.contains("unDoSurvey")) {
            String objectVal = sp.getString("unDoSurvey", null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
