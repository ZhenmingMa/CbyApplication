package com.example.uuun.cbyapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.ShopRvAdapter;
import com.example.uuun.cbyapplication.bean.ShopBean;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.myview.MyRecyclerView;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分商城页面
 */
public class IntegrateChangeActivity extends BaseActivity {
    private RecyclerView rv, rv_down;
    private ShopRvAdapter adapter;
    private List<ShopBean.DataBean> list = new ArrayList<>();
    private ImageView back;
    private Context context = MyApp.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrate_change);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initData();
        initControl();
    }

    private void initControl() {
        adapter.setOnItemClickListener(new ShopRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(IntegrateChangeActivity.this,ShopDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopBean",list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        rv = (MyRecyclerView) findViewById(R.id.shop_recyclerView);
        rv_down = (MyRecyclerView) findViewById(R.id.shop_recyclerView_down);
        back = (ImageView) findViewById(R.id.change_back);

        adapter = new ShopRvAdapter(context);
        rv.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
        manager.setSmoothScrollbarEnabled(true);
        rv.setLayoutManager(manager);
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);//这两行让recyclerview滑动更流畅
        rv.setFocusable(false);

        rv_down.setAdapter(adapter);
        GridLayoutManager manager1 = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
        rv_down.setLayoutManager(manager1);
        rv_down.setHasFixedSize(true);
        rv_down.setNestedScrollingEnabled(false);//这两行让recyclerview滑动更流畅
        rv_down.setFocusable(false);

    }

    //获取商品数据
    private void initData() {
        MyLog.info(UrlConfig.URL_GETALLGOODS);
        RequestParams params = new RequestParams(UrlConfig.URL_GETALLGOODS);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MyLog.info(result);

                MyLog.info(System.currentTimeMillis()+"");
                Gson gson = new Gson();
                ShopBean shopBean = gson.fromJson(result, ShopBean.class);
                List<ShopBean.DataBean> data = shopBean.getData();
                list.addAll(data);
                adapter.setList(list);
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(context, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
                MyLog.info(ex.getMessage());
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
