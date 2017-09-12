package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.ExpressBean;
import com.example.uuun.cbyapplication.myview.My2ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/5.
 */

public class ExpressLvAdapter extends BaseAdapter {
    private List<ExpressBean> list = new ArrayList<>();
    private Context context;
    private ExpressDetailAdapter adapter;
    private boolean flag = false;

    public ExpressLvAdapter( Context context) {
        this.context = context;
    }

    public List<ExpressBean> getList() {
        return list;
    }

    public void setList(List<ExpressBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.express_lv_item,viewGroup,false);
            holder.state = (TextView) view.findViewById(R.id.express_lv_state);
            holder.company = (TextView) view.findViewById(R.id.express_lv_company);
            holder.number = (TextView) view.findViewById(R.id.express_lv_number);
            holder.phone = (TextView) view.findViewById(R.id.express_lv_phone);
            holder.showDetail = (TextView) view.findViewById(R.id.express_lv_showdetail);
            holder.lv = (My2ListView) view.findViewById(R.id.express_detail);
            holder.iv = (ImageView) view.findViewById(R.id.express_lv_iv);
            holder.state1 = (TextView) view.findViewById(R.id.express_state1);
            holder.sanjiaoxing = (ImageView) view.findViewById(R.id.express_sanjiaoxing);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.state.setText("待收货");
        holder.state1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        holder.company.setText("承运公司: "+list.get(i).getCompany());
        holder.number.setText("运单编号: "+list.get(i).getNumber());
        holder.phone.setText("官方电话: "+list.get(i).getPhone());
        holder.iv.setImageResource(R.mipmap.ic_launcher);
        adapter = new ExpressDetailAdapter(context);
        adapter.setList(list.get(i).getExpressDetail());
        holder.lv.setAdapter(adapter);
        final ViewHolder finalHolder = holder;
        final ViewHolder finalHolder1 = holder;
            holder.showDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!flag){//展开状态
                        finalHolder.lv.setVisibility(View.VISIBLE);
                        finalHolder1.showDetail.setText("收起");
                        finalHolder1.sanjiaoxing.setImageResource(R.mipmap.express_up);
                        flag = true ;
                    }else{//收起状态
                        finalHolder.lv.setVisibility(View.GONE);
                        finalHolder1.showDetail.setText("展开");
                        finalHolder1.sanjiaoxing.setImageResource(R.mipmap.express_dowm);
                        flag = false;
                    }

                }
            });
        return view;
    }
    class ViewHolder{
        private TextView state,company,number,phone,showDetail,state1;
        private My2ListView lv;
        private ImageView iv,sanjiaoxing;
    }

}
