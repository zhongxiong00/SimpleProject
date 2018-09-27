package com.simpleproject.android.utils;

import android.content.Context;
import android.text.TextUtils;

import com.meituan.android.walle.WalleChannelReader;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/29
 * 描述： 渠道信息工具
 **/
public class ChannelUtils {
    private static String mChannel;

    public static final String readChannel(Context c) {
        if (!TextUtils.isEmpty(mChannel)) {
            return mChannel;
        }
        mChannel = WalleChannelReader.getChannel(c.getApplicationContext());
        if (TextUtils.isEmpty(mChannel)) {
            mChannel = "gumi";
        }
        return mChannel;
    }
}
