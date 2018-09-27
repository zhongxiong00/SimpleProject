package com.simpleproject.android.base.model;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： 统一包装接口返回bean对象
 **/
public class ResultBean<T> {
    public int code;
    public String message;
    public T data;

    public boolean isSuccess() {
        return code == 200;
    }

}
