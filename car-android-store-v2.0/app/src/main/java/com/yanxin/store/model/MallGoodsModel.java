package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.GoodsBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.contract.MallGoodsContract;
import com.yanxin.store.req.CreateMallReq;
import com.yanxin.store.req.GoodsReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class MallGoodsModel extends BaseModel implements MallGoodsContract.MallGoodsModel {
    public static MallGoodsModel getInstance() {
        return new MallGoodsModel();
    }

    @Override
    public Observable<GoodsBean> queryGoodsList(GoodsReq goodsReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryGoodsList(MyApplication.getUserToken(), goodsReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsBean -> goodsBean);
    }

    @Override
    public Observable<GoodsDetailBean> updateGoodsStatus(CreateMallReq mallTabReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .updateMallGoodsStatus(MyApplication.getUserToken(), mallTabReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsBean -> goodsBean);
    }
}
