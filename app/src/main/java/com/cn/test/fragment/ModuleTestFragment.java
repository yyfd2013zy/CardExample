package com.cn.test.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.test.R;
import com.cn.test.databinding.ModuleTestFragmentBinding;
import com.cn.test.viewmodel.ShareViewModel;

public class ModuleTestFragment extends Fragment {

    private ShareViewModel mViewModel;
    private ModuleTestFragmentBinding mBinding;
    public static ModuleTestFragment newInstance() {
        return new ModuleTestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_test_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        // TODO: Use the ViewModel
    }


}