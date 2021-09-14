package com.yanxin.store.activity;

import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.SquareContentContract;
import com.yanxin.store.presenter.SquareContentPresenter;

@XmlLayoutResId(contentId = R.layout.activity_square_content)
public class SquareContentActivity extends BaseMvpActivity<SquareContentPresenter> implements SquareContentContract.SquareContentView {
    @Override
    protected void initMVPData() {

    }

    @Override
    protected void initMVPView() {

    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public BasePresenter initPresenter() {
        return SquareContentPresenter.newInstance();
    }
}
