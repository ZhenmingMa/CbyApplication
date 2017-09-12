package com.example.uuun.cbyapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.WriteAddressActivity;
import com.example.uuun.cbyapplication.bean.Address;
import com.example.uuun.cbyapplication.myview.YtfjrProcessDialog;
import com.example.uuun.cbyapplication.utils.MyLog;
import com.example.uuun.cbyapplication.utils.SPUtil;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/1.
 */

public class AddressLvAdapter extends BaseAdapter {

    List<Address> list = new ArrayList<>();
    Context context;

    public AddressLvAdapter( Context context) {
        this.context = context;
    }


    public List<Address> getList() {
        return list;
    }

    public void setList(List<Address> list) {

        this.list.clear();
        MyLog.info("listq清空");
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
            view = LayoutInflater.from(context).inflate(R.layout.address_lv_item, viewGroup, false);
            holder.name = (TextView) view.findViewById(R.id.address_item_name);
            holder.province = (TextView) view.findViewById(R.id.address_province);
            holder.city = (TextView) view.findViewById(R.id.address_city);
            holder.detail = (TextView) view.findViewById(R.id.address_detail);
            holder.phone = (TextView) view.findViewById(R.id.address_item_phone);
            holder.delete = (TextView) view.findViewById(R.id.address_item_delete);
            holder.write = (TextView) view.findViewById(R.id.address_item_write);
           holder.setDefault = (TextView) view.findViewById(R.id.address_lv_item_check_tv);
            holder.district = (TextView) view.findViewById(R.id.address_district);
            holder.check = (ImageView) view.findViewById(R.id.address_lv_item_check);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(list.get(i).getName());
        holder.phone.setText(list.get(i).getPhone());
        holder.province.setText(list.get(i).getProvince());
        holder.city.setText(list.get(i).getCity());
        holder.detail.setText(list.get(i).getDetailAddress());
        holder.district.setText(list.get(i).getDistrict());

        if (list.get(i).isCurrent()){
            holder.check.setImageResource(R.mipmap.checked);
        }else{
            holder.check.setImageResource(R.mipmap.nocheck);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("确认删除?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                deleteAddress(i);
                                list.remove(i);
                                notifyDataSetChanged();
                            }

                        });
                builder.show();
            }
        });

        //跳转到编辑地址页面
        holder.write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,WriteAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address",list.get(i));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });



        holder.setDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.info("点击了按钮");
                YtfjrProcessDialog.showLoading(context,true);
                RequestParams params = new RequestParams(UrlConfig.URL_SETDEFAULTADD);
                params.addQueryStringParameter("token",SPUtil.getToken(context));
                params.addQueryStringParameter("id",String.valueOf(list.get(i).getId()));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            MyLog.info(result);
                            JSONObject obj = new JSONObject(result);
                            int code = obj.getInt("code");
                            if(code==0){
                                JSONArray jsonArray = obj.getJSONArray("data");
                                Gson gson = new Gson();
                                List<Address> list_address = new ArrayList<Address>();
                                for (int i = 0; i <= jsonArray.length() - 1; i++) {
                                    JSONObject json1 = jsonArray.getJSONObject(i);
                                    Address address = gson.fromJson(json1.toString(), Address.class);
                                    MyLog.info(address.getId() + "  " + i);
                                    list_address.add(address);
                                }
                                MyLog.info("list size ="+list_address.size());
                                Toast.makeText(context,"设置成功",Toast.LENGTH_SHORT).show();

                                setList(list_address);
                                notifyDataSetChanged();


                            }else{
                                Toast.makeText(context,"请检查您的网络",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

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
        });
        return view;
    }

    class ViewHolder {
        private TextView name, phone,province,city,detail,district,write, delete,setDefault;
        private ImageView check;

    }
    public void deleteAddress(int index){
        YtfjrProcessDialog.showLoading(context,true);
        Address address = list.get(index);
        RequestParams params = new RequestParams(UrlConfig.URL_DELETEADDRESS);
        params.addQueryStringParameter("token", SPUtil.getToken(context));
        params.addQueryStringParameter("id",address.getId()+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(context, R.string.network_connettions_error, Toast.LENGTH_SHORT).show();
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

}
