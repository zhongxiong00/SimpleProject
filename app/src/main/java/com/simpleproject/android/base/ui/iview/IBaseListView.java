package com.simpleproject.android.base.ui.iview;

public interface IBaseListView extends ILoadingView {
    void refreshComplete();

    void hasMoreData(boolean hasMore, boolean isSuccess);

    void refreshData();
}
