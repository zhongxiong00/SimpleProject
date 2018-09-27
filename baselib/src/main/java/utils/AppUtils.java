package utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.List;

/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述： App相关
 **/
public class AppUtils {

    public static boolean isMyAppProcess(Context c) { //是否是在自己app的进程
        if (c == null) {
            return false;
        }
        return c.getPackageName().equals(getCurrentProcessName(c));
    }

    public static String getCurrentProcessName(Context c) { //获取当前进程名
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = manager.getRunningAppProcesses();
        if (runningApps == null) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return "";
    }

    public static boolean isActivityForeground(Context context, String className) {//判断Activity是否在前台展示
        if (context == null || TextUtils.isEmpty(className)) { //需要权限<uses-permission android:name="android.permission.GET_TASKS"/>
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isBackground(Context context) { //判断app是否在前台
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                    .getRunningAppProcesses();
            if (appProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    if (appProcess.processName.equals(context.getPackageName())) {
                        if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void openSystemSetting(Context c) {//打开系统设置页面
        if (c != null) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
            c.startActivity(intent);
        }
    }

    public static boolean isIntentAvailable(Context context, Intent intent) { //判断intent是否可用
        return context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null;
    }
}
