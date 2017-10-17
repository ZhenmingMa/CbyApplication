package com.example.uuun.cbyapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;


/**
 * 一键分享工具类
 * Created by uuun on 2016/11/1.
 */
public class ShareUtils {
    public static void showShare(final Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用


       // oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        //oks.setTitleUrl("https://infile.cekom.com.cn/test/fileStore/portal/ecdoc/thumbnail/ios/20173/b08cb10aa52d4004b277e6ac53363e73.jpg");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

        // url仅在微信（包括好友和朋友圈）中使用
     //   oks.setUrl("http://101.200.53.112:8080//images/68976310798640530.jpg");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
       // oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
     //   oks.setSite("分享此内容的网站名称");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
      //  oks.setSiteUrl("http://101.200.53.112:8080//images/68976310798640530.jpg");

        //oks.setSilent(false);
      //  oks.setSilent(true);
        /*oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");*/
       // oks.setImageUrl("https://infile.cekom.com.cn/test/fileStore/portal/ecdoc/thumbnail/ios/20173/b08cb10aa52d4004b277e6ac53363e73.jpg");
      //  oks.setText("我是分享文本");

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                if ("QZone".equals(platform.getName())) {
                    /*paramsToShare.setTitle("我是Title，怎样？");
                    paramsToShare.setTitleUrl("http://sharesdk.cn");*/
                    paramsToShare.setTitle("标题");
                    String imgUrl = "http://101.200.53.112:8080/images/share.jpg";
                    paramsToShare.setImageUrl(imgUrl);
                    paramsToShare.setText("qq空间测试文本");
                    paramsToShare.setSite("分享此内容的网站名称");
                    paramsToShare.setComment("我是测试评论文本");
                    paramsToShare.setUrl("http://101.200.53.112:8080/images/share.jpg");
                   // paramsToShare.setSiteUrl("http://101.200.53.112:8080//images/68976310798640530.jpg");
                }
                if("QQ".equals(platform.getName())){
                    paramsToShare.setTitle("标题");
                    paramsToShare.setText("qq测试文本");
                    paramsToShare.setImageUrl("http://101.200.53.112:8080/images/share.jpg");
                    paramsToShare.setTitleUrl("http://101.200.53.112:8080/images/share.jpg");
                }
                if ("SinaWeibo".equals(platform.getName())) {
                    //paramsToShare.setUrl(null);
                    paramsToShare.setText("分享内容");

                    paramsToShare.setImageUrl("http://101.200.53.112:8080/images/share.jpg");
                }
//                if ("Wechat".equals(platform.getName())) {
//                    Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.ssdk_logo);
//                    paramsToShare.setImageData(imageData);
//                    paramsToShare.setText("测试以文本拼接形式分享链接" + "http://www.baidu.com");
//
//                }
//                if ("WechatMoments".equals(platform.getName())) {
//                    Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.ssdk_logo);
//                    paramsToShare.setImageData(imageData);
//                }
//
                if("ShortMessage".equals(platform.getName())){
                    paramsToShare.setText("分享内容");
                   // paramsToShare.setAddress(number);
                    paramsToShare.setTitle("wo shi title");
                }

//                if("Facebook".equals(platform.getName())){
//                    paramsToShare.setText("测试没有客户端的情况下，分享网页版的FaceBook");
//                    paramsToShare.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
//                }

            }
        });

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("!!!!!!!!!!!!", "onComplete ---->  分享成功");
                platform.isClientValid();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("!!!!!!!!!!", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.d("!!!!!!!!!!!!", "onError ---->  分享失败" + throwable.getMessage());
                throwable.getMessage();
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

// 启动分享GUI
        oks.show(context);
    }
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
