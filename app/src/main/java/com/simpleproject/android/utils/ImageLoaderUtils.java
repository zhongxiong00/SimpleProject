package com.simpleproject.android.utils;

import android.content.Context;
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

    public static void loadImage(LoaderOptions options) {
        ImageLoader.getInstance().loadOptions(options);
    }


    //加载普通图片
    public static void loadImage(Context c, String url, ImageView imageView) {
        ImageLoader.getInstance().loadOptions(createLoaderOptions(c, url, false, imageView));
    }

    //加载圆形图片
    public static void loadCirclrImage(Context c, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        ImageLoader.getInstance().loadOptions(createLoaderOptions(c, url, true, imageView));
    }

    private static LoaderOptions createLoaderOptions(Context c, String url, boolean isCirlce, ImageView imageView) {
        LoaderOptions options = generateCommonOptions(c, url, imageView);
        options = isCirlce ? options.asCircleBitmap() : options;
        return options;
    }

    private static LoaderOptions generateCommonOptions(Context c, String url, ImageView imageView) {
        LoaderOptions options = new LoaderOptions(url);
        options.context = c;
        options.targetView = imageView;
        options.isCenterCrop = true;
        options.isBitmap = true;
        // options.placeholderResId = R.drawable.ic_default_img; //设置加载中的图
        // options.errorResId = R.drawable.ic_default_img;//设置加载失败的图
        return options;
    }
}
