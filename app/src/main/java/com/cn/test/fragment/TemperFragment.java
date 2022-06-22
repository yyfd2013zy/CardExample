package com.cn.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cn.test.R;
import com.cn.test.databinding.ModuleTestFragmentBinding;
import com.cn.test.databinding.TemperFragmentBinding;
import com.cn.test.viewmodel.ShareViewModel;

public class TemperFragment extends Fragment {
    private TemperFragmentBinding mBinding;
    private ShareViewModel mViewModel;
    public static TemperFragment newInstance() {
        return new TemperFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.temper_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
//        mViewModel.getmSerial().setValue("/dev/ttyS0");
//        mViewModel.getmBaudrate().setValue("57600");
    }
}
