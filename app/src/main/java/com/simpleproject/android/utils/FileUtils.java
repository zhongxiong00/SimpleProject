package com.simpleproject.android.utils;

import android.os.Environment;

import com.simpleproject.android.Constants;

import utils.CommonUtils;


public class FileUtils {
    public static String getCacheDir() {
        return Environment.getExternalStorageDirectory() + Constants.FileConstants.CACHE_PACKET_DIR;
    }

    public static String getPatchDir() {
        return CommonUtils.getApp().getCacheDir() + Constants.FileConstants.PATCH_DIR;
    }

    public static String getPatchRealPath() {
        return getPatchDir() + "/patch.apk";
    }
}
