package com.foss.fota.sysoper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class SysService extends Service {
    private static final String TAG = "sys";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }
}
