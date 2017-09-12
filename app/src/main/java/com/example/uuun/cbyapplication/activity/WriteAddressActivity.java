package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.example.uuun.cbyapplication.bean.Address;
import com.example.uuun.cbyapplication.bean.Province;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.CheckUtils;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.PullUtil;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


/**
 * 编辑地址页面
 */

public class WriteAddressActivity extends BaseActivity {
    private EditText name_et, phone_et, detail_et;
    private TextView province1;
    private TextView save;
    private int id;
    private List<Province>provinces;
    private AddLvAdapter adapter;
    private int pId,cId;
    private List<Province> city;
    private String text, text1,text2;//用户点击的省份,城市
    private ListView lv;
    private ImageView defaultIv,readPhone,back;
    private Address address;
    private boolean flag;
    private int defaultTag,mTag = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_address);
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

        readPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteAddressActivity.this,GetContactsActivity.class);
                startActivityForResult(intent,1);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLog.info("save");
                updateAddress();
            }
        });

        province1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });

        //设为默认
        defaultIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(defaultTag==1){//默认地址状态
                   defaultIv.setImageResource(R.mipmap.switch_close);
                    defaultTag = 0;
                }else{//未默认状态
                    defaultIv.setImageResource(R.mipmap.address_switch);
                    defaultTag = 1;
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
                    String phone = data.getStringExtra("returnPhone");
                    phone_et.setText(phone);
                }
                break;
        }
    }

    private void showPop() {
        View contentView = LayoutInflater.from(WriteAddressActivity.this).inflate(R.layout.addaddress_pop, null);
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
                    province1.setText(text+" "+text1+" "+text2);
                    popupWindow.dismiss();
                }

            }
        });
    }
    /**
     * 更新地址信息
     */
    private void updateAddress() {
        YtfjrProcessDialog.showLoading(this,true);
        String name = name_et.getText().toString();
        String phone = phone_et.getText().toString();
        String detailAddress = detail_et.getText().toString();
        String province = province1.getText().toString();
        String[] split = province.split(" ");

        RequestParams params = new RequestParams(UrlConfig.URL_UPDATEADDRESS);
        params.addQueryStringParameter("token", SPUtil.getToken(WriteAddressActivity.this));
        params.addQueryStringParameter("id",id+"");
        params.addQueryStringParameter("name",name);
        params.addQueryStringParameter("current", String.valueOf(defaultTag));
        if(CheckUtils.isMobileNO(phone)){
            params.addQueryStringParameter("phone",phone);
        }else{
            Toast.makeText(this,"您输入的手机号格式不对哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        params.addQueryStringParameter("province",split[0]);
        params.addQueryStringParameter("city",split[1]);
        params.addQueryStringParameter("district",split[2]);
        params.addQueryStringParameter("detailAddress",detailAddress);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MyLog.info(result);
                Toast.makeText(WriteAddressActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(WriteAddressActivity.this, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                YtfjrProcessDialog.showLoading(WriteAddressActivity.this,false);
            }
        });
    }

    private void initView() {
        name_et = (EditText) findViewById(R.id.write_name);
        phone_et = (EditText) findViewById(R.id.write_phone);
        province1 = (TextView) findViewById(R.id.write_address_location);
        detail_et = (EditText) findViewById(R.id.write_address_detail);
        save = (TextView) findViewById(R.id.write_save);
        defaultIv = (ImageView) findViewById(R.id.address_default_bt);
        readPhone = (ImageView) findViewById(R.id.writeAddress_readPhone);
        back = (ImageView) findViewById(R.id.write_address_back);

        adapter = new AddLvAdapter(this);

        Intent intent = getIntent();
        address = (Address) intent.getSerializableExtra("address");
        String name = address.getName();
        String phone = address.getPhone();
        String province = address.getProvince();
        String city = address.getCity();
        String district = address.getDistrict();
        String detail = address.getDetailAddress();
        flag = address.isCurrent();
        MyLog.info("!!!!!!!!!!!!!!"+flag);
        id = address.getId();
        name_et.setText(name);
        phone_et.setText(phone);
        province1.setText(province+" "+city+" "+district);
        detail_et.setText(detail);


        if(flag){
            defaultIv.setImageResource(R.mipmap.address_switch);
            defaultTag = 1;
        }else{
            defaultIv.setImageResource(R.mipmap.switch_close);
            defaultTag = 0;
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
