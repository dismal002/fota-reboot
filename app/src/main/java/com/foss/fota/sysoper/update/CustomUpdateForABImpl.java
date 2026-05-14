package com.foss.fota.sysoper.update;

import android.content.Context;
import android.os.RemoteException;
import android.os.UpdateEngine;
import android.os.UpdateEngineCallback;
import android.util.Log;
import com.foss.fota.sysoper.IRecoveryCallback;
import com.foss.fota.sysoper.RecoveryParams;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class CustomUpdateForABImpl implements UpdateInterface {
    private static final String TAG = "sys";
    private IRecoveryCallback recoveryCallback;
    private UpdateEngine updateEngine = new UpdateEngine();

    @Override // com.foss.fota.sysoper.update.UpdateInterface
    public int update(Context context, String str) {
        Log.i(TAG, "abUpdateRef  enter");
        return 1;
    }

    @Override // com.foss.fota.sysoper.update.UpdateInterface
    public int update_ab_binder(IRecoveryCallback iRecoveryCallback) {
        this.recoveryCallback = iRecoveryCallback;
        this.updateEngine.bind(new UpdateEngineCallback() { // from class: com.foss.fota.sysoper.update.CustomUpdateForABImpl.1
            public void onStatusUpdate(int i, float f) {
                Log.d(CustomUpdateForABImpl.TAG, "CustomUpdateForABImpl onStatusUpdate,status_code=" + i + ",,percentage=" + f);
                if (CustomUpdateForABImpl.this.recoveryCallback != null) {
                    try {
                        CustomUpdateForABImpl.this.recoveryCallback.onStatusUpdate(i, f);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onPayloadApplicationComplete(int i) {
                Log.d(CustomUpdateForABImpl.TAG, "CustomUpdateForABImpl onPayloadApplicationComplete,error_code=" + i);
                if (CustomUpdateForABImpl.this.recoveryCallback != null) {
                    try {
                        CustomUpdateForABImpl.this.recoveryCallback.onPayloadApplicationComplete(i);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return 1;
    }

    @Override // com.foss.fota.sysoper.update.UpdateInterface
    public int update_ab(Context context, RecoveryParams recoveryParams) {
        this.updateEngine.applyPayload(recoveryParams.getUrl(), recoveryParams.getOffset(), recoveryParams.getSize(), recoveryParams.getHeaderKeyValuePairs());
        return 1;
    }
}
