package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.contract.SubAccountContract;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class SubAccountModel extends BaseModel implements SubAccountContract.SubAccountModel {
    public static SubAccountModel getInstance() {
        return new SubAccountModel();
    }


    @Override
    public Observable<SupportOrderBean> getSupportOrder(GrabOrderReq supportReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .querySceneOrderList(MyApplication.getUserToken(),supportReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(depositBankBean -> depositBankBean);
    }

    @Override
    public Observable<SupportOrderBean> getCompletedOrder(GrabOrderReq supportReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .querySceneOrderList(MyApplication.getUserToken(),supportReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(depositBankBean -> depositBankBean);
    }
}
