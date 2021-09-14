package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.FDTCContract;
import com.yanxin.store.model.FDTCModel;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.DTCReq;

public class FDTCPresenter extends FDTCContract.FDTCPresenter {

    public static BasePresenter newInstance() {
        return new FDTCPresenter();
    }


    @Override
    protected FDTCContract.FDTCModel getModel() {
        return FDTCModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getDtcList(DTCReq dtcReq) {
        rxUtils.register(mIModel.getDtcList(dtcReq)
                .subscribe(dtcBean -> {
                            if (dtcBean.isSuccess()) {
                                mIView.getDTCSuccess(dtcBean.getData());
                            } else {
                                mIView.getDTCFailed(dtcBean.getMsg());
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

    @Override
    public void queryAllBrand(String brandUuid) {
        rxUtils.register(mIModel.queryAllBrand(brandUuid)
                .subscribe(brandBean -> {
                            if (brandBean.isSuccess()) {
                                mIView.queryAllBrandSuccess(brandBean.getData());
                            } else {
                                mIView.failed(brandBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
