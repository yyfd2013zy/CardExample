package com.cn.test.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
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
import com.cn.test.databinding.SerialFragmentBinding;
import com.cn.test.viewmodel.ShareViewModel;
import java.util.ArrayList;
import java.util.List;
import android_serialport_api.SerialPortFinder;

public class Serial_Operation extends Fragment {

    private ShareViewModel mShareViewModel;
    private SerialFragmentBinding mBinding;
    private List<String> mSerialDataList = new ArrayList<String>();//数据源
    private ArrayAdapter<String> mSerialAdapter;//适配器
    private List<String> mBaudrateDataList = new ArrayList<String>();//数据源
    private ArrayAdapter<String> mBaudrateAdapter;//适配器
    private static final String TAG = "SerialFragment";
    private SerialPortFinder mSerialPortFinder = new SerialPortFinder();//串口设备搜索
    private ObservableField<String> mSerialStr = new ObservableField<String>();

    public static Serial_Operation newInstance() {
        return new Serial_Operation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.serial_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mShareViewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        initData();
        initView();
    }

    private void initData(){
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();
        for(String str:entryValues){
            if(!str.contains("ttyGS")){
                mSerialDataList.add(str);
            }

        }
        String[] baudrates = getResources().getStringArray(R.array.baudrates_value);
        for(String str:baudrates){
            if(Integer.valueOf(str)>=9600){
                mBaudrateDataList.add(str);
            }
        }
    }

    private void initView(){
        mSerialAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item,R.id.tv_spinner,mSerialDataList);
        //设置样式
        mSerialAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //加载适配器
        mBinding.idSerial.setAdapter(mSerialAdapter);
        //只能设置项选择监听，不能设置项点击监听
        mBinding.idSerial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                mShareViewModel.getmSerial().setValue(mSerialDataList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mBaudrateAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item,R.id.tv_spinner,mBaudrateDataList);
        mBaudrateAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mBinding.idBaudrate.setAdapter(mBaudrateAdapter);
        mBinding.idBaudrate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                mShareViewModel.getmBaudrate().setValue(mBaudrateDataList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}