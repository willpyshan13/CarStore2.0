package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.PushCaseContract;
import com.yanxin.store.model.PushCaseModel;
import com.yanxin.store.req.PushCaseReq;

import java.io.File;

public class PushCasePresenter extends PushCaseContract.PushCasePresenter {

    public static BasePresenter newInstance() {
        return new PushCasePresenter();
    }


    @Override
    protected PushCaseContract.PushCaseModel getModel() {
        return PushCaseModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void uploadFile(File mFile) {
        rxUtils.register(mIModel.uploadFile(mFile)
                .subscribe(uploadFileBean -> {
                            if (uploadFileBean.isSuccess()) {
                                mIView.uploadFileSuccess(uploadFileBean.getData());
                            } else {
                                mIView.uploadFileFailed(uploadFileBean.getMsg());
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
                                mIView.queryAllBrandFailed(brandBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryAllModel(String modUuid) {
        rxUtils.register(mIModel.queryAllModel(modUuid)
                .subscribe(modelBean -> {
                            if (modelBean.isSuccess()) {
                                mIView.queryAllModelSuccess(modelBean.getData());
                            } else {
                                mIView.queryAllModelFailed(modelBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryAllSystem(String sysUuid) {
        rxUtils.register(mIModel.queryAllSystem(sysUuid)
                .subscribe(sysBean -> {
                            if (sysBean.isSuccess()) {
                                mIView.queryAllSystemSuccess(sysBean.getData());
                            } else {
                                mIView.queryAllSystemFailed(sysBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void submitCase(PushCaseReq pushCaseReq) {
        rxUtils.register(mIModel.submitCase(pushCaseReq)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.submitSuccess(defaultBean.getMsg());
                            } else {
                                mIView.failed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
