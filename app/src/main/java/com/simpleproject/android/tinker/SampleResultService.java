package com.simpleproject.android.tinker;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerServiceInternals;

import java.io.File;

import device.ScreenUtils;

public class SampleResultService extends DefaultTinkerResultService {
    @Override
    public void onPatchResult(final PatchResult result) {
        if (result == null) {
            return;
        }
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());
        if (result.isSuccess) {
            deleteRawPatchFile(new File(result.rawPatchFilePath));

            if (checkIfNeedKill(result)) {
                new ScreenUtils.ScreenState(getApplicationContext(), new ScreenUtils.ScreenState.IOnScreenOff() {
                    @Override
                    public void onScreenOff() { //锁屏的时候杀死进程,为了让用户尽快重启
                        killProcess();
                    }
                });
            } else {
            }
        }
    }

    /**
     * you can restart your process through service or broadcast
     */
    private void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}