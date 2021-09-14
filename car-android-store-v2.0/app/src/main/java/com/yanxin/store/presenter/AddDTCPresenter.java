package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.AddDTCContract;
import com.yanxin.store.model.AddDTCModel;
import com.yanxin.store.req.AddDTCReq;

public class AddDTCPresenter extends AddDTCContract.AddDTCPresenter {

    public static BasePresenter newInstance() {
        return new AddDTCPresenter();
    }


    @Override
    protected AddDTCContract.AddDTCModel getModel() {
        return AddDTCModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void uploadDTCInfo(AddDTCReq dtcReq) {
        rxUtils.register(mIModel.uploadDTCInfo(dtcReq)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.success(defaultBean.getMsg());
                            } else {
                                mIView.failed(defaultBean.getMsg());
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

    @Override
    public void queryAllModel(String modelUuid) {
        rxUtils.register(mIModel.queryAllModel(modelUuid)
                .subscribe(modelBean -> {
                            if (modelBean.isSuccess()) {
                                mIView.queryAllModelSuccess(modelBean.getData());
                            } else {
                                mIView.failed(modelBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
//
//    @Override
//    public void queryDtcTypeList(String dtcType) {
//        rxUtils.register(mIModel.queryDtcTypeList(dtcType)
//                .subscribe(brandBean -> {
//                            if (brandBean.getSuccess()) {
//                                mIView.queryDtcTypeList(brandBean.getData());
//                            } else {
//                                mIView.failed(brandBean.getMsg());
//                            }
//                        }
//                        , throwable -> mIView.failed(throwable.getMessage())));
//    }
}
