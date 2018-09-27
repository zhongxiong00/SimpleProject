package com.simpleproject.android.presenter;


import com.simpleproject.android.base.presenter.BasePresenter;
import com.simpleproject.android.ui.iview.IWebView;

public class WebPresenter extends BasePresenter<IWebView> {

    @Override
    public void createModel() {

    }

    public void goBack(String url) {
        if (isViewAttached()) {
            getView().goBack();
        }
    }

}
