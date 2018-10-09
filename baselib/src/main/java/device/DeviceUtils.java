package device;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import ui.ToastUtil;
import utils.AppUtils;

/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述：设备相关类
 **/
public class DeviceUtils {
    private static String deviceResolvingPower;
    private static String androidId;

    /**
     * @return 设配唯一标识 AndroidId
     */
    public static String getUniqueId(Context context) {
        if (!TextUtils.isEmpty(androidId)) {
            return androidId;
        }
        androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public static void callPhone(Context c, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (AppUtils.isIntentAvailable(c, intent)) {
            c.startActivity(intent);
        } else {
            ToastUtil.showToastShort(c, "您的设备无法拨打电话");
        }
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * @return 手机分辨率
     */
    public static String getResolvingPower(Context c) {
        if (!TextUtils.isEmpty(deviceResolvingPower)) {
            return deviceResolvingPower;
        }
        if (c != null) {
            DisplayMetrics dm = c.getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;
            int screenHeight = dm.heightPixels;
            deviceResolvingPower = screenWidth + "*" + screenHeight;
        }
        return deviceResolvingPower;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;

    }
}