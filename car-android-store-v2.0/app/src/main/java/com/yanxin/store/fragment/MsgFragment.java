package com.yanxin.store.fragment;

import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpFragment;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MsgContract;
import com.yanxin.store.presenter.MsgPresenter;

@XmlLayoutResId(contentId = R.layout.fragment_msg)
public class MsgFragment extends BaseMvpFragment<MsgPresenter> implements MsgContract.MsgView {
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
        return MsgPresenter.newInstance();
    }
}
