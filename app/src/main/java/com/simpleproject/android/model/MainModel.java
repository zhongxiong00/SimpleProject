package com.simpleproject.android.model;

import android.content.Context;


import com.simpleproject.android.Constants;
import com.simpleproject.android.base.model.BaseModel;
import com.simpleproject.android.base.network.GsonHttpCallback;
import com.simpleproject.android.bean.UpdateBean;
import com.simpleproject.android.http.HttpClient;

import java.util.HashMap;
import java.util.Map;


public class MainModel extends BaseModel {


    public void checkUpdate(Context c, GsonHttpCallback<UpdateBean> callback) {
        Map param = new HashMap();
        HttpClient.getInstance().postWithTag(c, Constants.URLConstants.CHECK_UPDATE, param, new HashMap<String, String>(), callback);
    }

}
