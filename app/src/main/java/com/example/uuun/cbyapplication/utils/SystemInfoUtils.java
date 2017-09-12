package com.example.uuun.cbyapplication.utils;

import android.content.Context;
import android.util.Log;

import com.example.uuun.cbyapplication.bean.APILocation;
import com.example.uuun.cbyapplication.bean.SystemInfo;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Ma on 2017/8/7.
 */

public class SystemInfoUtils {
    private MobileManager manager;
    private Context context;
    private static final String TAG = "SystemInfoUtils";
    private String addr;

    public SystemInfoUtils(Context context) {

        this.manager = MobileManager.getPhoneManager(context);
        this.context = context;
        getLocation();
    }

    public SystemInfo getSystemInfo(){
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setBrand(manager.getPhoneInfo().getPhoneName1());
        systemInfo.setModel(manager.getPhoneInfo().getPhoneModelName());
        systemInfo.setSystemVersion(manager.getSystemInfo().getPhoneSystemVersion());
        systemInfo.setSystemVersionSDK(manager.getSystemInfo().getPhoneSystemVersionSDK()+"");
        systemInfo.setCpuName(manager.getPhoneInfo().getPhoneCPUName());
        systemInfo.setCpuMinFreq(manager.getPhoneInfo().getPhoneCpuMinFreq());
        systemInfo.setCpuCurrentFreq(manager.getPhoneInfo().getPhoneCpuCurrentFreq());
        systemInfo.setCpuMaxFreq(manager.getPhoneInfo().getPhoneCpuMaxFreq());
        systemInfo.setCpuNumber(manager.getPhoneInfo().getPhoneCpuNumber()+"");

        systemInfo.setSNCode(manager.getPhoneInfo().getPhoneSNCode());
        systemInfo.setUUID(manager.getPhoneInfo().getPhoneIMEI());
        systemInfo.setMacAddress(manager.getWifiInfo().getPhoneWifiMac());
        systemInfo.setPhone(manager.getPhoneInfo().getPhoneNumber());
        systemInfo.setIMEICode(manager.getPhoneInfo().getPhoneIMEI());
        systemInfo.setPhoneTelSimName(manager.getPhoneInfo().getPhoneTelSimName());
        systemInfo.setWifeName(manager.getWifiInfo().getPhoneWifiName());
        systemInfo.setIPAddress(manager.getWifiInfo().getPhoneWifiIP());

        systemInfo.setBaiduLocation("北京朝阳");
        return systemInfo;
    }


    public void commit(SystemInfo systemInfo){
        Log.e(TAG, "commit:");
        RequestParams params = new RequestParams(UrlConfig.URL_SUBMITINFO);
        Gson gson = new Gson();
        String s = gson.toJson(systemInfo);
        params.addQueryStringParameter("systemInfo",s);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG,"success"+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG,"error"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private String getLocation(){
        String temp = manager.getPhoneInfo().getPhoneLocation();
        if (temp==null||"".equals("")){
            return null;
        }
        String[] strings = temp.substring(1,temp.length()-1).split(",");
        RequestParams params = new RequestParams("http://apicloud.mob.com/station/query");
        params.addQueryStringParameter("key","1fb7d87c0b110");
        params.addQueryStringParameter("mnc",manager.getPhoneInfo().getPhoneMNCCode());
        params.addQueryStringParameter("lac",strings[0]);
        params.addQueryStringParameter("cell",strings[1]);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                APILocation location = gson.fromJson(result,APILocation.class);
                addr =  location.getResult().getAddr();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished: 开始提交" );
                SystemInfo s = getSystemInfo();
                Log.e(TAG, "onFinished:"+s.getBrand() );
                s.setLocation(addr);
                commit(s);
            }
        });
        if (addr!=null){
            return addr;
        }else {
            return null;
        }
    }

}
