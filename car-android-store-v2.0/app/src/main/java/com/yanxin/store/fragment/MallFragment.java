package com.yanxin.store.fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

@XmlLayoutResId(contentId = R.layout.fragment_mall)
public class MallFragment extends BaseFragment{
    private TabLayout tabLayout;
    private NoScrollViewPager viewPager;
    private PagerTitleAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] titles = new String[]{"商品", "拼团活动", "订单"};

    @Override
    protected void initData() {
        MallGoodsOrderFragment goodsFragment = new MallGoodsOrderFragment();
        mFragments.add(goodsFragment);
        MallGroupFragment groupBuyFragment = new MallGroupFragment();
        mFragments.add(groupBuyFragment);
        MallGoodsEmployeeFragment employeeFragment = new MallGoodsEmployeeFragment();
        mFragments.add(employeeFragment);
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        tabLayout = findViewById(R.id.mall_tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager, false);
        mPagerAdapter = new PagerTitleAdapter(getChildFragmentManager(), titles, mFragments);
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
