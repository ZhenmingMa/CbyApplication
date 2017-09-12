package com.example.uuun.cbyapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.adapter.AccountVpAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的账户
 * Created by uuun on 2017/5/27.
 * 我的账户页面
 */

public class AccountFragment extends Fragment {
    private TabLayout tab;
    private ViewPager vp;
    private View view;
    private List<Fragment> list;
    private List<String> listName;
    private AccountVpAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account,container,false);
        initView();
        return view;
    }

    private void initView() {
        tab = (TabLayout) view.findViewById(R.id.account_tabLayout);
        vp = (ViewPager) view.findViewById(R.id.account_vp);
        tab.setSelectedTabIndicatorColor(Color.parseColor("#0099FF"));
        tab.setSelectedTabIndicatorHeight(5);
        tab.setMinimumWidth(100);
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabTextColors(Color.BLACK,Color.parseColor("#0099FF"));

        listName = new ArrayList<>();
        listName.add("我的奖金");
        listName.add("我的积分");

        list = new ArrayList<>();
        list.add(new RewardFragment());
        list.add(new IntegrationFragment());

        adapter = new AccountVpAdapter(getChildFragmentManager(),list,listName);
        tab.setupWithViewPager(vp);
        vp.setAdapter(adapter);
    }
}
