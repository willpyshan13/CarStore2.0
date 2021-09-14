package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.AskEveryoneContract;
import com.yanxin.store.model.AskEveryoneModel;
import com.yanxin.store.req.AskExpertReq;

import java.io.File;

public class AskEveryonePresenter extends AskEveryoneContract.AskEveryonePresenter {

    public static BasePresenter newInstance() {
        return new AskEveryonePresenter();
    }


    @Override
    protected AskEveryoneContract.AskEveryoneModel getModel() {
        return AskEveryoneModel.getInstance();
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
    public void queryTechnicianTypeBean(String technicianId) {
        rxUtils.register(mIModel.queryTechnicianTypeBean(technicianId)
                .subscribe(technicianTypeBean -> {
                            if (technicianTypeBean.isSuccess()) {
                                mIView.queryTechnicianTypeSuccess(technicianTypeBean.getData());
                            } else {
                                mIView.queryTechnicianTypeFailed(technicianTypeBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void submit(AskExpertReq askExpertReq) {
        rxUtils.register(mIModel.submit(askExpertReq)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.submitSuccess(defaultBean.getData());
                            } else {
                                mIView.submitFailed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

}
