package imageloader;

import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： 使用者需要设置LoaderOptions
 **/

public class ImageLoader {
    private ILoaderStrategy sLoader;
    private static ImageLoader sInstance;

    private ImageLoader() {
    }

    //单例模式
    public static ImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    //若切换其它图片加载框架，可以实现一键替换
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }

    //提供实时替换图片加载框架的接口
    public void setImageLoader(ILoaderStrategy loader) {
        if (loader != null) {
            sLoader = loader;
            sLoader.init();
        }
    }

    public LoaderOptions load(String path) {
        return new LoaderOptions(path);
    }

    public LoaderOptions load(int drawable) {
        return new LoaderOptions(drawable);
    }

    public LoaderOptions load(File file) {
        return new LoaderOptions(file);
    }

    public LoaderOptions load(Uri uri) {
        return new LoaderOptions(uri);
    }

    public void loadOptions(LoaderOptions options) {
        sLoader.loadImage(options);
    }

    public void clearMemoryCache(Context c) {
        sLoader.clearMemoryCache(c);
    }

    public void clearDiskCache(Context c) {
        sLoader.clearDiskCache(c);
    }
}
