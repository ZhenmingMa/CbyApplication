package com.example.uuun.cbyapplication.activity;


import android.content.Context;
import android.content.Intent;
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
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.ActivityCollector;
import com.example.uuun.cbyapplication.utils.CheckUtils;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity {
    private EditText et_phonenum, identify, password;
    private TextView register, login, getcheckcode;
    private Context context = MyApp.getInstance();
    private ImageView back;
    private static final String TAG = "RegisterActivity";
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
                        Log.e(TAG, "提交成功");
//                        register(et_phonenum.getText().toString(), password.getText().toString());
                        checkaccount(et_phonenum.getText().toString());


                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.e(TAG, "验证码已经发送");
                        Toast.makeText(RegisterActivity.this, "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "验证码有误");

                        Toast.makeText(RegisterActivity.this, "验证码有误",
                                Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "result==" + result);

                }
                if (result == SMSSDK.RESULT_ERROR) {

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Toast.makeText(RegisterActivity.this, "验证失败请重新获取",
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
        setContentView(R.layout.activity_register);
        ActivityCollector.addActivity(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initControl();
        registerSDK();
    }

    EventHandler eventHandler2;

    private void registerSDK() {
        Log.e(TAG, "注册了mobsdk");
        eventHandler2 = new EventHandler() {
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
        SMSSDK.registerEventHandler(eventHandler2);
        ready = true;
    }

    @Override
    public void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
            SMSSDK.unregisterEventHandler(eventHandler2);
        }
        super.onDestroy();
    }

    private void initControl() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(et_phonenum.getText())) {
                    Toast.makeText(RegisterActivity.this, "手机号不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CheckUtils.isMobileNO(et_phonenum.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "您输入的手机号格式不正确!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(identify.getText())) {
                    Toast.makeText(RegisterActivity.this, "验证码不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空~", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CheckUtils.isPassword(password.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "密码要求大于6位,小于16位,没有特殊字符", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e(TAG, "点击了注册按钮");
                SMSSDK.submitVerificationCode("86", et_phonenum.getText().toString(), identify.getText().toString());


            }


        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getcheckcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_phonenum.getText().toString();
                if (TextUtils.isEmpty(phone) || !CheckUtils.isMobileNO(phone)) {
                    Toast.makeText(RegisterActivity.this, "您输入的手机号格式不对!", Toast.LENGTH_SHORT).show();
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
    }

    private void initView() {
        et_phonenum = (EditText) findViewById(R.id.register_phone);
        identify = (EditText) findViewById(R.id.register_identify);
        password = (EditText) findViewById(R.id.register_password);
        register = (TextView) findViewById(R.id.register_register);
        login = (TextView) findViewById(R.id.register_login);
        back = (ImageView) findViewById(R.id.register_back);
        getcheckcode = (TextView) findViewById(R.id.activity_regisiter_getCheckCode);

    }

    //注册方法
    private void checkaccount(String phone) {
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_LOGINBYPHONE);
        params.addQueryStringParameter("phone", phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("message");
                    String data = jsonObject.getString("data");
                    int code = jsonObject.getInt("code");
                    if (code==102) {
                        Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
                        String phone = et_phonenum.getText().toString();
                        String passName = password.getText().toString();
                        String[] strs = new String[]{phone, passName};
                        intent.putExtra("msg", strs);
                        startActivity(intent);
                    } else if(code ==0) {
                        Toast.makeText(RegisterActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegisterActivity.this, "请联系客服", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    MyLog.info(e.getMessage().toString());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyLog.info(ex.getMessage().toString());
                Toast.makeText(RegisterActivity.this, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                YtfjrProcessDialog.showLoading(RegisterActivity.this,false);
            }
        });

    }


}
