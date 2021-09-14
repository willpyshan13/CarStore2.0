package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.DtcTypeBean;
import com.yanxin.store.contract.AddDTCContract;
import com.yanxin.store.req.AddDTCReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class AddDTCModel extends BaseModel implements AddDTCContract.AddDTCModel {
    public static AddDTCModel getInstance() {
        return new AddDTCModel();
    }


    @Override
    public Observable<DefaultBean> uploadDTCInfo(AddDTCReq dtcReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .addDtcInfo(MyApplication.getUserToken(),dtcReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(defaultBean -> defaultBean);
    }

    @Override
    public Observable<BrandBean> queryAllBrand(String brandUuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryBrand(brandUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<BrandBean> queryAllModel(String modelUuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryListNextModel(modelUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }
//
//    @Override
//    public Observable<DtcTypeBean> queryDtcTypeList(String dtcType) {
//        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
//                .queryDtcType(dtcType)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map(caseVehicleBean -> caseVehicleBean);
//    }
}
