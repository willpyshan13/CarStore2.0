package com.yanxin.store.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.MyDtcBean;
import com.yanxin.store.bean.MyDtcNumberBean;
import com.yanxin.store.event.DtcMyBuyEvent;
import com.yanxin.store.fragment.MyDtcFragment;
import com.yanxin.store.ui.NoScrollViewPager;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

@XmlLayoutResId(contentId = R.layout.activity_dtc_mybuy)
public class DtcMyBuyActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private NoScrollViewPager mDtcVp;
    private PagerTitleAdapter mPagerTitleAdapter;
    private final String[] titles = {"可查看", "次数已用完", "我的补录"};
    private ArrayList<Fragment> dtcFragment;

    @Override
    protected void initData() {
        MyDtcFragment mBuyFragment = new MyDtcFragment();
        Bundle mBuyBundle = new Bundle();
        mBuyBundle.putInt("dtc_type", 0);
        mBuyFragment.setArguments(mBuyBundle);
        dtcFragment.add(mBuyFragment);
        MyDtcFragment mExpiredFragment = new MyDtcFragment();
        Bundle mExpiredBundle = new Bundle();
        mExpiredBundle.putInt("dtc_type", 1);
        mExpiredFragment.setArguments(mExpiredBundle);
        dtcFragment.add(mExpiredFragment);
        MyDtcFragment mReleaseFragment = new MyDtcFragment();
        Bundle mReleaseBundle = new Bundle();
        mReleaseBundle.putInt("dtc_type", 2);
        mReleaseFragment.setArguments(mReleaseBundle);
        dtcFragment.add(mReleaseFragment);
        mPagerTitleAdapter.notifyDataSetChanged();
        getBuyCount();
    }

    @SuppressLint("CheckResult")
    private void getBuyCount() {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryDtcCount(MyApplication.getUserToken())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(myDtcBean -> {
                    if (myDtcBean.getSuccess()) {
                        MyDtcNumberBean.DataDTO data = myDtcBean.getData();
                        titles[0] = "可查看(" + data.getKecha() + ")";
                        titles[1] = "次数已用完(" + data.getGuoqi() + ")";
                        mPagerTitleAdapter.notifyDataSetChanged();
                    }
                }, throwable -> ToastUtils.showShort(throwable.getMessage()));
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        dtcFragment = new ArrayList<>();
        mTabLayout = findViewById(R.id.tabLayout);
        mDtcVp = findViewById(R.id.dtc_vp);
        mPagerTitleAdapter = new PagerTitleAdapter(getSupportFragmentManager(), titles, dtcFragment);
        mDtcVp.setAdapter(mPagerTitleAdapter);
        mTabLayout.setupWithViewPager(mDtcVp);
    }

    @Subscribe
    public void eventMyBuy(DtcMyBuyEvent dtcMyBuyEvent) {
        getBuyCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
