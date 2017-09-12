package com.example.uuun.cbyapplication.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.RecordLvAdapter;
import com.example.uuun.cbyapplication.bean.ExchangeRecord;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 我的账户里的兑换记录页面
 */
public class IntegrateRecordActivity extends BaseActivity {
    private ListView lv;
    private RecordLvAdapter adapter;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrate_record);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initControl();
        initData();
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
        lv = (ListView) findViewById(R.id.record_lv);
        back = (ImageView) findViewById(R.id.integrate_record_back);

        adapter = new RecordLvAdapter(this);
        lv.setAdapter(adapter);
    }
    private void initData(){
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_GETEXCHANGERECORD);
        params.addBodyParameter("token", SPUtil.getToken(this));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ExchangeRecord exchangeRecord =gson.fromJson(result, ExchangeRecord.class);
                List<ExchangeRecord.DataBean> list = exchangeRecord.getData();
                if(list!=null){
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();

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
                YtfjrProcessDialog.showLoading(IntegrateRecordActivity.this,false);
            }
        });

    }
}
