package com.simpleproject.android.base.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.simpleproject.android.base.presenter.BasePresenter;
import com.simpleproject.android.base.ui.iview.IBaseView;
import com.simpleproject.android.http.HttpClient;
import com.simpleproject.android.manager.AppManager;
import com.simpleproject.android.widgets.dialog.LoadingDialog;

import mvp.MvpBaseActivity;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/27
 * 描述： 放置公共业务的Activity
 **/
public abstract class BaseActivity<P extends BasePresenter> extends MvpBaseActivity<P> implements IBaseView {
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(layoutId());
        initLayoutView(savedInstanceState);
    }

    protected abstract int layoutId();

    protected abstract void initLayoutView(Bundle savedInstanceState);

    @Override
    public void showLoadingDialog(boolean cacelAble) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
        mLoadingDialog.show(this, cacelAble);
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.disMiss(this);
        }
    }

    @Override
    public void showToastMsg(String msg) {
       com.yingu.baselib.ui.ToastUtil.showToastShort(this, msg);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        HttpClient.getInstance().cancelRequest(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

}
