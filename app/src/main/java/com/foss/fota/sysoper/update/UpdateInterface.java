package com.foss.fota.sysoper.update;

import android.content.Context;
import com.foss.fota.sysoper.IRecoveryCallback;
import com.foss.fota.sysoper.RecoveryParams;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public interface UpdateInterface {
    int update(Context context, String str);

    int update_ab(Context context, RecoveryParams recoveryParams);

    int update_ab_binder(IRecoveryCallback iRecoveryCallback);
}
