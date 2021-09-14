package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.FCaseContract;
import com.yanxin.store.model.FCaseModel;
import com.yanxin.store.req.CaseQueryVehicleReq;

public class FCasePresenter extends FCaseContract.FCasePresenter {

    public static BasePresenter newInstance() {
        return new FCasePresenter();
    }


    @Override
    protected FCaseContract.FCaseModel getModel() {
        return FCaseModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void queryCaseForVehicleList(CaseQueryVehicleReq caseQueryVehicleReq) {
        rxUtils.register(mIModel.queryCaseForVehicleList(caseQueryVehicleReq)
                .subscribe(caseVehicleBean -> {
                            if (caseVehicleBean.isSuccess()) {
                                mIView.queryCaseForVehicleSuccess(caseVehicleBean.getData());
                            } else {
                                mIView.queryCaseForVehicleFailed(caseVehicleBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryAllBrand(String uuid) {
        rxUtils.register(mIModel.queryAllBrand(uuid)
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
    public void queryAllModel(String uuid) {
        rxUtils.register(mIModel.queryAllModel(uuid)
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

}
