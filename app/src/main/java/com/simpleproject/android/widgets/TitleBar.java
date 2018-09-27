package com.simpleproject.android.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simpleproject.android.R;


/**
 * 作者： 钟雄辉
 * 时间： 2018/7/12
 * 描述： 标题栏
 **/
public class TitleBar extends RelativeLayout {
    private RelativeLayout mRlLeft;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvRight;

    public TitleBar(Context context) {
        super(context);
        init();
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.widget_titlebar, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRlLeft = findViewById(R.id.rl_title_left);
        mIvBack = findViewById(R.id.iv_left);
        mTvTitle = findViewById(R.id.tv_title_mid);
        mTvRight = findViewById(R.id.tv_title_right);
    }

    public void setTitleText(String title) {
        mTvTitle.setText(title);
    }

    public void setOnLeftBackClickListener(OnClickListener l) {
        mRlLeft.setVisibility(VISIBLE);
        mRlLeft.setOnClickListener(l);
    }

    public void setOnRightTextAndClickListner(String text, OnClickListener l,Boolean boldOrNormal) {
        mTvRight.setVisibility(VISIBLE);
        mTvRight.setText(text);
        mTvRight.setOnClickListener(l);
        if(boldOrNormal){
            mTvRight .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

}
