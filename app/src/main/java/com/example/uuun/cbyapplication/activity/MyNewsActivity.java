package com.example.uuun.cbyapplication.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.NewsAdapter;
import com.example.uuun.cbyapplication.bean.Message;
import com.example.uuun.cbyapplication.bean.NewsBean;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *我的消息页面
 */
public class MyNewsActivity extends BaseActivity {
    private ListView lv;
    private NewsAdapter adapter;
    private List<NewsBean>list;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initData();
        initControl();
        updateMessage();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


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
       lv = (ListView) findViewById(R.id.news_lv);
        back = (ImageView) findViewById(R.id.news_back);
        list = new ArrayList<>();
        for(int i=0;i<5;++i){
            list.add(new NewsBean("恭喜您","18848980000","20","成功","2017-7-25"));
        }

        adapter = new NewsAdapter(this);
//        adapter.setList(list);
        lv.setAdapter(adapter);
    }

    void initData(){
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_GETMESSAGE);
        params.addBodyParameter("token", SPUtil.getToken(MyNewsActivity.this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    if (code == 0){
                        String data = json.getString("data");
                        Gson gson = new Gson();
                        List<Message> list = new ArrayList<Message>();
                        Type type = new TypeToken<ArrayList<Message>>() {}.getType();
                        list =gson.fromJson(data,type);
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();

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
                YtfjrProcessDialog.showLoading(MyNewsActivity.this,false);
            }
        });
    }
    void updateMessage(){
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_UPDATEMESSAGE);
        params.addBodyParameter("token", SPUtil.getToken(MyNewsActivity.this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    if (code == 0){
                        MyLog.info("success");
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
                YtfjrProcessDialog.showLoading(MyNewsActivity.this,false);
            }
        });
    }
}
