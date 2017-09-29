package com.example.uuun.cbyapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.Address;
import com.example.uuun.cbyapplication.bean.ShopBean;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认换购页面
 */
public class IntegrateActivity extends BaseActivity {
    private ImageView img;
    private TextView name, color, number, jian, jia, mNumber, addAddress, commit
            , reminder, addDefault, personName, province, city, district, detail
            ,write, delete;
    private int num,id;
    private ShopBean.DataBean bean;
    private RelativeLayout rl;
    private Address address, address1;//address网络请求下来的默认地址,address1是添加地址选择的地址
    private ImageView back;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrate);

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

        num = Integer.parseInt(mNumber.getText().toString());
        //减号点击事件
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (num == 1) {
                    Toast.makeText(IntegrateActivity.this, "不能再减了哦~", Toast.LENGTH_SHORT).show();
                } else {
                    num--;
                    mNumber.setText(num + "");
                }
            }
        });

        //加号的点击事件
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                mNumber.setText(num + "");
            }
        });

        //跳转到添加地址页面
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegrateActivity.this, IntegrateAddressActivity.class);
                intent.putExtra("isClick", "111");
                startActivity(intent);
                finish();
            }
        });

        //跳转到编辑地址页面
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegrateActivity.this, WriteAddressActivity.class);
                Bundle bundle = new Bundle();
                if(!flag){//添加地址页面选择的地址
                    bundle.putSerializable("address", address1);
                }else{//网络请求
                    bundle.putSerializable("address", address);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(IntegrateActivity.this);
                builder.setTitle("确认删除").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rl.setVisibility(View.GONE);
                        addDefault.setVisibility(View.VISIBLE);
                    }
                }).setNegativeButton("取消", null);
                builder.show();
            }
        });

        //提交!!!!!
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag){
                    Toast.makeText(IntegrateActivity.this,"兑换地址不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    YtfjrProcessDialog.showLoading(IntegrateActivity.this,true);
                    RequestParams params = new RequestParams(UrlConfig.URL_EXCHANGEGOODS);
                    params.addBodyParameter("token", SPUtil.getToken(IntegrateActivity.this));
                    params.addBodyParameter("id", bean.getId() + "");
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject json = new JSONObject(result);
                                if (json.getInt("code") == 0) {
                                    int integration = SPUtil.getIntegration(IntegrateActivity.this);
                                    reminder.setText(integration - bean.getPrice() + "积分");
                                    Toast.makeText(IntegrateActivity.this, "兑换成功", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    Toast.makeText(IntegrateActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {

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
                            YtfjrProcessDialog.showLoading(IntegrateActivity.this,false);

                        }
                    });
                }

            }
        });
    }

    private void initView() {
        name = (TextView) findViewById(R.id.integrate_name);
        color = (TextView) findViewById(R.id.integrate_color);
        number = (TextView) findViewById(R.id.integrate_number);
        jian = (TextView) findViewById(R.id.integrate_jian);
        jia = (TextView) findViewById(R.id.integrate_jia);
        mNumber = (TextView) findViewById(R.id.integrate_mNumber);
        addAddress = (TextView) findViewById(R.id.integrate_addAddress);
        commit = (TextView) findViewById(R.id.integrate_commit);
        reminder = (TextView) findViewById(R.id.integrate_reminder);
        img = (ImageView) findViewById(R.id.integrate_iv);
        addDefault = (TextView) findViewById(R.id.integrate_addDefault_tv);
        rl = (RelativeLayout) findViewById(R.id.integrate_addDefault_rl);
        personName = (TextView) findViewById(R.id.integrate_address_name);
        province = (TextView) findViewById(R.id.integrate_address_province);
        city = (TextView) findViewById(R.id.integrate_address_city);
        detail = (TextView) findViewById(R.id.integrate_address_detail);
        write = (TextView) findViewById(R.id.integrate_writeAddress);
        delete = (TextView) findViewById(R.id.integrate_deleteAddress);
        district = (TextView) findViewById(R.id.integrate_address_district);
        back = (ImageView) findViewById(R.id.integrate_back);

        Intent intent = getIntent();

        int integration = SPUtil.getIntegration(this);
        reminder.setText(integration + "积分");
        if (!SPUtil.getAddressFlag(this)) {

            Bundle bundle = intent.getExtras();
            bean = (ShopBean.DataBean) bundle.getSerializable("shopbean");
            name.setText(bean.getName());
            color.setText("pk01");
            number.setText(bean.getPrice() + "积分");
            number.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//        Glide.with(context).load(bean.getImg()).into(holder.image);
            Glide.with(this).load(UrlConfig.URL_BASE+bean.getImg()).into(img);
            SPUtil.setShopName(this, bean.getName());
            SPUtil.setShopColor(this, "pk01");
            SPUtil.setShopNumber(this, bean.getPrice() + "积分");
            SPUtil.setShopImg(this,bean.getImg());
        } else {
            String shopName = SPUtil.getShopName(this);
            name.setText(shopName);
            String shopColor = SPUtil.getShopColor(this);
            color.setText(shopColor);
            String shopNumber = SPUtil.getShopNumber(this);
            number.setText(shopNumber);
            number.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            Glide.with(this).load(SPUtil.getShopImg(this)).into(img);
        }

        if (SPUtil.getAddressFlag(this)) {
            SPUtil.setAddressFlag(this, false);
            address1 = (Address) intent.getSerializableExtra("address1");
            rl.setVisibility(View.VISIBLE);
            addDefault.setVisibility(View.GONE);
            personName.setText(address1.getName());
           // MyLog.info("!!!!!!!!!!!!!!!" + address1.getName());
            province.setText(address1.getProvince());
            city.setText(address1.getCity());
            detail.setText(address1.getDetailAddress());
            district.setText(address1.getDistrict());
            id = address1.getId();
            flag = false;
        }else{
            flag = true;
            initData();
        }
    }

    private void initData() {
        RequestParams params = new RequestParams(UrlConfig.URL_GETALLADDRESS);
        params.addQueryStringParameter("token", MyApp.currentUser.getToken());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("code") == 0) {
                        JSONArray jsonArray = json.getJSONArray("data");
                        Gson gson = new Gson();
                        List<Address> list_address = new ArrayList<Address>();
                        for (int i = 0; i <= jsonArray.length() - 1; i++) {
                            JSONObject json1 = jsonArray.getJSONObject(i);
                            address = gson.fromJson(json1.toString(), Address.class);
                            if (address.isCurrent()) {
                                list_address.add(address);
                            }

                        }
                        if (list_address.size() > 0) {
                            rl.setVisibility(View.VISIBLE);
                            addDefault.setVisibility(View.GONE);
                            personName.setText(list_address.get(0).getName());
                            province.setText(list_address.get(0).getProvince());
                            city.setText(list_address.get(0).getCity());
                            detail.setText(list_address.get(0).getDetailAddress());
                            district.setText(list_address.get(0).getDistrict());
                        } else {
                            rl.setVisibility(View.GONE);
                            addDefault.setVisibility(View.VISIBLE);

                        }
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

            }
        });
    }
}
