package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.FQSquareContract;
import com.yanxin.store.model.FQSquareModel;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.PayAuditReq;

public class FQSquarePresenter extends FQSquareContract.FQSquarePresenter {

    public static BasePresenter newInstance() {
        return new FQSquarePresenter();
    }


    @Override
    protected FQSquareContract.FQSquareModel getModel() {
        return FQSquareModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void queryAnswerAll(AuditReq auditReq) {
        rxUtils.register(mIModel.queryAnswerAll(auditReq)
                .subscribe(squareBean -> {
                            if (squareBean.isSuccess()) {
                                mIView.queryAnswerSuccess(squareBean.getData());
                            } else {
                                mIView.failed(squareBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void addAuditorOrder(PayAuditReq payAuditReq) {
        rxUtils.register(mIModel.addAuditorOrder(payAuditReq)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.paySuccess(defaultBean.getData());
                            } else {
                                mIView.failed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
