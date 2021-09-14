package com.yanxin.store.adapter;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerTitleAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private ArrayList<Fragment> fragments ;
    private FragmentManager fm;

    public PagerTitleAdapter(FragmentManager fm, String[] mTitles, ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        this.titles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
