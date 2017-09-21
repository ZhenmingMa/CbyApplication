package com.example.uuun.cbyapplication.activity;


import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.AddLvAdapter;
import com.example.uuun.cbyapplication.bean.Province;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.CheckUtils;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.PullUtil;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 我的账户里的添加地址页面
 */
public class AddAddressActivity extends BaseActivity {
    @ViewInject(R.id.act_add_address_name_tv)
    private EditText name;
    @ViewInject(R.id.act_add_address_phone_tv)
    private EditText phone;
    private EditText detailAddress;
    private String text, text1,text2;//用户点击的省份,城市
    private TextView province, save;
    private List<Province> provinces;
    private AddLvAdapter adapter;
    private int pId,cId;
    private ListView lv;
    private List<Province> city;
    private ImageView setDefault,back;
    private int tag = 0,mTag = 1;
    private boolean flag = false;
    private String phoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

       // pb.setVisibility(View.VISIBLE);
        initView();
        initControl();

        x.view().inject(this); //绑定注解
    }


    private void initControl() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHandle(view);

            }
        });
        setDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!flag){
                setDefault.setImageResource(R.mipmap.address_switch);
                flag = true;
                tag =1;
            }else{
                setDefault.setImageResource(R.mipmap.switch_close);
               // pb.setVisibility(View.GONE);
                flag = false;
                tag = 0;
            }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String phoneNum = data.getStringExtra("returnPhone");
                    phone.setText(phoneNum);
                }
                break;
        }
    }

    private void showPop() {
        View contentView = LayoutInflater.from(AddAddressActivity.this).inflate(R.layout.addaddress_pop, null);
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
        lv.setAdapter(adapter);
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
                    province.setText(text+" "+text1+" "+text2);
                    popupWindow.dismiss();
                }

            }
        });
    }
    private void initView() {
        province = (TextView) findViewById(R.id.addAddress_province);
        save = (TextView) findViewById(R.id.addAddress_save);
        detailAddress = (EditText) findViewById(R.id.addAddress_detail);
        setDefault = (ImageView) findViewById(R.id.addAddress_default);
        back = (ImageView) findViewById(R.id.addAddress_back);

        adapter = new AddLvAdapter(this);
    }

    /**
     * 保存地址的方法
     *
     * @param view
     */
    //@Event(value = R.id.act_add_address_save_tv)
    private void saveHandle(View view) {
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(phone.getText())
                || TextUtils.isEmpty(province.getText())
                || TextUtils.isEmpty(detailAddress.getText())) {
            Toast.makeText(this, "请把你的信息填写完整~", Toast.LENGTH_SHORT).show();
            phoneNum = phone.getText().toString();
            if(!CheckUtils.isMobileNO(phoneNum)){
                Toast.makeText(AddAddressActivity.this,"您输入的手机号码格式不正确~",Toast.LENGTH_SHORT).show();
            }
        } else {
            YtfjrProcessDialog.showLoading(this,true);
            RequestParams params = new RequestParams(UrlConfig.URL_ADDADDRESS);
            params.addQueryStringParameter("token", SPUtil.getToken(AddAddressActivity.this));
            params.addQueryStringParameter("name", name.getText().toString());
                params.addQueryStringParameter("phone",phone.getText().toString() );

            params.addQueryStringParameter("province", text);
            params.addQueryStringParameter("city", text1);
            params.addQueryStringParameter("district", text2);
            //MyLog.info("!!!!!!!!!!!!!!!!"+text2);
            params.addQueryStringParameter("current", String.valueOf(tag));
            params.addQueryStringParameter("detailAddress", detailAddress.getText().toString());
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(AddAddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    MyLog.info("!!!!!!!!!!!!!!!!!"+result);
                    finish();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(AddAddressActivity.this, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    YtfjrProcessDialog.showLoading(AddAddressActivity.this,false);
                }
            });
        }
    }

    private void initData() {
        /**"demo.xml"为assets中xml文件名字，"SMQK"为该xml中tablename对应的value*/
        provinces = PullUtil.getAttributeList(this, "provinces.xml", "SMQK");
        adapter.setList(provinces);
        mTag = 1;
    }

    private void initCityData() {
        /**"demo.xml"为assets中xml文件名字，"SMQK"为该xml中tablename对应的value*/
        // MyLog.info("!!!!!!!!!!!!!!!initCityData");
        city = PullUtil.getCityList(this, "cities.xml", pId+"");
        adapter.setList(city);
        mTag = 2;
    }
    private void initDistricts(){
        List<Province> distincts = PullUtil.getDistinctList(this, "districts.xml", cId + "");
        adapter.setList(distincts);
        mTag = 3;
    }
}
