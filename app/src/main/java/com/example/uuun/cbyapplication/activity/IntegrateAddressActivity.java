package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.AddressLvAdapter;
import com.example.uuun.cbyapplication.bean.Address;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.MyLog;
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
 * 我的账户里的兑换地址页面
 */

public class IntegrateAddressActivity extends BaseActivity {
    private ListView lv;
    private AddressLvAdapter adapter;
    private TextView add;
    private String isClick;
    private List<Address> list_address;
    private ImageView back,check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrate_address);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initControl();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_GETALLADDRESS);
        params.addQueryStringParameter("token", SPUtil.getToken(IntegrateAddressActivity.this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    MyLog.info("!!!!!!!!!!!!!!!"+result);
                    if (json.getInt("code") == 0) {
                        JSONArray jsonArray = json.getJSONArray("data");

                        Gson gson = new Gson();
                        list_address = new ArrayList<Address>();
                        for (int i = 0; i <= jsonArray.length() - 1; i++) {
                            JSONObject json1 = jsonArray.getJSONObject(i);
                            Address address = gson.fromJson(json1.toString(), Address.class);
                            MyLog.info(address.getId() + "  " + i);
                            list_address.add(address);
                        }
                        adapter.setList(list_address);
                        adapter.notifyDataSetChanged();
                        YtfjrProcessDialog.showLoading(IntegrateAddressActivity.this,true);
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
                YtfjrProcessDialog.showLoading(IntegrateAddressActivity.this,false);
            }
        });
    }

    private void initControl() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegrateAddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });

        if("111".equals(isClick)){
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //跳回到兑换页面
                    Intent intent = new Intent(IntegrateAddressActivity.this,IntegrateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("address1",list_address.get(i));
                    SPUtil.setAddressFlag(IntegrateAddressActivity.this,true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    private void initView() {
        lv = (ListView) findViewById(R.id.address_lv);
        add = (TextView) findViewById(R.id.address_addAdd);
        back = (ImageView) findViewById(R.id.integrate_addAddress_back);

        adapter = new AddressLvAdapter(IntegrateAddressActivity.this);
        lv.setAdapter(adapter);
        Intent intent = getIntent();
        isClick = intent.getStringExtra("isClick");
    }
}
