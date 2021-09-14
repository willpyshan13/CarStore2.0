package com.yanxin.store.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_mall_order)
public class MallOrderFragment extends BaseFragment {
    private String[] mTitles = new String[]{"全部", "进行中", "已完成"};
    private int orderType; //0:"单品", 1:"拼团", 2:"工位"
    private ArrayList<Fragment> mFragment;
    private RadioGroup mServiceWay;
    private TabLayout mTabLayout;
    private NoScrollViewPager mMallOrderVp;
    private PagerTitleAdapter mPagerTitleAdapter;


    @Override
    protected void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            MallOrderChildFragment goodsFragment = new MallOrderChildFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("order_type", orderType);
            bundle.putInt("order_status_type", i);
            goodsFragment.setArguments(bundle);
            mFragment.add(goodsFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        orderType = getArguments().getInt("order_type");
        mFragment = new ArrayList<>();
        mServiceWay = findViewById(R.id.service_way);
        mTabLayout = findViewById(R.id.tabLayout);
        mMallOrderVp = findViewById(R.id.mall_order_vp);
        mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), mTitles, mFragment);
        mMallOrderVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mMallOrderVp);
        if (orderType == 2) {
            mServiceWay.setVisibility(View.GONE);
        } else {
            mServiceWay.setVisibility(View.VISIBLE);
        }
    }
}
