package com.example.uuun.cbyapplication.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.AddLvAdapter;
import com.example.uuun.cbyapplication.bean.Province;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.position.ShowSpicker;
import com.example.uuun.cbyapplication.utils.CheckUtils;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.example.uuun.cbyapplication.position.ShowSpicker.options1Items;
import static com.example.uuun.cbyapplication.position.ShowSpicker.options2Items;
import static com.example.uuun.cbyapplication.position.ShowSpicker.options3Items;

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
    private ImageView setDefault,back,readPhone;
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

        readPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAddressActivity.this,GetContactsActivity.class);
                startActivityForResult(intent,1);
                //1111111111111111111111
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView pvOptions = ShowSpicker.initPositionData(AddAddressActivity.this);
                pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPro_name()
                                + " " + options2Items.get(options1).get(option2).getName()
                                + " " + options3Items.get(options1).get(option2).get(options3).getName();
                        province.setText(tx);
                        String[] split = tx.split(" ");
                        text = split[0];
                        text1 = split[1];
                        text2 = split[2];
                    }
                });

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


    private void initView() {
        province = (TextView) findViewById(R.id.addAddress_province);
        save = (TextView) findViewById(R.id.addAddress_save);
        detailAddress = (EditText) findViewById(R.id.addAddress_detail);
        setDefault = (ImageView) findViewById(R.id.addAddress_default);
        back = (ImageView) findViewById(R.id.addAddress_back);
        readPhone = (ImageView) findViewById(R.id.addAddress_readPhone);

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
}
