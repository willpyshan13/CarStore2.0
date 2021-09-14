package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.FQMyRushContract;
import com.yanxin.store.model.FQMyRushModel;
import com.yanxin.store.req.MyRushReq;

public class FQMyRushPresenter extends FQMyRushContract.FQMyRushPresenter {

    public static BasePresenter newInstance() {
        return new FQMyRushPresenter();
    }


    @Override
    protected FQMyRushContract.FQMyRushModel getModel() {
        return FQMyRushModel.getInstance();
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
