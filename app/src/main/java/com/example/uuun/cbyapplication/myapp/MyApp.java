package com.example.uuun.cbyapplication.myapp;

import android.app.Application;

import com.example.uuun.cbyapplication.bean.User;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.xutils.DbManager;
import org.xutils.x;


/**
 * Created by uuun on 2017/6/2.
 */

public class MyApp extends Application {
    private static MyApp instance;
    public static User currentUser;
    private DbManager.DaoConfig daoConfig;

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
        daoConfig = new DbManager.DaoConfig()
                .setDbName("lyj_db.db")//创建数据库的名称
                .setDbVersion(1)//数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });//数据库更新操作

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        MyLog.info("开始注册推送服务");
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                MyLog.info("deviceToken=="+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                MyLog.info("s=="+s);
                MyLog.info("s1=="+s1);
            }
        });


    }

    public static MyApp getInstance() {
        // 因为我们程序运行后，Application是首先初始化的，如果在这里不用判断instance是否为空
        return instance;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MyApp.currentUser = currentUser;
    }


    /**
     * 检查是否登录
     * @return
     */
//    public  User checkStatus() {
//        String token = SPUtil.getToken(this);
//        if (token==null) {
//            MyLog.info("未登录");
//            currentUser = null;
//
//        } else {
//            RequestParams params = new RequestParams(UrlConfig.URL_CHECKSTATUS);
//            params.addQueryStringParameter("token", token);
//            x.http().post(params, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    try {
//                        JSONObject json = new JSONObject(result);
//                        int code = json.getInt("code");
//                        String data = json.getString("data");
//                        if (code==0) {
//                            Gson gson = new Gson();
//                            currentUser = gson.fromJson(data, User.class);
//                            MyLog.info(currentUser.toString());
//                            SPUtil.setTokenFlag(getApplicationContext(),true);
//                        } else {
//                            currentUser = null;
//                            SPUtil.setExitFlag(getApplicationContext(),false);
//                            SPUtil.setToken(getApplicationContext(),null);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
//
//        }
//        return currentUser;
//    }
}
