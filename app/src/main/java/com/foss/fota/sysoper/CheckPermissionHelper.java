package com.foss.fota.sysoper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class CheckPermissionHelper {
    private static final String CERT_OTA = "a0fdd5faf0c2da427730a25da1eb69ebdf2240d017033f63a5072a17a66c6a83";
    private static final String LOG_TAG = "CPH";
    private static final String OTA_PACKAGE_NAME = "adba050b48205abc424df983bd63903b7e9d440f5af3aacc45ecf778a061d4dc";
    private static CheckPermissionHelper mInstance;
    private final Context mContext;
    private PackageManager mPmManager = null;

    public static CheckPermissionHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CheckPermissionHelper.class) {
                mInstance = new CheckPermissionHelper(context);
            }
        }
        return mInstance;
    }

    private PackageManager getPackageManager() {
        if (this.mPmManager == null) {
            this.mPmManager = this.mContext.getPackageManager();
        }
        return this.mPmManager;
    }

    CheckPermissionHelper(Context context) {
        this.mContext = context.getApplicationContext();
        getPackageManager();
    }

    private PackageInfo GetPackageSignatureInfo(String str) {
        if (this.mPmManager == null) {
            return null;
        }
        try {
            return this.mPmManager.getPackageInfo(str, 64);
        } catch (Exception unused) {
            return null;
        }
    }

    public synchronized void checkPermission(String str) {
        String[] packagesForUid = null;
        try {
            packagesForUid = this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (packagesForUid != null && packagesForUid.length > 0) {
            for (String pkg : packagesForUid) {
                Util.d(LOG_TAG, "checking pkg: " + pkg);
                if (checkfota(pkg)) {
                    return; // Access granted
                }
            }
        }
        throw new SecurityException("ACCESS DENIED: Unauthorized caller");
    }

    private boolean checkfota(String str) {
        if (!OTA_PACKAGE_NAME.equals(new GetPkgSignatureHash().getSHA256String(str))) {
            return false;
        }
        String certInfo = getCertInfo(str);
        String certInfo2 = getCertInfo(this.mContext.getPackageName());
        if (certInfo != null) {
            return certInfo.equals(CERT_OTA) || certInfo.equals(certInfo2);
        }
        return false;
    }

    private String getCertInfo(String str) {
        String[] pkgSignatureSHA256;
        PackageInfo packageInfoGetPackageSignatureInfo = GetPackageSignatureInfo(str);
        if (packageInfoGetPackageSignatureInfo == null || (pkgSignatureSHA256 = new GetPkgSignatureHash().getPkgSignatureSHA256(packageInfoGetPackageSignatureInfo.signatures)) == null || pkgSignatureSHA256.length != 2) {
            return null;
        }
        return pkgSignatureSHA256[0];
    }
}
