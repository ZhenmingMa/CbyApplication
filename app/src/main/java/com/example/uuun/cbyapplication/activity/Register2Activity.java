package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.AddLvAdapter;
import com.example.uuun.cbyapplication.adapter.MySpinnerAdapter;
import com.example.uuun.cbyapplication.bean.Province;
import com.example.uuun.cbyapplication.bean.User;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.FirstEvent;
import com.example.uuun.cbyapplication.utils.MyDatePicker;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.PullUtil;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 注册的第二个页面
 */
public class Register2Activity extends BaseActivity implements MyDatePicker.TimePickerDialogInterface {
    private TextView birth, position, commit, man, woman;
    private EditText work, hobby;
    private Spinner earning;
    private List<String> list;
    MySpinnerAdapter adapter;
    private AddLvAdapter adapter1;
    private ListView lv;
    private List<Province> provinces;
    private List<Province> city;
    private int pId, cId,mTag = 1;
    private String text, text1, text2, money;
    private boolean flag = true;//true为女,false为男
    private MyDatePicker dialog;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

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
                showPop();
            }
        });

        //选择生日点击事件
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.showDatePickerDialog();
                dialog.setPickerMargin(5);
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

    private void showPop() {
        View contentView = LayoutInflater.from(Register2Activity.this).inflate(R.layout.addaddress_pop, null);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
        p.height = (int) (d.getHeight() * 1.0); // 高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
        p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.0f; // 设置黑暗度
        final PopupWindow popupWindow = new PopupWindow(findViewById(R.id.activity_add_address), p.width, p.height, true);
        popupWindow.setContentView(contentView);
        lv = (ListView) contentView.findViewById(R.id.addaddress_lv);
        lv.setAdapter(adapter1);
        initData();



        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view.findViewById(R.id.add_pop_item_tv);
                if(mTag==1){
                    text = (String) tv.getText();
                    pId = provinces.get(i).getId();
                    initCityData();
                }
                else if(mTag==2){
                    text1 = (String) tv.getText();
                    cId = city.get(i).getId();
                    initDistricts();
                }else{
                    text2 = (String) tv.getText();
                    position.setText(text+" "+text1+" "+text2);
                    popupWindow.dismiss();
                }

            }
        });
    }

    private void initView() {
        commit = (TextView) findViewById(R.id.register_commit);
        man = (TextView) findViewById(R.id.register2_man);
        woman = (TextView) findViewById(R.id.register2_woman);
        earning = (Spinner) findViewById(R.id.register2_sp);
        birth = (TextView) findViewById(R.id.register2_birth);
        position = (TextView) findViewById(R.id.register2_pos);
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

        adapter1 = new AddLvAdapter(this);

        dialog = new MyDatePicker(this);
    }


    private void initData() {
        /**"demo.xml"为assets中xml文件名字，"SMQK"为该xml中tablename对应的value*/
        provinces = PullUtil.getAttributeList(this, "provinces.xml", "SMQK");
        adapter1.setList(provinces);
        mTag = 1;
    }

    private void initCityData() {
        /**"demo.xml"为assets中xml文件名字，"SMQK"为该xml中tablename对应的value*/
        // MyLog.info("!!!!!!!!!!!!!!!initCityData");
        city = PullUtil.getCityList(this, "cities.xml", pId+"");
        adapter1.setList(city);
        mTag = 2;
    }
    private void initDistricts(){
        List<Province> distincts = PullUtil.getDistinctList(this, "districts.xml", cId + "");
        adapter1.setList(distincts);
        mTag = 3;
    }

    @Override
    public void positiveListener() {
        int year = dialog.getYear();
        int month = dialog.getMonth();
        int day = dialog.getDay();
        birth.setText(year + "-" + month + "-" + day);
    }

    @Override
    public void negativeListener() {

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
                        EventBus.getDefault().post(
                                new FirstEvent("success"));
                        Intent intent = new Intent(Register2Activity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
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
