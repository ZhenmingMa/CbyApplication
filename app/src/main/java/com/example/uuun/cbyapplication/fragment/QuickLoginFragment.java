package com.example.uuun.cbyapplication.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.HomeActivity;
import com.example.uuun.cbyapplication.activity.RegisterActivity;
import com.example.uuun.cbyapplication.bean.User;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.CheckUtils;
import com.example.uuun.cbyapplication.utils.FirstEvent;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by uuun on 2017/6/19.
 * 快速登陆页面
 */

public class QuickLoginFragment extends Fragment {
    private View view;
    private TextView register,login,getcheckcode;
    private EditText et_phonenum,identify;
    private static final String TAG = "QuickLoginFragment";
    private boolean ready;
    int i = 30;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                getcheckcode.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                getcheckcode.setText("获取验证码");
                getcheckcode.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功

                        login(et_phonenum.getText().toString());

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getActivity(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "验证码有误",
                                Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG,"result==" + result);

                }
                if (result == SMSSDK.RESULT_ERROR) {

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        Toast.makeText(getActivity(), "验证失败请重新获取",
                                Toast.LENGTH_SHORT).show();
                        Log.e(TAG,((Throwable)data).getMessage());
                    }
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quicklogin,container,false);
        initPermission();
        initView();
        initControl();
        registerSDK();
        return view;
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        registerSDK();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ready) {
            // 销毁回调监听接口
            Log.e(TAG,"onPause");

            SMSSDK.unregisterEventHandler(eventHandler);
            ready = false;
        }
    }

    @Override
    public void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
            Log.e(TAG,"ondestory");
            SMSSDK.unregisterEventHandler(eventHandler);
            ready = false;
        }
        super.onDestroy();
    }

    private void initControl() {

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phonenum.getText().toString();
                if(TextUtils.isEmpty(phone)||! CheckUtils.isMobileNO(phone)){
                    Toast.makeText(getActivity(),"您输入的手机号格式不对!",Toast.LENGTH_SHORT).show();
                    return;
                }else {
//                    login(et_phonenum.getText().toString());
                    SMSSDK.submitVerificationCode("86", et_phonenum.getText().toString(), identify.getText().toString());
                }
            }
        });

        getcheckcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_phonenum.getText().toString();
                if(TextUtils.isEmpty(phone)||! CheckUtils.isMobileNO(phone)){
                    Toast.makeText(getActivity(),"您输入的手机号格式不对!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    SMSSDK.getVerificationCode("86", et_phonenum
                            .getText().toString());
                    getcheckcode.setClickable(false);
                    getcheckcode.setText("重新发送(" + i + ")");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; i > 0; i--) {
                                handler.sendEmptyMessage(-9);
                                if (i <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(-8);
                        }
                    }).start();
                }

            }
        });

    }
    private void initView() {
        register = (TextView) view.findViewById(R.id.quick_register);
        et_phonenum = (EditText) view.findViewById(R.id.quick_phone);
        login = (TextView) view.findViewById(R.id.quick_login);
        identify = (EditText) view.findViewById(R.id.quick_identify);
        getcheckcode = (TextView) view.findViewById(R.id.fragment_qlogin_getcheck);
    }

    private void login(String phone){
        YtfjrProcessDialog.showLoading(getActivity(),true);
        RequestParams params = new RequestParams(UrlConfig.URL_LOGINBYPHONE);
        params.addQueryStringParameter("phone",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String  result) {
                MyLog.info("登录信息"+result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("message");
                    String data = jsonObject.getString("data");
                    if (message.equals("成功")){
                        message = "登录成功";
                        Gson gson = new Gson();
                        User user = gson.fromJson(data,User.class);
                        SPUtil.setToken(getActivity(),user.getToken());
                        SPUtil.setTokenFlag(getActivity(),true);
                        MyApp.setCurrentUser(user);
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(
                                new FirstEvent("success"));
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }else
                    {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    MyLog.info(e.getMessage().toString());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyLog.info(ex.getMessage().toString());
                Toast.makeText(getActivity(), R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                YtfjrProcessDialog.showLoading(getActivity(),false);
            }
        });

    }
    EventHandler eventHandler;
    private void registerSDK() {
        SMSSDK.initSDK(getActivity(),"1fb7d87c0b110","63303fe529a625e379fcd408ff8d586e");
       // MobSDK.init(getActivity(),"1fb7d87c0b110","63303fe529a625e379fcd408ff8d586e");
         eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Log.e(TAG,"发送handle");
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        ready = true;
    }

    void initPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            int readPhone = getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int receiveSms = getActivity().checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            int readSms = getActivity().checkSelfPermission(Manifest.permission.READ_SMS);
            int readContacts = getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS);
            int readSdcard = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            int requestCode = 0;
            ArrayList<String> permissions = new ArrayList<String>();
            if (readPhone != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 0;
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (receiveSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 1;
                permissions.add(Manifest.permission.RECEIVE_SMS);
            }
            if (readSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 2;
                permissions.add(Manifest.permission.READ_SMS);
            }
            if (readContacts != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 3;
                permissions.add(Manifest.permission.READ_CONTACTS);
            }
            if (readSdcard != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 4;
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (requestCode > 0) {
                String[] permission = new String[permissions.size()];
                this.requestPermissions(permissions.toArray(permission), requestCode);
                return;
            }
        }
    }
}
