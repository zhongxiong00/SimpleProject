package com.simpleproject.android.base.presenter;


import com.simpleproject.android.base.ui.iview.ILoadingView;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/29
 * 描述： 有网络加载数据的页面presenter需要继承此presenter
 **/

public abstract class BaseLoadingPresenter<V extends ILoadingView> extends BasePresenter<V> {

}
