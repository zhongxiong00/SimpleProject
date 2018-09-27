package com.simpleproject.android.presenter;

import android.content.Context;
import android.text.TextUtils;


import com.simpleproject.android.Constants;
import com.simpleproject.android.base.model.ResultBean;
import com.simpleproject.android.base.network.GsonHttpCallback;
import com.simpleproject.android.base.presenter.BasePresenter;
import com.simpleproject.android.bean.UpdateBean;
import com.simpleproject.android.model.MainModel;
import com.simpleproject.android.ui.iview.IMainView;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import file.ApkUtils;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： 主界面presenter
 **/

public class MainPresenter extends BasePresenter<IMainView> {

    private MainModel mModel;

    public void checkSign(Context c) {
        if (!ApkUtils.check(c, Constants.KeyConstants.APK_SIGN)) {
            if (isViewAttached()) {
                getView().showToastMsg("请到应用市场下载正版apk使用");
                //杀死该应用进程
                Executors.newScheduledThreadPool(1).schedule(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }, 1, TimeUnit.SECONDS);

            }
        }
    }


    public void checkUpdate(Context c) {
        mModel.checkUpdate(c, new GsonHttpCallback<UpdateBean>() {
            @Override
            protected void error(String msg) {

            }

            @Override
            protected void response(ResultBean<UpdateBean> t) {
                if (t.isSuccess() && t.data != null && !TextUtils.isEmpty(t.data.newApkUrl)) {
                    if (isViewAttached()) {
                        getView().showUpdateDialog(t.data.newApkUrl, t.data.isForceUpdate);
                    }
                }
            }
        });
    }

    @Override

    public void createModel() {
        mModel = new MainModel();
    }
}
