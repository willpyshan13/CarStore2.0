package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MySiteContract;
import com.yanxin.store.model.MySiteModel;
import com.yanxin.store.req.GrabOrderReq;

public class MySitePresenter extends MySiteContract.MySitePresenter {
    public static BasePresenter newInstance() {
        return new MySitePresenter();
    }


    @Override
    protected MySiteContract.MySiteModel getModel() {
        return MySiteModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getSiteOrder(GrabOrderReq supportReq) {
        rxUtils.register(mIModel.getSiteOrder(supportReq)
                .subscribe(supportOrderBean -> {
                            if (supportOrderBean.isSuccess()) {
                                mIView.orderSuccess(supportOrderBean.getData());
                            } else {
                                mIView.failed(supportOrderBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
