package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.IntegrateActivity;
import com.example.uuun.cbyapplication.bean.ShopBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by uuun on 2017/6/1.
 */

public class ShopRvAdapter extends RecyclerView.Adapter<ShopRvAdapter.MyViewHolder> implements View.OnClickListener{
    private List<ShopBean> list = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener = null;
    private Context context;

    public ShopRvAdapter(Context context) {
        this.context = context;
    }

    public List<ShopBean> getList() {
        return list;
    }

    public void setList(List<ShopBean> list) {
        this.list = list;
    }

    @Override
    public ShopRvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_rv_item,parent,false);
        ShopRvAdapter.MyViewHolder holder = new ShopRvAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    @Override
    public void onBindViewHolder(ShopRvAdapter.MyViewHolder holder, int position) {
        final ShopBean bean = list.get(position);
        Glide.with(context).load(bean.getImg()).into(holder.image);
        holder.number.setText(bean.getPrice()+"积分");
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date date = new Date();
        date.setTime(bean.getTime());
        String time = sdf.format(date);
        holder.time.setText(time+"准时抢购");
        holder.name.setText(bean.getName());
        holder.itemView.setTag(position);
        holder.integrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,IntegrateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopbean",bean);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name,time,number,integrate;
        private ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.shop_item_name);
            time = (TextView) itemView.findViewById(R.id.shop_item_time);
            number = (TextView) itemView.findViewById(R.id.shop_item_number);
            image = (ImageView) itemView.findViewById(R.id.shop_item_iv);
            integrate = (TextView) itemView.findViewById(R.id.shop_item_integrate);
        }
    }
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
