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


@XmlLayoutResId(contentId = R.layout.fragment_serving_scene_order_parent)
public class SSceneOrderParentFragment extends BaseFragment {
    private ArrayList<Fragment> mSceneFragment;
    private String[] sceneTitle = {"全部", "进行中", "完成"};
    private PagerTitleAdapter mPagerTitleAdapter;
    private TabLayout mOrderTab;
    private NoScrollViewPager mOrderVp;


    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            SSceneOrderFragment mAllFragment = new SSceneOrderFragment();
            Bundle allBundle = new Bundle();
            allBundle.putInt("order_type", i);
            mAllFragment.setArguments(allBundle);
            mSceneFragment.add(mAllFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mSceneFragment = new ArrayList<>();
        mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), sceneTitle, mSceneFragment);
        mOrderTab = findViewById(R.id.order_tab);
        mOrderVp = findViewById(R.id.order_vp);
        mOrderVp.setAdapter(mPagerTitleAdapter);
        mOrderTab.setupWithViewPager(mOrderVp);
    }
}
