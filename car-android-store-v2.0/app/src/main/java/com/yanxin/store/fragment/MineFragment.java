package com.yanxin.store.fragment;

import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MineContract;
import com.yanxin.store.presenter.MinePresenter;

@XmlLayoutResId(contentId = R.layout.fragment_mine)
public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.MineView {
    @Override
    protected void initView() {

    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public BasePresenter initPresenter() {
        return MinePresenter.newInstance();
    }
}
