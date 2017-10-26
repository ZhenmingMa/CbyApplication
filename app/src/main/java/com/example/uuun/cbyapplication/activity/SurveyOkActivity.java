package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.utils.ActivityCollector;
import com.example.uuun.cbyapplication.utils.ShareUtils;

/**
 * 问卷提交完成页面
 */

public class SurveyOkActivity extends BaseActivity {
    private TextView moneyGet, share, look, toTop;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_ok);


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
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.showShare(SurveyOkActivity.this);
            }
        });
        //去我的账户页面
        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SurveyOkActivity.this, HomeActivity.class);
                HomeActivity.tag = true;
                startActivity(intent);
                ActivityCollector.finishAll();
                finish();
            }
        });
        toTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SurveyOkActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        moneyGet = (TextView) findViewById(R.id.surveyok_money);
        share = (TextView) findViewById(R.id.surveyok_share);
        look = (TextView) findViewById(R.id.surveyok_look);
        toTop = (TextView) findViewById(R.id.surveyok_toTop);
        back = (ImageView) findViewById(R.id.surveyOk_back);
        Intent intent = getIntent();
        double money = intent.getDoubleExtra("msg", 0.0);
        moneyGet.setText("提交完成,恭喜获得" + money + "元");
        share.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
    }
}
