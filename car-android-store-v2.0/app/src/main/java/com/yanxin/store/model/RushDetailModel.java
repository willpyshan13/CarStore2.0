package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.RushDetailBean;
import com.yanxin.store.bean.RushOrderDetailBean;
import com.yanxin.store.contract.RushDetailContract;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class RushDetailModel extends BaseModel implements RushDetailContract.RushDetailModel {
    public static RushDetailModel getInstance() {
        return new RushDetailModel();
    }


    @Override
    public Observable<RushOrderDetailBean> queryRushOrderDetail(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryRushOrderDetail(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushDetailBean -> rushDetailBean);
    }

    @Override
    public Observable<RushDetailBean> queryRushDetail(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryRushDetail(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushDetailBean -> rushDetailBean);
    }
}
