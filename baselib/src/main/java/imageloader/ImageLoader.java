package imageloader;

import android.content.Context;

/**
 * 作者： 钟雄辉
 * 时间： 2019/6/19
 * 描述： 策略模式，无缝切换图片框架
 **/
public class ImageLoader {
    private IImageLoaderStrategy mLoader;
    private volatile static ImageLoader INSTANCE;

    private ImageLoader() {
    }

    //单例模式
    public static ImageLoader getInstance() {
        if (INSTANCE == null) {
            synchronized (ImageLoader.class) {
                if (INSTANCE == null) {
                    //若切换其它图片加载框架，可以实现一键替换
                    INSTANCE = new ImageLoader();
                    INSTANCE.setImageLoader(new GlideImageLoader());
                }
            }
        }
        return INSTANCE;
    }

    public IImageLoaderStrategy getStrategy() {
        return mLoader;
    }

    //提供实时替换图片加载框架的接口
    private void setImageLoader(IImageLoaderStrategy loader) {
        if (loader != null) {
            mLoader = loader;
            mLoader.init();
        }
    }
}
