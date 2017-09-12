package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.Message;
import com.example.uuun.cbyapplication.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/7/25.
 */

public class NewsAdapter extends BaseAdapter {
    private List<Message> list = new ArrayList<>();
    private Context context;
    public NewsAdapter(Context context) {
        this.context = context;
    }

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.news_lv_item,viewGroup,false);
            holder.news1 = (TextView) view.findViewById(R.id.news_tv);
            holder.time = (TextView) view.findViewById(R.id.news_time);
            holder.news = (TextView) view.findViewById(R.id.news_tv1);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.news.setText(list.get(i).getContent());
        String time = DateUtils.getDateToString(list.get(i).getDate());
        holder.time.setText(time);

        if (list.get(i).getState().equals("0")){//新消息
            holder.news1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.news.setTextColor(Color.parseColor("#4F4F4F"));
            holder.news1.setVisibility(View.VISIBLE);
        }else{//旧消息
            holder.news1.setVisibility(View.INVISIBLE);
            holder.news.setTextColor(Color.parseColor("#7E7E7E"));
        }


//        holder.news.setText(list.get(i).getHello()+",您提现到支付宝账号"+list.get(i).getNumber()+"的"
//        +list.get(i).getMoney()+"元现金红包已"+list.get(i).getResult()+"!");
//        holder.time.setText(list.get(i).getTime());
//        holder.news1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            holder.news.setTextColor(Color.parseColor("#4F4F4F"));
//            holder.news1.setVisibility(View.VISIBLE);
            //SPUtil.setNewsFlag(context,true);

//        EventBus.getDefault().post(
//                new FirstEvent("news"));
//        holder1 = holder;



        return view;
    }
    class ViewHolder{
        TextView time,news1,news;

    }
}



