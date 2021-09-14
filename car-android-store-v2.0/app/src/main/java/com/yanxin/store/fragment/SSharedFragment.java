package com.yanxin.store.fragment;

import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.SharedSceneContract;
import com.yanxin.store.presenter.SceneOrderPresenter;
import com.yanxin.store.presenter.SharedScenePresenter;


/**
 * 共享技师订单页面
 */
@XmlLayoutResId(contentId = R.layout.fragment_serving_shared_scene)
public class SSharedFragment extends BaseMvpFragment<SharedScenePresenter> implements SharedSceneContract.SharedSceneView {
    private int mSharedType;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mSharedType = getArguments().getInt("shared_type");


    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public BasePresenter initPresenter() {
        return SceneOrderPresenter.newInstance();
    }
}
