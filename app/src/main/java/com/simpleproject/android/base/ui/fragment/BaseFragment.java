package com.simpleproject.android.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.simpleproject.android.base.presenter.BasePresenter;
import com.simpleproject.android.base.ui.iview.IBaseView;
import com.simpleproject.android.http.HttpClient;
import com.simpleproject.android.widgets.dialog.LoadingDialog;

import mvp.MvpBaseFragment;
import ui.ToastUtil;


public abstract class BaseFragment<P extends BasePresenter> extends MvpBaseFragment<P> implements IBaseView {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void showLoadingDialog(boolean cacelAble) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
        mLoadingDialog.show((AppCompatActivity) getActivity(), cacelAble);
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.disMiss(getActivity());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        HttpClient.getInstance().cancelRequest(this);
    }

    @Override
    public void showToastMsg(String msg) {
        ToastUtil.showToastShort(getContext(), msg);
    }

    public abstract int getLayoutId();

    protected abstract void initView(View view);
}
