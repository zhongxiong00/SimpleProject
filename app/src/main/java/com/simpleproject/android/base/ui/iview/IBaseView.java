package com.simpleproject.android.base.ui.iview;

import mvp.IMvpBaseView;

public interface IBaseView extends IMvpBaseView {
    void showLoadingDialog(boolean cancelAble);

    void dismissLoadingDialog();

    void showToastMsg(String msg);
}
