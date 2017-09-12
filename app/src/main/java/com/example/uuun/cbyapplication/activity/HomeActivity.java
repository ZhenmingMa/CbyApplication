package com.example.uuun.cbyapplication.activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.fragment.AccountFragment;
import com.example.uuun.cbyapplication.fragment.MyFragment;
import com.example.uuun.cbyapplication.fragment.ShopFragment;
import com.example.uuun.cbyapplication.fragment.TopFragment;
import com.example.uuun.cbyapplication.utils.ActivityCollector;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.SystemInfoUtils;
import com.example.uuun.cbyapplication.utils.WindowUtil;

/**
 * 主页面!!!
 */
public class HomeActivity extends BaseActivity {
    private RadioGroup rg;
    private LinearLayout layout;
    private RadioButton buttons[];
    private TextView head;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment[] fgs = {new TopFragment(), new AccountFragment(), new ShopFragment(), new MyFragment()};
    private long exitTime;

    public static boolean tag = false; //显示我的账户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SystemInfoUtils systemInfoUtils = new SystemInfoUtils(this);

        ActivityCollector.addActivity(this);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //   MyLog.info("!!!!!!!!!!!!!!!!!"+SPUtil.getLookFlag(this));

        transaction.add(R.id.home_layout, fgs[0]).commit();

        for (int i = 1; i < 4; i++) {
            manager.beginTransaction().add(R.id.home_layout, fgs[i]).hide(fgs[i]).commit();
        }
        boolean tokenFlag = SPUtil.getTokenFlag(this);
        if (!tokenFlag) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        initView();
        initControl();
    }

    protected void initControl() {
        if (tag == true) {
            buttons[1].setTextColor(Color.parseColor("#0099FF"));
            buttons[1].setChecked(true);
            head.setText("我的账户");
            transaction.show(fgs[1]);
            transaction.hide(fgs[0]);
            transaction.hide(fgs[2]);
            transaction.hide(fgs[3]);
            buttons[1].setTextColor(Color.parseColor("#0099FF"));
            buttons[0].setTextColor(Color.parseColor("#898989"));
            buttons[2].setTextColor(Color.parseColor("#898989"));
            buttons[3].setTextColor(Color.parseColor("#898989"));
            tag = false;
        } else {
            buttons[0].setTextColor(Color.parseColor("#0099FF"));
            buttons[0].setChecked(true);
            head.setText("App名字");
            transaction.show(fgs[0]);
            transaction.hide(fgs[1]);
            transaction.hide(fgs[2]);
            transaction.hide(fgs[3]);
            buttons[0].setTextColor(Color.parseColor("#0099FF"));
            buttons[1].setTextColor(Color.parseColor("#898989"));
            buttons[2].setTextColor(Color.parseColor("#898989"));
            buttons[3].setTextColor(Color.parseColor("#898989"));
        }
    }

    protected void initRadioButton() {
        int[] imgs = {R.mipmap.home_home_checked, R.mipmap.home_account, R.mipmap.home_shop, R.mipmap.home_my};
        buttons = new RadioButton[imgs.length];
        for (int i = 0; i < buttons.length; ++i) {
            buttons[i] = (RadioButton) rg.getChildAt(i);
            buttons[i].setChecked(false);
            buttons[i].setTextColor(Color.parseColor("#898989"));
            Drawable page = getResources().getDrawable(imgs[i]);
            //1为上下距离,2为左右距离,3,4为宽高
            page.setBounds(0, 0, WindowUtil.dipTopx(this, 30), WindowUtil.dipTopx(this, 30));
            //设置在上方
            // buttons[i].setCompoundDrawables(null, page, null, null);
        }
        buttons[0].setTextColor(Color.parseColor("#0099FF"));
        buttons[0].setChecked(true);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                FragmentTransaction transaction = manager.beginTransaction();
                for (int i = 0; i < 4; i++) {
                    transaction.hide(fgs[i]);
                }
                switch (checkId) {
                    case R.id.home_rb1:
                        head.setText("App名字");
                        transaction.show(fgs[0]);
                        transaction.hide(fgs[1]);
                        transaction.hide(fgs[2]);
                        transaction.hide(fgs[3]);
                        buttons[0].setTextColor(Color.parseColor("#0099FF"));
                        buttons[1].setTextColor(Color.parseColor("#898989"));
                        buttons[2].setTextColor(Color.parseColor("#898989"));
                        buttons[3].setTextColor(Color.parseColor("#898989"));
                        break;
                    case R.id.home_rb2:
                        head.setText("我的账户");
                        transaction.show(fgs[1]);
                        transaction.hide(fgs[0]);
                        transaction.hide(fgs[2]);
                        transaction.hide(fgs[3]);
                        buttons[1].setTextColor(Color.parseColor("#0099FF"));
                        buttons[0].setTextColor(Color.parseColor("#898989"));
                        buttons[2].setTextColor(Color.parseColor("#898989"));
                        buttons[3].setTextColor(Color.parseColor("#898989"));
                        break;
                    case R.id.home_rb3:
                        head.setText("积分商城");
                        transaction.show(fgs[2]);
                        transaction.hide(fgs[1]);
                        transaction.hide(fgs[0]);
                        transaction.hide(fgs[3]);
                        buttons[2].setTextColor(Color.parseColor("#0099FF"));
                        buttons[1].setTextColor(Color.parseColor("#898989"));
                        buttons[0].setTextColor(Color.parseColor("#898989"));
                        buttons[3].setTextColor(Color.parseColor("#898989"));
                        break;
                    case R.id.home_rb4:
                        head.setText("个人中心");
                        transaction.show(fgs[3]);
                        transaction.hide(fgs[1]);
                        transaction.hide(fgs[2]);
                        transaction.hide(fgs[0]);
                        buttons[3].setTextColor(Color.parseColor("#0099FF"));
                        buttons[1].setTextColor(Color.parseColor("#898989"));
                        buttons[2].setTextColor(Color.parseColor("#898989"));
                        buttons[0].setTextColor(Color.parseColor("#898989"));
                        break;
                }
                transaction.commit();
            }
        });
    }

    protected void initView() {
        rg = (RadioGroup) findViewById(R.id.home_rg);
        layout = (LinearLayout) findViewById(R.id.home_layout);
        head = (TextView) findViewById(R.id.home_head);

        initRadioButton();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {// && event.getAction() ==
            // KeyEvent.ACTION_DOWN
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}


