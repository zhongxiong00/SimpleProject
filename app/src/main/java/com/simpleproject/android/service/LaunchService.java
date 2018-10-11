package com.simpleproject.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.tencent.smtt.sdk.QbSdk;

import log.LogUtils;

/**
 * 作者： 钟雄辉
 * 时间： 2018/10/10
 * 描述： 一些耗时的启动操作可以放这里面进行
 **/
public class LaunchService extends IntentService {
    public LaunchService() {
        super("LaunchService");
    }

    public LaunchService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initX5WebKit();
    }

    private void initX5WebKit() {//底层会自己开启异步线程初始化，如需兼容视频播放，参考文档重新配置
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                LogUtils.e("X5WebView  onViewInitFinished: " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                LogUtils.e("X5WebView  onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplication(), cb);
    }
}
