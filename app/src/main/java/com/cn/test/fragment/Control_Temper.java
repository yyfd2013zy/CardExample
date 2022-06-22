package com.cn.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cn.test.R;
import com.cn.test.databinding.ControlTempBinding;
import com.cn.test.listener.MyViewListener;
import com.cn.test.viewmodel.ShareViewModel;

public class Control_Temper extends Fragment {
    private ControlTempBinding mBinding;
    private ShareViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.control_temp, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        mBinding.setVariable(BR.viewlistener,new MyViewListener(mViewModel));
    }
}
