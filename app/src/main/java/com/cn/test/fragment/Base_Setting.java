package com.cn.test.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cn.test.R;
import com.cn.test.data.MyFunc;
import com.cn.test.databinding.BaseSettingFragmentBinding;
import com.cn.test.util.LogFileUtil;
import com.cn.test.viewmodel.ShareViewModel;

import java.util.Arrays;

public class Base_Setting extends Fragment {

    private ShareViewModel mShareViewModel;
    private BaseSettingFragmentBinding mBinding;
    private SharedPreferences mSharedPreferences;
    private static final String TAG = "Base_Setting";
    public static Base_Setting newInstance() {
        return new Base_Setting();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.base__setting_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mShareViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        mShareViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        mShareViewModel.getmStatusLiveData(getContext().getResources().getString(R.string.signal_status)).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.idStatus.setText(s);
            }
        });
        mShareViewModel.getmCardIdLiveData().observe(this, new Observer<byte[]>() {
            @Override
            public void onChanged(byte[] bytes) {
                mSharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                LogFileUtil.saveLog("卡号: "+Arrays.toString(bytes)+"   "+mSharedPreferences.getString("cardtype",""));
                if(!TextUtils.isEmpty(mSharedPreferences.getString("cardtype",""))){
                    if(mSharedPreferences.getString("cardtype","").equalsIgnoreCase("forward10") || mSharedPreferences.getString("cardtype","").equalsIgnoreCase("reverse10")){
                        mBinding.idWg34cardid.setText(MyFunc.ByteArrToHex(bytes));
                        mBinding.id16cardid.setText("");
                    }
                    else{
                        mBinding.idWg34cardid.setText("");
                        mBinding.id16cardid.setText(MyFunc.ByteArrToHex(bytes));
                    }
                }
                else{
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("cardtype","forward10");
                    editor.commit();
                    LogFileUtil.saveLog("onChanged: forward10");
                    mBinding.id16cardid.setText("");
                    mBinding.idWg34cardid.setText(MyFunc.ByteArrToHex(bytes));
                }
            }
        });
    }
}