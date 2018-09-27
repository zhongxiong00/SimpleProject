package com.simpleproject.android.h5;

import com.simpleproject.android.h5.SafeWebViewBridge.InjectedChromeClient;

public class AppChromeClient extends InjectedChromeClient {
    public AppChromeClient(String injectedName, Class injectedCls) {
        super(injectedName, injectedCls);
    }

}
