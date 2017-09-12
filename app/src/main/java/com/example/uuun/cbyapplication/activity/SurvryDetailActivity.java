package com.example.uuun.cbyapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.QuestionOptions;
import com.example.uuun.cbyapplication.bean.ResultQuestionList;
import com.example.uuun.cbyapplication.bean.ResultQuestions;
import com.example.uuun.cbyapplication.bean.SurveyBean;
import com.example.uuun.cbyapplication.bean.SurveyBean1;
import com.example.uuun.cbyapplication.bean.SurveyRecord;
import com.example.uuun.cbyapplication.bean.Unfinish;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.FirstEvent;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

import static com.example.uuun.cbyapplication.activity.SurveyActivity.getContentView;

/**
 * 调查问卷页面（new）
 * Created by Ma on 2017/7/25.
 */

public class SurvryDetailActivity extends BaseActivity {
    private TextView invite;
    private int surveyId;

    private LinearLayout test_layout;

    private DbManager db;
    private boolean tag = false; //是否提交
    private PopupWindow window;
    //问卷实体
    private SurveyBean1.DataBean.ContentBean bean;
    List<ResultQuestions> list;
    List<QuestionOptions> list_option;
    //问题所在的View
    private View que_view;
    //答案所在的View
    private View ans_view;
    private LayoutInflater xInflater;
    //下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
    //存每个问题下的imageview
    private ArrayList<ArrayList<ImageView>> imglist = new ArrayList<ArrayList<ImageView>>();
    //存每个答案的imageview
    private ArrayList<ImageView> imglist2;
    //返回按钮
    private ImageView back;
    //存储答案list
    private List<SurveyRecord> list_commit = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_surveydetail);

        back = (ImageView) findViewById(R.id.survey_detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        invite = (TextView) findViewById(R.id.my_survey_invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });

        db = x.getDb(((MyApp) getApplicationContext()).getDaoConfig());
        xInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Intent intent = getIntent();
        bean = (SurveyBean1.DataBean.ContentBean) intent.getSerializableExtra("surveybean");
        surveyId = bean.getId();
        initDate();
        //提交按钮
        TextView button = (TextView) findViewById(R.id.submit);
        button.setOnClickListener(new submitOnClickListener(list_option));

    }
    private void showPop() {
        View view1 = View.inflate(SurvryDetailActivity.this, R.layout.invite_popwindow, null);
        LinearLayout cancel = (LinearLayout) view1.findViewById(R.id.invite_cancle);
        LinearLayout copy = (LinearLayout) view1.findViewById(R.id.invite_copy);
        LinearLayout weChat = (LinearLayout) view1.findViewById(R.id.invite_weChat);
        LinearLayout phoneFriends = (LinearLayout) view1.findViewById(R.id.invite_phone);
        //创建popupwindow为全屏
        window = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);
        //设置动画,就是style里创建的那个j
        window.setAnimationStyle(R.style.take_photo_anim);
        window.showAtLocation(getContentView(SurvryDetailActivity.this), Gravity.BOTTOM, 0, 0);
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
        phoneFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SurvryDetailActivity.this,GetContactsActivity.class);
                startActivity(intent);
                window.dismiss();
            }
        });
    }
    private void initDate() {
        try {
            Unfinish unfinish = db.findById(Unfinish.class, surveyId);
            if (unfinish != null) {
                initDateBySql(unfinish);
            } else {

                initDateByNet();
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initDateBySql(Unfinish unfinish) {
        Gson gson = new Gson();
        list = gson.fromJson(unfinish.getContent(), new TypeToken<List<ResultQuestions>>() {
        }.getType());

        initView(list);
    }

    private void initDateByNet() {
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_GETQUESTION);
        params.addQueryStringParameter("surveyId", surveyId + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ResultQuestionList RQL = gson.fromJson(result, ResultQuestionList.class);
                list = RQL.getData();
                //加载布局
                if (list != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView(list);
                        }
                    });
                }

            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyLog.info("error" + ex.getMessage().toString());
                Toast.makeText(SurvryDetailActivity.this, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                YtfjrProcessDialog.showLoading(SurvryDetailActivity.this,false);
            }
        });


    }

    private void initView(List<ResultQuestions> list) {
        // TODO Auto-generated method stub

        //这是要把问题的动态布局加入的布局
        test_layout = (LinearLayout) findViewById(R.id.lly_test);
        // TextView page_txt = (TextView) findViewById(R.id.txt_title);
        // page_txt.setText(bean.getName());

        //根据问题的多少，来动态加载布局
        for (int i = 0; i < list.size(); i++) {
            que_view = xInflater.inflate(R.layout.quesition_layout, null);
            TextView txt_que = (TextView) que_view.findViewById(R.id.txt_question_item);
            //这是第三层布局要加入的地方
            LinearLayout add_layout = (LinearLayout) que_view.findViewById(R.id.lly_answer);
            //判断单选-多选来实现后面是*号还是*多选，
            if (list.get(i).getQuestion().getType() == 1) {
                set(txt_que, i+1+"、"+list.get(i).getQuestion().getContent(), 1);
            }
            if (list.get(i).getQuestion().getType() == 2) {
                set(txt_que, i+1+"、"+list.get(i).getQuestion().getContent(), 2);
            }
            if (list.get(i).getQuestion().getType() == 3) {
                set(txt_que, i+1+"、"+list.get(i).getQuestion().getContent(), 3);
            }
            //获得答案即第三层数据

            list_option = list.get(i).getQuestionOptions();

            imglist2 = new ArrayList<ImageView>();
            for (int j = 0; j < list_option.size(); j++) {
                ans_view = xInflater.inflate(R.layout.answer_layout, null);
                TextView txt_ans = (TextView) ans_view.findViewById(R.id.txt_answer_item);
                ImageView image = (ImageView) ans_view.findViewById(R.id.image);
                View line_view = ans_view.findViewById(R.id.vw_line);
                if (j == list_option.size() - 1) {
                    //最后一条答案下面不要线是指布局的问题
                    line_view.setVisibility(View.GONE);
                }
                //判断单选多选加载不同选项图片
                if (list.get(i).getQuestion().getType() == 1) {
                    if (list_option.get(j).getAns_state() == 1) {
                        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_true));
                    } else {
                        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
                    }

                }
                if (list.get(i).getQuestion().getType() == 2) {
                    if (list_option.get(j).getAns_state() == 0) {
                        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_false));
                    } else {
                        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_true));
                    }
                }


                Log.e("---", "------" + image);
                imglist2.add(image);
                txt_ans.setText(list_option.get(j).getContent());
                LinearLayout lly_answer_size = (LinearLayout) ans_view.findViewById(R.id.lly_answer_size);
                lly_answer_size.setOnClickListener(new answerItemOnClickListener(i, j, list_option, txt_ans));
                add_layout.addView(ans_view);
            }
            /*for(int r=0; r<imglist2.size();r++){
                Log.e("---", "imglist2--------"+imglist2.get(r));
            }*/

            imglist.add(imglist2);

            test_layout.addView(que_view);
        }

    }

    private void set(TextView tv_test, String content, int type) {
        //为了加载问题后面的* 和*多选
        // TODO Auto-generated method stub
        String w = null;
        if (type == 1)
            w = content + "   [单选题]";
        if (type == 2) {
            w = content + "   [多选题]";
        }
        if (type == 3) {
            w = content + "   [填空题]";
        }

        int start = content.length();
        int end = w.length();
        Spannable word = new SpannableString(w);
        word.setSpan(new AbsoluteSizeSpan(35), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        word.setSpan(new StyleSpan(Typeface.BOLD), start, end,
//                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new ForegroundColorSpan(Color.BLACK), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv_test.setText(word);
    }

    class answerItemOnClickListener implements View.OnClickListener {
        private int i;
        private int j;
        private TextView txt;
        private List<QuestionOptions> list_option;

        public answerItemOnClickListener(int i, int j, List<QuestionOptions> list_option, TextView text) {
            this.i = i;
            this.j = j;
            this.list_option = list_option;
            this.txt = text;

        }

        //实现点击选项后改变选中状态以及对应图片
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断当前问题是单选还是多选
            /*Log.e("------", "选择了-----第"+i+"题");
            for(int q=0;q<imglist.size();q++){
                for(int w=0;w<imglist.get(q).size();w++){
//                  Log.e("---", "共有------"+imglist.get(q).get(w));
                }
            }
            Log.e("----", "点击了---"+imglist.get(i).get(j));*/

            if (list.get(i).getQuestion().getType() == 2) {
                //多选
                if (list_option.get(j).getAns_state() == 0) {
                    //如果未被选中
                    txt.setTextColor(Color.parseColor("#EA5514"));
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_true));
                    list_option.get(j).setAns_state(1);
                    list.get(i).getQuestion().setQue_state(1);
                } else {
                    txt.setTextColor(Color.parseColor("#595757"));
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_false));
                    list_option.get(j).setAns_state(0);
                    int count = 0;
                    for (QuestionOptions options : list.get(i).getQuestionOptions()) {
                        if (options.getAns_state() == 0) {
                            count++;
                        }
                    }
                    if (count == list.get(i).getQuestionOptions().size()) {
                        list.get(i).getQuestion().setQue_state(0);
                    } else {
                        list.get(i).getQuestion().setQue_state(1);
                    }

                }
            } else {
                //单选

                for (int z = 0; z < list_option.size(); z++) {
                    list_option.get(z).setAns_state(0);
                    imglist.get(i).get(z).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
                    list.get(i).getQuestion().setQue_state(0);
                }

                if (list_option.get(j).getAns_state() == 0) {
                    //如果当前未被选中
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_true));
                    list_option.get(j).setAns_state(1);
                    list.get(i).getQuestion().setQue_state(1);
                }

            }

        }

    }

    class submitOnClickListener implements View.OnClickListener {

        private List<QuestionOptions> list_option;

        public submitOnClickListener(List<QuestionOptions> list_option) {
            this.list_option = list_option;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断是否答完题
            boolean isState = true;

            //点击提交的时候，先判断状态，如果有未答完的就提示，如果没有再把每条答案提交（包含问卷ID 问题ID 及答案ID）
            //注：不用管是否是一个问题的答案，就以答案的个数为准来提交上述格式的数据
            for (int i = 0; i < list.size(); i++) {
                list_option = list.get(i).getQuestionOptions();
                //判断是否有题没答完
                if (list.get(i).getQuestion().getQue_state() == 0) {
                    Toast.makeText(getApplicationContext(), "您第" + (i + 1) + "题没有答完", Toast.LENGTH_LONG).show();
                    isState = false;
                    break;
                } else {
                    for (int j = 0; j < list_option.size(); j++) {
                        if (list_option.get(j).getAns_state() == 1) {
                            getAnswer(list_option.get(j));
                        }
                    }
                }
            }

            if (isState) {
                if (list_commit.size() > 0) {

                    commit();

                }

            }


        }
    }

    //获取选项
    public void getAnswer(QuestionOptions questionOptions) {
        SurveyRecord surveyRecord = new SurveyRecord();
        surveyRecord.setChecked(true);
        surveyRecord.setTime(System.currentTimeMillis());
        surveyRecord.setCustom(questionOptions.getCustom());
        surveyRecord.setOptionId(questionOptions.getId());
        surveyRecord.setSurveyId(surveyId);
        surveyRecord.setUserId(MyApp.getCurrentUser().getId());
        surveyRecord.setQuestionId((questionOptions.getQuestionId()));
        list_commit.add(surveyRecord);
    }

    //提交网络请求
    public void commit() {
        YtfjrProcessDialog.showLoading(SurvryDetailActivity.this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_COMMITSURVEY);
        Gson gson = new Gson();
        String s = gson.toJson(list_commit);
        params.addQueryStringParameter("list", s);
        params.addBodyParameter("token", SPUtil.getToken(SurvryDetailActivity.this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MyLog.info("comit---"+result);
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    if (jsonObject.getInt("code")==0) {

                        tag = true;

                        SurveyBean surveyBean = null;
                        try {
                            surveyBean = db.findById(SurveyBean.class, bean.getId());
                            Unfinish unfinish = db.findById(Unfinish.class, surveyId);
                            if (surveyBean != null) {
                                db.delete(surveyBean);
                            }
                            if (unfinish != null) {
                                db.delete(unfinish);
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        double bonus = jsonObject.getDouble("data");
                        Intent intent = new Intent(SurvryDetailActivity.this, SurveyOkActivity.class);
                        intent.putExtra("msg", bonus);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(SurvryDetailActivity.this, "请联系客服", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyLog.info("comit---"+ex.getMessage());
                Toast.makeText(SurvryDetailActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                YtfjrProcessDialog.showLoading(SurvryDetailActivity.this,false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData();
    }

    private void SaveData() {
        if (SPUtil.getTokenFlag(this)) {//判断用户是否登录状态
            //如果未提交,则保存
            if (tag == false) {
                try {
                    if (list!=null) {
                        for (int i = 0; i < list.size(); i++) {
                            list_option = list.get(i).getQuestionOptions();
                            //判断是否有题没答完

                            if (list.get(i).getQuestion().getQue_state() == 0) {
//                            break;
                             } else {
                                for (int j = 0; j < list_option.size(); j++) {
                                    if (list_option.get(j).getAns_state() == 1) {
                                        getAnswer(list_option.get(j));
                                    }
                                }
                            }
                        }
                    }

                    if (!(list_commit == null || list_commit.size() == 0)) {
                        Unfinish unfinish = db.findById(Unfinish.class, surveyId);
                        Gson gson = new Gson();
                        String s = gson.toJson(list);
                        if (unfinish == null) {
                            unfinish = new Unfinish();
                            unfinish.setId(surveyId);
                            unfinish.setContent(s);
                            db.save(unfinish);

                        } else {
                            unfinish.setContent(s);
                            db.update(unfinish);
                        }

                        SurveyBean1.DataBean.ContentBean surveyBean = db.findById(SurveyBean1.DataBean.ContentBean.class, bean.getId());
                        if (surveyBean == null) {
                            EventBus.getDefault().post(
                                    new FirstEvent("visible"));
                            Set set = new HashSet();
                            for (SurveyRecord surveyRecord : list_commit) {
                                set.add(surveyRecord.getQuestionId());
                            }
                            bean.setQuestions(bean.getQuestions() - set.size());

                            db.save(bean);

                        } else {
                            Set set = new HashSet();
                            for (SurveyRecord surveyRecord : list_commit) {
                                set.add(surveyRecord.getQuestionId());
                            }
                            bean.setQuestions(bean.getQuestions() - set.size());


                            db.update(bean);
                        }
                    }else{

                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
