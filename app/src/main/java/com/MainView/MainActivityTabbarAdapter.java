package com.MainView;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.MainView.MainActivityUnit;

import java.util.ArrayList;

/*
主界面下方tabbar的适配器
 */
public class MainActivityTabbarAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentArray=null;

    /*
    初始化，传入有哪些页面
     */
    public MainActivityTabbarAdapter(@NonNull FragmentManager fm, int behavior,ArrayList<Fragment> fragments) {
        super(fm, behavior);
        this.fragmentArray=fragments;
    }


    /*
    点击tabbar时切换页面
     */
    @Override
    public Fragment getItem(int position) {
        return fragmentArray.get(position);
    }

    /*
    得到页面总数
     */
    @Override
    public int getCount() {
        return fragmentArray.size();
    }

}