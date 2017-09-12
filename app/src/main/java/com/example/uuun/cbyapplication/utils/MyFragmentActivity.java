package com.example.uuun.cbyapplication.utils;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.fragment.AccountFragment;
import com.example.uuun.cbyapplication.fragment.MyFragment;
import com.example.uuun.cbyapplication.fragment.ShopFragment;
import com.example.uuun.cbyapplication.fragment.TopFragment;

/**
 * Created by uuun on 2017/7/20.
 */

public class MyFragmentActivity extends FragmentActivity {
    private FragmentManager manager;
    private Fragment[] fgs = {new TopFragment(), new AccountFragment()
            , new ShopFragment(), new MyFragment()};
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.home_layout, fgs[0]).commit();


        for (int i = 1; i < 4; i++) {
            manager.beginTransaction().add(R.id.home_layout, fgs[i]).hide(fgs[i]).commit();
        }
    }
}
