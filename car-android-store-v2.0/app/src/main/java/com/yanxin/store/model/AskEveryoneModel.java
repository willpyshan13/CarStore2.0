package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.OrderInfoBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.contract.AskEveryoneContract;
import com.yanxin.store.req.AskExpertReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AskEveryoneModel extends BaseModel implements AskEveryoneContract.AskEveryoneModel {
    public static AskEveryoneModel getInstance() {
        return new AskEveryoneModel();
    }


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
    public Observable<BrandBean> queryAllBrand(String brandUuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryBrand(brandUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<BrandBean> queryAllModel(String modUuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryListNextModel(modUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<TechnicianTypeBean> queryTechnicianTypeBean(String technicianId) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryTechnicianTypeBean(technicianId)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<DefaultBean> submit(AskExpertReq askExpertReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .consultOrder(MyApplication.getUserToken(),askExpertReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }
}
