package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.contract.MySiteContract;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class MySiteModel extends BaseModel implements MySiteContract.MySiteModel {
    public static MySiteModel getInstance() {
        return new MySiteModel();
    }


    @Override
    public Observable<SupportOrderBean> getSiteOrder(GrabOrderReq supportReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .querySceneOrderList(MyApplication.getUserToken(),supportReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(depositBankBean -> depositBankBean);
    }
}
