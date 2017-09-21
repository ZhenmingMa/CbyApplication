package com.example.uuun.cbyapplication.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.LoginActivity;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.utils.FirstEvent;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.NiceSpinnerBaseAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * 我的奖金
 * Created by uuun on 2017/5/31.
 */

public class RewardFragment extends Fragment {
    private View view;
    private EditText et;
    private TextView mMoney;
    private String money = "20";
    private TextView getAlipay, allReward, answerReward, inviteReward,transferred,auditing;
    private RelativeLayout reward_1, reward_2;
    private NiceSpinner spinner;
    //private boolean flag = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reward, container, false);
        initView();
        initControl();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {

        if (MyApp.getCurrentUser() != null) {
            getData();
        }else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void getData() {
        RequestParams params = new RequestParams(UrlConfig.URL_GETREWARD);
        params.addQueryStringParameter("token", MyApp.currentUser.getToken());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String message = jsonObject.getString("message");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String count = data.getString("count");
                        String answer = data.getString("answer");
                        allReward.setText("￥ " +count);
                        answerReward.setText("￥ " + answer);
                        inviteReward.setText("￥ " + data.getString("invite"));
                        transferred.setText("￥ " + data.getString("transferred"));
                        auditing.setText("￥ " + data.getString("auditing"));

                    } else {
                        allReward.setText("￥ " +0);
                        answerReward.setText("￥ " + 0);
                        inviteReward.setText("￥ " + 0);
                        transferred.setText("￥ " + 0);
                        auditing.setText("￥ " + 0);
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

    private void initControl() {
        getAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et.getText().toString() == null || et.getText().toString().equals(""))
                    return;
                reward_1.setVisibility(View.GONE);
                reward_2.setVisibility(View.VISIBLE);
            }
        });
        //获取spinner里的字符串
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //flag = true;
                NiceSpinnerBaseAdapter adapter = (NiceSpinnerBaseAdapter) adapterView.getAdapter();
                money = adapter.getItemInDataset(i).toString();
                //Toast.makeText(getActivity(),item,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(),money,Toast.LENGTH_SHORT).show();
                RequestParams params = new RequestParams(UrlConfig.URL_WITHDRAW);
                params.addBodyParameter("token", SPUtil.getToken(getActivity()));
                params.addBodyParameter("count",money);
                params.addBodyParameter("alipay",et.getText().toString());
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("code");
                            String message = jsonObject.getString("message");
                            if (code == 0) {
                                getData();
                                Toast.makeText(getActivity(), "兑换成功", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
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
        });
    }

    private void initView() {
        et = (EditText) view.findViewById(R.id.reward_et);
        getAlipay = (TextView) view.findViewById(R.id.reward_getAlipay);
        allReward = (TextView) view.findViewById(R.id.fragrewad_allReward_tv);
        answerReward = (TextView) view.findViewById(R.id.fragrewad_answerBonus_tv);
        inviteReward = (TextView) view.findViewById(R.id.fragrewad_inviteBonus_tv);
        transferred = (TextView) view.findViewById(R.id.fragrewad_transferred_tv);
        auditing = (TextView) view.findViewById(R.id.fragrewad_auditing_tv);
        reward_1 = (RelativeLayout) view.findViewById(R.id.reward_1);
        reward_2 = (RelativeLayout) view.findViewById(R.id.reward_2);
        spinner = (NiceSpinner) view.findViewById(R.id.reward_spinner);
        mMoney = (TextView) view.findViewById(R.id.reward_aliPay);

        List<String> dataset = new LinkedList<>(Arrays.asList("20", "50", "70","100"));
        spinner.attachDataSource(dataset);

        EventBus.getDefault().register(this);

        allReward.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("exit")) {
            allReward.setText("￥ " +0);
            answerReward.setText("￥ " + 0);
            inviteReward.setText("￥ " + 0);
            transferred.setText("￥ " + 0);
            auditing.setText("￥ " + 0);
        }else{
            getData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
