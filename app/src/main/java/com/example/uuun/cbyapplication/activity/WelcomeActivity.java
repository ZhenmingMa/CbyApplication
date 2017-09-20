package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.WelcomeVpAdapter;
import com.example.uuun.cbyapplication.bean.User;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.example.uuun.cbyapplication.myapp.MyApp.currentUser;
import static com.example.uuun.cbyapplication.myapp.MyApp.getInstance;

public class WelcomeActivity extends BaseActivity {
    private ViewPager vp;
    private int []icons = {R.mipmap.welcome1,R.mipmap.welcome2,R.mipmap.welcome3};
    private List<ImageView> images;
    private ImageView iv;
    private WelcomeVpAdapter adapter;
    private MyRunnable runnable = new MyRunnable();
    private int index = -1;
    private boolean firstFlag;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };
    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //handler.sendEmptyMessageDelayed(0,1000);
        myApp = getInstance();
        initView();
        initControl();
    }

    private void initControl() {
        //ll.setBackgroundResource(R.mipmap.welcome);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==(icons.length-1)){

                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //放这里,放前面的话可能viewpager没有初始化完成
        handler.post(runnable);
    }

    private void initView() {
        firstFlag = SPUtil.getFirstFlag(this);
        vp = (ViewPager) findViewById(R.id.welcome_vp);
        images = new ArrayList<>();
        for(int i=0;i<icons.length;++i){
            iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(icons[i]);
            images.add(iv);
        }
        if(!firstFlag){
            adapter = new WelcomeVpAdapter(images);
            vp.setAdapter(adapter);
            SPUtil.setFirstFlag(this,true);
        }else{
            myApp.setCurrentUser(checkStatus());
        }

    }

    /**
     * 检查是否登录
     * @return
     */
    public User checkStatus() {
        String token = SPUtil.getToken(this);
        if (token==null) {
            MyLog.info("未登录");
            currentUser = null;
            SPUtil.setExitFlag(this,false);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            SPUtil.setTokenFlag(getApplicationContext(),false);
        } else {
            RequestParams params = new RequestParams(UrlConfig.URL_CHECKSTATUS);
            params.addQueryStringParameter("token", token);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject json = new JSONObject(result);
                        int code = json.getInt("code");
                        String data = json.getString("data");
                        if (code==0) {
                            Gson gson = new Gson();
                            currentUser = gson.fromJson(data, User.class);
                            MyLog.info(currentUser.toString());
                            SPUtil.setTokenFlag(getApplicationContext(),true);
                            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            currentUser = null;
                            SPUtil.setExitFlag(getApplicationContext(),false);
                            SPUtil.setToken(getApplicationContext(),null);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"请检查您的网络",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

        }
        return currentUser;
    }
    class MyRunnable implements Runnable{

        @Override
        public void run() {
            if(index<=2){
                index++;
                //处理下标越界的问题
                //index = index%icons.length;

                vp.setCurrentItem(index);

                handler.postDelayed(runnable,2000);
            }

        }
    }
}
