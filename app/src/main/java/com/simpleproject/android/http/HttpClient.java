package com.simpleproject.android.http;

import com.simpleproject.android.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import http.OkHttpUtils;
import http.builder.PostFormBuilder;
import http.callback.Callback;
import http.callback.FileCallBack;
import log.LogUtils;
import okhttp3.Response;


public class HttpClient {

    public static HttpClient getInstance() {
        return RestClientHolder.INSTANCE;
    }

    private static class RestClientHolder {
        public static HttpClient INSTANCE = new HttpClient();
    }

    public void getWithTag(Object o, String url, Map p, Callback callback) {
        OkHttpUtils
                .get()
                .url(getAbsoluteUrl(url))
                .params(p)
                .tag(o)
                .build()
                .execute(callback);
    }

    /**
     * 同步http post请求
     *
     * @param o
     * @param url
     * @param params
     * @return
     */
    public Response postSync(Object o, String url, Map params) {
        Response response = null;
        try {
            response = OkHttpUtils
                    .post()
                    .url(getAbsoluteUrl(url))
                    .tag(o)
                    .params(params)
                    .build()
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 带tag的post请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public void postWithTag(Object o, String url, Map params, Map<String, String> headParam, Callback callback) {
        basePost(o, getAbsoluteUrl(url), params, headParam, callback);
    }

    public void postWithTag(Object o, String baseUrl, String url, Map params, Map<String, String> headParam, Callback callback) {
        basePost(o, baseUrl + url, params, headParam, callback);
    }

    private void basePost(Object activity, String url,
                          Map params, Map<String, String> headParam, Callback callback) {
        PostFormBuilder postFormBuilder;
        postFormBuilder =
                OkHttpUtils.post()
                        .url(url)
                        .params(params);
        if (headParam != null) {
            Set<String> keys = headParam.keySet();
            for (String key : keys) {
                postFormBuilder.addHeader(key, headParam.get(key));
            }
        }
        LogUtils.e("接口请求地址： " + url);
        if (params != null) {
            LogUtils.e("接口传参body:" + params.toString());
        }
        if (headParam != null) {
            LogUtils.e("接口传参header:" + headParam.toString());
        }
        if (activity != null)
            postFormBuilder.tag(activity);

        postFormBuilder
                .build()
                .execute(callback);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     * @param callback
     */

    public void postFile(Object o, String url, File file, String token, Callback callback) {
        LogUtils.e("接口传参token:" + token);
        OkHttpUtils.post()
                .url(getAbsoluteUrl(url))
                .addFile("file", file.getName(), file)
                .addHeader("token", token)
                .addHeader("Content-type", "multipart/form-data")
                .addHeader("enctype", "multipart/form-data")
                .tag(o)
                .build()
                .execute(callback);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param callback 需要传入保存的文件夹以及文件名
     */
    public void downloadFile(String url, FileCallBack callback) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(callback);
    }

    /**
     * 根据tag取消请求
     *
     * @param o
     */
    public void cancelRequest(Object o) {
        OkHttpUtils.getInstance().cancelTag(o);
    }


    private String getAbsoluteUrl(String relativeUrl) {
        return BuildConfig.BASE_SERVER_URL + relativeUrl;
    }

}
