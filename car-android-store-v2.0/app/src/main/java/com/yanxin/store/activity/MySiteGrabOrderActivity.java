package com.yanxin.store.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.fragment.MySiteOrderFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.activity_my_site)
public class MySiteGrabOrderActivity extends BaseActivity {
    private PagerTitleAdapter mPagerTitleAdapter;
    private final String[] titles = {"全部", "进行中", "已完成"};
    private ArrayList<Fragment> mSiteFragment;
    private NoScrollViewPager mSiteVp;
    private TabLayout mTabLayout;

    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            MySiteOrderFragment mySiteOrderFragment = new MySiteOrderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            mySiteOrderFragment.setArguments(bundle);
            mSiteFragment.add(mySiteOrderFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mSiteFragment = new ArrayList<>();
        mSiteVp = findViewById(R.id.site_vp);
        mTabLayout = findViewById(R.id.tabLayout);
        mPagerTitleAdapter = new PagerTitleAdapter(getSupportFragmentManager(), titles, mSiteFragment);
        mSiteVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mSiteVp);
    }
}
