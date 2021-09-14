package com.yanxin.store.activity.subactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.R;
import com.yanxin.store.activity.LoginActivity;
import com.yanxin.store.adapter.MainFragmentPagerAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.SubAccountContract;
import com.yanxin.store.fragment.subfragment.SubAccountFragment;
import com.yanxin.store.fragment.subfragment.SubMallOrderFragment;
import com.yanxin.store.presenter.SubAccountPresenter;
import com.yanxin.store.ui.NoScrollViewPager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

@XmlLayoutResId(contentId = R.layout.activity_sub_account)
public class SubAccountActivity extends BaseActivity {
    private RadioGroup mSubTab;
    private NoScrollViewPager mSubVp;
    private ArrayList<Fragment> mSubFragment;
    private MainFragmentPagerAdapter mPagerAdapter;
    private CircleImageView mSubAvatar;
    private TextView mStoreName;
    private TextView mUserName;
    private ImageView mSubExit;
    private RadioGroup mServingTab;

    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                SubMallOrderFragment mallOrderFragment = new SubMallOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("fragment_type", i);
                mallOrderFragment.setArguments(bundle);
                mSubFragment.add(mallOrderFragment);
            } else {
                SubAccountFragment accountFragment = new SubAccountFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("fragment_type", i);
                accountFragment.setArguments(bundle);
                mSubFragment.add(accountFragment);
            }
        }
        mPagerAdapter.notifyDataSetChanged();
        mSubExit.setOnClickListener(v -> {
            SPUtils.getInstance().remove("user_type");
            SPUtils.getInstance().remove("user_uuid");
            SPUtils.getInstance().remove("user_sub_account");
            SPUtils.getInstance().remove("user_token");
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        for (int i = 0; i < mServingTab.getChildCount(); i++) {
            int finalI = i;
            mServingTab.getChildAt(i).setOnClickListener(v -> mSubVp.setCurrentItem(finalI));
        }
    }

    @Override
    protected void initView() {
        mSubFragment = new ArrayList<>();
        mSubAvatar = findViewById(R.id.sub_avatar);
        mStoreName = findViewById(R.id.store_name);
        mUserName = findViewById(R.id.user_name);
        mSubExit = findViewById(R.id.sub_exit);
        mSubVp = findViewById(R.id.sub_vp);
        mServingTab = findViewById(R.id.serving_tab);
        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mSubFragment);
        mSubVp.setAdapter(mPagerAdapter);
    }
}
