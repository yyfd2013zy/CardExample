package com.cn.test.viewmodel;

import android.text.TextUtils;
import android.widget.CheckBox;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cn.test.serial.SerialControl;
import java.util.ArrayList;
import java.util.List;

public class ShareViewModel extends ViewModel {
    private MutableLiveData<String> mSerial = new MutableLiveData<String>();
    private MutableLiveData<String> mBaudrate = new MutableLiveData<String>();
    private MutableLiveData<String> mSeconds = new MutableLiveData<String>();
    private MutableLiveData<String> mSendLiveData = new MutableLiveData<String>();
    private MutableLiveData<String> mReceiveLiveData = new MutableLiveData<String>();
    private MutableLiveData<String> mStatusLiveData = new MutableLiveData<String>();
    private MutableLiveData<byte[]> mCardIdLiveData = new MutableLiveData<byte[]>();
    private MutableLiveData<String> mSwitchFragmentLiveData = new MutableLiveData<String>();
    private MutableLiveData<ArrayList<TemperInfo>> mTemperInfoList = new MutableLiveData<ArrayList<TemperInfo>>();
    private SerialControl mSerialControl = new SerialControl(this);
    private String mCheckType = "";
    private List<Boolean> mLiftArrayList = new ArrayList<>();//电梯数据
    private MutableLiveData<Boolean> mAllLift = new MutableLiveData<Boolean>();//电梯数据
    public MutableLiveData<String> getmSerial() {
        return mSerial;
    }

    public MutableLiveData<String> getmBaudrate() {
        return mBaudrate;
    }

    public MutableLiveData<String> getmSeconds() {
        if(TextUtils.isEmpty(mSeconds.getValue())){
            mSeconds.setValue("1s");
        }
        return mSeconds;
    }

    public MutableLiveData<String> getmSendLiveData() {
        return mSendLiveData;
    }

    public MutableLiveData<String> getmReceiveLiveData() {
        return mReceiveLiveData;
    }

    public MutableLiveData<String> getmStatusLiveData(String defaultData) {
        if(TextUtils.isEmpty(mStatusLiveData.getValue())){
            mStatusLiveData.setValue(defaultData);
        }
        return mStatusLiveData;
    }

    public MutableLiveData<byte[]> getmCardIdLiveData() {
        return mCardIdLiveData;
    }

    public MutableLiveData<String> getmSwitchFragmentLiveData() {
        return mSwitchFragmentLiveData;
    }

    public List<Boolean> getmLiftArrayList() {
        return mLiftArrayList;
    }

    public void setmCheckType(String mCheckType) {
        this.mCheckType = mCheckType;
    }

    public String getmCheckType() {
        return mCheckType;
    }

    public SerialControl getmSerialControl() {
        return mSerialControl;
    }

    public MutableLiveData<Boolean> getmAllLift() {
        return mAllLift;
    }

    public MutableLiveData<ArrayList<TemperInfo>> getmTemperInfoList() {
        return mTemperInfoList;
    }
}
