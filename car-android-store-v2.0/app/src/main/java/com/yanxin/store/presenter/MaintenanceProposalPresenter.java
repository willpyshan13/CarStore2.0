package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MaintenanceProposalContract;
import com.yanxin.store.model.MaintenanceProposalModel;
import com.yanxin.store.req.AddSceneOrderServiceReq;

import java.io.File;

public class MaintenanceProposalPresenter extends MaintenanceProposalContract.MaintenanceProposalPresenter {

    public static BasePresenter newInstance() {
        return new MaintenanceProposalPresenter();
    }


    @Override
    protected MaintenanceProposalContract.MaintenanceProposalModel getModel() {
        return MaintenanceProposalModel.getInstance();
    }

    @Override
    public void onStart() {

    }

//    @Override
//    public void queryBasicInspectCost(String type) {
//        rxUtils.register(mIModel.queryBasicInspectCost(type)
//                .subscribe(goodsBean -> {
//                    if (goodsBean.isSuccess()) {
//                        mIView.queryBasicInspectCost(goodsBean.getData());
//                    } else {
//                        mIView.failed(goodsBean.getMsg());
//                    }
//                }, throwable -> mIView.failed(throwable.getMessage())));
//    }
//
//    @Override
//    public void queryLineInspectCost(String type) {
//        rxUtils.register(mIModel.queryLineInspectCost(type)
//                .subscribe(goodsBean -> {
//                    if (goodsBean.isSuccess()) {
//                        mIView.queryLineInspectCost(goodsBean.getData());
//                    } else {
//                        mIView.failed(goodsBean.getMsg());
//                    }
//                }, throwable -> mIView.failed(throwable.getMessage())));
//
//    }
//
//    @Override
//    public void querySheetMetalRepairCost(String type) {
//        rxUtils.register(mIModel.querySheetMetalRepairCost(type)
//                .subscribe(goodsBean -> {
//                    if (goodsBean.isSuccess()) {
//                        mIView.querySheetMetalRepairCost(goodsBean.getData());
//                    } else {
//                        mIView.failed(goodsBean.getMsg());
//                    }
//                }, throwable -> mIView.failed(throwable.getMessage())));
//
//    }
//
//    @Override
//    public void queryDiagnosisInstrumentUseCost(String type) {
//        rxUtils.register(mIModel.queryDiagnosisInstrumentUseCost(type)
//                .subscribe(goodsBean -> {
//                    if (goodsBean.isSuccess()) {
//                        mIView.queryDiagnosisInstrumentUseCost(goodsBean.getData());
//                    } else {
//                        mIView.failed(goodsBean.getMsg());
//                    }
//                }, throwable -> mIView.failed(throwable.getMessage())));
//
//    }
//
//    @Override
//    public void queryPaintRepairCost(String type) {
//        rxUtils.register(mIModel.queryPaintRepairCost(type)
//                .subscribe(goodsBean -> {
//                    if (goodsBean.isSuccess()) {
//                        mIView.queryPaintRepairCost(goodsBean.getData());
//                    } else {
//                        mIView.failed(goodsBean.getMsg());
//                    }
//                }, throwable -> mIView.failed(throwable.getMessage())));
//
//    }
//
//    @Override
//    public void queryOtherCost(String type) {
//        rxUtils.register(mIModel.queryOtherCost(type)
//                .subscribe(goodsBean -> {
//                    if (goodsBean.isSuccess()) {
//                        mIView.queryOtherCost(goodsBean.getData());
//                    } else {
//                        mIView.failed(goodsBean.getMsg());
//                    }
//                }, throwable -> mIView.failed(throwable.getMessage())));
//
//    }
//
//    @Override
//    public void queryOrderServiceCost(String type) {
//        rxUtils.register(mIModel.queryOrderServiceCost(type)
//                .subscribe(goodsBean -> {
//                    if (goodsBean.isSuccess()) {
//                        mIView.queryOrderServiceCost(goodsBean.getData());
//                    } else {
//                        mIView.failed(goodsBean.getMsg());
//                    }
//                }, throwable -> mIView.failed(throwable.getMessage())));
//    }

    @Override
    public void uploadFile(File mFile) {
        rxUtils.register(mIModel.uploadFile(mFile)
                .subscribe(defaultBean -> {
                    if (defaultBean.isSuccess()) {
                        mIView.uploadSuccess(defaultBean.getData());
                    } else {
                        mIView.failed(defaultBean.getMsg());
                    }
                }, throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void submitProposal(AddSceneOrderServiceReq addSceneOrderServiceReq) {
        rxUtils.register(mIModel.submitProposal(addSceneOrderServiceReq)
                .subscribe(defaultBean -> {
                    if (defaultBean.isSuccess()) {
                        mIView.submitProposal(defaultBean.getData());
                    } else {
                        mIView.failed(defaultBean.getMsg());
                    }
                }, throwable -> mIView.failed(throwable.getMessage())));
    }
}
