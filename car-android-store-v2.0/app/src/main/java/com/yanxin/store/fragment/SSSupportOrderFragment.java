package com.yanxin.store.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.activity.AddSiteOrderActivity;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.req.SceneOrderReq;
import com.yanxin.store.ui.NoScrollViewPager;
import com.yanxin.store.ui.TouchLayout;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_support)
public class SSSupportOrderFragment extends BaseFragment {
    private int supportType;  //0:待抢单列表   1:支持下单/我的下单   2:已抢单列表
    private ArrayList<Fragment> mSupportFragment;
    private PagerTitleAdapter mPagerTitleAdapter;
    private final String[] allTitles = {"全部", "进行中", "完成"};
    private final String[] waitTitles = {"全部"};
    private TouchLayout mCaseTouchView;
    private ImageView mAddCase;
    private TabLayout mTabLayout;
    private NoScrollViewPager mSupportVp;

    @Override
    protected void initData() {
        SSSupportListFragment mAllFragment = new SSSupportListFragment();
        Bundle mAllBundle = new Bundle();
        mAllBundle.putInt("support_type", supportType);
        mAllBundle.putInt("type", 0);
        mAllFragment.setArguments(mAllBundle);
        mSupportFragment.add(mAllFragment);
        if (supportType != 0) {
            SSSupportListFragment mProcessFragment = new SSSupportListFragment();
            Bundle mProcessBundle = new Bundle();
            mProcessBundle.putInt("support_type", supportType);
            mProcessBundle.putInt("type", 1);
            mProcessFragment.setArguments(mProcessBundle);
            mSupportFragment.add(mProcessFragment);
            SSSupportListFragment mEnterFragment = new SSSupportListFragment();
            Bundle mEnterBundle = new Bundle();
            mEnterBundle.putInt("support_type", supportType);
            mEnterBundle.putInt("type", 2);
            mEnterFragment.setArguments(mEnterBundle);
            mSupportFragment.add(mEnterFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
        switch (supportType) {
            case 0:
                mTabLayout.setVisibility(View.GONE);
                mCaseTouchView.setVisibility(View.GONE);
                break;
            case 1:
                mTabLayout.setVisibility(View.VISIBLE);
                mCaseTouchView.setVisibility(View.GONE);
                break;
            case 2:
                mTabLayout.setVisibility(View.VISIBLE);
                mCaseTouchView.setVisibility(View.VISIBLE);
                break;
        }
        mAddCase.setOnClickListener(v -> startActivity(new Intent(mActivity, AddSiteOrderActivity.class)));
    }

    @Override
    protected void initView() {
        supportType = getArguments().getInt("support_type");
        mSupportFragment = new ArrayList<>();
        if (supportType != 0) {
            mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), allTitles, mSupportFragment);
        } else {
            mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), waitTitles, mSupportFragment);
        }
        mSupportVp = findViewById(R.id.support_vp);
        mSupportVp.setAdapter(mPagerTitleAdapter);
        mCaseTouchView = findViewById(R.id.case_touch_view);
        mAddCase = findViewById(R.id.add_case);
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mSupportVp);
    }
}
