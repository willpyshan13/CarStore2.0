package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DepositBankBean;
import com.yanxin.store.bean.PersonTypeBean;
import com.yanxin.store.bean.RegisterBean;
import com.yanxin.store.bean.StoreDetailBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.contract.StoreContract;
import com.yanxin.store.req.StoreRegisterReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class StoreModel extends BaseModel implements StoreContract.StoreModel {
    public static StoreModel getInstance() {
        return new StoreModel();
    }


    @Override
    public Observable<DepositBankBean> getDepositBank() {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .getDepositBank()
                .compose(RxHelper.rxSchedulerHelper())
                .map(depositBankBean -> depositBankBean);
    }

    @Override
    public Observable<PersonTypeBean> getPersonType() {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .getPersonType()
                .compose(RxHelper.rxSchedulerHelper())
                .map(depositBankBean -> depositBankBean);
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
    public Observable<BrandBean> queryBrand(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryBrand(uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(brandBean -> brandBean);
    }

    @Override
    public Observable<RegisterBean> addStore(StoreRegisterReq storeRegisterReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .registerStore(storeRegisterReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(brandBean -> brandBean);
    }

    @Override
    public Observable<RegisterBean> updateStore(String token,StoreRegisterReq storeRegisterReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .updateStore(token,storeRegisterReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(brandBean -> brandBean);
    }

    @Override
    public Observable<StoreDetailBean> getStoreDetail(String token) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .getStoreDetail(token)
                .compose(RxHelper.rxSchedulerHelper())
                .map(brandBean -> brandBean);
    }
}
