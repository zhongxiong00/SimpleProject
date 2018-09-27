package com.simpleproject.android.widgets.refresh;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 作者： 钟雄辉
 * 时间： 2018/7/3
 * 描述： 下拉刷新，上拉加载更多
 **/
public class RefreshLoadMoreLayout extends RefreshLayout {
    public RefreshLoadMoreLayout(Context context) {
        super(context);
        initFooter();
    }

    public RefreshLoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFooter();
    }

    public RefreshLoadMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFooter();
    }

    private void initFooter() {
        setEnableLoadMore(true);
        setRefreshFooter(new RefreshFooter(getContext()));
    }

}
