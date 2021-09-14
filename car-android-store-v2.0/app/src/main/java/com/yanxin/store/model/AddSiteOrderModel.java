package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.DrivingBean;
import com.yanxin.store.bean.EngineDisplacementBean;
import com.yanxin.store.bean.SiteFeeBean;
import com.yanxin.store.bean.SuperchargingBean;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.TransmissionBean;
import com.yanxin.store.bean.TransmissonChildBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.contract.AddSiteOrderContract;
import com.yanxin.store.req.AddSiteReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.yanxin.store.commont.Constant.AppConfig.DRIVING_MODE;
import static com.yanxin.store.commont.Constant.AppConfig.ENGINE_DISPLACEMENT;
import static com.yanxin.store.commont.Constant.AppConfig.SUPERCHARGING_SYSTEM;
import static com.yanxin.store.commont.Constant.AppConfig.TECHNICIAN_TYPE;
import static com.yanxin.store.commont.Constant.AppConfig.TRANSMISSION_TYPE;

public class AddSiteOrderModel extends BaseModel implements AddSiteOrderContract.AddSiteOrderModel {
    public static AddSiteOrderModel getInstance() {
        return new AddSiteOrderModel();
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
    public Observable<SiteFeeBean> queryBaseFee(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryBaseFee(uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(siteFeeBean -> siteFeeBean);
    }

    @Override
    public Observable<SiteFeeBean> queryBaseServiceFee(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryBaseFee(uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(siteFeeBean -> siteFeeBean);
    }

    @Override
    public Observable<CityBean> queryCity() {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryCity()
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<TechnicianTypeBean> queryTechnicianType(String type) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryTechnicianTypeBean(type)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<TransmissonChildBean> queryManualTransmission(String type) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryManualTransmission(type)
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
    public Observable<EngineDisplacementBean> queryEngineDisplacement(String type) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryEngineDisplacement(ENGINE_DISPLACEMENT)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<DrivingBean> queryDriving(String type) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryDriving(DRIVING_MODE)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<TransmissionBean> queryTransmission(String type) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryTransmission(TRANSMISSION_TYPE)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<SuperchargingBean> querySupercharging(String type) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .querySupercharging(SUPERCHARGING_SYSTEM)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }

    @Override
    public Observable<DefaultBean> addSite(AddSiteReq addSiteReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .addSiteOrder(MyApplication.getUserToken(), addSiteReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(cityBean -> cityBean);
    }
}
