package com.simpleproject.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.simpleproject.android.tinker.TinkerManger;
import com.simpleproject.android.utils.ChannelUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.yingu.baselib.CommonUtils;

import encrypt.AES_ECB;
import imageloader.GlideImageLoader;
import imageloader.ImageLoader;
import log.LogUtils;
import me.jessyan.autosize.AutoSizeConfig;

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
public class FruitApplication extends DefaultApplicationLike {

    public FruitApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initUtils();
        initImageLoader();
        initLeakCanary();
        // initEncrypt();
        initX5WebKit();
        //initBugly();
        AutoSizeConfig.getInstance().setLog(true);
        LogUtils.e(AutoSizeConfig.getInstance().getDesignWidthInDp() + "");
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerManger.installedTinker(this);
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplication(), Constants.KeyConstants.BUGLY_KEY, BuildConfig.DEBUG);
        CrashReport.setIsDevelopmentDevice(getApplication(), BuildConfig.DEBUG);
        CrashReport.setAppChannel(getApplication(), ChannelUtils.readChannel(getApplication()));
    }


    private void initUtils() {
        CommonUtils.init(getApplication());
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
        ImageLoader.getInstance().setImageLoader(new GlideImageLoader()); //使用glide加载图片
    }

    private void clearImageCache() {
        ImageLoader.getInstance().clearMemoryCache(getApplication());
    }
}
