package com.foss.fota.sysoper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.foss.fota.sysoper.IRecovery;
import com.foss.fota.sysoper.update.CustomUpdateForABImpl;
import com.foss.fota.sysoper.update.UpdateInterface;
import java.io.File;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class RecoveryService extends Service {
    private static final String TAG = "sys";
    private static UpdateInterface ota;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (intent == null || !"android.intent.action.FossFota.Recovery".equals(intent.getAction())) {
            return null;
        }
        return new Serviceimpl();
    }

    public class Serviceimpl extends IRecovery.Stub {
        public Serviceimpl() {
        }

        @Override // com.foss.fota.sysoper.IRecovery
        public int recovery(String str) throws RemoteException {
            return RecoveryService.this.EnterRecovery(str);
        }

        @Override // com.foss.fota.sysoper.IRecovery
        public int recovery_ab_install(RecoveryParams recoveryParams) throws RemoteException {
            try {
                CheckPermissionHelper.getInstance(RecoveryService.this.getApplicationContext()).checkPermission("0");
                Log.d(RecoveryService.TAG, "enter recovery_ab_install");
                if (RecoveryService.ota == null) {
                    UpdateInterface unused = RecoveryService.ota = new CustomUpdateForABImpl();
                }
                return RecoveryService.ota.update_ab(RecoveryService.this.getApplicationContext(), recoveryParams);
            } catch (SecurityException e) {
                e.printStackTrace();
                return -401;
            }
        }

        @Override // com.foss.fota.sysoper.IRecovery
        public int recovery_ab_binder(IRecoveryCallback iRecoveryCallback) throws RemoteException {
            try {
                CheckPermissionHelper.getInstance(RecoveryService.this.getApplicationContext()).checkPermission("0");
                if (RecoveryService.ota == null) {
                    UpdateInterface unused = RecoveryService.ota = new CustomUpdateForABImpl();
                }
                Log.d(RecoveryService.TAG, "enter recovery_ab_binder");
                RecoveryService.ota.update_ab_binder(iRecoveryCallback);
                return 1;
            } catch (SecurityException e) {
                e.printStackTrace();
                return -401;
            }
        }

        @Override // com.foss.fota.sysoper.IRecovery
        public int reboot() throws RemoteException {
            try {
                CheckPermissionHelper.getInstance(RecoveryService.this.getApplicationContext()).checkPermission("0");
                Log.d(RecoveryService.TAG, "enter recovery_ab_reboot");
                ((PowerManager) RecoveryService.this.getSystemService("power")).reboot(BuildConfig.FLAVOR);
                return 1;
            } catch (SecurityException e) {
                e.printStackTrace();
                return -401;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int EnterRecovery(String str) {
        Log.i(TAG, "RecoveryService  path : " + str);
        if (TextUtils.isEmpty(str)) {
            return -400;
        }
        try {
            CheckPermissionHelper.getInstance(getApplicationContext()).checkPermission("0");
            return update(this, str);
        } catch (SecurityException e) {
            e.printStackTrace();
            return -401;
        }
    }

    public int update(Context context, String str) {
        try {
            File file = new File(str);
            ((PowerManager) context.getSystemService("power")).newWakeLock(1, "FOTASys").acquire(5000L);
            Log.i(TAG, "RecoveryService:!!! Update System !!!");
            RecoverySystem.installPackage(context, file);
            Log.i(TAG, "RecoveryService:Still running after update system?!");
            return 1;
        } catch (Exception e) {
            Log.e(TAG, "RecoveryService:Can't perform update system, IOException = " + e.getMessage());
            return -500;
        }
    }
}
