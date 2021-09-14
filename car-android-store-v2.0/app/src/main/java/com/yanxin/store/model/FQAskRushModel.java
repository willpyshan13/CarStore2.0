package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.contract.FQAskRushContract;
import com.yanxin.store.req.MyRushReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class FQAskRushModel extends BaseModel implements FQAskRushContract.FQAskRushModel {
    public static FQAskRushModel getInstance() {
        return new FQAskRushModel();
    }


    @Override
    public Observable<MyRushBean> getMineRush(MyRushReq myRushReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryOrderFrontConsultList(MyApplication.getUserToken(), myRushReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(caseVehicleBean -> caseVehicleBean);
    }
}
