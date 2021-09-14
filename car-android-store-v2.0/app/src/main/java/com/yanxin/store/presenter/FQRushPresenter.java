package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.FQRushContract;
import com.yanxin.store.model.FQRushModel;
import com.yanxin.store.req.DefaultReq;

public class FQRushPresenter extends FQRushContract.FQRushPresenter {

    public static BasePresenter newInstance() {
        return new FQRushPresenter();
    }


    @Override
    protected FQRushContract.FQRushModel getModel() {
        return FQRushModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void queryAnswerAll(DefaultReq defaultReq) {
        rxUtils.register(mIModel.queryAnswerAll(defaultReq)
                .subscribe(dtcBean -> {
                            if (dtcBean.isSuccess()) {
                                mIView.queryAnswerSuccess(dtcBean.getData());
                            } else {
                                mIView.failed(dtcBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
