package com.yanxin.store.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.yanxin.store.R;
import com.yanxin.store.adapter.MainFragmentPagerAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;


@XmlLayoutResId(contentId = R.layout.fragment_serving_store_grab_order)
public class SGrabOrdersOrderFragment extends BaseFragment {
    private RadioGroup mSquareTab;
    private NoScrollViewPager mStoreVp;
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    private ArrayList<Fragment> mFragments;


    @Override
    protected void initData() {
        int childCount = mSquareTab.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int finalI = i;
            mSquareTab.getChildAt(i).setOnClickListener(v -> mStoreVp.setCurrentItem(finalI));
        }
        for (int i = 0; i < 2; i++) {
            SGrabChildFragment childFragment = new SGrabChildFragment();
            Bundle childBundle = new Bundle();
            childBundle.putInt("service_type", i);
            childFragment.setArguments(childBundle);
            mFragments.add(childFragment);
        }
        mainFragmentPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mSquareTab = findViewById(R.id.square_tab);
        mStoreVp = findViewById(R.id.store_vp);
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getChildFragmentManager(), mFragments);
        mStoreVp.setAdapter(mainFragmentPagerAdapter);
    }
}
