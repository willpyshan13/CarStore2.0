package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.SubAccountContract;
import com.yanxin.store.model.SubAccountModel;
import com.yanxin.store.req.GrabOrderReq;

public class SubAccountPresenter extends SubAccountContract.SubAccountPresenter {

    public static BasePresenter newInstance() {
        return new SubAccountPresenter();
    }


    @Override
    protected SubAccountContract.SubAccountModel getModel() {
        return SubAccountModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getCompletedOrder(GrabOrderReq supportReq) {
        rxUtils.register(mIModel.getCompletedOrder(supportReq)
                .subscribe(supportOrderBean -> {
                            if (supportOrderBean.isSuccess()) {
                                mIView.completedOrderSuccess(supportOrderBean.getData());
                            } else {
                                mIView.failed(supportOrderBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void getSupportOrder(GrabOrderReq supportReq) {
        rxUtils.register(mIModel.getSupportOrder(supportReq)
                .subscribe(supportOrderBean -> {
                            if (supportOrderBean.isSuccess()) {
                                mIView.supportOrderSuccess(supportOrderBean.getData());
                            } else {
                                mIView.failed(supportOrderBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
