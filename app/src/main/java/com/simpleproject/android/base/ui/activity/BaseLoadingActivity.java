package com.simpleproject.android.base.ui.activity;

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
 * 描述： 要使用网络加载来初始化页面数据时，继承此Activity
 * 用法： 有标题栏和Loading页面的Activity继承此Activity
 * 注意： 继承此Activity，你的View接口需要继承ILoadingView
 **/
public abstract class BaseLoadingActivity<P extends BaseLoadingPresenter> extends BaseTitleBarActivity<P> implements ILoadingView {
    private ViewStub mVbReload, mVbEmpty;
    private FrameLayout mFlContent;
    private TextView mTvReload;
    private TextView mTvEmpty;
    private View mLoadingView, mReloadView, mEmptyView;

    @Override
    final protected void initContentView() {
        mVbReload = findViewById(R.id.vb_reload);
        mFlContent = findViewById(R.id.fl_content_sub);
        mVbEmpty = findViewById(R.id.vb_empty);
        mLoadingView = findViewById(R.id.ly_loading);
        View viewContent = View.inflate(this, dataLayoutId(), null);
        mFlContent.removeAllViews();
        mFlContent.addView(viewContent);
        mFlContent.setVisibility(View.GONE);
        initView();
        loadData();
    }


    @Override
    final protected int contentLayoutId() {
        return R.layout.layout_base_loading;
    }

    @Override
    public void showReload() {
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
    public void showLoadingData() {
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
    public void loadingDataComplete() {
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
    public void showEmpty() {
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
    public void showEmpty(String hint) {
        showEmpty();
        if (mTvEmpty != null) {
            mTvEmpty.setText(hint);
        }
    }

    protected abstract int dataLayoutId();//展示数据的布局文件

    protected abstract void initView();//初始化你内容的布局

    protected abstract void loadData();//加载数据

    protected abstract void reloadData();//点击重新加载按钮后回调此方法
}
