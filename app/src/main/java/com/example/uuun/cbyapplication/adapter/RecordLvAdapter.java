package com.example.uuun.cbyapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.ExchangeRecord;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.DateUtils;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/2.
 */

public class RecordLvAdapter extends BaseAdapter {
    List<ExchangeRecord.DataBean> list = new ArrayList<>();
    Context context;

    public RecordLvAdapter(Context context) {
        this.context = context;
    }

    public List<ExchangeRecord.DataBean> getList() {
        return list;
    }

    public void setList(List<ExchangeRecord.DataBean> list) {
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
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.record_lv_item,viewGroup,false);
            holder.color = (TextView) view.findViewById(R.id.record_lv_color);
            holder.name = (TextView) view.findViewById(R.id.record_lv_name);
            holder.number = (TextView) view.findViewById(R.id.record_lv_number);
            holder.state = (TextView) view.findViewById(R.id.record_lv_state);
            holder.time = (TextView) view.findViewById(R.id.record_lv_time);
            holder.img = (ImageView) view.findViewById(R.id.record_lv_iv);
            holder.delete = (TextView) view.findViewById(R.id.record_lv_delete);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.time.setText(DateUtils.getDateToString(list.get(i).getExchangeRecord().getTime()));
        holder.state.setText(list.get(i).getExchangeRecord().getStatus());
        holder.number.setText(list.get(i).getGoods().getPrice()+"积分");
        holder.name.setText(list.get(i).getGoods().getName());
        holder.color.setText("");
        Glide.with(context).load(list.get(i).getGoods().getImg()).into(holder.img);


        holder.name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("确认删除该订单吗?")
                       .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int position) {
                       YtfjrProcessDialog.showLoading(context,true);
                       RequestParams params = new RequestParams(UrlConfig.URL_DELETEEXCHANGERECORD);
                       params.addBodyParameter("token", SPUtil.getToken(context));
                       params.addBodyParameter("id",list.get(i).getExchangeRecord().getId()+"");
                       x.http().post(params, new Callback.CommonCallback<String>() {
                           @Override
                           public void onSuccess(String result) {
                               MyLog.info("删除成功");
                               list.remove(i);
                               notifyDataSetChanged();
                           }

                           @Override
                           public void onError(Throwable ex, boolean isOnCallback) {
                               Toast.makeText(context, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
                               MyLog.info("error");
                           }

                           @Override
                           public void onCancelled(CancelledException cex) {

                           }

                           @Override
                           public void onFinished() {
                               YtfjrProcessDialog.showLoading(context,false);
                           }
                       });





                   }
               }).setNegativeButton("取消",null);
               builder.show();
           }
       });
        return view;
    }
    class ViewHolder{
        private TextView name,number,color,state,time,delete;
        private ImageView img;

    }

}
