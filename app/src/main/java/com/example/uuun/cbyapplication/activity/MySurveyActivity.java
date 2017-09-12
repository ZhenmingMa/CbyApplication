package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.SurveyLvAdapter;
import com.example.uuun.cbyapplication.bean.QuestionOptions;
import com.example.uuun.cbyapplication.bean.ResultQuestionList;
import com.example.uuun.cbyapplication.bean.ResultQuestions;
import com.example.uuun.cbyapplication.bean.SurveyBean;
import com.example.uuun.cbyapplication.bean.SurveyRecord;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.utils.FirstEvent;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 调查问卷页面
 */
public class MySurveyActivity extends BaseActivity implements SurveyLvAdapter.GetAnswer {
    private SurveyLvAdapter adapter;
    private ListView lv;
    private TextView invite;
    private int surveyId;
    private List<SurveyRecord> list = new ArrayList<>();
    private boolean tag = false; //是否提交
    private String saveSurvey;
    private SurveyBean bean;
    private DbManager db;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_survey);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initData();
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
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(SPUtil.getTokenFlag(this)){//判断用户是否登录状态
            //如果未提交,则保存
            if (tag == false) {
                try {
                    SurveyBean surveyBean = db.findById(SurveyBean.class, bean.getId());
                    if (surveyBean == null) {
                        EventBus.getDefault().post(
                                new FirstEvent("visible"));
                        Set set = new HashSet();
                        for (SurveyRecord surveyRecord : list) {
                            set.add(surveyRecord.getQuestionId());
                        }
                        bean.setQuestions(bean.getQuestions() - set.size());
                        MyLog.info(bean.toString());
                        db.save(bean);
                    }else
                    {
                        Set set = new HashSet();
                        for (SurveyRecord surveyRecord : list) {
                            set.add(surveyRecord.getQuestionId());
                        }
                        bean.setQuestions(bean.getQuestions() - set.size());
                        MyLog.info(bean.toString());
                        db.update(bean);
                    }
                    MyLog.info("123213");
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private void initView() {
        lv = (ListView) findViewById(R.id.survey_lv);
        back = (ImageView) findViewById(R.id.mysurvey_back);

        invite = (TextView) findViewById(R.id.my_survey_invite);
        Intent intent = getIntent();
        bean = (SurveyBean) intent.getSerializableExtra("surveybean");
        surveyId = bean.getId();
        adapter = new SurveyLvAdapter(MySurveyActivity.this, this);
        lv.setAdapter(adapter);
        db = x.getDb(((MyApp) getApplicationContext()).getDaoConfig());
    }

    private void showPop() {
        View view1 = View.inflate(MySurveyActivity.this, R.layout.invite_popwindow, null);
        LinearLayout cancel = (LinearLayout) view1.findViewById(R.id.invite_cancle);
        LinearLayout copy = (LinearLayout) view1.findViewById(R.id.invite_copy);
        LinearLayout weChat = (LinearLayout) view1.findViewById(R.id.invite_weChat);
        LinearLayout phoneFriends = (LinearLayout) view1.findViewById(R.id.invite_phone);
        //创建popupwindow为全屏
        final PopupWindow window = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        //设置动画,就是style里创建的那个j
        window.setAnimationStyle(R.style.take_photo_anim);
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
    }

    //问卷提交按钮!!!!
    public void commit(View view) {

        RequestParams params = new RequestParams(UrlConfig.URL_COMMITSURVEY);
        Gson gson = new Gson();
        String s = gson.toJson(list);
        MyLog.info(s);
        params.addQueryStringParameter("list", s);
        params.addBodyParameter("token", SPUtil.getToken(MySurveyActivity.this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    SurveyBean surveyBean = null;
                    try {
                        surveyBean = db.findById(SurveyBean.class, bean.getId());
                        if (surveyBean != null) {
                            db.delete(surveyBean);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonObject = new JSONObject(result);
                    //MyLog.info("!!!!!!!!!!!!!!!!"+result+"!!!!!!!!!");
                    double bonus = jsonObject.getDouble("data");
                   Toast.makeText(MySurveyActivity.this, "!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                    //MyLog.info(bonus + "");
                    Toast.makeText(MySurveyActivity.this, "success", Toast.LENGTH_SHORT).show();
                    tag = true;
                    Intent intent = new Intent(MySurveyActivity.this, SurveyOkActivity.class);
                    intent.putExtra("msg", bonus);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initData() {
        RequestParams params = new RequestParams(UrlConfig.URL_GETQUESTION);
        params.addQueryStringParameter("surveyId", surveyId + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MyLog.info(result);
                Gson gson = new Gson();
                saveSurvey = result;
                ResultQuestionList RQL = gson.fromJson(result, ResultQuestionList.class);
                List<ResultQuestions> list = RQL.getData();
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                View view = LayoutInflater.from(MySurveyActivity.this).inflate(R.layout.survey_lv_bottom, null);
                lv.addFooterView(view);
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyLog.info(ex.getMessage().toString());
                Toast.makeText(MySurveyActivity.this, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void getAnswer(QuestionOptions questionOptions) {
        SurveyRecord surveyRecord = new SurveyRecord();
        surveyRecord.setChecked(true);
        surveyRecord.setTime(System.currentTimeMillis());
        surveyRecord.setCustom(questionOptions.getCustom());
        surveyRecord.setOptionId(questionOptions.getId());
        surveyRecord.setSurveyId(surveyId);
        surveyRecord.setUserId(MyApp.getCurrentUser().getId());
        surveyRecord.setQuestionId((questionOptions.getQuestionId()));
        MyLog.info(questionOptions.getQuestionId() + "");
        list.add(surveyRecord);
    }
}