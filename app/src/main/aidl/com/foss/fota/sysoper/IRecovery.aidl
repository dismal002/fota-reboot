package com.foss.fota.sysoper;

import com.foss.fota.sysoper.IRecoveryCallback;
import com.foss.fota.sysoper.RecoveryParams;

interface IRecovery {
    int recovery(String str);
    int recovery_ab_install(in RecoveryParams recoveryParams);
    int recovery_ab_binder(IRecoveryCallback iRecoveryCallback);
    int reboot();
}
