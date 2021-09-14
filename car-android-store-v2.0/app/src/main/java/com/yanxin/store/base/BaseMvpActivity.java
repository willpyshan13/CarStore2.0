package com.yanxin.store.base;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.yanxin.store.R;
import com.yanxin.store.ui.OptionalEditLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IBaseView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mPresenter.attachMVP(this);
        }
        initMVPView();
        initMVPData();
    }

    protected abstract void initMVPData();

    protected abstract void initMVPView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachMVP();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
    }
}
