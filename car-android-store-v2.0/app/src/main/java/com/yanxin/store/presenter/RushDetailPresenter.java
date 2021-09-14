package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.RushDetailContract;
import com.yanxin.store.model.RushDetailModel;

public class RushDetailPresenter extends RushDetailContract.RushDetailPresenter {

    public static BasePresenter newInstance() {
        return new RushDetailPresenter();
    }


    @Override
    protected RushDetailContract.RushDetailModel getModel() {
        return RushDetailModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void queryRushOrderDetail(String uuid) {
        rxUtils.register(mIModel.queryRushOrderDetail(uuid)
                .subscribe(rushDetailBean -> {
                            if (rushDetailBean.isSuccess()) {
                                mIView.queryRushOrderDetail(rushDetailBean.getData());
                            } else {
                                mIView.failed(rushDetailBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryRushDetail(String uuid) {
        rxUtils.register(mIModel.queryRushDetail(uuid)
                .subscribe(rushDetailBean -> {
                            if (rushDetailBean.isSuccess()) {
                                mIView.queryRushDetail(rushDetailBean.getData());
                            } else {
                                mIView.failed(rushDetailBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }


}
