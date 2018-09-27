package com.simpleproject.android.base.presenter;

import android.content.Context;
import android.support.annotation.CallSuper;


import com.simpleproject.android.base.model.BaseModel;
import com.simpleproject.android.base.ui.iview.IBaseListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseListPresenter<V extends IBaseListView> extends BaseLoadingPresenter<V> {
    protected BaseModel model;
    protected Map<String, String> mParam;

    public <T> void loadData(Context c, final List<T> mData, Class<T> clazz, final boolean isRefresh, boolean isReload) {
        mParam.put("currentPage", getcurrentPage(isRefresh, mData) + "");
        mParam.put("pageSize", getPageCount() + "");
        addParam(mParam);
        if (isViewAttached() && isReload) {
            getView().showLoadingData();
        }

    }


    protected abstract String getUrl();//数据请求地址

    protected abstract int getPageCount();//每页的数据

    protected abstract void addParam(Map<String, String> map);//自动放置页码和页数，只需添加额外参数


    protected int getcurrentPage(boolean isRefresh, List data) {
        if (data == null || isRefresh) {
            return 1;
        }
        return data.size() / getPageCount() + 1;
    }

    protected boolean hasMoreData(List returnData) {
        if (returnData == null || returnData.size() < getPageCount()) { //没有更多数据
            return false;
        }
        return true;
    }


    @CallSuper
    @Override
    public void createModel() {
        model = new BaseModel();
        mParam = new HashMap();
    }
}
