package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.MyLoadingActivity;
import com.example.uuun.cbyapplication.activity.SurvryDetailActivity;
import com.example.uuun.cbyapplication.bean.SurveyBean;
import com.example.uuun.cbyapplication.bean.SurveyBean1;
import com.example.uuun.cbyapplication.bean.Unfinish;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.utils.FirstEvent;

import org.greenrobot.eventbus.EventBus;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/20.
 */

public class LoadingAdapter extends BaseAdapter {
    private Context context;
    private List<SurveyBean1.DataBean.ContentBean> list = new ArrayList<>();
    private MyLoadingActivity act;
    private boolean flag = false;

    public LoadingAdapter(Context context,MyLoadingActivity act) {
        this.context = context;
        this.act = act;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<SurveyBean1.DataBean.ContentBean> getList() {
        return list;
    }

    public void setList(List<SurveyBean1.DataBean.ContentBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.loading_item, viewGroup, false);
            holder.iv = (ImageView) view.findViewById(R.id.loading_item_select);
            holder.name = (TextView) view.findViewById(R.id.loading_item_name);
            holder.remind = (TextView) view.findViewById(R.id.loading_item_reminder);
            holder.money = (TextView) view.findViewById(R.id.loading_item_money);
            holder.ll = (LinearLayout) view.findViewById(R.id.loading_item_ll);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(list.get(i).getName());
        holder.remind.setText("[还有" + list.get(i).getQuestions() + "道未完成]");
        holder.money.setText("￥" + list.get(i).getBonus());
        final ViewHolder finalHolder1 = holder;
        final ViewHolder finalHolder = holder;
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPop(i);
                finalHolder1.iv.setVisibility(View.VISIBLE);
                return true;
            }

            private void showPop(final int i) {
                View view1 = View.inflate(context, R.layout.loading_pop, null);
                TextView delete = (TextView) view1.findViewById(R.id.loading_pop_delete);
                TextView cancel = (TextView) view1.findViewById(R.id.loading_pop_cancel);
                //创建popupwindow为全屏
                final PopupWindow window = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                //设置动画,就是style里创建的那个j
                window.setAnimationStyle(R.style.take_photo_anim);
                //设置popupwindow的位置,这里直接放到屏幕上就行
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
                        finalHolder1.iv.setVisibility(View.INVISIBLE);
                        EventBus.getDefault().post(
                                new FirstEvent("changePosition"));
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DbManager db = x.getDb(((MyApp) context.getApplicationContext()).getDaoConfig());
                        try {
                            db.deleteById(SurveyBean.class,list.get(i).getId());
                            db.deleteById(Unfinish.class,list.get(i).getId());
                            list.remove(i);
                            notifyDataSetChanged();
                            window.dismiss();
                            finalHolder.iv.setVisibility(View.INVISIBLE);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }


        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SurvryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("surveybean",list.get(i));
                intent.putExtras(bundle);
                context.startActivity(intent);
                act.finish();
            }
        });

        return view;
    }

    class ViewHolder {
        ImageView iv;
        TextView name, remind, money;
        LinearLayout ll;
    }
}
