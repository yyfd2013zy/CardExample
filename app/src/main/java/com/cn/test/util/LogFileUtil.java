package com.cn.test.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.cn.test.data.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFileUtil {
    private static StringBuffer sb = new StringBuffer();
    private static String mPath = Environment.getExternalStorageDirectory().getPath();
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
    private static SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
    private static File mFile;
    private static String fileName = "";
    private static FileOutputStream fos;
    private static OutputStreamWriter osWriter;
    private static BufferedWriter writer;
    private static File dir = new File(mPath+ "/Log/");
    private static boolean mFlag = false;//日志开关
    private static final String TAG = "LogFileUtil";
    private static LogFileUtil Instance;

    public LogFileUtil() {
         EventBus.getDefault().register(this);
    }



    public static boolean ismFlag() {
        return mFlag;
    }


    /**
     * 接收日志
     * @param messageEvent
     */
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getAction().equalsIgnoreCase(MessageEvent.SAVELOG)){
            try {
                synchronized (df){
                    String date = df.format(new Date()).substring(0,13);
                    if(date.length() > 0 && mFlag){
                        if(mFile == null || !mFile.exists() || !date.replaceAll("-","").replaceAll(" ","_").equalsIgnoreCase(fileName))
                        {
                            if(!date.replaceAll("-","").replaceAll(" ","_").equalsIgnoreCase("20200720_00")){
                                fileName = date.replaceAll("-","").replaceAll(" ","_");
//                                Util.log("日志文件名: "+fileName+"   "+"日期时间: "+date);
                                if (!dir.exists() && !dir.mkdirs())
                                {
                                    Log.i(TAG,"文件夹: "+dir.getAbsolutePath()+"  创建失败");
                                    return;
                                }
                                mFile = new File(dir,fileName + ".txt");
                                if(!mFile.exists() && !mFile.createNewFile())
                                {
                                    Log.i(TAG,mFile.getAbsolutePath()+"创建失败");
                                    return;
                                }
                                if(osWriter != null){
                                    osWriter.close();
                                    osWriter = null;
                                }
                                if(writer != null){
                                    writer.close();
                                    writer = null;
                                }
                                if(fos != null){
                                    fos.close();
                                    fos = null;
                                }
                                fos  = new FileOutputStream(mFile, true);
                                osWriter = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
                                writer = new BufferedWriter(osWriter);
                            }
                        }
                        write(messageEvent.getMessage());
                    }
                    date = null;
                }
            } catch (Exception e) {
//                Util.log("mCopyOnWriteArrayList:  "+e.toString());
                e.printStackTrace();
            }
        }
        else if(messageEvent.getAction().equalsIgnoreCase(MessageEvent.STOPLOG)){
//            Util.log("写日志: 停止日志记录");
            try {
                mFlag = false;
                if(osWriter != null){
                    osWriter.close();
                    osWriter = null;
                }
                if(writer != null){
                    writer.close();
                    writer = null;
                }
                if(fos != null){
                    fos.close();
                    fos = null;
                }
                mFile = null;
            }catch (Exception e){

            }
        }
        else if(messageEvent.getAction().equalsIgnoreCase(MessageEvent.STARTLOG)){
//            Util.log("写日志: 重新打开日志记录");
            SystemClock.sleep(2000);
            mFlag = true;
        }
    }

    /**
     * 保存日志
     * @param log
     */
    public static void saveLog(String log)
    {
        if(Instance == null){
            Instance = new LogFileUtil();
        }
        Log.i(TAG,"写日志: "+log);
        if(mFlag){
            synchronized (dfs){
                String mDate = dfs.format(new Date());
                sb.setLength(0);
                sb.append(mDate);
                sb.append("  ");
                sb.append(log);
                sb.append("\r\n");
                log = null;
                mDate = null;
                EventBus.getDefault().post(new MessageEvent(MessageEvent.SAVELOG,sb.toString()));
            }
        }
    }
    private synchronized static void write(String log) throws IOException {
//        Util.log("写日志: "+log);

        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if(writer != null && osWriter != null && !TextUtils.isEmpty(log))
            {
                writer.write(log);
                writer.flush();
                osWriter.flush();
            }
        } catch (Exception e) {
//            Util.log("写日志:  异常"+e.toString());
            e.printStackTrace();
            fos.close();
        }
    }
}
