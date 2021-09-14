package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.AskDetailContract;
import com.yanxin.store.model.AskDetailModel;
import com.yanxin.store.req.ReplyRushReq;

import java.io.File;

public class AskDetailPresenter extends AskDetailContract.AskDetailPresenter {

    public static BasePresenter newInstance() {
        return new AskDetailPresenter();
    }


    @Override
    protected AskDetailContract.AskDetailModel getModel() {
        return AskDetailModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void queryRushDetail(String orderUuid) {
        rxUtils.register(mIModel.queryRushDetail(orderUuid)
                .subscribe(rushDetailBean -> {
                            if (rushDetailBean.isSuccess()) {
                                mIView.queryAskDetail(rushDetailBean.getData());
                            } else {
                                mIView.failed(rushDetailBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void putAskOrderCancel(String uuid) {
        rxUtils.register(mIModel.putAskOrderCancel(uuid)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.putAskOrderCancel(defaultBean.getData());
                            } else {
                                mIView.failed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void grabRushOrder(String uuid) {
        rxUtils.register(mIModel.grabRushOrder(uuid)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.grabRushOrder(defaultBean.getData());
                            } else {
                                mIView.failed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void uploadImgFile(File file) {
        rxUtils.register(mIModel.uploadImgFile(file)
                .subscribe(fileBean -> {
                            if (fileBean.isSuccess()) {
                                mIView.uploadImgFile(fileBean.getData());
                            } else {
                                mIView.failed(fileBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void replyRush(ReplyRushReq replyRushReq) {
        rxUtils.register(mIModel.replyRush(replyRushReq)
                .subscribe(fileBean -> {
                            if (fileBean.isSuccess()) {
                                mIView.replyRush(fileBean.getMsg());
                            } else {
                                mIView.failed(fileBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
