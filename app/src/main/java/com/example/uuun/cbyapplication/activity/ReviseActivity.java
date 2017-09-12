package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 修改密码页面
 */
public class ReviseActivity extends BaseActivity {
    private TextView commit;
    private EditText oldPw,newPw;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);

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

        //提交!!!!!!!!!!
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old  = oldPw.getText().toString();//旧密码
                String newP = newPw.getText().toString();//新密码
                if(!CheckUtils.isPassword(newP)){
                    Toast.makeText(ReviseActivity.this,"您输入的密码格式不正确哦~",Toast.LENGTH_SHORT).show();
                    return;
                }
                updatePass(old,newP);
            }
        });


    }

    private void initView() {
        commit = (TextView) findViewById(R.id.revise_login);
        oldPw = (EditText) findViewById(R.id.revise_oldPw);
        newPw = (EditText) findViewById(R.id.revise_newPw);
        back = (ImageView) findViewById(R.id.revise_back);
    }

    private void updatePass(String oldPass,String newPass){
        YtfjrProcessDialog.showLoading(this,true);
        RequestParams params = new RequestParams(UrlConfig.URL_UPDATEPASS);
        params.addQueryStringParameter("token", SPUtil.getToken(this));
        params.addQueryStringParameter("oldPass",oldPass);
        params.addQueryStringParameter("newPass",newPass);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    if (code == 0){
                        Toast.makeText(ReviseActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ReviseActivity.this,ReviseOkActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(code == 104){
                        Toast.makeText(ReviseActivity.this, "旧密码验证失败", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ReviseActivity.this, "未知错误，请联系客服", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ReviseActivity.this, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                YtfjrProcessDialog.showLoading(ReviseActivity.this,false);
            }
        });


    }
}
