package com.yingu.baselib.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： 吐司提示管理类
 **/
public class ToastUtil {

    private static Toast mToast;

    private ToastUtil() {

    }

    private static Toast getToast(Context c) { //没有做同步，因为限制只能在主线程调用。
        if (mToast == null) {
            if (c == null) {
                throw new RuntimeException("构造 Toast的context 不能为空");
            }
            mToast = Toast.makeText(c.getApplicationContext(), "构造toast", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        return mToast;
    }


    public static void showToastShort(Context context, String text) {
        if (TextUtils.isEmpty(text))
            return;
        getToast(context).setDuration(Toast.LENGTH_SHORT);
        getToast(context).setText(text);
        getToast(context).show();
    }


    public static void showToastLong(Context context, String text) {
        if (TextUtils.isEmpty(text))
            return;
        getToast(context).setDuration(Toast.LENGTH_LONG);
        getToast(context).setText(text);
        getToast(context).show();
    }

    public static void showToastShort(Context context, int textId) {
        showToastShort(context, context.getResources().getString(textId));
    }

    public static void showToastLong(Context context, int textId) {
        showToastLong(context, context.getResources().getString(textId));
    }

}
