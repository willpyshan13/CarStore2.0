package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CaseVehicleBean;
import com.yanxin.store.bean.DictBean;
import com.yanxin.store.contract.FCaseContract;
import com.yanxin.store.req.CaseQueryVehicleReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class FCaseModel extends BaseModel implements FCaseContract.FCaseModel {
    public static FCaseModel getInstance() {
        return new FCaseModel();
    }


    @Override
    public Observable<CaseVehicleBean> queryCaseForVehicleList(CaseQueryVehicleReq caseQueryVehicleReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryCaseVehicle(MyApplication.getUserToken(), caseQueryVehicleReq)
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

    @Override
    public Observable<BrandBean> queryAllModel(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryListNextModel(uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<DictBean> queryAllSystem(String sysUuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .querySystem(sysUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }
}
