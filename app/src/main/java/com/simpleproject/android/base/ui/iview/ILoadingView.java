package com.simpleproject.android.base.ui.iview;


/**
 * 作者： 钟雄辉
 * 时间： 2018/6/29
 * 描述： 需要网络加载页面数据的需要继承此View
 **/
public interface ILoadingView extends IBaseView {
    void showReload();

    void showLoadingData();

    void showEmpty(); //默认显示暂无信息

    void showEmpty(String hint);

    void loadingDataComplete();

}

