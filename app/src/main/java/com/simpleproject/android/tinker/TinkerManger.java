package com.simpleproject.android.tinker;

import android.Manifest;
import android.text.TextUtils;

import com.simpleproject.android.tinker.SampleResultService;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;
import utils.CommonUtils;

public class TinkerManger {
    /**
     * 完成Tinker初始化
     */
    public static void installedTinker(ApplicationLike applicationLike) {
        if (!Tinker.isTinkerInstalled()) {
            LoadReporter loadReporter = new DefaultLoadReporter(applicationLike.getApplication());
            PatchReporter patchReporter = new DefaultPatchReporter(applicationLike.getApplication());
            PatchListener patchListener = new DefaultPatchListener(applicationLike.getApplication());
            AbstractPatch upgradePatchProcessor = new UpgradePatch();
            TinkerInstaller.install(applicationLike,
                    loadReporter, patchReporter, patchListener,
                    SampleResultService.class, upgradePatchProcessor);
        }
    }

    public static String getPatchVersion() {
        Map map = Tinker.with(CommonUtils.getApp()).getTinkerLoadResultIfPresent().packageConfig;
        if (map != null) {
            String verion = (String) map.get("patchVersion");
            if (TextUtils.isEmpty(verion)) {
                return "";
            }
            return verion;
        }
        return "";
    }

    public static void rollBackPatch() {//清除所有补丁并杀死进程
        Tinker.with(CommonUtils.getApp()).rollbackPatch();
    }

    public static void clearAllPatch() { //清除所有补丁
        Tinker.with(CommonUtils.getApp()).cleanPatch();
    }

    public static void clearPatchByVersion(String version) {//清除某个版本补丁
        Tinker.with(CommonUtils.getApp()).cleanPatchByVersion(version);
    }

    /**
     * 完成patch文件的加载
     *
     * @param path 补丁文件路径
     */
    public static void loadPatch(String path) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Tinker.isTinkerInstalled() && EasyPermissions.hasPermissions(CommonUtils.getApp(), perms)) {//是否已经安装过
            TinkerInstaller.onReceiveUpgradePatch(CommonUtils.getApp(), path);
        }
    }
}
