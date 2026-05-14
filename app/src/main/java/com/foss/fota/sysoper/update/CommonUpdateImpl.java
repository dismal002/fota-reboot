package com.foss.fota.sysoper.update;

import android.content.Context;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.util.Log;
import com.foss.fota.sysoper.IRecoveryCallback;
import com.foss.fota.sysoper.RecoveryParams;
import com.android.internal.app.IntentForwarderActivity;
import java.io.File;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class CommonUpdateImpl implements UpdateInterface {
    @Override // com.foss.fota.sysoper.update.UpdateInterface
    public int update_ab(Context context, RecoveryParams recoveryParams) {
        return 0;
    }

    @Override // com.foss.fota.sysoper.update.UpdateInterface
    public int update_ab_binder(IRecoveryCallback iRecoveryCallback) {
        return 0;
    }

    @Override // com.foss.fota.sysoper.update.UpdateInterface
    public int update(Context context, String str) {
        File file = new File(str);
        ((PowerManager) context.getSystemService("power")).newWakeLock(1, "FOTASys").acquire(5000L);
        try {
            Log.i(IntentForwarderActivity.TAG, "RecoveryService:!!! Update System !!!");
            RecoverySystem.installPackage(context, file);
            Log.i(IntentForwarderActivity.TAG, "RecoveryService:Still running after update system?!");
            return 1;
        } catch (Exception e) {
            Log.e(IntentForwarderActivity.TAG, "RecoveryService:Can't perform update system, IOException = " + e.getMessage());
            return -1;
        }
    }
}
