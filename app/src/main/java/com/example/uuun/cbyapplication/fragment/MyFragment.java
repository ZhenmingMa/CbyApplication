package com.example.uuun.cbyapplication.fragment;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.LoginActivity;
import com.example.uuun.cbyapplication.activity.MyApplyActivity;
import com.example.uuun.cbyapplication.activity.MyFinishActivity;
import com.example.uuun.cbyapplication.activity.MyLoadingActivity;
import com.example.uuun.cbyapplication.activity.MyNewsActivity;
import com.example.uuun.cbyapplication.activity.RegisterActivity;
import com.example.uuun.cbyapplication.activity.ReviseActivity;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.utils.FirstEvent;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import cn.bingoogolapple.badgeview.BGABadgeTextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.system.text.ShortMessage;

/**
 * Created by uuun on 2017/5/27.
 * 个人中心页面
 */

public class MyFragment extends Fragment {
    private View view, view1;
    //login已登陆页面
    private LinearLayout login;
    //noLogin未登陆页面
    private RelativeLayout finish, apply, noLogin, invite,news,loading;
    private TextView exit, register, toLogin, revise, name;
    private PopupWindow window;
    private boolean flag;
    private BGABadgeTextView badge;
    private  OnekeyShare oks;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        EventBus.getDefault().register(this);
        initView();
        initControl();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApp.getCurrentUser() != null){
            login.setVisibility(View.GONE);
            noLogin.setVisibility(View.VISIBLE);
            name.setText(MyApp.getCurrentUser().getPhone()+"");
            name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

    private void initControl() {

        //跳转到我的消息页面
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyNewsActivity.class);
                startActivity(intent);
            }
        });

        //跳转到登陆界面
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        //跳转到注册界面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        //跳转到"已完成的调研"界面
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyFinishActivity.class);
                startActivity(intent);

            }
        });

        //跳转到"未完成的调研"界面
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyLoadingActivity.class);
                badge.hiddenBadge();
                startActivity(intent);
            }
        });

        //跳转到合作申请页面
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyApplyActivity.class);
                startActivity(intent);
            }
        });

        //跳转到修改密码页面
        revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReviseActivity.class);
                startActivity(intent);
            }
        });

        //跳转到退出登录页面
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("确认退出登录?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        login.setVisibility(View.VISIBLE);
                        noLogin.setVisibility(View.GONE);
                        SPUtil.setToken(getActivity(), null);
                        EventBus.getDefault().post(
                                new FirstEvent("exit"));

                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                }).setNegativeButton("取消", null);
                builder.show();
                flag = true;
                SPUtil.setTokenFlag(getContext(),false);
                SPUtil.setExitFlag(getContext(), flag);
            }
        });
        if (TextUtils.isEmpty(SPUtil.getToken(getActivity()))) {
            login.setVisibility(View.VISIBLE);
            noLogin.setVisibility(View.GONE);
        } else {
            login.setVisibility(View.GONE);
            noLogin.setVisibility(View.VISIBLE);
        }
        //邀请好友
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }

            private void showPop() {
                view1 = View.inflate(getActivity(), R.layout.invite_popwindow, null);
                LinearLayout cancel = (LinearLayout) view1.findViewById(R.id.invite_cancle);
                LinearLayout copy = (LinearLayout) view1.findViewById(R.id.invite_copy);
                LinearLayout weChat = (LinearLayout) view1.findViewById(R.id.invite_weChat);
                LinearLayout phoneFriends = (LinearLayout) view1.findViewById(R.id.invite_phone);
                //创建popupwindow为全屏
                window = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                //设置动画,就是style里创建的那个j
                window.setAnimationStyle(R.style.take_photo_anim);
                window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
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

                copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText("分享内容啦啦啦啦");
                        Toast.makeText(getActivity(), "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                    }
                });

                phoneFriends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //分享至短信

                        Platform.ShareParams sp = new Platform.ShareParams();
                        sp.setText("测试分享的文本");
                       // sp.setImagePath(“/mnt/sdcard/测试分享的图片.jpg”);

                        Platform sm = ShareSDK.getPlatform(ShortMessage.NAME);
                        sm.setPlatformActionListener(new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {

                            }

                            @Override
                            public void onCancel(Platform platform, int i) {

                            }
                        });

                        sm.share(sp);

                    }
                });
            }
        });
    }

    private void initView() {
        loading = (RelativeLayout) view.findViewById(R.id.my_loading_rl);
        finish = (RelativeLayout) view.findViewById(R.id.my_finish_rl);
        apply = (RelativeLayout) view.findViewById(R.id.my_apply_rl);
        exit = (TextView) view.findViewById(R.id.my_login_exit);
        login = (LinearLayout) view.findViewById(R.id.my_login_exit_ll);
        noLogin = (RelativeLayout) view.findViewById(R.id.my_login_login);
        register = (TextView) view.findViewById(R.id.my_register);
        toLogin = (TextView) view.findViewById(R.id.my_login);
        revise = (TextView) view.findViewById(R.id.my_login_revisePw);
        name = (TextView) view.findViewById(R.id.my_name);
        invite = (RelativeLayout) view.findViewById(R.id.my_invite_rl);
        badge = (BGABadgeTextView) view.findViewById(R.id.badgeView);
        news = (RelativeLayout) view.findViewById(R.id.my_news_rl);

        if(!(MyApp.getCurrentUser() ==null)){
            name.setText(MyApp.getCurrentUser().getPhone()+"");
            name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        }
        badge.hiddenBadge();


        ShareSDK.initSDK(getActivity());
        oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FirstEvent event) {

        MyLog.info(System.currentTimeMillis()+event.getMsg());
        if(event.getMsg().equals("visible")){
            badge.showCirclePointBadge();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}

