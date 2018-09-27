package com.simpleproject.android.base.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.simpleproject.android.R;
import com.simpleproject.android.base.adapter.BaseRecyclerAdapter;
import com.simpleproject.android.base.presenter.BaseListPresenter;
import com.simpleproject.android.base.ui.iview.IBaseListView;
import com.simpleproject.android.widgets.refresh.RefreshLoadMoreLayout;

import log.LogUtils;


public abstract class BaseListViewFragment<P extends BaseListPresenter, A extends BaseRecyclerAdapter> extends BaseLoadingFragment<P> implements IBaseListView {
    protected A mAdapter;
    protected RefreshLoadMoreLayout mRefreshView;
    protected RecyclerView mRvList;

    @Override
    protected final int dataLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void reloadData() {
        initContentData();
    }

    @CallSuper
    @Override
    protected final void initContentView(View view) {
        mRefreshView = view.findViewById(R.id.v_refresh);
        mRvList = view.findViewById(R.id.rv_list);
        mAdapter = getAdapter();
        mRvList.setAdapter(mAdapter);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @CallSuper
    @Override
    public void refreshComplete() {
        if (mRefreshView != null) {
            mRefreshView.finishRefresh();
        }
    }

    @CallSuper
    @Override
    public void hasMoreData(boolean hasMore, boolean isSuccess) {
        if (mRefreshView != null) {
            mRefreshView.finishLoadMore(isSuccess);
            mRefreshView.setEnableLoadMore(hasMore);
        }
    }

    @CallSuper
    @Override
    public void refreshData() {
        LogUtils.e("刷新数据");
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    protected abstract A getAdapter();

}
