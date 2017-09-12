package com.example.uuun.cbyapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.activity.SurveyActivity;
import com.example.uuun.cbyapplication.adapter.TopLVAdapter;
import com.example.uuun.cbyapplication.bean.SurveyBean1;
import com.example.uuun.cbyapplication.myapp.MyApp;
import com.example.uuun.cbyapplication.utils.UrlConfig;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by uuun on 2017/5/27.
 * 首页页面
 */

public class TopFragment extends Fragment {

    private PullToRefreshListView lv;
    private View view;
    private TopLVAdapter lvAdapter;
    private List<SurveyBean1.DataBean.ContentBean> content;
    private Context context = MyApp.getInstance();
    private List<SurveyBean1.DataBean.ContentBean> totalList = new ArrayList<>();
    private int page;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s = mGetData(page);
            if (TextUtils.isEmpty(s)){
                Toast.makeText(getActivity(), "获取不到更多数据了", Toast.LENGTH_SHORT).show();
            }
           // lvAdapter.notifyDataSetChanged();
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top,container,false);
        initView();
        initControl();
        return view;
    }

    private void initControl() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SurveyBean1.DataBean.ContentBean bean = lvAdapter.getList().get(i-2);
               // MyLog.info("!!!!!!!!!!!!!------------"+bean.getName());
                Intent intent = new Intent(getActivity(),SurveyActivity.class);
                intent.putExtra("SurveyBean",bean);
                startActivity(intent);
            }
        });

    }

    private void initView() {

        lv = (PullToRefreshListView) view.findViewById(R.id.home_lv);
        lvAdapter = new TopLVAdapter(context);
        page = 1;
        lv.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv.setRefreshing(true);
            }
        }, 500);

        lv.setAdapter(lvAdapter);
        mGetData(page);
        lv.setFocusable(false);
        initPTRListView();

    }
    /**
     * 设置下拉刷新的listview的动作
     */
    private void initPTRListView() {
        totalList.clear();
        //在刷新时允许继续滑动
        lv.setScrollingWhileRefreshingEnabled(true);
        //上下都可以刷新的模式。这里有两个选择：Mode.PULL_FROM_START，Mode.BOTH，PULL_FROM_END
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                totalList.clear();
                page = 1;
                mGetData(page);
                lv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                mGetData(page);
                lv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv.onRefreshComplete();
                    }
                }, 1000);
            }
        });

    }

    /**
     * 设置listview的适配器
     */


    private String mGetData(int page) {
        RequestParams params = new RequestParams(UrlConfig.URL_GETSURVEY);
        params.addQueryStringParameter("page", String.valueOf(page));
        final String[] result1 = {""};
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                result1[0] = result;
                //MyLog.info(result);
                if(!TextUtils.isEmpty(result)){
                    Gson gson = new Gson();
                    SurveyBean1 surveyBean1 = gson.fromJson(result, SurveyBean1.class);
                    SurveyBean1.DataBean data1 = surveyBean1.getData();
                    content = data1.getContent();
                    totalList.addAll(content);
                    lvAdapter.setList(totalList);
               }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                String message = ex.getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
           return result1[0];
    }
}
