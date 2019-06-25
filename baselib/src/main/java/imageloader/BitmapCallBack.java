package imageloader;

import android.graphics.Bitmap;

/**
 * 作者： 钟雄辉
 * 时间： 2018/7/16
 * 描述：
 **/


public interface BitmapCallBack {

    void onBitmapLoaded(Bitmap bitmap);

    void onBitmapFailed(Exception e);
}
