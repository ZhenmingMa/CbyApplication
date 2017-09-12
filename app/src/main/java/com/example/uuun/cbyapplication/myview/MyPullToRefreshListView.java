package com.example.uuun.cbyapplication.myview;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by uuun on 2017/8/10.
 */

public class MyPullToRefreshListView extends PullToRefreshListView {
    public MyPullToRefreshListView(Context context) {
        super(context);
    }

    public MyPullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPullToRefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    public MyPullToRefreshListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }
}
