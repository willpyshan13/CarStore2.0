package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.FQAskRushContract;
import com.yanxin.store.model.FQAskRushModel;
import com.yanxin.store.req.MyRushReq;

public class FQAskRushPresenter extends FQAskRushContract.FQAskRushPresenter {

    public static BasePresenter newInstance() {
        return new FQAskRushPresenter();
    }


    @Override
    protected FQAskRushContract.FQAskRushModel getModel() {
        return FQAskRushModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getMineRush(MyRushReq myRushReq) {
        rxUtils.register(mIModel.getMineRush(myRushReq)
                .subscribe(myRushBean -> {
                            if (myRushBean.isSuccess()) {
                                mIView.mineRushSuccess(myRushBean.getData());
                            } else {
                                mIView.failed(myRushBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
