package com.cn.test.listener;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cn.test.R;
import com.cn.test.data.MyFunc;
import com.cn.test.serial.SerialControl;
import com.cn.test.util.LogFileUtil;
import com.cn.test.viewmodel.ShareViewModel;

import java.util.Arrays;

public class MyViewListener {
    private static final String TAG = "MyViewListener";
    private ShareViewModel mShareViewModel;
    public MyViewListener(ShareViewModel shareViewModel) {
        mShareViewModel = shareViewModel;
    }

    public void btnClick(View view){
        switch (view.getId()){
            case R.id.id_sendsupercard:
                LogFileUtil.saveLog("发送超级管理卡");
                mShareViewModel.getmSendLiveData().setValue("AA0704EAFC44D155");
                break;
            case R.id.id_sendwg:
                LogFileUtil.saveLog("发送韦根输出");
                if(!TextUtils.isEmpty(Arrays.toString(mShareViewModel.getmCardIdLiveData().getValue())) && !"null".equalsIgnoreCase(Arrays.toString(mShareViewModel.getmCardIdLiveData().getValue()))){
                    LogFileUtil.saveLog("发送韦根输出: "+Arrays.toString(mShareViewModel.getmCardIdLiveData().getValue()));
                    mShareViewModel.getmSendLiveData().setValue("AA0804"+MyFunc.ByteArrToHex(mShareViewModel.getmCardIdLiveData().getValue())+"55");
                }
                else{
                    mShareViewModel.getmSendLiveData().setValue("AA080455");
                }
                break;
            case R.id.id_sendcardid_10:
                LogFileUtil.saveLog("卡号格式切换正向10进制");
                SerialControl.setOnClick(true);
                mShareViewModel.getmSendLiveData().setValue("AABB0600000001060205");
                break;
            case R.id.id_sendcardid_16:
                LogFileUtil.saveLog("卡号格式切换正向16进制");
                SerialControl.setOnClick(true);
                mShareViewModel.getmSendLiveData().setValue("AABB0600000001060304");
                break;
            case R.id.id_send_10cardid:
                LogFileUtil.saveLog("卡号格式切换反向10进制");
                SerialControl.setOnClick(true);
                mShareViewModel.getmSendLiveData().setValue("AABB0600000001060403");
                break;
            case R.id.id_set_opentime:
                LogFileUtil.saveLog("设置开门时间");
                String timeDataStr = MyFunc.IntToHex(Integer.valueOf(mShareViewModel.getmSeconds().getValue().replace("秒","")));
                if (timeDataStr.length() == 1){
                    timeDataStr = "0"+timeDataStr;
                }
                mShareViewModel.getmSendLiveData().setValue("AA0101"+timeDataStr +"55");
                break;
            case R.id.id_set_remoteopen:
                LogFileUtil.saveLog("远程开门");
                mShareViewModel.getmSendLiveData().setValue("AA02000055");
                break;
            case R.id.id_open_relaypower:
                LogFileUtil.saveLog("继电器常开");
                mShareViewModel.getmSendLiveData().setValue("AA03000055");
                break;
            case R.id.id_close_relaypower:
                LogFileUtil.saveLog("继电器常闭");
                mShareViewModel.getmSendLiveData().setValue("AA04000055");
                break;
            case R.id.id_open_buzzer:
                LogFileUtil.saveLog("打开蜂鸣器");
                mShareViewModel.getmSendLiveData().setValue("AA09010055");
                break;
            case R.id.id_close_buzzer:
                LogFileUtil.saveLog("蜂鸣器关闭");
                mShareViewModel.getmSendLiveData().setValue("AA09010155");
                break;
            case R.id.id_red_led:
                LogFileUtil.saveLog("LED红灯常亮");
                mShareViewModel.getmSendLiveData().setValue("AA13010155");
                break;
            case R.id.id_green_led:
                LogFileUtil.saveLog("LED绿灯常亮");
                mShareViewModel.getmSendLiveData().setValue("AA13010255");
                break;
            case R.id.id_blue_led:
                LogFileUtil.saveLog("LED蓝灯常亮");
                mShareViewModel.getmSendLiveData().setValue("AA13010355");
                break;
            case R.id.id_marquee_forever:
                LogFileUtil.saveLog("LED跑马灯");
                mShareViewModel.getmSendLiveData().setValue("AA15000055");
                break;
            case R.id.id_red_twinkle:
                LogFileUtil.saveLog("LED红灯闪烁");
                mShareViewModel.getmSendLiveData().setValue("AA14010155");
                break;
            case R.id.id_green_twinkle:
                LogFileUtil.saveLog("LED绿灯闪烁");
                mShareViewModel.getmSendLiveData().setValue("AA14010255");
                break;
            case R.id.id_blue_twinkle:
                LogFileUtil.saveLog("LED蓝灯闪烁");
                mShareViewModel.getmSendLiveData().setValue("AA14010355");
                break;
            case R.id.id_led_extinguish:
                LogFileUtil.saveLog("LED熄灭");
                mShareViewModel.getmSendLiveData().setValue("AA12000055");
                break;
            case R.id.id_lift:
                LogFileUtil.saveLog("梯控");
                mShareViewModel.getmSwitchFragmentLiveData().setValue("lift");
                break;
            case R.id.id_setopenclose:
                StringBuffer mStringBuffer = new StringBuffer();
                for(Boolean value:mShareViewModel.getmLiftArrayList()){
                    mStringBuffer.append(value?"1":"0");
                }
                String mStr = "";
                if(mShareViewModel.getmCheckType().equalsIgnoreCase("自动")){
                    mStr = "AA" +"B1"+padLeft(Integer.toHexString(Integer.parseInt(mStringBuffer.reverse().toString(), 2)).toUpperCase(),16)+"DD";
                }
                else{
                    mStr = "AA" +"B2"+padLeft(Integer.toHexString(Integer.parseInt(mStringBuffer.reverse().toString(), 2)).toUpperCase(),16)+"DD";
                }
                LogFileUtil.saveLog("设置开关: "+mStr);
                mShareViewModel.getmSendLiveData().setValue(mStr);
                break;
            case R.id.id_setallopen:
                LogFileUtil.saveLog("全开");
                mShareViewModel.getmAllLift().setValue(true);
                mShareViewModel.getmSendLiveData().setValue("AAB30000000000000000DD");
                break;
            case R.id.id_setallclose:
                LogFileUtil.saveLog("全关");
                mShareViewModel.getmAllLift().setValue(false);
                mShareViewModel.getmSendLiveData().setValue("AAB40000000000000000DD");
                break;
            case R.id.id_back:
                LogFileUtil.saveLog("返回");
                mShareViewModel.getmSwitchFragmentLiveData().setValue("test");
                break;
            case R.id.id_open_temp:
                LogFileUtil.saveLog("打开温度测试");
                mShareViewModel.getmSwitchFragmentLiveData().setValue("temperture");
                break;
            case R.id.id_gettemperture:
                LogFileUtil.saveLog("获取温度");
                mShareViewModel.getmSerialControl().sendHex("EEE10155FFFC");
                break;
            case R.id.id_continuitygettemperture:
                if(!mTempFlag){
                    mTempFlag = true;
                    if(mThread.getState() == Thread.State.NEW){
                        mThread.start();
                    }
                    ((Button)view).setText("停止连续获取温度");
                    LogFileUtil.saveLog("连续获取温度");
                }
                else{
                    ((Button)view).setText("连续获取温度");
                    LogFileUtil.saveLog("停止连续获取温度");
                    mTempFlag = false;
                }
                break;
            case R.id.id_cleartemperture:
                LogFileUtil.saveLog("清空数据");
                mShareViewModel.getmTemperInfoList().getValue().clear();
                mShareViewModel.getmTemperInfoList().setValue(mShareViewModel.getmTemperInfoList().getValue());
                break;
            case R.id.id_backhome:
                LogFileUtil.saveLog("返回");
                mTempFlag = false;
                mShareViewModel.getmSwitchFragmentLiveData().setValue("test");
                break;
        }
        
    }
    private boolean mTempFlag = false;
    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().interrupted()){
                SystemClock.sleep(1000);
                if(mTempFlag){
                    mShareViewModel.getmSerialControl().sendHex("EEE10155FFFC");
                }
            }
        }
    });

    private String padLeft(String str,int length) {
        if (str.length() >= length) {
            return str;
        } else {
            return padLeft("0"+str, length);
        }
    }
}
