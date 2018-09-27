package com.simpleproject.android.base.ui.fragment;

import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.simpleproject.android.R;
import com.simpleproject.android.base.presenter.BaseLoadingPresenter;
import com.simpleproject.android.base.ui.iview.ILoadingView;


/**
 * 作者： 钟雄辉
 * 时间： 2018/6/29
 * 描述： 需要从网络加载数据的页面继承此Fragment
 **/

public abstract class BaseLoadingFragment<P extends BaseLoadingPresenter> extends BaseFragment<P> implements ILoadingView {
    private ViewStub mVbReload, mVbEmpty;
    private FrameLayout mFlContent;
    private View mLoadingView;
    private TextView mTvReload;
    private TextView mTvEmpty;
    private View mReloadView, mEmptyView;

    @Override
    public int getLayoutId() {
        return R.layout.layout_base_loading;
    }

    @Override
    protected final void initView(View view) {
        mVbReload = view.findViewById(R.id.vb_reload);
        mVbEmpty = view.findViewById( R.id.vb_empty);
        mLoadingView = view.findViewById( R.id.ly_loading);
        mFlContent = view.findViewById(R.id.fl_content_sub);
        View viewContent = View.inflate(getContext(), dataLayoutId(), null);
        mFlContent.removeAllViews();
        mFlContent.addView(viewContent);
        mFlContent.setVisibility(View.GONE);
        initContentView(viewContent);
        initContentData();
    }

    @Override
    public final void showReload() {
        mLoadingView.setVisibility(View.GONE);
        if (mFlContent != null) {
            mFlContent.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mReloadView == null) {
            mReloadView = mVbReload.inflate();
        }
        mReloadView.setVisibility(View.VISIBLE);
        mTvReload = mReloadView.findViewById(R.id.tv_reload);
        mTvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadData();
            }
        });
    }

    @Override
    public final void showLoadingData() {
        mLoadingView.setVisibility(View.VISIBLE);
        if (mFlContent != null) {
            mFlContent.setVisibility(View.GONE);
        }
        if (mReloadView != null) {
            mReloadView.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public final void loadingDataComplete() {
        mLoadingView.setVisibility(View.GONE);
        if (mReloadView != null) {
            mReloadView.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        mFlContent.setVisibility(View.VISIBLE);
    }

    @Override
    public final void showEmpty() {
        mLoadingView.setVisibility(View.GONE);
        if (mReloadView != null) {
            mReloadView.setVisibility(View.GONE);
        }
        if (mFlContent != null) {
            mFlContent.setVisibility(View.GONE);
        }
        if (mEmptyView == null) {
            mEmptyView = mVbEmpty.inflate();
        }
        mTvEmpty = mEmptyView.findViewById(R.id.tv_empty_hint);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public final void showEmpty(String hint) {
        showEmpty();
        if (mTvEmpty != null) {
            mTvEmpty.setText(hint);
        }
    }

    protected abstract int dataLayoutId();//展示数据的布局文件

    protected abstract void initContentView(View view);//初始化页面View

    protected abstract void initContentData();//初始化页面数据

    protected abstract void reloadData();//点击重新加载按钮后回调此方法
}
