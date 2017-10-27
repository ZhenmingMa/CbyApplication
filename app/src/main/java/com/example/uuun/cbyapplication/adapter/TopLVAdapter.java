package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.MyApplyActivity;
import com.example.uuun.cbyapplication.bean.SurveyBean1;
import com.example.uuun.cbyapplication.utils.DateUtils;
import com.example.uuun.cbyapplication.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/1.
 */

public class TopLVAdapter extends BaseAdapter {
    List<SurveyBean1.DataBean.ContentBean> list;
    Context context;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;

    public TopLVAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();

    }

    public List<SurveyBean1.DataBean.ContentBean> getList() {
        return list;
    }

    public void setList(List<SurveyBean1.DataBean.ContentBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i-1);
    }

    @Override
    public long getItemId(int i) {
        return i-1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_1;
        }
        return TYPE_2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder2 = new ViewHolder();
        ViewHolder holder1 = new ViewHolder();
        int type = getItemViewType(i);
        if (view == null) {
            switch (type){
                case TYPE_1:
                    view = LayoutInflater.from(context).inflate(R.layout.lv_toplayout, viewGroup, false);
                    holder1.iv = (ImageView) view.findViewById(R.id.top_iv);
                    view.setTag(holder1);
                    break;
                case TYPE_2:
                    view = LayoutInflater.from(context).inflate(R.layout.top_rv_item, viewGroup, false);
                    holder2.name = (TextView) view.findViewById(R.id.top_rv_name);
                    holder2.time = (TextView) view.findViewById(R.id.top_rv_time);
                    holder2.require_age = (TextView) view.findViewById(R.id.require_age);
                    holder2.require_sex = (TextView) view.findViewById(R.id.require_sex);
                    holder2.require_location = (TextView) view.findViewById(R.id.require_location);
                    holder2.money = (TextView) view.findViewById(R.id.top_rv_money);
                    view.setTag(holder2);
                    view.setTag(R.id.tag_first,list.get(i-1));
                    MyLog.info(R.id.tag_first+"");
                    break;
            }

        } else {
            switch (type){
                case TYPE_2:
                    holder2 = (ViewHolder) view.getTag();
                    break;
                case TYPE_1:
                    holder1 = (ViewHolder) view.getTag();
                    break;

            }

        }
        switch (type){
            case TYPE_2:
                holder2.name.setText(list.get(i-1).getName());
                holder2.time.setText("发布时间:"+ DateUtils.getDateToString1(list.get(i-1).getCreateTime()));
                holder2.money.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                holder2.money.setText("￥"+list.get(i-1).getBonus());
                if (list.get(i-1).getAge()!=null) {
                    holder2.require_age.setText("要求:年龄:" +list.get(i-1).getAge());
                } else {
                    holder2.require_age.setText("要求:年龄:不限");
                }

                if (list.get(i-1).getSex()== 0) {
                    holder2.require_sex.setText(",性别:女");
                }
                if (list.get(i-1).getSex()== 1){
                    holder2.require_sex.setText(",性别:男");
                }
                if (list.get(i-1).getSex()== 3) {
                    holder2.require_sex.setText(",性别:不限");
                }

                if (list.get(i-1).getCity()!=null || list.get(i-1).getCity().equals("")) {
                    holder2.require_location.setText(",坐标:" + list.get(i-1).getCity());
                } else {
                    holder2.require_location.setText(",坐标:不限");
                }
                break;
            case TYPE_1:

                holder1.iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, MyApplyActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
        }

        return view;
    }

    class ViewHolder {
        TextView name, time, money, require_age, require_sex, require_location;
        ImageView iv;
    }
}
