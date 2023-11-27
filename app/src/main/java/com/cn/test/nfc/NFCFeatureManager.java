package com.cn.test.nfc;

import android.app.Activity;
import android.content.Intent;

public abstract class NFCFeatureManager {
    public Activity mContext;
    private String mBoardType;
    private NfcDataListener mfcDataListener;


    /**
     * 初始化
     * @param
     */
    abstract NFCFeatureManager nfcInit();

    abstract void startWork();

    abstract void stopWork();

    abstract String readNativeNfcData(Intent intent);

    abstract void playTipSound();

    abstract void nfcRelease();
}
