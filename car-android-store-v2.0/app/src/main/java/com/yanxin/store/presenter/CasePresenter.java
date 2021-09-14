package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.CaseContract;
import com.yanxin.store.model.CaseModel;
import com.yanxin.store.req.DefaultReq;

public class CasePresenter extends CaseContract.CasePresenter {

    public static BasePresenter newInstance() {
        return new CasePresenter();
    }


    @Override
    protected CaseContract.CaseModel getModel() {
        return CaseModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getMineCase(DefaultReq defaultReq) {
        rxUtils.register(mIModel.getMineCase(defaultReq)
                .subscribe(mineCaseBean -> {
                            if (mineCaseBean.isSuccess()) {
                                mIView.mineCaseSuccess(mineCaseBean.getData());
                            } else {
                                mIView.failed(mineCaseBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void getMyBuyCase(DefaultReq defaultReq) {
        rxUtils.register(mIModel.getMyBuyCase(defaultReq)
                .subscribe(mineCaseBean -> {
                            if (mineCaseBean.isSuccess()) {
                                mIView.myBuyCaseSuccess(mineCaseBean.getData());
                            } else {
                                mIView.failed(mineCaseBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void getBuyCase(DefaultReq defaultReq) {
        rxUtils.register(mIModel.getBuyCase(defaultReq)
                .subscribe(buyCaseBean -> {
                            if (buyCaseBean.isSuccess()) {
                                mIView.buyCaseSuccess(buyCaseBean.getData());
                            } else {
                                mIView.failed(buyCaseBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
