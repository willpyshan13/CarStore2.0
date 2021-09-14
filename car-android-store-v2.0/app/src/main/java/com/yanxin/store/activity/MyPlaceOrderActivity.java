package com.yanxin.store.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.fragment.MyPlaceOrderFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.activity_my_site)
public class MyPlaceOrderActivity extends BaseActivity {
    private PagerTitleAdapter mPagerTitleAdapter;
    private final String[] titles = {"全部", "进行中", "已完成"};
    private ArrayList<Fragment> mPlaceFragment;
    private NoScrollViewPager mSiteVp;
    private TabLayout mTabLayout;

    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            MyPlaceOrderFragment myPlaceOrderFragment = new MyPlaceOrderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            myPlaceOrderFragment.setArguments(bundle);
            mPlaceFragment.add(myPlaceOrderFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mPlaceFragment = new ArrayList<>();
        mSiteVp = findViewById(R.id.site_vp);
        mTabLayout = findViewById(R.id.tabLayout);
        mPagerTitleAdapter = new PagerTitleAdapter(getSupportFragmentManager(), titles, mPlaceFragment);
        mSiteVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mSiteVp);
    }
}
