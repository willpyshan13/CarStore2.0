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

/**
 * 服务紧急共享工位的子页面(待抢单/已抢单)下的
 */
@XmlLayoutResId(contentId = R.layout.fragment_serving_store_grab_child)
public class SGrabChildFragment extends BaseFragment {
    private ArrayList<Fragment> mSceneFragment;
    private int serviceType;
    private String[] sceneTitle = {"全部", "进行中", "完成"};
    private PagerTitleAdapter mPagerTitleAdapter;
    private TabLayout mOrderTab;
    private NoScrollViewPager mStoreVp;

    @Override
    protected void initData() {
        if (serviceType == 0) {
            mOrderTab.setVisibility(View.GONE);
            SGrabChildContentFragment sGrabChildContentFragment = new SGrabChildContentFragment();
            Bundle sGrabBundle = new Bundle();
            sGrabBundle.putInt("service_type", serviceType);
            sGrabBundle.putInt("status", 0);
            sGrabChildContentFragment.setArguments(sGrabBundle);
            mSceneFragment.add(sGrabChildContentFragment);
        } else {
            mOrderTab.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                SGrabChildContentFragment sGrabChildContentFragment = new SGrabChildContentFragment();
                Bundle sGrabBundle = new Bundle();
                sGrabBundle.putInt("service_type", serviceType);
                sGrabBundle.putInt("status", i);
                sGrabChildContentFragment.setArguments(sGrabBundle);
                mSceneFragment.add(sGrabChildContentFragment);
            }
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        serviceType = getArguments().getInt("service_type");
        mSceneFragment = new ArrayList<>();
        mOrderTab = (TabLayout) findViewById(R.id.tabLayout);
        mStoreVp = (NoScrollViewPager) findViewById(R.id.store_vp);
        mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), sceneTitle, mSceneFragment);
        mStoreVp.setAdapter(mPagerTitleAdapter);
        mOrderTab.setupWithViewPager(mStoreVp);
    }
}
