package com.yanxin.store.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.yanxin.store.R;
import com.yanxin.store.adapter.MainFragmentPagerAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

/**
 * 服务  支持订单页面
 */
@XmlLayoutResId(contentId = R.layout.fragment_serving_shared_sustain)
public class SSharedSustainFragment extends BaseFragment {
    private RadioGroup mServingTab;
    private NoScrollViewPager mServingVp;
    private ArrayList<Fragment> mServingFragment;
    private MainFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void initData() {
        int childCount = mServingTab.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int finalI = i;
            mServingTab.getChildAt(i).setOnClickListener(v -> mServingVp.setCurrentItem(finalI));
        }
        SSSupportOrderFragment waitFragment = new SSSupportOrderFragment();
        Bundle waitBundle = new Bundle();
        waitBundle.putInt("support_type", 0);
        waitFragment.setArguments(waitBundle);
        mServingFragment.add(waitFragment);
        SSSupportOrderFragment mineOrderFragment = new SSSupportOrderFragment();
        Bundle mineOrderBundle = new Bundle();
        mineOrderBundle.putInt("support_type", 2);
        mineOrderFragment.setArguments(mineOrderBundle);
        mServingFragment.add(mineOrderFragment);
        SSSupportOrderFragment enterFragment = new SSSupportOrderFragment();
        Bundle enterBundle = new Bundle();
        enterBundle.putInt("support_type", 1);
        enterFragment.setArguments(enterBundle);
        mServingFragment.add(enterFragment);
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mServingTab = findViewById(R.id.serving_tab);
        mServingVp = findViewById(R.id.serving_vp);
        mServingFragment = new ArrayList<>();
        mPagerAdapter = new MainFragmentPagerAdapter(getChildFragmentManager(), mServingFragment);
        mServingVp.setAdapter(mPagerAdapter);

    }
}
