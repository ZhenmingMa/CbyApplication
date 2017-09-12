package com.example.uuun.cbyapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.AccountVpAdapter;
import com.example.uuun.cbyapplication.fragment.NumberLoginFragment;
import com.example.uuun.cbyapplication.fragment.QuickLoginFragment;
import com.example.uuun.cbyapplication.utils.ActivityCollector;
import com.example.uuun.cbyapplication.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆页面
 */

public class LoginActivity extends BaseActivity {

    private TabLayout tab;
    private ViewPager vp;
    private List<Fragment> list;
    private List<String> listName;
    private AccountVpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



            ActivityCollector.addActivity(this);
            initView();
            initControl();

    }

    private void initControl() {
        
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.login_tabLayout);
        vp = (ViewPager) findViewById(R.id.login_vp);

        tab.setSelectedTabIndicatorColor(Color.parseColor("#0099FF"));
        tab.setSelectedTabIndicatorHeight(5);
        tab.setMinimumWidth(100);
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabTextColors(Color.BLACK, Color.parseColor("#0099FF"));

        listName = new ArrayList<>();
        listName.add("账号登陆");
        listName.add("快速登录");

        list = new ArrayList<>();
        list.add(new NumberLoginFragment());
        list.add(new QuickLoginFragment());

        adapter = new AccountVpAdapter(getSupportFragmentManager(), list, listName);
        tab.setupWithViewPager(vp);
        vp.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_DEL) {
            if (!SPUtil.getExitFlag(this)) {//如果不是点击退出登录再登录
                ActivityCollector.finishAll();
                System.exit(0);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
