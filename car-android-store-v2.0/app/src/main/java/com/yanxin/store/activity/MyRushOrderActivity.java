package com.yanxin.store.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.MainFragmentPagerAdapter;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.fragment.MyRushOrderParentFragment;
import com.yanxin.store.fragment.RushOrderFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.activity_my_rush_order)
public class MyRushOrderActivity extends BaseActivity {
    private NoScrollViewPager mRushVp;
    private ArrayList<Fragment> mFragment;
    private MainFragmentPagerAdapter mPagerTitleAdapter;
    private boolean isOrder;
    private android.widget.RadioGroup mSquareTab;

    @Override
    protected void initData() {
        for (int i = 0; i < 2; i++) {
            MyRushOrderParentFragment orderFragment = new MyRushOrderParentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            bundle.putBoolean("is_order", isOrder);
            orderFragment.setArguments(bundle);
            mFragment.add(orderFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
        int childCount = mSquareTab.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int finalI = i;
            mSquareTab.getChildAt(i).setOnClickListener(v -> {
                mRushVp.setCurrentItem(finalI);
            });
        }
    }

    @Override
    protected void initView() {
        mRushVp = findViewById(R.id.rush_vp);
        mSquareTab = findViewById(R.id.square_tab);
        mFragment = new ArrayList<>();
        isOrder = getIntent().getBooleanExtra("is_order", false);
        mPagerTitleAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragment);
        mRushVp.setAdapter(mPagerTitleAdapter);
    }
}
