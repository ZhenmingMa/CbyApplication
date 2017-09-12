package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;

/**
 * 密码修改完成页面
 */
public class ReviseOkActivity extends BaseActivity {
    private TextView toLogin;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_ok);

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

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviseOkActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        toLogin = (TextView) findViewById(R.id.reviseok_tologin);
        back = (ImageView) findViewById(R.id.reviseOk_back);
    }
}
