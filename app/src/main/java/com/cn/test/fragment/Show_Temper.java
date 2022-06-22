package com.cn.test.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.test.R;
import com.cn.test.databinding.ShowTemperFragmentBinding;
import com.cn.test.util.LogFileUtil;
import com.cn.test.viewmodel.ShareViewModel;
import com.cn.test.viewmodel.TemperAdapter;
import com.cn.test.viewmodel.TemperInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Show_Temper extends Fragment {
    private ShareViewModel mViewModel;
    private ShowTemperFragmentBinding mBinding;
    private TemperAdapter mTemperAdapter;
    private static final String TAG = "Show_Temper";
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.show_temper_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        initData();
    }

    private void initData(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.idList.setLayoutManager(layoutManager);
        if(mViewModel.getmTemperInfoList().getValue() == null){
            mViewModel.getmTemperInfoList().setValue(new ArrayList<TemperInfo>());
        }
        mTemperAdapter = new TemperAdapter(mViewModel.getmTemperInfoList().getValue());
        mBinding.idList.setAdapter(mTemperAdapter);
        mViewModel.getmReceiveLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String data) {
                Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                LogFileUtil.saveLog("获取温度信息: "+data.substring(6,10)+"  "+data.substring(10,12)+"  "+data.substring(12,14));
                int temp = Integer.parseInt(data.substring(10,12),16)*256+Integer.parseInt(data.substring(12,14),16)-27315;
                if (data.startsWith("58A5")){
                    String mDate = df.format(new Date()).split(" ")[1];
                    String temp1 = Float.valueOf(Integer.parseInt(data.substring(6,10),16))/100+"℃";
                    String temp2 = Float.valueOf(temp)/100+"℃";
                    LogFileUtil.saveLog("获取温度信息时间: "+mDate);
                    LogFileUtil.saveLog("获取温度信息额温: "+temp1);
                    LogFileUtil.saveLog("获取温度信息环温: "+temp2);
                    mViewModel.getmTemperInfoList().getValue().add(new TemperInfo(mDate,temp1,temp2));
                    mViewModel.getmTemperInfoList().setValue(mViewModel.getmTemperInfoList().getValue());
                }
            }
        });
        mViewModel.getmTemperInfoList().observe(this,new Observer<ArrayList<TemperInfo>>() {
            @Override
            public void onChanged(ArrayList<TemperInfo> temperInfos) {
                mTemperAdapter.notifyDataSetChanged();
                mBinding.idList.scrollToPosition(mTemperAdapter.getItemCount()-1);
            }
        });
    }


}
