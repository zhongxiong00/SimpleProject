package com.simpleproject.android.base.network;


import com.simpleproject.android.base.model.ResultBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import http.callback.Callback;
import http.parse.GsonUtil;
import http.parse.ParameterizedTypeImpl;
import log.LogUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： 钟雄辉
 * 时间： 2018/7/24
 * 描述：
 **/
public abstract class GsonHttpCallback<T> extends Callback<ResultBean<T>> {

    private Type mType;


    public GsonHttpCallback() {
        mType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public ResultBean<T> parseNetworkResponse(Response response, int id) throws Exception {
        String json;
        json = response.body().string();
        LogUtils.e("网络请求url: " + response.request().url());
        LogUtils.e("网络接口返回： " + json);
        ResultBean<T> t = null;

        Type type = new ParameterizedTypeImpl(ResultBean.class, new Type[]{mType});
        try {
            t = GsonUtil.getGson().fromJson(json, type);
        } catch (Exception e) {
            LogUtils.e("网络数据gson解析异常：" + e.toString());
        }
        return t;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        LogUtils.e("网络请求url: " + call.request().url());
        error("连接服务器异常");
    }

    @Override
    public void onResponse(ResultBean<T> t, int id) {
        if (t == null) {
            error("服务器出了点小问题");
        } else {
            response(t);
        }
    }

    protected abstract void error(String msg);

    protected abstract void response(ResultBean<T> t);
}
