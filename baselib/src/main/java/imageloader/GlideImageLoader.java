package imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.Transition;

class GlideImageLoader implements IImageLoaderStrategy {

    @Override
    public void init() {  //Glide不需要做初始化，会自动读取GlideModelConfig初始化

    }

    @Override
    public void loadImage(Context context, ImageView imageView, String url, int placeImageId) {
       Glide.with(context)
                .asBitmap()
                .load(url)
                .placeholder(placeImageId)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, ImageView imageView, String url, int placeImageId, final BitmapCallBack callBack) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .placeholder(placeImageId)
                .centerCrop()
                .into(new SimpleBitmapCallback<Bitmap>() {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        if (callBack != null) {
                            callBack.onBitmapFailed(new Exception("下载失败"));
                        }
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (callBack != null) {
                            callBack.onBitmapLoaded(resource);
                        }
                    }
                });
    }

    @Override
    public void loadGif(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .asGif()
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadWrapHeightImage(Context context, ImageView imageView, String url, int placeImageId) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .placeholder(placeImageId)
                .fitCenter()
                .into(imageView);
    }


    @Override
    public void clearMemoryCache(Context c) {
        GlideApp.get(c).clearMemory();
    }

    @Override
    public void clearDiskCache(Context c) {
        GlideApp.get(c).clearDiskCache();
    }


}
