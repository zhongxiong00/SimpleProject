package com.simpleproject.android.h5.inreractive;

import com.tencent.smtt.sdk.WebView;

/**
 * 作者： 钟雄辉
 * 时间： 2018/10/11
 * 描述： H5调用Android方法代理类
 **/
public class H5InvokeAndroidProxy implements IH5InvokeAndroid {
    private WebView mWebView;

    public H5InvokeAndroidProxy(WebView webView) {
        this.mWebView = webView;
    }

    @Override
    public void test() {

    }
}
