package com.yanxin.store.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.R;
import com.yanxin.store.activity.MallGoodsCreateActivity;
import com.yanxin.store.activity.MallGroupCreateActivity;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;


@XmlLayoutResId(contentId = R.layout.fragment_mall_tab_group_buy)
public class MallGroupFragment extends BaseFragment {
    private TabLayout mTabLayout;
    private ImageView mAddTaskIv;
    private NoScrollViewPager mGoodsVp;
    private PagerTitleAdapter mPagerTitleAdapter;
    private String[] mGroupBuyTitles = new String[]{"全部", "未开始", "进行中", "已结束"};
    private ArrayList<Fragment> mFragment;

    @Override
    protected void initData() {
        mAddTaskIv.setOnClickListener(view -> startActivity(new Intent(mActivity, MallGroupCreateActivity.class)));
        for (int i = 0; i < 4; i++) {
            MallGroupBuyFragment goodsFragment = new MallGroupBuyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("goods_type", i);
            goodsFragment.setArguments(bundle);
            mFragment.add(goodsFragment);
        }
        mPagerTitleAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        mTabLayout = findViewById(R.id.tabLayout);
        mAddTaskIv = findViewById(R.id.mall_tab_add_task_iv);
        mGoodsVp = findViewById(R.id.mall_vp);
        mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), mGroupBuyTitles, mFragment);
        mGoodsVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mGoodsVp);
    }
}

