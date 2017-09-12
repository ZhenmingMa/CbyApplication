package com.example.uuun.cbyapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.CheckUtils;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 忘记密码页面
 */
public class ForgetActivity extends BaseActivity {
    ImageView back;
    EditText et_phonenum,password,identify;
    TextView getcheckcode,submit_tv;
    private static final String TAG = "ForgetActivity";
    private boolean ready;
    int i = 30;
    EventHandler eventHandler;
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
                        Log.e(TAG, "提交成功");

                        reset(et_phonenum.getText().toString(),password.getText().toString());


                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.e(TAG, "验证码已经发送");
                        Toast.makeText(ForgetActivity.this, "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "验证码有误");

                        Toast.makeText(ForgetActivity.this, "验证码有误",
                                Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "result==" + result);

                }
                if (result == SMSSDK.RESULT_ERROR) {

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Toast.makeText(ForgetActivity.this, "验证失败请重新获取",
                                Toast.LENGTH_SHORT).show();
                        Log.e(TAG, ((Throwable) data).getMessage());
                    }
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initControl();
        registerSDK();
    }
    @Override
    public void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
            SMSSDK.unregisterEventHandler(eventHandler);
        }
        super.onDestroy();
    }
    private void initControl() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getcheckcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"点击获取验证码");
                String phone = et_phonenum.getText().toString();
                if (TextUtils.isEmpty(phone) || !CheckUtils.isMobileNO(phone)) {
                    Toast.makeText(ForgetActivity.this, "您输入的手机号格式不对!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
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
        submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"点击了确定");
                if (TextUtils.isEmpty(et_phonenum.getText())) {
                    Toast.makeText(ForgetActivity.this, "手机号不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CheckUtils.isMobileNO(et_phonenum.getText().toString())) {
                    Toast.makeText(ForgetActivity.this, "您输入的手机号格式不正确!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(identify.getText())) {
                    Toast.makeText(ForgetActivity.this, "验证码不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(ForgetActivity.this, "密码不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CheckUtils.isPassword(password.getText().toString())) {
                    Toast.makeText(ForgetActivity.this, "密码要求大于6位,小于16位,没有特殊字符", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e(TAG, "点击了注册按钮");
                SMSSDK.submitVerificationCode("86", et_phonenum.getText().toString(), identify.getText().toString());
            }
        });

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.forget_back);
        et_phonenum = (EditText) findViewById(R.id.activity_forget_phone_et);
        identify = (EditText) findViewById(R.id.activity_forget_identifit_et);
        password = (EditText) findViewById(R.id.activity_forget_password_et);
        submit_tv = (TextView) findViewById(R.id.activity_forget_submit_tv);
        getcheckcode = (TextView) findViewById(R.id.activity_forget_getcheckcode_tv);
    }

    private void registerSDK() {
        Log.e(TAG, "注册了mobsdk");
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Log.e(TAG, "发送handle");
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

    void reset(String phone,String password){
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_FORGETPASS);
        params.addBodyParameter("phone",phone);
        params.addBodyParameter("password",password);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    String message = json.getString("message");
                    if (code == 0){
                        Toast.makeText(ForgetActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                        YtfjrProcessDialog.showLoading(ForgetActivity.this,false);
                        finish();
                    }else {
                        Toast.makeText(ForgetActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ForgetActivity.this, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
