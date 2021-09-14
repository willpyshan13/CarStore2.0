package com.yanxin.store.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_my_rush_order_parent)
public class MyRushOrderParentFragment extends BaseFragment {
    private TabLayout mTabLayout;
    private NoScrollViewPager mRushVp;
    private String[] sceneTitle = {"全部", "进行中", "完成"};
    private ArrayList<Fragment> mSceneFragment;
    private PagerTitleAdapter mPagerTitleAdapter;
    private int type;

    @Override
    protected void initData() {
        if (type == 1) {
            mTabLayout.setVisibility(View.GONE);
            MyRushOrderFragment mAllFragment = new MyRushOrderFragment();
            Bundle allBundle = new Bundle();
            allBundle.putInt("order_type", type);
            mAllFragment.setArguments(allBundle);
            mSceneFragment.add(mAllFragment);
        } else {
            mTabLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                MyRushOrderFragment mAllFragment = new MyRushOrderFragment();
                Bundle allBundle = new Bundle();
                allBundle.putInt("order_type", type);
                allBundle.putInt("order_status", i + 1);
                mAllFragment.setArguments(allBundle);
                mSceneFragment.add(mAllFragment);
            }
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tabLayout);
        mRushVp = findViewById(R.id.rush_vp);
        mSceneFragment = new ArrayList<>();
        type = getArguments().getInt("type");
        mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), sceneTitle, mSceneFragment);
        mRushVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mRushVp);
    }
}
