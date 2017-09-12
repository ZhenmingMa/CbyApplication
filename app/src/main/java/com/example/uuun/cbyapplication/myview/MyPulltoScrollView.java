package com.example.uuun.cbyapplication.myview;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by uuun on 2017/8/10.
 */

public class MyPulltoScrollView extends PullToRefreshScrollView {
    public MyPulltoScrollView(Context context) {
        super(context);
    }

    public MyPulltoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPulltoScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public MyPulltoScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }
}
