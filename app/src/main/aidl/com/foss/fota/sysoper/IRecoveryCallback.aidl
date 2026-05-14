package com.foss.fota.sysoper;

interface IRecoveryCallback {
    int onStatusUpdate(int status, float progress);
    int onPayloadApplicationComplete(int errorCode);
}
