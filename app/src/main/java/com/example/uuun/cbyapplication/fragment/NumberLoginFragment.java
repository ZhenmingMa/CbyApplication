package com.example.uuun.cbyapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.ForgetActivity;
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

/**
 * Created by uuun on 2017/6/19.
 * 账号登陆页面
 */

public class NumberLoginFragment extends Fragment {
    private TextView forget,login,register;
    private EditText phone,password;
    private static final String TAG = "NumberLoginFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numberlogin,container,false);

//        boolean b = ((InputMethodManager) getActivity()
//                .getSystemService(Context.INPUT_METHOD_SERVICE))
//                .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken()
//                        , InputMethodManager.HIDE_NOT_ALWAYS);


        initView(view);
        initControl();

        return view;
    }

    private void initControl() {
        //跳转到忘记密码页面
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ForgetActivity.class);
                startActivity(intent);
            }
        });

        //解决edittext点叉叉被闪退的方法
//        phone.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_DEL) {
//                    num++;
//                    //在这里加判断的原因是点击一次软键盘的删除键,会触发两次回调事件
//                    if (num % 2 != 0) {
//                        String s = phone.getText().toString();
//                        if (!TextUtils.isEmpty(s)) {
//                            phone.setText("" + s.substring(0, s.length() - 1));
//                            //将光标移到最后
//                            phone.setSelection(phone.getText().length());
//                        }
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"denglu");
                String phoneNum = phone.getText().toString();
                String passWord = password.getText().toString();
                if(CheckUtils.isMobileNO(phoneNum)&&CheckUtils.isPassword(passWord)){
                    login(phoneNum,passWord);
                }else{
                    Toast.makeText(getActivity(),"您输入的手机号或密码的格式不正确!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //跳转到注册页面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void initView(View view) {
        forget = (TextView) view.findViewById(R.id.login_forget);
        phone = (EditText) view.findViewById(R.id.login_phone);
        password = (EditText) view.findViewById(R.id.login_password);
        login = (TextView) view.findViewById(R.id.login_login);
        register = (TextView) view.findViewById(R.id.login_register);
    }
    private void login(final String phone, final String password){
        YtfjrProcessDialog.showLoading(getActivity(),true);
        RequestParams params = new RequestParams(UrlConfig.URL_LOGIN);
        params.addQueryStringParameter("phone",phone);
        params.addQueryStringParameter("password",password);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String  result) {
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
}
