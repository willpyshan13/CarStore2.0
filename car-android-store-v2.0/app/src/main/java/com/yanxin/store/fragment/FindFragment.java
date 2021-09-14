package com.yanxin.store.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

import static com.yanxin.store.commont.Constant.AsynchronousStatus.USER_TYPE_STORE;

@XmlLayoutResId(contentId = R.layout.fragment_find)
public class FindFragment extends BaseFragment {
    private String[] titles = {"技术案例", "知识问答", "DTC查询"};
    private PagerTitleAdapter mTitleAdapter;
    private ArrayList<Fragment> mFindFragments;
    private TabLayout mFindTab;
    private RadioGroup mFindQuizGroup;
    private EditText mFindDTC;
    private ImageView mFindSearch;
    private LinearLayout mDtcLayout;
    private TextView mDtcBrand;

    private NoScrollViewPager mFindVp;


    @Override
    protected void initData() {
        FCaseFragment mCaseFragment = new FCaseFragment();
        mFindFragments.add(mCaseFragment);
        FQuizFragment mQuizFragment = new FQuizFragment();
        mFindFragments.add(mQuizFragment);
        FDTCFragment mDtcFragment = new FDTCFragment();
        mFindFragments.add(mDtcFragment);
        mTitleAdapter.notifyDataSetChanged();
        if (MyApplication.getUserType() == USER_TYPE_STORE) {  //APP只有技师和店铺身份，店铺身份没有向我提问
            mFindQuizGroup.removeViewAt(2);
        }
    }

    @Override
    protected void initView() {
        mFindTab = findViewById(R.id.find_tab);
        mFindVp = findViewById(R.id.find_vp);
        mFindQuizGroup = findViewById(R.id.quiz_tab_group);
        mFindDTC = findViewById(R.id.find_dtc);
        mDtcLayout = findViewById(R.id.find_dtc_layout);
        mDtcBrand = findViewById(R.id.find_dtc_brand);
        mFindSearch = findViewById(R.id.dtc_search);
        mFindFragments = new ArrayList<>();
        mTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), titles, mFindFragments);
        mFindVp.setAdapter(mTitleAdapter);
        mFindTab.setupWithViewPager(mFindVp);
        mFindTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mFindQuizGroup.setVisibility(View.GONE);
                    mDtcLayout.setVisibility(View.GONE);
                } else if (tab.getPosition() == 1) {
                    mFindQuizGroup.setVisibility(View.VISIBLE);
                    mDtcLayout.setVisibility(View.GONE);
                    ((FQuizFragment) mFindFragments.get(1)).setRadioGroup(mFindQuizGroup);
                } else if (tab.getPosition() == 2) {
                    mFindQuizGroup.setVisibility(View.GONE);
                    mDtcLayout.setVisibility(View.VISIBLE);
                    mFindSearch.setOnClickListener(v ->
                            ((FDTCFragment) mFindFragments.get(2))
                                    .setDtcSearchStr(mFindDTC.getText().toString()));
                    mDtcBrand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((FDTCFragment) mFindFragments.get(2))
                                    .showDtcBrand(mDtcBrand);
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void setAskTitle(int size) {
        ((RadioButton) mFindQuizGroup.getChildAt(2)).setText("回答(" + size + ")");
    }

    @SuppressLint("SetTextI18n")
    public void setMineTitle(int size) {
        ((RadioButton) mFindQuizGroup.getChildAt(1)).setText("已抢答(" + size + ")");
    }

    public void setDtcBrandValue(String value) {
        mDtcBrand.setText(value);
    }
}
