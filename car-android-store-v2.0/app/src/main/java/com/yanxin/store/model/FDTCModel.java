package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DTCBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.contract.FDTCContract;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.DTCReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class FDTCModel extends BaseModel implements FDTCContract.FDTCModel {
    public static FDTCModel getInstance() {
        return new FDTCModel();
    }


    @Override
    public Observable<DTCBean> getDtcList(DTCReq dtcReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryDtc(MyApplication.getUserToken(), dtcReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<DefaultBean> createDTCOrder(CreateDTCReq createDTCReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .createDtcOrder(MyApplication.getUserToken(), createDTCReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<BrandBean> queryAllBrand(String brandUuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryBrand(brandUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }
}
