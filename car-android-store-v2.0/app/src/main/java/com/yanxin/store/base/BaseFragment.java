package com.yanxin.store.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.KeyboardUtils;
import com.yanxin.store.utils.XmlLayoutResIdUtils;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Context mContext;
    protected BaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(XmlLayoutResIdUtils.checkRes(this), container, false);
            initView();
            initData();
        }
        return mRootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        KeyboardUtils.hideSoftInput(mRootView);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        this.mActivity = (BaseActivity) context;
        this.mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }


    protected <T extends View> T findViewById(int resId) {
        return mRootView.findViewById(resId);
    }

    protected abstract void initData();

    protected abstract void initView();
}
