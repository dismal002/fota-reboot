package com.foss.fota.sysoper.update;

import android.content.Context;
import android.util.Log;
import com.foss.fota.sysoper.IRecoveryCallback;
import com.foss.fota.sysoper.RecoveryParams;
import com.android.internal.app.IntentForwarderActivity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class CustomUpdateForHCTImpl implements UpdateInterface {
    private File RECOVERY_DIR = new File("/cache/recovery");
    private File UPDATE_FLAG_FILE = new File(this.RECOVERY_DIR, "last_flag");
    private File COMMAND_FILE = new File(this.RECOVERY_DIR, "command");
    private File LOG_FILE = new File(this.RECOVERY_DIR, "log");
    private String LAST_PREFIX = "last_";

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
        try {
            Log.w(IntentForwarderActivity.TAG, "!!! REBOOTING TO change package " + str + " !!!");
            String str2 = "--update_package=" + str + "\n--locale=" + Locale.getDefault().toString();
            writeFlagCommand(str);
            bootCommand(context, str2);
            return 1;
        } catch (Exception unused) {
            return -1;
        }
    }

    private void writeFlagCommand(String str) throws IOException {
        this.RECOVERY_DIR.mkdirs();
        this.UPDATE_FLAG_FILE.delete();
        FileWriter fileWriter = new FileWriter(this.UPDATE_FLAG_FILE);
        try {
            fileWriter.write("updating$path=" + str);
        } finally {
            fileWriter.close();
        }
    }

    private void bootCommand(Context context, String str) throws IOException {
        this.RECOVERY_DIR.mkdirs();
        this.COMMAND_FILE.delete();
        this.LOG_FILE.delete();
        FileWriter fileWriter = new FileWriter(this.COMMAND_FILE);
        try {
            fileWriter.write(str);
            fileWriter.write("\n");
            fileWriter.flush();
            fileWriter.close();
            throw new IOException("Reboot failed (no permissions?)");
        } catch (Throwable th) {
            fileWriter.flush();
            fileWriter.close();
            throw th;
        }
    }
}
