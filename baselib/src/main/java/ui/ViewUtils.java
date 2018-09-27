package com.yingu.baselib.ui;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述： View操作相关
 **/
public class ViewUtils {
    public static void setTextViewDrawableLeft(TextView tv, Drawable drawable) {
        if (tv != null && drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tv.setCompoundDrawables(drawable, null, null, null);
        }
    }

}
