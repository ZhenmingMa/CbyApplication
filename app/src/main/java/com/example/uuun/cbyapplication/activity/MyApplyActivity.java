package com.example.uuun.cbyapplication.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.CheckUtils;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 合作申请页面
 */
public class MyApplyActivity extends BaseActivity {
    private EditText name,phone,email;
    private TextView apply;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply);

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

        //提交!!!!!!!
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText())){
                    Toast.makeText(MyApplyActivity.this,"请输入公司名~",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone.getText())||!CheckUtils.isMobileNO(phone.getText().toString())){
                    Toast.makeText(MyApplyActivity.this,"请输入正确的公司联系电话格式~",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.isEmpty(email.getText())&&!CheckUtils.isEmail(email.getText().toString())){
                    Toast.makeText(MyApplyActivity.this,"请输入正确的公司邮箱地址~",Toast.LENGTH_SHORT).show();
                    return;
                }
                apply();
            }
        });
    }

    private void initView() {
        name = (EditText) findViewById(R.id.apply_name);
        phone = (EditText) findViewById(R.id.apply_phone);
        email = (EditText) findViewById(R.id.apply_email);
        apply = (TextView) findViewById(R.id.apply_ok);
        back = (ImageView) findViewById(R.id.myApply_back);
    }

    private void apply(){
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_APPLY);
        params.addBodyParameter("token", SPUtil.getToken(MyApplyActivity.this));
        params.addBodyParameter("phone",phone.getText().toString());
        params.addBodyParameter("email",email.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MyApplyActivity.this,"提交成功,工作人员将尽快回复您",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                YtfjrProcessDialog.showLoading(MyApplyActivity.this,false);
            }
        });
    }
}
