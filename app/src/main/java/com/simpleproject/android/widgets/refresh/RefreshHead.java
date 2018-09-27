package com.simpleproject.android.widgets.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class RefreshHead extends ClassicsHeader {
    public RefreshHead(Context context) {
        super(context);
        init();
    }


    public RefreshHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 27);
        mLastUpdateText.setTextSize(TypedValue.COMPLEX_UNIT_PX,22);
    }
}
