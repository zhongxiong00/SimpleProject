package com.simpleproject.android.widgets;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simpleproject.android.R;


/**
 * 作者： 钟雄辉
 * 时间： 2018/7/11
 * 描述： 主界面底部单个tab
 **/
public class MainTabView extends RelativeLayout {
    private TextView mTvTitle;
    private ImageView mIcon;

    public MainTabView(Context context) {
        this(context, null);
    }

    public MainTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public MainTabView setText(String txt) {
        mTvTitle.setText(txt);
        return this;
    }

    public MainTabView setIconRecource(@DrawableRes int selectorId) {
        mIcon.setImageResource(selectorId);
        return this;
    }


    private void init() {
        inflate(getContext(), R.layout.item_main_tab, this);
        mTvTitle = findViewById(R.id.tv_tabname);
        mIcon = findViewById(R.id.iv_tabicon);
    }
}
