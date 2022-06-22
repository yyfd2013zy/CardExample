package com.cn.test.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cn.test.R;
import com.cn.test.databinding.DataShowFragmentBinding;
import com.cn.test.serial.SerialControl;
import com.cn.test.serial.SerialHelper;
import com.cn.test.util.LogFileUtil;
import com.cn.test.viewmodel.ShareViewModel;

public class Data_Show extends Fragment {

    private ShareViewModel mShareViewModel;
    private DataShowFragmentBinding mBinding;

    private SharedPreferences mSharedPreferences;
    private static final String TAG = "Data_Show";
    public static Data_Show newInstance() {
        return new Data_Show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.data_show_fragment, container, false);
        mBinding.idReceivemessage.setMovementMethod(new ScrollingMovementMethod());
        mBinding.idSendmessage.setMovementMethod(new ScrollingMovementMethod());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mShareViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        mShareViewModel.getmSendLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(TextUtils.isEmpty(mShareViewModel.getmSendLiveData().getValue())){
                    mBinding.idSendmessage.setText(s+"\r\n");
                }
                else{
                    mBinding.idSendmessage.setText(mBinding.idSendmessage.getText()+s+"\r\n");
                }
                int offset=mBinding.idSendmessage.getLineCount()*mBinding.idSendmessage.getLineHeight();
                if(offset>(mBinding.idSendmessage.getHeight()-mBinding.idSendmessage.getLineHeight()-20)){
                    mBinding.idSendmessage.scrollTo(0,offset-mBinding.idSendmessage.getHeight()+mBinding.idSendmessage.getLineHeight()+20);
                }
                mShareViewModel.getmSerialControl().sendHex(s);
                if(s.equalsIgnoreCase("AABB0600000001060205")){
                    mSharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("cardtype","forward10");
                    editor.commit();
                }
                else if(s.equalsIgnoreCase("AABB0600000001060304")){
                    mSharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("cardtype","forward16");
                    editor.commit();
                }
                else if(s.equalsIgnoreCase("AABB0600000001060403")){
                    mSharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("cardtype","reverse10");
                    editor.commit();
                }
            }
        });
        mShareViewModel.getmReceiveLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(TextUtils.isEmpty(mShareViewModel.getmReceiveLiveData().getValue())){
                    mBinding.idReceivemessage.setText(s+"\r\n");
                }
                else{
                    mBinding.idReceivemessage.setText(mBinding.idReceivemessage.getText()+s+"\r\n");
                }
                int offset=mBinding.idReceivemessage.getLineCount()*mBinding.idReceivemessage.getLineHeight();
                if(offset>(mBinding.idReceivemessage.getHeight()-mBinding.idReceivemessage.getLineHeight()-20)){
                    mBinding.idReceivemessage.scrollTo(0,offset-mBinding.idReceivemessage.getHeight()+mBinding.idReceivemessage.getLineHeight()+20);
                }
            }
        });
        mShareViewModel.getmSerial().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!TextUtils.isEmpty(mShareViewModel.getmBaudrate().getValue())){
                    LogFileUtil.saveLog("原有波特率不为空: "+mShareViewModel.getmBaudrate().getValue());
                    if(!TextUtils.isEmpty(mShareViewModel.getmSerialControl().getPort())){
                        LogFileUtil.saveLog("原有串口不为空: "+mShareViewModel.getmSerialControl().getPort());
                        CloseComPort(mShareViewModel.getmSerialControl());
                    }
                    mShareViewModel.getmSerialControl().setPort(s);
                    mShareViewModel.getmSerialControl().setBaudRate(mShareViewModel.getmBaudrate().getValue());
                    OpenComPort(mShareViewModel.getmSerialControl());
                }
                LogFileUtil.saveLog("打开串口号: "+s);

            }
        });
        mShareViewModel.getmBaudrate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!TextUtils.isEmpty(mShareViewModel.getmSerial().getValue())){
                    if(!TextUtils.isEmpty(mShareViewModel.getmSerialControl().getPort())){
                        CloseComPort(mShareViewModel.getmSerialControl());
                    }
                    mShareViewModel.getmSerialControl().setPort(mShareViewModel.getmSerial().getValue());
                    mShareViewModel.getmSerialControl().setBaudRate(s);
                    OpenComPort(mShareViewModel.getmSerialControl());
                }
                LogFileUtil.saveLog("波特率: "+s);
            }
        });
        mShareViewModel.getmSeconds().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                LogFileUtil.saveLog("开门时间: "+s);
            }
        });
    }
    private void OpenComPort(SerialHelper ComPort){
        try
        {
            ComPort.open();
            LogFileUtil.saveLog("打开串口!");
        } catch (Exception e) {
            Log.e(TAG, "打开串口失败!"+e.toString());
        }
    }

    private void CloseComPort(SerialHelper ComPort){
        ComPort.stopSend();
        ComPort.close();
    }
}