package com.yanxin.store.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private FragmentManager fm;

    public MainFragmentPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
