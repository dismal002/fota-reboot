package com.foss.fota.sysoper;

import android.app.Application;
import android.content.Context;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class MyApplication extends Application {
    private static Context mContext;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        mContext = this;
        init();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getContext() {
        return mContext;
    }

    private void init() {
        try {
            Schedule.c(this);
        } catch (Exception unused) {
        }
    }
}
