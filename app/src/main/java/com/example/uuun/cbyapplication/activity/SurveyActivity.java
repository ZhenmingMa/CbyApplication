package com.example.uuun.cbyapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.SurveyBean1;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.PermissionCompat;
import com.example.uuun.cbyapplication.utils.ShareUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.text.ShortMessage;


/**
 * 首页调查问卷二级页面
 */

public class SurveyActivity extends AppCompatActivity implements PermissionCompat.PermissionListener{
    private TextView name, time, number, reminder, sex, age, location, invite,state,endTime;
    private SurveyBean1.DataBean.ContentBean bean;
    private PopupWindow window;
    private String[] needPermission = new String[]{Manifest.permission.READ_CONTACTS
            ,Manifest.permission.WRITE_CONTACTS};
    PermissionCompat permission;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

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

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }

            private void showPop() {
                View view1 = View.inflate(SurveyActivity.this, R.layout.invite_popwindow, null);
                LinearLayout cancel = (LinearLayout) view1.findViewById(R.id.invite_cancle);
                LinearLayout copy = (LinearLayout) view1.findViewById(R.id.invite_copy);
                LinearLayout weChat = (LinearLayout) view1.findViewById(R.id.invite_weChat);
                LinearLayout phoneFriends = (LinearLayout) view1.findViewById(R.id.invite_phone);
                //创建popupwindow为全屏
                window = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);
                //设置动画,就是style里创建的那个j
                window.setAnimationStyle(R.style.take_photo_anim);
                window.showAtLocation(getContentView(SurveyActivity.this), Gravity.BOTTOM, 0, 0);
                //设置popupwindow的位置,这里直接放到屏幕上就行
                window.showAsDropDown(view1, 0, -WindowManager.LayoutParams.MATCH_PARENT);

                //可以点击外部消失
                window.setOutsideTouchable(true);
                //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
                window.setBackgroundDrawable(new ColorDrawable(0xb0000000));

                //为popwindow的每个item添加监听
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        window.dismiss();
                    }
                });
                copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText("分享内容啦啦啦啦");
                        Toast.makeText(SurveyActivity.this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                    }
                });

                phoneFriends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //分享至短信

                        Platform.ShareParams sp = new Platform.ShareParams();
                        sp.setText("测试分享的文本");
                        // sp.setImagePath(“/mnt/sdcard/测试分享的图片.jpg”);

                        Platform sm = ShareSDK.getPlatform(ShortMessage.NAME);
                        sm.setPlatformActionListener(new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {

                            }

                            @Override
                            public void onCancel(Platform platform, int i) {

                            }
                        });

                        sm.share(sp);

                    }
                });
            }
        });
    }
    private void initView() {
        name = (TextView) findViewById(R.id.survey_name);
        time = (TextView) findViewById(R.id.surver_time);
        sex = (TextView) findViewById(R.id.demand_sex1);
        age = (TextView) findViewById(R.id.demand_age1);
        location = (TextView) findViewById(R.id.demand_location1);
        number = (TextView) findViewById(R.id.surver_number);
        reminder = (TextView) findViewById(R.id.survey_reminder);
        invite = (TextView) findViewById(R.id.survey_invite);
        back = (ImageView) findViewById(R.id.survey_back);
        state = (TextView) findViewById(R.id.survey_state);
        endTime = (TextView) findViewById(R.id.survey_endTime);

        Intent intent = getIntent();
        bean = (SurveyBean1.DataBean.ContentBean) intent.getSerializableExtra("SurveyBean");

        // name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        name.setText(bean.getName());


        // if (bean.getCreateTime() != null)
        time.setText("发布时间:"+"2017-7-26");
        age.setText("要求:年龄:" + bean.getAge());
        if (bean.getSex() == 1)
            sex.setText(",性别:" + "男");
        else
            sex.setText(",性别:" + "女");
        location.setText(",地区:" + bean.getCity());
        number.setText("题量:" + bean.getQuestions() + "道");
        reminder.setText("剩余:" + bean.getCount() + "席位");
        state.setText("状态:"+"运行中");
        endTime.setText("截止时间:"+"2018-05-25");

        permission = new PermissionCompat(SurveyActivity.this,needPermission);
        permission.setPermissionListener(SurveyActivity.this);
        permission.request();
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.survey_bt:
                Intent intent = new Intent(this, SurvryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("surveybean",bean);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            case R.id.survey_share:
                ShareUtils.showShare(this);
                MyLog.info("!!!!!!!!!!!!!!!!!!!!!!");
                break;
        }
    }

    @Override
    public void onGranted() {

    }

    @Override
    public void onDenied() {

    }
    public static View getContentView(Activity ac){
        ViewGroup view = (ViewGroup)ac.getWindow().getDecorView();
        FrameLayout content = (FrameLayout)view.findViewById(android.R.id.content);
        return content.getChildAt(0);
    }
}
