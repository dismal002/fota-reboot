package com.foss.fota.sysoper.update;

import android.os.SystemClock;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class AbUpdateEngine {
    AbUpdateEngineCallback abUpdateEngineCallback;

    public void bind(AbUpdateEngineCallback abUpdateEngineCallback) {
        this.abUpdateEngineCallback = abUpdateEngineCallback;
    }

    public void applyPayload(String str, long j, long j2, String[] strArr) {
        int i = 1;
        do {
            if (i == 10) {
                this.abUpdateEngineCallback.onPayloadApplicationComplete(1);
            } else {
                this.abUpdateEngineCallback.onStatusUpdate(1, i * 10);
            }
            SystemClock.sleep(1000L);
            i++;
        } while (i <= 10);
    }
}
