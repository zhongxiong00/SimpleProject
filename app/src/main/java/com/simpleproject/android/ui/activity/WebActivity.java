package com.simpleproject.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.simpleproject.android.R;
import com.simpleproject.android.base.ui.activity.BaseActivity;
import com.simpleproject.android.h5.X5WebView;
import com.simpleproject.android.h5.inreractive.IH5InvokeAndroid;
import com.simpleproject.android.presenter.WebPresenter;
import com.simpleproject.android.ui.iview.IWebView;

import log.LogUtils;

/**
 * 作者： 钟雄辉
 * 时间： 2018/7/12
 * 描述： H5加载Activity
 * 注意：与H5交互的方法写到H5Interactive
 **/
public class WebActivity extends BaseActivity<WebPresenter> implements IWebView, IH5InvokeAndroid {
    private X5WebView mWebView;
    private String mUrl, mJS;
    private static final String PASS_URL = "PASS_URL";
    private static final String PASS_JS = "PASS_JS";

    public static void startThere(Context c, String url) { //进入页面不需要调js
        Intent intent = new Intent(c, WebActivity.class);
        intent.putExtra(PASS_URL, url);
        c.startActivity(intent);
    }

    public static void startThere(Context c, String url, String js) { //进入页面需要调用js方法
        Intent intent = new Intent(c, WebActivity.class);
        intent.putExtra(PASS_URL, url);
        intent.putExtra(PASS_JS, js);
        c.startActivity(intent);
    }


    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void onDestroy() {
        //释放资源
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }


    @Override
    protected void initLayoutView(Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(PASS_URL);
        LogUtils.e("打开的H5地址： " + mUrl);
        mJS = getIntent().getStringExtra(PASS_JS);
        LogUtils.e("打开的H5Js： " + mJS);
        mWebView = findViewById(R.id.wv_content);
        mWebView.setJs(mJS);
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onBackPressed() {
        presenter.goBack(mUrl);
    }

    @Override
    public void goBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }
}
