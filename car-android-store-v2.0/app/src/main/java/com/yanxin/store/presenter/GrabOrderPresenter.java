package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.GrabOrderContract;
import com.yanxin.store.model.GrabOrderModel;
import com.yanxin.store.req.SceneOrderConfirmReq;

public class GrabOrderPresenter extends GrabOrderContract.GrabOrderPresenter {

    public static BasePresenter newInstance() {
        return new GrabOrderPresenter();
    }


    @Override
    protected GrabOrderContract.GrabOrderModel getModel() {
        return GrabOrderModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getSceneOrderDetail(String uuid) {
        rxUtils.register(mIModel.getSceneOrderDetail(uuid)
                .subscribe(sceneInfoBean -> {
                    if (sceneInfoBean.isSuccess()) {
                        mIView.getSceneOrderDetail(sceneInfoBean.getData());
                    } else {
                        mIView.failed(sceneInfoBean.getMsg());
                    }
                }, throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void grabBingOrder(String uuid) {
        rxUtils.register(mIModel.grabBingOrder(uuid)
                .subscribe(defaultBean -> {
                    if (defaultBean.isSuccess()) {
                        mIView.grabBingOrder(defaultBean.getData());
                    } else {
                        mIView.failed(defaultBean.getMsg());
                    }
                }, throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void grabCancelOrder(String uuid, int type) {
        rxUtils.register(mIModel.grabCancelOrder(uuid, type)
                .subscribe(defaultBean -> {
                    if (defaultBean.isSuccess()) {
                        mIView.grabCancelOrder(defaultBean.getData());
                    } else {
                        mIView.failed(defaultBean.getMsg());
                    }
                }, throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void sceneOrderConfirm(SceneOrderConfirmReq sceneOrderConfirmReq) {
        rxUtils.register(mIModel.sceneOrderConfirm(sceneOrderConfirmReq)
                .subscribe(defaultBean -> {
                    if (defaultBean.isSuccess()) {
                        mIView.sceneOrderConfirm(defaultBean.getData());
                    } else {
                        mIView.failed(defaultBean.getMsg());
                    }
                }, throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void reminderOrders(String uuid) {
        rxUtils.register(mIModel.reminderOrders(uuid)
                .subscribe(defaultBean -> {
                    if (defaultBean.isSuccess()) {
                        mIView.reminderOrders(defaultBean.getData());
                    } else {
                        mIView.failed(defaultBean.getMsg());
                    }
                }, throwable -> mIView.failed(throwable.getMessage())));
    }
}
