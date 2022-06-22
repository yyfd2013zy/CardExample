package com.cn.test.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import com.cn.test.R;
import com.cn.test.databinding.LiftFragmentBinding;
import com.cn.test.listener.MyViewListener;
import com.cn.test.util.LogFileUtil;
import com.cn.test.viewmodel.ShareViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiftFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "LiftFragment";
    private ShareViewModel mViewModel;
    private LiftFragmentBinding mBinding;
    public static LiftFragment newInstance() {
        return new LiftFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.lift_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        mBinding.setVariable(BR.viewlistener,new MyViewListener(mViewModel));
        initView();
    }

    private void initView(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        for(int i = 1;i<21;i++){
            if(i<11){
                mBinding.idLayout.addView(getCheckBox(i),layoutParams);
            }
            else{
                mBinding.idLayout2.addView(getCheckBox(i),layoutParams);
            }
        }
        mViewModel.getmAllLift().setValue(false);
        mViewModel.getmAllLift().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    for(int i = 0;i<mBinding.idLayout.getChildCount();i++){
                        ((CheckBox)(mBinding.idLayout.getChildAt(i))).setChecked(true);
                    }
                    for(int i = 0;i<mBinding.idLayout2.getChildCount();i++){
                        ((CheckBox)(mBinding.idLayout2.getChildAt(i))).setChecked(true);
                    }
                }
                else{
                    for(int i = 0;i<mBinding.idLayout.getChildCount();i++){
                        ((CheckBox)(mBinding.idLayout.getChildAt(i))).setChecked(false);
                    }
                    for(int i = 0;i<mBinding.idLayout2.getChildCount();i++){
                        ((CheckBox)(mBinding.idLayout2.getChildAt(i))).setChecked(false);
                    }
                }
            }
        });
    }

    private CheckBox getCheckBox(int id){
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setId(id);
        checkBox.setButtonDrawable(getResources().getDrawable(R.drawable.my_checkbox));
        checkBox.setText(String.valueOf(id));
        checkBox.setTextColor(Color.BLACK);
        checkBox.setTextSize(40);
        checkBox.setPadding(20, 7, 0, 7);
        checkBox.setOnCheckedChangeListener(this);
        mViewModel.getmLiftArrayList().add(false);
        return checkBox;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        LogFileUtil.saveLog("选择开关: "+buttonView.getId()+"   "+isChecked);
        mViewModel.getmLiftArrayList().set(Integer.valueOf(buttonView.getId())-1,isChecked);
    }
}