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
import com.cn.test.R;
import com.cn.test.databinding.LiftOperationFragmentBinding;
import com.cn.test.listener.MyViewListener;
import com.cn.test.viewmodel.ShareViewModel;
import java.util.ArrayList;
import java.util.List;

public class Lift_Operation extends Fragment {

    private ShareViewModel mViewModel;
    private LiftOperationFragmentBinding mBinding;
    private ArrayAdapter<String> mLiftAdapter;//适配器
    private List<String> mLiftDataList = new ArrayList<String>();//数据源

    public static Lift_Operation newInstance() {
        return new Lift_Operation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.lift__operation_fragment, container, false);
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
        mLiftDataList.add("自动");
        mLiftDataList.add("手动");
        mViewModel.setmCheckType(mLiftDataList.get(0));
        mLiftAdapter = new ArrayAdapter<String>(getContext(), R.layout.lift_spinner_item,R.id.tv_spinner,mLiftDataList);
        //设置样式
        mLiftAdapter.setDropDownViewResource(R.layout.lift_spinner_dropdown_item);
        //加载适配器
        mBinding.idLiftspinner.setAdapter(mLiftAdapter);
        mBinding.idLiftspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                mViewModel.setmCheckType(mLiftDataList.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}