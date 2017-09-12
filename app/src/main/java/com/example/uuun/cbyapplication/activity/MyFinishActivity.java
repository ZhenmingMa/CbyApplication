package com.example.uuun.cbyapplication.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.MyFinishAdapter;
import com.example.uuun.cbyapplication.bean.SurveyList;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 已完成的调研页面
 */
public class MyFinishActivity extends BaseActivity {
    private ListView lv;
    private MyFinishAdapter adapter;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_finish);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initControl();
        initData();
    }

    private void initData() {
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_GETUSERSURVEY);
        params.addBodyParameter("token", SPUtil.getToken(this));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    String data = json.getString("data");
                    Gson gson = new Gson();
                    SurveyList surveyList =  gson.fromJson(result, SurveyList.class);
                    MyLog.info(surveyList.getList().get(0).getName());
                    adapter.setList(surveyList.getList());
                    adapter.notifyDataSetChanged();

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
                YtfjrProcessDialog.showLoading(MyFinishActivity.this,false);
            }
        });
    }

    private void initControl() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.myfinish_lv);
        back = (ImageView) findViewById(R.id.myFinish_back);

        adapter = new MyFinishAdapter(this);
        lv.setAdapter(adapter);

    }
}
