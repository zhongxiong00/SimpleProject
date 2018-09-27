package com.simpleproject.android.widgets.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * 作者： 钟雄辉
 * 时间： 2018/7/3
 * 描述： 下拉刷新
 **/
public class RefreshLayout extends SmartRefreshLayout {
    public RefreshLayout(Context context) {
        super(context);
        initHeader();
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeader();
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeader();
    }

    private void initHeader() {
        setEnableLoadMore(false);
        setRefreshHeader(new RefreshHead(getContext()));
    }
}
