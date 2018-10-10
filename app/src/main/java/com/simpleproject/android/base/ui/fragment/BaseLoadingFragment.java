package com.simpleproject.android.base.ui.fragment;

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
 * 描述： 需要从网络加载数据的页面继承此Fragment
 **/

public abstract class BaseLoadingFragment<P extends BaseLoadingPresenter> extends BaseFragment<P> implements ILoadingView {
    private ViewStub mVbReload, mVbEmpty;
    private FrameLayout mFlContent;
    private View mLoadingView;
    private TextView mTvEmpty;
    private View mReloadView, mEmptyView;
    private SparseArray<View> mViewMap;

    @Override
    public int getLayoutId() {
        return R.layout.layout_base_loading;
    }

    @Override
    protected final void initView(View view) {
        mViewMap = new SparseArray<>();
        mVbReload = view.findViewById(R.id.vb_reload);
        mVbEmpty = view.findViewById(R.id.vb_empty);
        mLoadingView = view.findViewById(R.id.ly_loading);
        mFlContent = view.findViewById(R.id.fl_content_sub);
        mViewMap.put(1, mLoadingView);
        mViewMap.put(4, mFlContent);
        View viewContent = View.inflate(getContext(), dataLayoutId(), null);
        mFlContent.addView(viewContent);
        mFlContent.setVisibility(View.GONE);
        initContentView(viewContent);
        initContentData();
    }

    @Override
    public final void showReload() {
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
    public final void showLoadingData() {
        showTarget(1);
    }

    @Override
    public final void loadingDataComplete() {
        showTarget(4);
    }

    @Override
    public final void showEmpty() {
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
    public final void showEmpty(String hint) {
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

    protected abstract void initContentView(View view);//初始化页面View

    protected abstract void initContentData();//初始化页面数据

    protected abstract void reloadData();//点击重新加载按钮后回调此方法
}
