package com.yanxin.store.base;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IBaseFragment {
    protected P mPresenter;

    @Override
    protected void initData() {
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mPresenter.attachMVP(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachMVP();
        }
    }
}
