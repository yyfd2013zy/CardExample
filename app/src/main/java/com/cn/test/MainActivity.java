package com.cn.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.cn.test.databinding.MainBinding;
import com.cn.test.fragment.LiftFragment;
import com.cn.test.fragment.ModuleTestFragment;
import com.cn.test.fragment.TemperFragment;
import com.cn.test.viewmodel.ShareViewModel;

public class MainActivity extends AppCompatActivity {
    private Fragment  mCurrentFragment;
    private MainBinding binding;
    private ModuleTestFragment mModuleTestFragment = new ModuleTestFragment();
    private LiftFragment mLiftFragment = new LiftFragment();
    private TemperFragment mTemperFragment = new TemperFragment();
    private ShareViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.main);
        mViewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        switchFragment(mModuleTestFragment).commit();
        mViewModel.getmSwitchFragmentLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equalsIgnoreCase("lift")){
                    switchFragment(mLiftFragment).commit();
                }
                else if(s.equalsIgnoreCase("temperture")){
                    switchFragment(mTemperFragment).commit();
                }
                else{
                    switchFragment(mModuleTestFragment).commit();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    private FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (mCurrentFragment != null) {
              transaction.hide(mCurrentFragment);
            }
            transaction.add(R.id.id_currentfragment, targetFragment,targetFragment.getClass().getName());
        }
        else {
            transaction.hide(mCurrentFragment).show(targetFragment);
        }
        mCurrentFragment = targetFragment;
        return  transaction;
    }

    @Override
    public void onBackPressed() {
        if(mViewModel.getmSwitchFragmentLiveData().getValue() != null && mViewModel.getmSwitchFragmentLiveData().getValue().equalsIgnoreCase("lift")){
            mViewModel.getmSwitchFragmentLiveData().setValue("test");
        }
        else{
            super.onBackPressed();
        }
    }
}