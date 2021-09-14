package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.contract.FQMyRushContract;
import com.yanxin.store.req.MyRushReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class FQMyRushModel extends BaseModel implements FQMyRushContract.FQMyRushModel {
    public static FQMyRushModel getInstance() {
        return new FQMyRushModel();
    }


    @Override
    public Observable<MyRushBean> getMineRush(MyRushReq myRushReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryOrderFrontConsultList(MyApplication.getUserToken(), myRushReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }
}
