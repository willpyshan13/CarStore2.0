package com.yanxin.store.fragment.subfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.fragment_sub_account)
public class SubAccountFragment extends BaseFragment {
    private ArrayList<Fragment> mSceneFragment;
    private final String[] sceneTitle = {"全部", "进行中", "完成"};
    private PagerTitleAdapter mPagerTitleAdapter;
    private TabLayout mSubChildTable;
    private NoScrollViewPager mSubChildVp;
    private int fragmentType;

    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            SubGrabAccountFragment grabAccountFragment = new SubGrabAccountFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("order_type", i);
            bundle.putInt("fragment_type",fragmentType);
            grabAccountFragment.setArguments(bundle);
            mSceneFragment.add(grabAccountFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        fragmentType = getArguments().getInt("fragment_type");
        mSubChildTable = findViewById(R.id.sub_child_table);
        mSubChildVp = findViewById(R.id.sub_child_vp);
        mSceneFragment = new ArrayList<>();
        mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), sceneTitle, mSceneFragment);
        mSubChildVp.setAdapter(mPagerTitleAdapter);
        mSubChildTable.setupWithViewPager(mSubChildVp);
    }
}
