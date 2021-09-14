package com.yanxin.store.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.yanxin.store.R;
import com.yanxin.store.adapter.MainFragmentPagerAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

/**
 * 发现页-知识问答  广场
 */
@XmlLayoutResId(contentId = R.layout.fragment_fq_square)
public class FQSquareFragment extends BaseFragment {
    private ArrayList<Fragment> mSquareFragment;
    private MainFragmentPagerAdapter squareAdapter;
    private RadioGroup mSquareTab;
    private NoScrollViewPager mSquareVp;

    @Override
    protected void initData() {
        FQSquareListFragment allFragment = new FQSquareListFragment();
        Bundle allBundle = new Bundle();
        allBundle.putInt("answer_type", 1);
        allFragment.setArguments(allBundle);
        mSquareFragment.add(allFragment);
        FQSquareListFragment answerFragment = new FQSquareListFragment();
        Bundle answerBundle = new Bundle();
        answerBundle.putInt("answer_type", 2);
        answerFragment.setArguments(answerBundle);
        mSquareFragment.add(answerFragment);
        squareAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mSquareFragment = new ArrayList<>();
        squareAdapter = new MainFragmentPagerAdapter(getChildFragmentManager(), mSquareFragment);
        mSquareTab = findViewById(R.id.square_tab);
        mSquareVp = findViewById(R.id.square_vp);
        int childCount = mSquareTab.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mSquareTab.getChildAt(i);
            int finalCurrentPosition = i;
            childAt.setOnClickListener(view -> mSquareVp.setCurrentItem(finalCurrentPosition));
        }
        mSquareVp.setAdapter(squareAdapter);
    }
}
