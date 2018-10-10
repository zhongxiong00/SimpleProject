package com.simpleproject.android;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： 所有常量放此类
 **/
public class Constants {

    public static class PermissionConstants { //申请权限code
        public static final int PERMISSION_STORAGE = 1;
    }


    public static class H5Constants {

    }

    public static class KeyConstants { //秘钥相关
        public static final String AES_KEY = "";
        public static final String APK_SIGN = "";
        public static final String BUGLY_KEY = "";
    }

    public static class URLConstants { //url常量
        public static final String CHECK_UPDATE = "";
    }

    public static class FileConstants { //文件相关常量
        public static String CACHE_PACKET_DIR = "/mycache"; //普通文件缓存路径
        public static final String PATCH_DIR = "/mypatch";//补丁包
    }

    public static class SpConstants {
    }
}
