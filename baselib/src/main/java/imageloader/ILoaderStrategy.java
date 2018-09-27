package imageloader;

import android.content.Context;

/**
 * 作者： 钟雄辉
 * 时间： 2018/7/16
 * 描述：
 **/

public interface ILoaderStrategy {
    void init();


    void loadImage(LoaderOptions options);

    /**
     * 清理内存缓存
     */
    void clearMemoryCache(Context c);

    /**
     * 清理磁盘缓存
     */
    void clearDiskCache(Context c);

}
