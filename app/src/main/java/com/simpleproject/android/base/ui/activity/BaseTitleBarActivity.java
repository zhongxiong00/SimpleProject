package com.simpleproject.android.base.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.simpleproject.android.R;
import com.simpleproject.android.base.presenter.BasePresenter;
import com.simpleproject.android.widgets.TitleBar;


/**
 * 作者： 钟雄辉
 * 时间： 2018/7/2
 * 描述： 固定标题栏的Activity
 * 用法： 有标题栏无Loading页面的Activity继承此Activity
 **/
public abstract class BaseTitleBarActivity<P extends BasePresenter> extends BaseActivity<P> {
    protected TitleBar mTitleBar;
    private LinearLayout mLlRoot;


    @Override
    protected final void initLayoutView(Bundle savedInstanceState) {
        mTitleBar = findViewById(R.id.titlebar_base);
        mLlRoot = findViewById(R.id.ll_root);
        View view = View.inflate(this, contentLayoutId(), null);
        mLlRoot.addView(view);
        mTitleBar.setOnLeftBackClickListener(new View.OnClickListener() { //默认直接关闭页面
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initTitleBar();
        initContentView(savedInstanceState);
    }

    @Override
    protected final int layoutId() {
        return R.layout.activity_base_titlebar;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected abstract int contentLayoutId();

    protected abstract void initTitleBar();//初始化标题栏

    protected abstract void initContentView(Bundle savedInstanceState);
}
