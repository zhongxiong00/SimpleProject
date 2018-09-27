package imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import log.LogUtils;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： Glide图片加载器，具体使用请用ImageLoaderUtils
 **/
public class GlideImageLoader implements ILoaderStrategy {

    @Override
    public void init() {  //Glide不需要做初始化，会自动读取GlideModelConfig初始化

    }


    @Override
    public void clearMemoryCache(Context c) {
        GlideApp.get(c).clearMemory();
    }

    @Override
    public void clearDiskCache(Context c) {
        GlideApp.get(c).clearDiskCache();
    }


    @Override
    public void loadImage(final LoaderOptions options) {
        if (options == null) {
            throw new RuntimeException("LoaderOptions 不能为空");
        }
        if (options.context == null) {
            throw new RuntimeException("Context 不能为空");
        }
        GlideRequests manager = GlideApp.with(options.context);
        if (options.isBitmap) {
            GlideRequest<Bitmap> bitmapManager = manager.asBitmap();
            if (options.isCenterCrop) {
                bitmapManager = bitmapManager.centerCrop();
            }
            if (options.isCenterInside) {
                bitmapManager = bitmapManager.centerInside();
            }
            if (options.isFitCenter) {
                bitmapManager = bitmapManager.fitCenter();
            }
            if (options.isCircle) {
                bitmapManager = bitmapManager.circleCrop();
            }
            if (options.placeholderResId != 0) {
                bitmapManager = bitmapManager.placeholder(options.placeholderResId);
            }
            if (options.placeholder != null) {
                bitmapManager = bitmapManager.placeholder(options.placeholder);
            }
            if (options.errorResId != 0) {
                bitmapManager = bitmapManager.error(options.errorResId);
            }
            if (options.url != null) {
                bitmapManager = bitmapManager.load(options.url);
            } else if (options.file != null) {
                bitmapManager = bitmapManager.load(options.file);
            } else if (options.drawableResId != 0) {
                bitmapManager = bitmapManager.load(options.drawableResId);
            } else if (options.uri != null) {
                bitmapManager = bitmapManager.load(options.uri);
            } else {
                bitmapManager = bitmapManager.load("");
            }
            if (options.targetView != null) {
                LogUtils.e("加载图片： " + options.url);
                bitmapManager.into((ImageView) options.targetView);
            } else if (options.callBack != null) {
                bitmapManager.into(new SimpleBitmapCallback<Bitmap>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        options.callBack.onBitmapFailed(new Exception("下载失败"));
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition transition) {
                        options.callBack.onBitmapLoaded(resource);
                    }
                });


                bitmapManager.into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    }
                });
            }
        } else if (options.isGif) { //项目上用到再添加
            GlideRequest<GifDrawable> girRequest = manager.asGif();
        }

    }
}
