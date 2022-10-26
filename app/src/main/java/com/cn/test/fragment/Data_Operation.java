package com.cn.test.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.cn.test.listener.MyViewListener;
import com.cn.test.R;
import com.cn.test.databinding.DataOperationFragmentBinding;
import com.cn.test.viewmodel.ShareViewModel;
import java.util.ArrayList;
import java.util.List;

public class Data_Operation extends Fragment {
    private List<String> mDataList = new ArrayList<String>();//数据源
    private ArrayAdapter<String> mAdapter;//适配器
    private DataOperationFragmentBinding mBinding;
    private static final String TAG = "Data_Operation";
    public static Data_Operation newInstance() {
        return new Data_Operation();
    }
    private ShareViewModel mShareViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.data_operation_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mShareViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        mBinding.setVariable(BR.viewlistener,new MyViewListener(mShareViewModel));
        initData();
        initView();
    }

    /**
     *
     */
    private void initData(){
        for(int i=1;i<256;i++){
            mDataList.add(i+"s");
        }
    }

    private void initView(){
        mAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item,R.id.tv_spinner,mDataList);
        //设置样式
        mAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //加载适配器
        mBinding.idSpinner.setAdapter(mAdapter);
        //只能设置项选择监听，不能设置项点击监听
        mBinding.idSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                mShareViewModel.getmSeconds().setValue(mDataList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}