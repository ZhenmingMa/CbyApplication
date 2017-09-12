package com.example.uuun.cbyapplication.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.LoadingAdapter;
import com.example.uuun.cbyapplication.bean.SurveyBean1;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.utils.MyLog;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * 未完成的调研页面
 */
public class MyLoadingActivity extends BaseActivity {
    private ListView lv;
    private LoadingAdapter adapter;
    private LinearLayout ll;
    private LinearLayout.LayoutParams layoutParams;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_loading);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initControl();
        mGetData();
    }


    private void initControl() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                          @Override
                                          public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                              int length = adapter.getList().size();
                                              for (int j = 0; j < length; ++j) {
                                                  View view1 = lv.getChildAt(j);
                                                  ll = (LinearLayout) view1.findViewById(R.id.loading_item_ll);
                                                  layoutParams = (LinearLayout.LayoutParams) ll.getLayoutParams();
                                                  layoutParams.setMargins(20, 0, 0, 0);
                                                  ll.setLayoutParams(layoutParams);
                                              }
                                              return true;
                                          }
                                      }
        );



    }

    private void initView() {
        lv = (ListView) findViewById(R.id.myloading_lv);
        back = (ImageView) findViewById(R.id.myLoading_back);

        adapter = new LoadingAdapter(this);
        lv.setAdapter(adapter);

    }

    private void mGetData() {
        DbManager db = x.getDb(((MyApp) getApplicationContext()).getDaoConfig());
        try {
            List<SurveyBean1.DataBean.ContentBean> list = db.findAll(SurveyBean1.DataBean.ContentBean.class);

            if (list != null) {
                for (SurveyBean1.DataBean.ContentBean s : list) {
                    MyLog.info(s.toString());
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();

            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

}
