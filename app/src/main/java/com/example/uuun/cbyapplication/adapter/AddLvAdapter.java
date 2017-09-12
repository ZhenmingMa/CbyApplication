package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/22.
 */

public class AddLvAdapter extends BaseAdapter {
    private Context context;
    private List<Province> list = new ArrayList<Province>();

    public AddLvAdapter(Context context) {
        this.context = context;
    }

    public List<Province> getList() {
        return list;
    }

    public void setList(List<Province> list) {
        this.list = list;
        notifyDataSetChanged();
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
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.add_pop_item, viewGroup, false);
            holder.tv = (TextView) view.findViewById(R.id.add_pop_item_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(list.get(i).getProvinceName());
        return view;
    }

    class ViewHolder {
        TextView tv;
    }
}
