package com.example.uuun.cbyapplication.myview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;


public class YtfjrProcessDialog extends Dialog {

    protected static YtfjrProcessDialog theYtfjrProcessDialog = null;

    public YtfjrProcessDialog(Context context) {
        super(context);
    }

    public YtfjrProcessDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("YtfjrProcessDialog", "Dialog created");

        setContentView(R.layout.ytfjr_dialog_progress);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    public static YtfjrProcessDialog createDialog(Context context) {
        YtfjrProcessDialog theYtfjrProcessDialog = new YtfjrProcessDialog(context);
        theYtfjrProcessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return theYtfjrProcessDialog;
    }

    public static void showLoading(Context context, boolean isLoading) {

        if (theYtfjrProcessDialog != null) {
            theYtfjrProcessDialog.dismiss();
            theYtfjrProcessDialog = null;
        }

        if (isLoading) {
            theYtfjrProcessDialog = createDialog(context);
            theYtfjrProcessDialog.show();
            theYtfjrProcessDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * [Summary]
     * setTitile 标题
     *
     * @param strTitle
     * @return
     */
    public YtfjrProcessDialog setTitile(String strTitle) {
        return this;
    }

    /**
     * [Summary]
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public YtfjrProcessDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) findViewById(R.id.wz_pt);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }

        return this;
    }

//
//    public static void setIsLoading(final Activity activity, boolean isLoading) {
//        if (isLoading) {
//            ETFApplication.getInstance().handler.post(new Runnable() {
//
//                @Override
//                public void run() {
//                    // TODO 自动生成的方法存根
//                    YtfjrProcessDialog.showLoading(activity, true);
//                }
//            });
//        } else {
//            YtfjrProcessDialog.showLoading(activity, false);
//        }
//    }


}
