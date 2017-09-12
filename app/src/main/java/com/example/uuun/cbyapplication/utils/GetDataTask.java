package com.example.uuun.cbyapplication.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.uuun.cbyapplication.adapter.TopLVAdapter;
import com.example.uuun.cbyapplication.bean.SurveyBean;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by uuun on 2017/8/10.
 */

public class GetDataTask extends AsyncTask<Void, Void, Void> {

    private PullToRefreshListView mPullRefreshListView;
    private TopLVAdapter mAdapter;
    private List<SurveyBean> mListItems;
    private Context mContext;

    public GetDataTask(PullToRefreshListView listView,
                       TopLVAdapter adapter,List<SurveyBean> listItems,Context context) {
        // TODO 自动生成的构造函数存根
        mPullRefreshListView = listView;
        mAdapter = adapter;
        mListItems = listItems;
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //模拟请求
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO 自动生成的方法存根
        super.onPostExecute(result);
        //得到当前的模式
        PullToRefreshBase.Mode mode = mPullRefreshListView.getCurrentMode();
        if(mode == PullToRefreshBase.Mode.PULL_FROM_START) {
            //下拉刷新
            //mListItems.addFirst"这是刷新出来的数据");
            Toast.makeText(mContext,"这是下拉刷新!!!!!!!",Toast.LENGTH_SHORT).show();
        }
        else {
            //上拉加载
            //mListItems.addLast("这是刷新出来的数据");
            Toast.makeText(mContext,"这是上拉加载!!!!!!!",Toast.LENGTH_SHORT).show();
        }
        // 通知数据改变了
        mAdapter.notifyDataSetChanged();
        // 加载完成后停止刷新
        mPullRefreshListView.onRefreshComplete();

    }



}
