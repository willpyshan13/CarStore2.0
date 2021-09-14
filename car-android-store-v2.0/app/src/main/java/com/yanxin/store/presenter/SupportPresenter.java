package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.SupportContract;
import com.yanxin.store.model.SupportModel;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.req.SceneOrderReq;
import com.yanxin.store.req.SupportReq;

import static com.yanxin.store.commont.Constant.AsynchronousStatus.SERVICE_ONLINE;

public class SupportPresenter extends SupportContract.SupportPresenter {

    public static BasePresenter newInstance() {
        return new SupportPresenter();
    }


    @Override
    protected SupportContract.SupportModel getModel() {
        return SupportModel.getInstance();
    }

    @Override
    public void onStart() {

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

    @Override
    public void getWaitOrder(GrabOrderReq supportReq) {
        rxUtils.register(mIModel.getWaitOrder(supportReq)
                .subscribe(waitOrderBean -> {
                            if (SERVICE_ONLINE) {
                                if (waitOrderBean.isSuccess()) {
                                    mIView.waitOrderSuccess(waitOrderBean.getData());
                                } else {
                                    mIView.failed(waitOrderBean.getMsg());
                                }
                            } else {
                                mIView.failed("您已离线");
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
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
}
