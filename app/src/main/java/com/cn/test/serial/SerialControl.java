package com.cn.test.serial;

import android.text.TextUtils;
import android.util.Log;
import com.cn.test.data.ComBean;
import com.cn.test.data.MyFunc;
import com.cn.test.util.LogFileUtil;
import com.cn.test.viewmodel.ShareViewModel;
import java.util.Arrays;

public class SerialControl extends SerialHelper {
    private ShareViewModel mShareViewModel;
    private static boolean OnClick = false;
    private static final String TAG = "SerialControl";
    public SerialControl(ShareViewModel shareViewModel){
        mShareViewModel = shareViewModel;
    }

    public static void setOnClick(boolean onClick) {
        OnClick = onClick;
    }

    @Override
    protected void onDataReceived(final ComBean ComRecData)
    {
        LogFileUtil.saveLog("onDataReceived: "+ Arrays.toString(ComRecData.bRec));
        StringBuilder sMsg=new StringBuilder();
        sMsg.append(ComRecData.sRecTime);
        sMsg.append("[");
        sMsg.append(ComRecData.sComPort);
        sMsg.append("]");
        sMsg.append(MyFunc.ByteArrToHex(ComRecData.bRec));
        sMsg.append("\r\n");
        LogFileUtil.saveLog("onDataReceived: "+sMsg.toString());
        if(OnClick){
            if(!TextUtils.isEmpty(mShareViewModel.getmReceiveLiveData().getValue())){
                mShareViewModel.getmReceiveLiveData().postValue(mShareViewModel.getmReceiveLiveData().getValue()+sMsg.toString());
            }
            else{
                mShareViewModel.getmReceiveLiveData().postValue(sMsg.toString());
            }
        }
        else{
            mShareViewModel.getmReceiveLiveData().postValue(MyFunc.ByteArrToHex(ComRecData.bRec));
            mShareViewModel.getmCardIdLiveData().postValue(ComRecData.bRec);
        }
        OnClick = false;
    }
}
