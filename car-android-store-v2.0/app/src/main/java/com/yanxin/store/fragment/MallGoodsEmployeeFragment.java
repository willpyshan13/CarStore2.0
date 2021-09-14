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


@XmlLayoutResId(contentId = R.layout.fragment_mall_tab_employee)
public class MallGoodsEmployeeFragment extends BaseFragment {
    private TabLayout mTabLayout;
    private NoScrollViewPager mallOrderVp;
    private PagerTitleAdapter mPagerTitleAdapter;
    private ArrayList<Fragment> mFragment;


    private String[] mTitles = new String[]{"单品订单", "拼团订单", "工位订单"};

    @Override
    protected void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            MallOrderFragment orderFragment = new MallOrderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("order_type", i);
            orderFragment.setArguments(bundle);
            mFragment.add(orderFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        mTabLayout = findViewById(R.id.mall_employee_tab1);
        mallOrderVp = findViewById(R.id.mall_order_vp);
        mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), mTitles, mFragment);
        mallOrderVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mallOrderVp);
    }
}
