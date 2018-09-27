package com.simpleproject.android.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<D, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> implements IBaseRecyclerAdapter<D> {

    protected Context mContext;
    protected List<D> mData;
    protected OnItemClickListener<D> mOnItemClickListener;


    public BaseRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseRecyclerAdapter(Context mContext, List<D> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setData(List<D> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutItemId(viewType), parent, false);
        return getHolder(view, viewType);
    }

    public abstract VH getHolder(View view, int viewType);

    public void setOnItemClickListener(OnItemClickListener<D> listener) {
        mOnItemClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public abstract int layoutItemId(int viewType);

    public D getItem(int pos) {
        if (mData != null && mData.size() > pos) {
            return mData.get(pos);
        }
        return null;
    }


}
