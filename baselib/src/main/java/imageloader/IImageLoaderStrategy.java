package imageloader;

import android.content.Context;
import android.widget.ImageView;


/**
 * 作者： 钟雄辉
 * 时间： 2019/6/19
 * 描述： 图片加载策略接口
 **/
public interface IImageLoaderStrategy {
    void init();

    //加载普通图片
    void loadImage(Context context, ImageView imageView, String url, int placeImageId);

    //加载普通图片带回调
    void loadImage(Context context, ImageView imageView, String url, int placeImageId, BitmapCallBack calBack);

    //加载gif
    void loadGif(Context context, ImageView imageView, String url);

    /**
     * 加载图片高度自适应图片本身时
     * ImageView用如下两个属性配置
     * android:adjustViewBounds="true"
     * android:scaleType="fitXY"
     */
    void loadWrapHeightImage(Context context, ImageView imageView, String url, int placeImageId);

    // 清理内存缓存
    void clearMemoryCache(Context c);

    //清理磁盘缓存
    void clearDiskCache(Context c);

}
