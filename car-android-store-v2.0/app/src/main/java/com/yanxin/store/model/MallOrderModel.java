package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.GoodsBean;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.contract.MallOrderContract;
import com.yanxin.store.req.GoodsReq;
import com.yanxin.store.req.GroupBuyReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class MallOrderModel extends BaseModel implements MallOrderContract.MallOrderModel {
    public static MallOrderModel getInstance() {
        return new MallOrderModel();
    }

    @Override
    public Observable<GoodsBean> queryGoodsOrderList(GoodsReq goodsReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryGoodsOrderList(MyApplication.getUserToken(), goodsReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsBean -> goodsBean);
    }

    @Override
    public Observable<GroupCreateBean> queryGroupBuyOrderList(GroupBuyReq groupBuyReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryGroupBuyOrderList(MyApplication.getUserToken(), groupBuyReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsBean -> goodsBean);
    }

    @Override
    public Observable<GroupCreateBean> queryStationOrderList(GroupBuyReq groupBuyBean) {
        return null;
    }

}
