package com.simpleproject.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.simpleproject.android.service.LaunchService;
import com.simpleproject.android.tinker.TinkerManger;
import com.simpleproject.android.utils.ChannelUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import encrypt.AES_ECB;
import imageloader.ImageLoader;
import me.jessyan.autosize.AutoSizeConfig;
import utils.AppUtils;
import utils.CommonUtils;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述：
 **/
@DefaultLifeCycle(
        application = ".HotFixApplication",                       //application类名
        flags = ShareConstants.TINKER_ENABLE_ALL,                 //tinkerFlags
        loaderClass = "com.tencent.tinker.loader.TinkerLoader",   //loaderClassName, 我们这里使用默认即可!
        loadVerifyFlag = false)                                   //tinkerLoadVerifyFlag
public class LaunchApplication extends DefaultApplicationLike {

    public LaunchApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerManger.installedTinker(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppUtils.isMyAppProcess(getApplication())) { //是自己的进程才做初始化工作
            initInMainThread(); //一些不耗时的操作可以在主线程初始化
            initSyncThread();//一些耗时的操作，可以异步初始化的操作放到异步线程
        }
    }

    private void initInMainThread() {
        initUtils();
        initImageLoader();
        initLeakCanary();
        initAutoAdapter();
        // initEncrypt();//如需要AES加密
        //initBugly(); //如需要集成bugly异常上报
    }

    private void initSyncThread() {
        Intent intent = new Intent(getApplication(), LaunchService.class);
        getApplication().startService(intent);
    }


    private void initAutoAdapter() {
        AutoSizeConfig.getInstance().setLog(BuildConfig.DEBUG);
    }


    private void initBugly() {
        CrashReport.initCrashReport(getApplication(), Constants.KeyConstants.BUGLY_KEY, BuildConfig.DEBUG);
        CrashReport.setIsDevelopmentDevice(getApplication(), BuildConfig.DEBUG);
        CrashReport.setAppChannel(getApplication(), ChannelUtils.readChannel(getApplication()));
    }


    private void initUtils() {
        CommonUtils.init(getApplication());
    }


    private void initEncrypt() {
        AES_ECB.init(Constants.KeyConstants.AES_KEY);
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(getApplication())) {
            return;
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        clearImageCache();
    }

    private void initImageLoader() {
        ImageLoader.getInstance().getStrategy().init();
    }

    private void clearImageCache() {
        ImageLoader.getInstance().getStrategy().clearMemoryCache(getApplication());
    }
}
