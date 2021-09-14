package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.AmountBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.contract.MaintenanceProposalContract;
import com.yanxin.store.req.AddSceneOrderServiceReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MaintenanceProposalModel extends BaseModel implements MaintenanceProposalContract.MaintenanceProposalModel {
    public static MaintenanceProposalModel getInstance() {
        return new MaintenanceProposalModel();
    }

//
//    @Override
//    public Observable<AmountBean> queryBasicInspectCost(String type) {
//        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
//                .queryBasicInspectCost(type)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map(goodsSubstanceBean -> goodsSubstanceBean);
//    }
//
//    @Override
//    public Observable<AmountBean> queryLineInspectCost(String type) {
//        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
//                .queryLineInspectCost(type)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map(goodsSubstanceBean -> goodsSubstanceBean);
//    }
//
//    @Override
//    public Observable<AmountBean> querySheetMetalRepairCost(String type) {
//        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
//                .querySheetMetalRepairCost(type)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map(goodsSubstanceBean -> goodsSubstanceBean);
//    }
//
//    @Override
//    public Observable<AmountBean> queryDiagnosisInstrumentUseCost(String type) {
//        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
//                .queryDiagnosisInstrumentUseCost(type)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map(goodsSubstanceBean -> goodsSubstanceBean);
//    }
//
//    @Override
//    public Observable<AmountBean> queryPaintRepairCost(String type) {
//        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
//                .queryPaintRepairCost(type)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map(goodsSubstanceBean -> goodsSubstanceBean);
//    }
//
//    @Override
//    public Observable<AmountBean> queryOtherCost(String type) {
//        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
//                .queryOtherCost(type)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map(goodsSubstanceBean -> goodsSubstanceBean);
//    }
//
//    @Override
//    public Observable<AmountBean> queryOrderServiceCost(String type) {
//        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
//                .queryOrderServiceCost(type)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map(goodsSubstanceBean -> goodsSubstanceBean);
//    }

    @Override
    public Observable<UploadFileBean> uploadFile(File mFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", mFile.getName(), requestFile);
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .uploadFile(part, "other")
                .compose(RxHelper.rxSchedulerHelper())
                .map(depositBankBean -> depositBankBean);
    }

    @Override
    public Observable<DefaultBean> submitProposal(AddSceneOrderServiceReq addSceneOrderServiceReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .sceneSubmitPlan(MyApplication.getUserToken(), addSceneOrderServiceReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsSubstanceBean -> goodsSubstanceBean);
    }
}
