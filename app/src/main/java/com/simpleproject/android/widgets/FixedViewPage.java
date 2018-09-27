package com.simpleproject.android.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述： 不可左右滑动的ViewPage
 **/
public class FixedViewPage extends ViewPager {
    public FixedViewPage(@NonNull Context context) {
        super(context);
    }

    public FixedViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

}
