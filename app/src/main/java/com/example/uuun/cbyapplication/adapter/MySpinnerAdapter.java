package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;

import java.util.List;

/**
 * Created by uuun on 2017/6/12.
 */

public class MySpinnerAdapter extends BaseAdapter {
    Context context;
    List<String> list;

    public MySpinnerAdapter(Context context) {
        this.context = context;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.reward_spinner_item,viewGroup,false);
            holder.tv = (TextView) view.findViewById(R.id.spinner_tv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(list.get(i)+"元");
        return view;
    }
    class ViewHolder{
        TextView tv;
    }
}
