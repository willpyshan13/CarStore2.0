package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DepositBankBean;
import com.yanxin.store.bean.RegisterBean;
import com.yanxin.store.bean.TechnicianDetailBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.contract.TechnicianContract;
import com.yanxin.store.req.TechnicianRegisterReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.yanxin.store.commont.Constant.AppConfig.TECHNICIAN_TYPE;

public class TechnicianModel extends BaseModel implements TechnicianContract.TechnicianModel {
    public static TechnicianModel getInstance() {
        return new TechnicianModel();
    }


    @Override
    public Observable<DepositBankBean> getDepositBank() {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .getDepositBank()
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
    public Observable<TechnicianTypeBean> queryTechnicianType() {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryTechnicianTypeBean(TECHNICIAN_TYPE)
                .compose(RxHelper.rxSchedulerHelper())
                .map(brandBean -> brandBean);
    }

    @Override
    public Observable<RegisterBean> registerTechnician(TechnicianRegisterReq technicianRegisterReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .registerTechnician(technicianRegisterReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(brandBean -> brandBean);
    }

    @Override
    public Observable<RegisterBean> updateTechnician(String token,TechnicianRegisterReq technicianRegisterReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .updateTechnician(token,technicianRegisterReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(brandBean -> brandBean);
    }


    @Override
    public Observable<TechnicianDetailBean> getTechnicianDetail(String token) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryTechnicianDetail(token)
                .compose(RxHelper.rxSchedulerHelper())
                .map(brandBean -> brandBean);
    }
}
