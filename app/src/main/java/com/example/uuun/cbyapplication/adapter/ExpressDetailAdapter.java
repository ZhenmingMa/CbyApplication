package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.ExpressDetailBean;

import java.util.List;

/**
 * Created by uuun on 2017/6/5.
 */

public class ExpressDetailAdapter extends BaseAdapter {
    private Context context;
    private List<ExpressDetailBean> list;

    public ExpressDetailAdapter(Context context) {
        this.context = context;
    }

    public List<ExpressDetailBean> getList() {
        return list;
    }

    public void setList(List<ExpressDetailBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.detail_lv_item,viewGroup,false);

            holder.detail = (TextView) view.findViewById(R.id.detail_tv);
            holder.date = (TextView) view.findViewById(R.id.detail_date);
            holder.time = (TextView) view.findViewById(R.id.detail_time);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.date.setText(list.get(i).getDate());
        holder.time.setText(list.get(i).getTime());
        holder.detail.setText(list.get(i).getExpressDetail());
        return view;
    }
    class ViewHolder{
        TextView time,detail,date;
    }
}
