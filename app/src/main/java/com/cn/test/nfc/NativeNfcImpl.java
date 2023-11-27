package com.cn.test.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;

public class NativeNfcImpl extends NFCFeatureManager{
    private final String TAG= this.getClass().getSimpleName();
    //nfc
    private NfcAdapter mNfcAdapter;
    private IntentFilter[] mIntentFilter;
    private PendingIntent mPendingIntent;
    private String[][] mTechList;
    private boolean nfcHasInit = false;

    public NativeNfcImpl(Activity context) {
        mContext = context;
        mNfcAdapter =  NfcCheck();
    }

    private NfcAdapter NfcCheck() {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(mContext);
        if (mNfcAdapter != null){
            if (!mNfcAdapter.isEnabled()) {
                Log.d(TAG, "打开NFC");
                mContext.startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            }
        } else  {
            Log.d(TAG, "NFC适配器获取失败请检查硬件信息");
            return null;
        }
        return mNfcAdapter;
    }

    @Override
    public NFCFeatureManager nfcInit() {
        if (!nfcHasInit) {
            Log.d(TAG, "NFC初始化");
            nfcHasInit = true;
            Intent nfcIntent = new Intent(mContext, mContext.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mPendingIntent = PendingIntent.getActivity(mContext, 0, nfcIntent, 0);
            IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            IntentFilter filter2 = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            try {
                filter2.addDataType("*/*");
            } catch (IntentFilter.MalformedMimeTypeException e) {
                throw new RuntimeException(e);
            }
            mIntentFilter = new IntentFilter[]{filter, filter2};
            mTechList = new String[][]{{MifareClassic.class.getName()}, {NfcA.class.getName()}};
        } else {
            Log.d(TAG, "NFC已经初始化-不再重复初始化");
        }
        return this;
    }

    @Override
    public void startWork() {
        Log.d(TAG, "NFC开始工作");
        mNfcAdapter.enableForegroundDispatch(mContext, mPendingIntent, mIntentFilter, mTechList);
    }

    @Override
    public void stopWork() {
        mNfcAdapter.disableForegroundDispatch(mContext);
    }

    @Override
    public String readNativeNfcData(Intent intent) {
        String data = "";
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            byte[] idBytes = tag.getId();
            if (idBytes != null) {
                data = ByteArrayToHexString(idBytes);
            }
        }
        Log.d(TAG, "NFC数据 -> " + data);
        return data;
    }

    @Override
    void playTipSound() {

    }

    @Override
    public void nfcRelease() {
        Log.d(TAG, "NFC释放");
        nfcHasInit = false;
    }

    /**
     * 将字节数组转换为字符串
     */
    public String ByteArrayToHexString(byte[] inarray) {
        int i, j, temp;
        String[] hex = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        StringBuilder out = new StringBuilder();
        for (j = 0; j < inarray.length; ++j) {
            temp = ((int) inarray[j]) & 0xff;
            i = temp >> 4 & 0x0f;
            out.append(hex[i]);
            i = temp & 0x0f;
            out.append(hex[i]);
        }
        return out.toString();
    }
}
