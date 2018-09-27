package com.simpleproject.android.manager;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.simpleproject.android.Constants;
import com.simpleproject.android.http.HttpClient;
import com.simpleproject.android.widgets.dialog.AppUpdateDialog;
import com.simpleproject.android.widgets.dialog.AppUpdateProgressDialog;
import com.yingu.baselib.CommonUtils;

import java.io.File;

import file.ApkUtils;
import http.callback.FileCallBack;
import log.LogUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 作者： 钟雄辉
 * 时间： 2018/7/30
 * 描述： App更新管理
 **/
public class UpdateManager {
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private Activity mContext;
    private FragmentManager mFragmentManager;
    private String mSaveDir, mDownUrl;
    private boolean mIsForce;
    private AppUpdateDialog mHintUpdateDialog;
    private AppUpdateProgressDialog mHintUpdateProgressDialog;

    public UpdateManager(Activity context, FragmentManager manager, String saveUrl, String url, boolean isForce) {
        mContext = context;
        mFragmentManager = manager;
        mSaveDir = saveUrl;
        mDownUrl = url;
        mIsForce = isForce;
        mHintUpdateDialog = AppUpdateDialog.newInstance(mIsForce);
        mHintUpdateProgressDialog = new AppUpdateProgressDialog();
        LogUtils.e("缓存路径： " + mSaveDir);
    }

    public void showUpdateDialog() {
        if (mHintUpdateDialog != null) {
            mHintUpdateDialog.show(mFragmentManager, "update");
            mHintUpdateDialog.setOnUpdateClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHintUpdateDialog.dismiss();
                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) { //先检查sd卡
                        if (EasyPermissions.hasPermissions(mContext, perms)) { //检查存储权限
                            onPermissGranted();
                        } else {
                            EasyPermissions.requestPermissions(mContext, "Gu迷需要您授予存储权限才能正常更新",
                                    Constants.PermissionConstants.PERMISSION_STORAGE, perms);
                        }
                    } else {
                        com.yingu.baselib.ui.ToastUtil.showToastShort(CommonUtils.getApp(), "sd卡未挂载");
                    }

                }
            });
        }
    }

    public void onPermissGranted() {
        if (mHintUpdateProgressDialog != null) {
            mHintUpdateProgressDialog.setProgressMax(100);
            mHintUpdateProgressDialog.show(mFragmentManager, "progress");
        }
        HttpClient.getInstance().downloadFile(mDownUrl, new FileCallBack(mSaveDir, "gumi_new.apk") {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (mHintUpdateProgressDialog != null) {
                    mHintUpdateProgressDialog.dismiss();
                }
                com.yingu.baselib.ui.ToastUtil.showToastShort(CommonUtils.getApp(), "网络异常，请检查您的网络再更新");
            }

            @Override
            public void onResponse(File response, int id) {
                if (mHintUpdateProgressDialog != null) {
                    mHintUpdateProgressDialog.dismiss();
                }
                ApkUtils.installApk(mContext, response.getAbsolutePath());
            }


            @Override
            public void inProgress(float progress, long total, int id) { //主线程
                if (mHintUpdateProgressDialog != null) {
                    mHintUpdateProgressDialog.setProgress((int) (100 * progress));
                }
            }
        });
    }
}
