package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.BuyCaseBean;
import com.yanxin.store.bean.MineCaseBean;
import com.yanxin.store.bean.MyOrderBean;
import com.yanxin.store.contract.CaseContract;
import com.yanxin.store.req.DefaultReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class CaseModel extends BaseModel implements CaseContract.CaseModel {
    public static CaseModel getInstance() {
        return new CaseModel();
    }


    @Override
    public Observable<MineCaseBean> getMineCase(DefaultReq defaultReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryMineCase(MyApplication.getUserToken(),defaultReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<MyOrderBean> getMyBuyCase(DefaultReq defaultReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .getMyorderCase(MyApplication.getUserToken())
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }

    @Override
    public Observable<BuyCaseBean> getBuyCase(DefaultReq defaultReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryBuyCase(MyApplication.getUserToken(),defaultReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }
}
