package com.simpleproject.android.h5;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.simpleproject.android.R;
import com.simpleproject.android.h5.inreractive.H5Interactive;
import com.simpleproject.android.h5.inreractive.H5InvokeAndroidProxy;
import com.simpleproject.android.h5.inreractive.IH5InvokeAndroid;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import log.LogUtils;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * 作者： 钟雄辉
 * 时间： 2018/9/27
 * 描述： 腾讯X5WebView封装
 **/
public class X5WebView extends WebView implements IH5InvokeAndroid {
    private H5InvokeAndroidProxy mInvokeProxy;
    private ProgressBar progressbar;  //进度条
    private String mJS;
    private int progressHeight;  //进度条的高度，默认10px

    public void setJs(String js) {
        mJS = js;
    }

    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
            if (!TextUtils.isEmpty(mJS)) {
                H5Utils.invokeJs(X5WebView.this, mJS);
            }
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }
    };
    private AppChromeClient chromeClient = new AppChromeClient("jsAction", H5Interactive.class) {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE) {
                    progressbar.setVisibility(VISIBLE);
                }
                progressbar.setProgress(newProgress);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

            LogUtils.e("h5调用弹窗：" + message);
            return super.onJsAlert(view, url, message, result);
        }
    };

    public X5WebView(Context context) {
        super(context);
        initWebViewSettings(context);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initWebViewSettings(context);
    }

    private void initWebViewSettings(Context context) {
        mInvokeProxy = new H5InvokeAndroidProxy(this);
        progressHeight = AutoSizeUtils.dp2px(context, 3.0f);
        //创建进度条
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        //设置加载进度条的高度
        progressbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, progressHeight));
        Drawable drawable = context.getResources().getDrawable(R.drawable.shape_webview_progress);
        progressbar.setProgressDrawable(drawable);
        //添加进度到WebView
        addView(progressbar);
        setWebViewClient(client);
        setWebChromeClient(chromeClient);
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }


    @Override
    public void test() {
        mInvokeProxy.test();
    }
}
