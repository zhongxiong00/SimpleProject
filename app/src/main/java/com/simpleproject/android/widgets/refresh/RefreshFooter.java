package com.simpleproject.android.widgets.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

public class RefreshFooter extends ClassicsFooter {
    public RefreshFooter(Context context) {
        super(context);
        init();
    }

    public RefreshFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 27);
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        boolean re = super.setNoMoreData(noMoreData);
        return re;
    }
}
