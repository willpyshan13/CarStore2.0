package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.RushOrderContract;
import com.yanxin.store.model.RushOrderModel;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.MyRushReq;
import com.yanxin.store.req.ReplyOrderReq;

public class RushOrderPresenter extends RushOrderContract.RushOrderPresenter {
    public static BasePresenter newInstance() {
        return new RushOrderPresenter();
    }


    @Override
    protected RushOrderContract.RushOrderModel getModel() {
        return RushOrderModel.getInstance();
    }

    @Override
    public void onStart() {

    }


    @Override
    public void querySqOrder(MyRushReq myRushReq) {
        rxUtils.register(mIModel.querySqOrder(myRushReq)
                .subscribe(myRushBean -> {
                            if (myRushBean.isSuccess()) {
                                mIView.querySqOrder(myRushBean.getData());
                            } else {
                                mIView.failed(myRushBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryMineRushOrder(int questionType) {
        rxUtils.register(mIModel.queryMineRushOrder(questionType)
                .subscribe(myRushBean -> {
                            if (myRushBean.isSuccess()) {
                                mIView.queryMineOrderRushList(myRushBean.getData());
                            } else {
                                mIView.failed(myRushBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryMineRushOrder(MyRushReq myRushReq) {
        rxUtils.register(mIModel.queryMineRushOrder(myRushReq)
                .subscribe(myRushBean -> {
                            if (myRushBean.isSuccess()) {
                                mIView.queryMineRushList(myRushBean.getData());
                            } else {
                                mIView.failed(myRushBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryAuditOrder(AuditReq auditReq) {
        rxUtils.register(mIModel.queryAuditOrder(auditReq)
                .subscribe(auditBean -> {
                            if (auditBean.isSuccess()) {
                                mIView.queryAuditOrder(auditBean.getData());
                            } else {
                                mIView.failed(auditBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryReplyOrder(ReplyOrderReq replyOrderReq) {
        rxUtils.register(mIModel.queryReplyOrder(replyOrderReq)
                .subscribe(replyOrderBean -> {
                            if (replyOrderBean.isSuccess()) {
                                mIView.queryReplyOrder(replyOrderBean.getData());
                            } else {
                                mIView.failed(replyOrderBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
