package com.yanxin.store.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.fragment.MyCaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.activity_my_case)
public class MyCaseActivity extends BaseActivity {
    private String[] caseTitle = {"我发布的", "我购买的"};
    private ArrayList<Fragment> mCaseFragment;
    private PagerTitleAdapter mPagerTitleAdapter;
    private TabLayout mCaseTab;
    private NoScrollViewPager mCaseVp;


    @Override
    protected void initData() {
        MyCaseFragment caseMyFragment = new MyCaseFragment();
        Bundle myBundle = new Bundle();
        myBundle.putInt("case_type",0);
        caseMyFragment.setArguments(myBundle);
        mCaseFragment.add(caseMyFragment);
        MyCaseFragment caseBuyFragment = new MyCaseFragment();
        Bundle buyBundle = new Bundle();
        buyBundle.putInt("case_type",1);
        caseBuyFragment.setArguments(buyBundle);
        mCaseFragment.add(caseBuyFragment);
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mCaseFragment = new ArrayList<>();
        mCaseTab = findViewById(R.id.case_tab);
        mCaseVp = findViewById(R.id.case_vp);
        mCaseTab.setupWithViewPager(mCaseVp);
        mPagerTitleAdapter = new PagerTitleAdapter(getSupportFragmentManager(), caseTitle, mCaseFragment);
        mCaseVp.setAdapter(mPagerTitleAdapter);
    }
}
