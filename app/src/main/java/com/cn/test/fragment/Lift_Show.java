package com.cn.test.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import com.cn.test.databinding.LiftShowFragmentBinding;
import com.cn.test.util.LogFileUtil;
import com.cn.test.viewmodel.ShareViewModel;

public class Lift_Show extends Fragment {
    private LiftShowFragmentBinding mBinding;
    private ShareViewModel mViewModel;
    private static final String TAG = "Lift_Show";
    public static Lift_Show newInstance() {
        return new Lift_Show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.lift_show_fragment, container, false);
        mBinding.idReceivemessage.setMovementMethod(new ScrollingMovementMethod());
        mBinding.idSendmessage.setMovementMethod(new ScrollingMovementMethod());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        mViewModel.getmSendLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(TextUtils.isEmpty(mViewModel.getmSendLiveData().getValue())){
                    mBinding.idSendmessage.setText(s+"\r\n");
                }
                else{
                    mBinding.idSendmessage.setText(mBinding.idSendmessage.getText()+s+"\r\n");
                }
                int offset=mBinding.idSendmessage.getLineCount()*mBinding.idSendmessage.getLineHeight();
                if(offset>(mBinding.idSendmessage.getHeight()-mBinding.idSendmessage.getLineHeight()+10)){
                    mBinding.idSendmessage.scrollTo(0,offset-mBinding.idSendmessage.getHeight()+mBinding.idSendmessage.getLineHeight());
                    LogFileUtil.saveLog("scrollTo: "+(offset-mBinding.idSendmessage.getHeight()+mBinding.idSendmessage.getLineHeight()));
                }
                else{
                    LogFileUtil.saveLog("offset: "+offset+"   "+(mBinding.idSendmessage.getHeight()-mBinding.idSendmessage.getLineHeight()+10));
                }
                mViewModel.getmSerialControl().sendHex(s);
            }
        });
        mViewModel.getmReceiveLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(TextUtils.isEmpty(mViewModel.getmReceiveLiveData().getValue())){
                    mBinding.idReceivemessage.setText(s+"\r\n");
                }
                else{
                    mBinding.idReceivemessage.setText(mBinding.idReceivemessage.getText()+s+"\r\n");
                }
                int offset=mBinding.idReceivemessage.getLineCount()*mBinding.idReceivemessage.getLineHeight();
                if(offset>(mBinding.idReceivemessage.getHeight()-mBinding.idReceivemessage.getLineHeight()+10)){
                    mBinding.idReceivemessage.scrollTo(0,offset-mBinding.idReceivemessage.getHeight()+mBinding.idReceivemessage.getLineHeight());
                    LogFileUtil.saveLog("scrollTo: "+(offset-mBinding.idReceivemessage.getHeight()+mBinding.idReceivemessage.getLineHeight()));
                }
                else{
                    LogFileUtil.saveLog("offset: "+offset+"   "+(mBinding.idReceivemessage.getHeight()-mBinding.idReceivemessage.getLineHeight()+10));
                }
            }
        });
    }

}