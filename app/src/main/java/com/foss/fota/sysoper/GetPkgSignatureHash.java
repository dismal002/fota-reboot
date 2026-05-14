package com.foss.fota.sysoper;

import android.content.pm.Signature;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: /tmp/decompiler/5c266520f0f64269b2862d0226ed13cd/AdupsFotaReboot_classes.dex */
public class GetPkgSignatureHash {
    private String toHexString(byte[] bArr, String str) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
            sb.append(str);
        }
        return sb.toString();
    }

    public String getMD5String(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(bArr);
            return toHexString(messageDigest.digest(), BuildConfig.FLAVOR);
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public String getSHA256String(String str) {
        byte[] bytes = str.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes);
            return bytes2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    private String bytes2Hex(byte[] bArr) {
        String str = BuildConfig.FLAVOR;
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                str = str + "0";
            }
            str = str + hexString;
        }
        return str;
    }

    public String[] getPkgSignatureMD5(Signature[] signatureArr) {
        if (signatureArr.length == 0 || signatureArr[0] == null) {
            return null;
        }
        byte[] byteArray = signatureArr[0].toByteArray();
        if (byteArray.length <= 0) {
            return null;
        }
        String[] strArr = new String[2];
        try {
            X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(byteArray));
            strArr[0] = getMD5String(Base64.encodeToString(x509Certificate.getEncoded(), 2).getBytes());
            strArr[1] = x509Certificate.getIssuerDN().toString();
            return strArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getPkgSignatureSHA256(Signature[] signatureArr) {
        if (signatureArr.length == 0 || signatureArr[0] == null) {
            return null;
        }
        byte[] byteArray = signatureArr[0].toByteArray();
        if (byteArray.length <= 0) {
            return null;
        }
        String[] strArr = new String[2];
        try {
            X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(byteArray));
            strArr[0] = getSHA256String(Base64.encodeToString(x509Certificate.getEncoded(), 2));
            strArr[1] = x509Certificate.getIssuerDN().toString();
            return strArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
