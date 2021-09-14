package com.yanxin.store.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.fragment.RushOrderFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.activity_rush_order)
public class RushOrderActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private NoScrollViewPager mRushVp;
    private String[] mTitles = new String[]{"我提问的", "我旁听的", "我回答的"};
    private ArrayList<Fragment> mFragment;
    private PagerTitleAdapter mPagerTitleAdapter;
    private boolean isOrder;

    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            RushOrderFragment orderFragment = new RushOrderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            bundle.putBoolean("is_order", isOrder);
            orderFragment.setArguments(bundle);
            mFragment.add(orderFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tabLayout);
        mRushVp = findViewById(R.id.rush_vp);
        mFragment = new ArrayList<>();
        isOrder = getIntent().getBooleanExtra("is_order", false);
        mPagerTitleAdapter = new PagerTitleAdapter(getSupportFragmentManager(), mTitles, mFragment);
        mRushVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mRushVp);
    }
}
