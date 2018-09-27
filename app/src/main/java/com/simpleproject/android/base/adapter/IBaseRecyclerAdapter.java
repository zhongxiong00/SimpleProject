package com.simpleproject.android.base.adapter;

import android.support.annotation.Nullable;
import android.view.View;

/**
*作者： 钟雄辉
*时间： 2018/9/26
*描述：
**/
public interface IBaseRecyclerAdapter<D> {

    void setOnItemClickListener(@Nullable OnItemClickListener<D> listener);

    interface OnItemClickListener<T> {
        void onItemClick(View view, T item, int position);
    }
}
