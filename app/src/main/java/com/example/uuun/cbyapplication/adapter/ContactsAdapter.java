package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.ContactsBean;

import java.util.List;

/**
 * Created by uuun on 2017/5/19.
 */

public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private List<ContactsBean> list;

    public ContactsAdapter(Context context, List<ContactsBean> list) {
        this.context = context;
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
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.contacts_item_layout,viewGroup,false);
            holder.name = (TextView) view.findViewById(R.id.contacts_name);
            holder.number = (TextView) view.findViewById(R.id.contacts_number);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
       holder.name.setText(list.get(i).getName());
        holder.number.setText(list.get(i).getNumber());
        return view;
    }
    class ViewHolder{
        private TextView name;
        private TextView number;
    }
}
