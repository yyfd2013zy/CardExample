package com.cn.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.cn.test.databinding.NfcActivityBinding;
import com.cn.test.nfc.NativeNfcImpl;

public class NFCActivity extends AppCompatActivity {
    NativeNfcImpl nativeNfc;

    private NfcActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.nfc_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNfc();
    }

    private void initNfc() {
        if (nativeNfc == null){
            nativeNfc = new NativeNfcImpl(this);
            nativeNfc.nfcInit();
            nativeNfc.startWork();
        } else {
            nativeNfc.startWork();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        nativeNfc.stopWork();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String  nfcData = nativeNfc.readNativeNfcData(intent);
        binding.tvNfcData.setText(nfcData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nativeNfc.nfcRelease();
    }

}