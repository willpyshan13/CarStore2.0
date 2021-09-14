package com.yanxin.store.fragment;

import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.LoginActivity;
import com.yanxin.store.adapter.PagerTitleAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.commont.Constant;
import com.yanxin.store.event.SupportEvent;
import com.yanxin.store.ui.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.yanxin.store.commont.Constant.AsynchronousStatus.USER_TYPE_STORE;

@XmlLayoutResId(contentId = R.layout.fragment_serving)
public class ServingFragment extends BaseFragment {
    private final String[] storeTitle = {"现场支持订单", "紧急共享工位"};
    private final String[] technicianTitle = {"共享技师订单", "现场支持订单"};

    private ArrayList<Fragment> mStoreFragment;
    private ArrayList<Fragment> mTechnicianFragment;

    private PagerTitleAdapter mPagerTitleAdapter;
    private TabLayout mServingTab;
    private NoScrollViewPager mServingVp;
    private Switch mServiceOnline;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mServingTab = findViewById(R.id.serving_tab);
        mServingVp = findViewById(R.id.serving_vp);
        mServingTab.setupWithViewPager(mServingVp);
        mServiceOnline = findViewById(R.id.service_online);
        mServiceOnline.setChecked(Constant.AsynchronousStatus.SERVICE_ONLINE);
        mServiceOnline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Constant.AsynchronousStatus.SERVICE_ONLINE = isChecked;
            SPUtils.getInstance().put("is_online",isChecked);
            EventBus.getDefault().post(new SupportEvent());
        });
        if (MyApplication.getUserType() == USER_TYPE_STORE) {//店铺身份
            mStoreFragment = new ArrayList<>();
            mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), storeTitle, mStoreFragment);
            mServingVp.setAdapter(mPagerTitleAdapter);
            SSharedSustainFragment sSharedSustainFragment = new SSharedSustainFragment();
            mStoreFragment.add(sSharedSustainFragment);
            SGrabOrdersOrderFragment sGrabOrdersOrderFragment = new SGrabOrdersOrderFragment();
            mStoreFragment.add(sGrabOrdersOrderFragment);
            mPagerTitleAdapter.notifyDataSetChanged();
        } else if (MyApplication.getUserType() == 2) { //技师身份
            mTechnicianFragment = new ArrayList<>();
            mPagerTitleAdapter = new PagerTitleAdapter(getChildFragmentManager(), technicianTitle, mTechnicianFragment);
            mServingVp.setAdapter(mPagerTitleAdapter);
            SSharedParentFragment sSharedParentFragment = new SSharedParentFragment();
            mTechnicianFragment.add(sSharedParentFragment);
            SSharedSustainFragment sSharedSustainFragment = new SSharedSustainFragment();
            mTechnicianFragment.add(sSharedSustainFragment);
            mPagerTitleAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort("当前不是技师/店铺身份");
            SPUtils.getInstance().remove("user_type");
            SPUtils.getInstance().remove("user_uuid");
            SPUtils.getInstance().remove("user_sub_account");
            SPUtils.getInstance().remove("user_token");
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
