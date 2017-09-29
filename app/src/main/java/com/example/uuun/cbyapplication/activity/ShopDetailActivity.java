package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.ShopBean;
import com.example.uuun.cbyapplication.utils.UrlConfig;

/**
 * 商品详情页
 */
public class ShopDetailActivity extends AppCompatActivity {
    private ImageView back,img;
    private ShopBean.DataBean bean;
    private TextView integrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initControl();
    }

    private void initControl() {

        integrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopDetailActivity.this,IntegrateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopbean",bean);
                intent.putExtras(bundle);
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
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.detail_back);
        integrate = (TextView) findViewById(R.id.detail_integrate);
        img = (ImageView) findViewById(R.id.shopdetail_img);

        Intent intent = getIntent();
        bean = (ShopBean.DataBean) intent.getSerializableExtra("shopBean");
        Glide.with(this).load(UrlConfig.URL_BASE+bean.getDetailImg()).into(img);
    }
}
