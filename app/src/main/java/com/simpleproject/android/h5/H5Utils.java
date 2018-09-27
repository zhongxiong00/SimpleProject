package com.simpleproject.android.h5;

import android.os.Build;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

import log.LogUtils;

public class H5Utils {
    public static void invokeJs(WebView webView, String js) {
        LogUtils.e("调用js: " + js);
        if (webView != null) {
            if (Build.VERSION.SDK_INT < 19) {
                webView.loadUrl(js);
            } else {
                webView.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            }
        }
    }
}
