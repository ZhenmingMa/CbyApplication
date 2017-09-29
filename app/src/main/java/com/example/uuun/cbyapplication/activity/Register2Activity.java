package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.MySpinnerAdapter;
import com.example.uuun.cbyapplication.bean.User;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.position.ShowSpicker;
import com.example.uuun.cbyapplication.utils.ActivityCollector;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.uuun.cbyapplication.position.ShowSpicker.options1Items;
import static com.example.uuun.cbyapplication.position.ShowSpicker.options2Items;
import static com.example.uuun.cbyapplication.position.ShowSpicker.options3Items;
import static com.scp.pickerview.TimeActivity.getTime;


/**
 * 注册的第二个页面
 */
public class Register2Activity extends BaseActivity{
    private TextView commit, man, woman;
    private EditText birth,position;
    private EditText work, hobby;
    private Spinner earning;
    private List<String> list;
    MySpinnerAdapter adapter;
    private String  money;
    private boolean flag = true;//true为女,false为男
    private User user = new User();
    private String text,text1,text2;//分别是省市区

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ActivityCollector.addActivity(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initControl();
    }

    private void initControl() {
        //提交!!!!!!!!!
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag) {
                    user.setSex("女");
                } else {
                    user.setSex("男");
                }

                if (!TextUtils.isEmpty(birth.getText().toString())) {
                    user.setBirthday(getStringToDate(birth.getText().toString()));
                } else {
                    Toast.makeText(Register2Activity.this, "请输入您的生日", Toast.LENGTH_SHORT).show();
                    return;
                }

                String position = text + " " + text1 + " " + text2;
                if (!TextUtils.isEmpty(position)) {
                    user.setLocation(position);
                } else {
                    Toast.makeText(Register2Activity.this, "请输入您的位置", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(work.getText().toString())) {
                    user.setOccupation(work.getText().toString());
                } else {
                    Toast.makeText(Register2Activity.this, "请输入您的职业", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(money)) {
                    user.setIncome(money);
                } else {
                    Toast.makeText(Register2Activity.this, "请输入您的收入", Toast.LENGTH_SHORT).show();
                    return;
                }

                String hobbys;
                if (!TextUtils.isEmpty(hobby.getText().toString())) {
                    hobbys = hobby.getText().toString();
                    user.setHobby(hobbys);
                } else {
                    hobbys = null;
                }

                commitInfo(user);

            }
        });

        //性别点击事件
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                man.setBackgroundResource(R.drawable.reward_confirm);
                woman.setBackgroundResource(R.drawable.rect_null);
                man.setTextColor(Color.WHITE);
                woman.setTextColor(Color.BLACK);
                flag = false;
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                woman.setBackgroundResource(R.drawable.reward_confirm);
                man.setBackgroundResource(R.drawable.rect_null);
                woman.setTextColor(Color.WHITE);
                man.setTextColor(Color.BLACK);
                flag = true;
            }
        });

        //选择地区点击事件
        position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView pvOptions = ShowSpicker.initPositionData(Register2Activity.this);
                pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPro_name()
                                + " " + options2Items.get(options1).get(option2).getName()
                                + " " + options3Items.get(options1).get(option2).get(options3).getName();
                        position.setText(tx);
                        String[] split = tx.split(" ");
                        text = split[0];
                        text1 = split[1];
                        text2 = split[2];

                    }
                });
            }
        });

        //选择生日点击事件
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerView pvTime = ShowSpicker.initTimeView(Register2Activity.this);
                pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        birth.setText(getTime(date));
                    }
                });

            }
        });

        //年收入下拉框的点击事件
        earning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MySpinnerAdapter adapter = (MySpinnerAdapter) adapterView.getAdapter();
                money = (String) adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initView() {
        commit = (TextView) findViewById(R.id.register_commit);
        man = (TextView) findViewById(R.id.register2_man);
        woman = (TextView) findViewById(R.id.register2_woman);
        earning = (Spinner) findViewById(R.id.register2_sp);
        birth = (EditText) findViewById(R.id.register2_birth);
        position = (EditText) findViewById(R.id.register2_pos);
        work = (EditText) findViewById(R.id.register2_work);
        hobby = (EditText) findViewById(R.id.register2_hobby);
        String[] str = getIntent().getStringArrayExtra("msg");
        user.setPhone(Long.parseLong(str[0]));
        user.setPassword(str[1]);
        list = new ArrayList<>();
        list.add("10w以下");
        list.add("10w");
        list.add("50w");
        list.add("100w");
        list.add("100w以上");

        adapter = new MySpinnerAdapter(this);
        adapter.setList(list);
        earning.setAdapter(adapter);

    }
    void commitInfo(User user) {
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_REGISIT);
        params.addQueryStringParameter("phone", user.getPhone() + "");
        params.addQueryStringParameter("password", user.getPassword());
        Date date = new Date(user.getBirthday());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        params.addBodyParameter("birthday", sdf.format(date));
        params.addBodyParameter("location", user.getLocation());
        params.addBodyParameter("occupation", user.getOccupation());
        params.addBodyParameter("income", user.getIncome());
        params.addBodyParameter("hobby", user.getHobby());
        params.addBodyParameter("sex", user.getSex());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MyLog.info(result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String message = jsonObject.getString("message");
                    if (code == 0) {
                        String data = jsonObject.getString("data");
                        Gson gson = new Gson();
                        User user = gson.fromJson(data, User.class);

                        SPUtil.setToken(Register2Activity.this, user.getToken());
                        SPUtil.setTokenFlag(Register2Activity.this, true);
                        MyApp.setCurrentUser(user);
                        Toast.makeText(Register2Activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register2Activity.this, HomeActivity.class);
                        startActivity(intent);
                        ActivityCollector.finishAll();
                    } else {
                        Toast.makeText(Register2Activity.this, message, Toast.LENGTH_SHORT).show();
                    }
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
                YtfjrProcessDialog.showLoading(Register2Activity.this,false);
            }
        });
    }

    //将字符串转为时间戳
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

}
