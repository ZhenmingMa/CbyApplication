package com.example.uuun.cbyapplication.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.ExpressActivity;
import com.example.uuun.cbyapplication.activity.IntegrateAddressActivity;
import com.example.uuun.cbyapplication.activity.IntegrateChangeActivity;
import com.example.uuun.cbyapplication.activity.IntegrateRecordActivity;
import com.example.uuun.cbyapplication.activity.LoginActivity;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.utils.FirstEvent;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * 我的积分
 * Created by uuun on 2017/5/31.
 * 我的积分页面
 */

public class IntegrationFragment extends Fragment {
    private View view;
    private RelativeLayout address,record,change,express;
    private TextView num,loginNum,shareNum,integrate_get;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                int[] a = (int[]) msg.obj;
                num.setText(a[0]+" 积分");
                loginNum.setText(a[1]+" 积分");
                shareNum.setText(a[2]+" 积分");
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_integration, container, false);
        initView();
        initControl();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApp.getCurrentUser() != null) {
            getData();
        }else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initControl() {
        //跳转到兑换地址界面
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),IntegrateAddressActivity.class);
                startActivity(intent);
            }
        });
        //跳转到兑换记录界面
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),IntegrateRecordActivity.class);
                startActivity(intent);
            }
        });
        //跳转到积分商城页面
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),IntegrateChangeActivity.class);
                startActivity(intent);


            }
        });
        //跳转到礼物物流页面
        express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ExpressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        address = (RelativeLayout) view.findViewById(R.id.integration_address);
        record = (RelativeLayout) view.findViewById(R.id.integration_record_rl);
        change = (RelativeLayout) view.findViewById(R.id.integration_change);
        express = (RelativeLayout) view.findViewById(R.id.integration_express);
        num = (TextView) view.findViewById(R.id.integration_num);
        loginNum = (TextView) view.findViewById(R.id.integration_login);
        shareNum = (TextView) view.findViewById(R.id.integration_share);
        integrate_get = (TextView) view.findViewById(R.id.integration_get);
        EventBus.getDefault().register(this);

        num.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private void getData() {
        RequestParams params = new RequestParams(UrlConfig.URL_GETINTEGRATION);
        params.addQueryStringParameter("token", SPUtil.getToken(getActivity()));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String message = jsonObject.getString("message");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        int count = data.getInt("count");
                        int login = data.getInt("login");
                        int share = data.getInt("share");
                        MyLog.info(count+" "+login+""+" "+share);
                        SPUtil.setIntegration(getActivity(),count);
                        Message msg = new Message();
                        msg.what = 1;
                        int[] a = new int[3];
                        a[0] = count;
                        a[1] = login;
                        a[2] = share;
                        msg.obj = a;
                        handler.sendMessage(msg);

                    } else {
                        num.setText("0"+" 积分");
                        loginNum.setText("0"+" 积分");
                        shareNum.setText("0"+" 积分");
                    }
                    MyLog.info(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("exit")) {
            num.setText("0"+" 积分");
            loginNum.setText("0"+" 积分");
            shareNum.setText("0"+" 积分");
        }else
        {
            getData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
