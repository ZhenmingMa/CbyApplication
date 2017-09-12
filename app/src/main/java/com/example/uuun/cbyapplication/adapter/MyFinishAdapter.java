package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.SurveyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/29.
 */

public class MyFinishAdapter extends BaseAdapter {
    private List<SurveyBean> list = new ArrayList<>();
    private Context context;

    public MyFinishAdapter(Context context) {
        this.context = context;
    }

    public List<SurveyBean> getList() {
        return list;
    }

    public void setList(List<SurveyBean> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if(view == null){
           view = LayoutInflater.from(context).inflate(R.layout.myfinish_item,viewGroup,false);
            holder.name = (TextView) view.findViewById(R.id.myfinish_item_name);
            holder.money = (TextView) view.findViewById(R.id.myfinish_item_money);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(i).getName());
        holder.money.setText(list.get(i).getBonus()+"");
        return view;
    }
    class ViewHolder{
        TextView name,money;
    }
}
