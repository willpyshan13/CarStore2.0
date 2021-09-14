package com.yanxin.store.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

/**
 * 发现页-知识问答  我抢答的
 */
@XmlLayoutResId(contentId = R.layout.fragment_fq_my_rush_parent)
public class FQMineRushParentFragment extends BaseFragment {

    private ArrayList<Fragment> mSupportFragment;
    private PagerTitleAdapter mPagerTitleAdapter;
    private String[] mTitles = {"全部", "进行中", "完成"};
    private TabLayout mTabLayout;
    private NoScrollViewPager mMyRushVp;

    @Override
    protected void initData() {
        FQMineRushFragment mAllFragment = new FQMineRushFragment();
        Bundle mAllBundle = new Bundle();
        mAllBundle.putInt("rush_type", 0);
        mAllFragment.setArguments(mAllBundle);
        mSupportFragment.add(mAllFragment);
        FQMineRushFragment mProcessFragment = new FQMineRushFragment();
        Bundle mProcessBundle = new Bundle();
        mProcessBundle.putInt("rush_type", 2);
        mProcessFragment.setArguments(mProcessBundle);
        mSupportFragment.add(mProcessFragment);
        FQMineRushFragment mEnterFragment = new FQMineRushFragment();
        Bundle mEnterBundle = new Bundle();
        mEnterBundle.putInt("rush_type", 3);
        mEnterFragment.setArguments(mEnterBundle);
        mSupportFragment.add(mEnterFragment);
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mMyRushVp = findViewById(R.id.my_rush_vp);
        mTabLayout = findViewById(R.id.tabLayout);
        mSupportFragment = new ArrayList<>();
        mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), mTitles, mSupportFragment);
        mMyRushVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mMyRushVp);
    }
}
