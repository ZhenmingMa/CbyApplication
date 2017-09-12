package com.example.uuun.cbyapplication.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.ExpressLvAdapter;
import com.example.uuun.cbyapplication.bean.ExpressBean;
import com.example.uuun.cbyapplication.bean.ExpressDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的账户里的礼物物流页面
 */
public class ExpressActivity extends BaseActivity {
    private ListView lv;
    private ExpressLvAdapter adapter;
    private List<ExpressBean> list;
    private List<ExpressDetailBean> listDetail;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initControl();
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
        lv = (ListView) findViewById(R.id.express_lv);
        back = (ImageView) findViewById(R.id.express_back);
        list = new ArrayList<>();
        listDetail = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            listDetail.add(new ExpressDetailBean("2017-8-2","15:52:47"
                    ,"【朝阳区】 您的订单正在配送途中" +
                    "，请您准备签收（配送员：刘吉超，联系电话56013227或15652622835）感谢您的耐心等待。"));
        }
        for (int i = 0; i < 20; ++i) {
            list.add(new ExpressBean("待收货", "圆通速递", "122222222222", "暂无", listDetail));
        }
        adapter = new ExpressLvAdapter(this);
        adapter.setList(list);
        lv.setAdapter(adapter);
    }

}
