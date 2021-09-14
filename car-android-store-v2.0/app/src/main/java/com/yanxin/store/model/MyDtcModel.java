package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.DTCBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.MyDtcBean;
import com.yanxin.store.contract.MyDtcContract;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.DTCReq;
import com.yanxin.store.req.MyDtcReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class MyDtcModel extends BaseModel implements MyDtcContract.MyDtcModel {
    public static MyDtcModel getInstance() {
        return new MyDtcModel();
    }


    @Override
    public Observable<MyDtcBean> getDtcList(MyDtcReq myDtcReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryMyDtc(MyApplication.getUserToken(), myDtcReq)
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
}
