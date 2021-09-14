package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MyDtcContract;
import com.yanxin.store.model.MyDtcModel;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.DTCReq;
import com.yanxin.store.req.MyDtcReq;

public class MyDtcPresenter extends MyDtcContract.MyDtcPresenter {

    public static BasePresenter newInstance() {
        return new MyDtcPresenter();
    }


    @Override
    protected MyDtcContract.MyDtcModel getModel() {
        return MyDtcModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getDtcList(MyDtcReq myDtcReq) {
        rxUtils.register(mIModel.getDtcList(myDtcReq)
                .subscribe(myDtcBean -> {
                            if (myDtcBean.getSuccess()) {
                                mIView.getDTCSuccess(myDtcBean.getData());
                            } else {
                                mIView.getDTCFailed(myDtcBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void createDTCOrder(CreateDTCReq createDTCReq) {
        rxUtils.register(mIModel.createDTCOrder(createDTCReq)
                .subscribe(dtcBean -> {
                            if (dtcBean.isSuccess()) {
                                mIView.createDTCOrder(dtcBean.getData());
                            } else {
                                mIView.getDTCFailed(dtcBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
