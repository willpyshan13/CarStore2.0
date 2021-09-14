package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.bean.GoodsSubstanceBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.contract.CreateGoodsContract;
import com.yanxin.store.req.CreateMallReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateGoodsModel extends BaseModel implements CreateGoodsContract.CreateGoodsModel {
    public static CreateGoodsModel getInstance() {
        return new CreateGoodsModel();
    }

    @Override
    public Observable<GoodsDetailBean> queryGoodsDetail(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryGoodsDetail(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<DefaultBean> addMallGoods(CreateMallReq createMallReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .addMallGoods(MyApplication.getUserToken(), createMallReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<GoodsDetailBean> updateMallGoods(CreateMallReq createMallReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .updateMallGoods(MyApplication.getUserToken(), createMallReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
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
    public Observable<CityBean> queryCity() {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryCity()
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
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
    public Observable<TechnicianTypeBean> queryTechnicianType(String type) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryTechnicianTypeBean(type)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<GoodsSubstanceBean> queryOneTypeBean(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryGoodsSubstanceType(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<GoodsSubstanceBean> queryTwoTypeBean(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryGoodsSubstanceType(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }
}
