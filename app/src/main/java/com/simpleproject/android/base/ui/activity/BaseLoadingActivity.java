package com.simpleproject.android.base.ui.activity;

import android.os.Bundle;
import android.util.SparseArray;
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
    private TextView mTvEmpty;
    private View mLoadingView, mReloadView, mEmptyView;
    private FrameLayout mFlContent;
    private SparseArray<View> mViewMap;

    @Override
    final protected void initContentView(Bundle savedInstanceState) {
        mViewMap = new SparseArray<>();
        mVbReload = findViewById(R.id.vb_reload);
        mVbEmpty = findViewById(R.id.vb_empty);
        mLoadingView = findViewById(R.id.ly_loading);
        mFlContent = findViewById(R.id.fl_content_sub);
        mViewMap.put(1, mLoadingView);
        mViewMap.put(4, mFlContent);
        View viewContent = View.inflate(this, dataLayoutId(), null);
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
        if (mReloadView == null) {
            mReloadView = mVbReload.inflate();
            mViewMap.put(2, mReloadView);
            mReloadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reloadData();
                }
            });
        }
        showTarget(2);
    }

    @Override
    public void showLoadingData() {
        showTarget(1);
    }

    @Override
    public void loadingDataComplete() {
        showTarget(4);
    }

    @Override
    public void showEmpty() {
        if (mEmptyView == null) {
            mEmptyView = mVbEmpty.inflate();
            mViewMap.put(3, mEmptyView);
        }
        if (mTvEmpty == null) {
            mTvEmpty = mEmptyView.findViewById(R.id.tv_empty_hint);
        }
        showTarget(3);
    }

    @Override
    public void showEmpty(String hint) {
        showEmpty();
        if (mTvEmpty != null) {
            mTvEmpty.setText(hint);
        }
    }

    private void showTarget(int index) {
        for (int i = 0; i < mViewMap.size(); i++) {
            if (mViewMap.keyAt(i) != index) {
                mViewMap.get(mViewMap.keyAt(i)).setVisibility(View.GONE);
            } else {
                mViewMap.get(index).setVisibility(View.VISIBLE);
            }
        }

    }

    protected abstract int dataLayoutId();//展示数据的布局文件

    protected abstract void initView();//初始化你内容的布局

    protected abstract void loadData();//加载数据

    protected abstract void reloadData();//点击重新加载按钮后回调此方法
}
