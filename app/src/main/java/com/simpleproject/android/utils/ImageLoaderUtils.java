package com.simpleproject.android.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.simpleproject.android.R;

import imageloader.ImageLoader;
import imageloader.LoaderOptions;


/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： 图片加载类
 **/
public class ImageLoaderUtils {

    //加载普通图片
    public static void loadImage(Context c, String url, @IdRes int defaultImg, ImageView imageView) {
        ImageLoader.getInstance().loadOptions(createLoaderOptions(c, url, false, defaultImg, imageView));
    }

    //加载圆形图片
    public static void loadCirclrImage(Context c, String url, @IdRes int defaultImg, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        ImageLoader.getInstance().loadOptions(createLoaderOptions(c, url, true, defaultImg, imageView));
    }

    private static LoaderOptions createLoaderOptions(Context c, String url, boolean isCirlce, @IdRes int defaultImg, ImageView imageView) {
        LoaderOptions options = generateCommonOptions(c, url, defaultImg, imageView);
        options = isCirlce ? options.asCircleBitmap() : options;
        return options;
    }

    private static LoaderOptions generateCommonOptions(Context c, String url, @IdRes int defaultImg, ImageView imageView) {
        LoaderOptions options = new LoaderOptions(url);
        options.context = c;
        options.targetView = imageView;
        options.isCenterCrop = true;
        options.isBitmap = true;
        options.placeholderResId = defaultImg; //设置加载中的图
        options.errorResId = defaultImg;//设置加载失败的图
        return options;
    }
}
