package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.SceneInfoBean;
import com.yanxin.store.contract.GrabOrderContract;
import com.yanxin.store.req.SceneOrderConfirmReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class GrabOrderModel extends BaseModel implements GrabOrderContract.GrabOrderModel {
    public static GrabOrderModel getInstance() {
        return new GrabOrderModel();
    }


    @Override
    public Observable<DefaultBean> reminderOrders(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .reminderOrders(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsSubstanceBean -> goodsSubstanceBean);
    }

    @Override
    public Observable<SceneInfoBean> getSceneOrderDetail(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .querySceneOrderInfo(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsSubstanceBean -> goodsSubstanceBean);
    }

    @Override
    public Observable<DefaultBean> grabBingOrder(String uuid) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .grabBingOrders(MyApplication.getUserToken(), uuid)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsSubstanceBean -> goodsSubstanceBean);
    }

    @Override
    public Observable<DefaultBean> grabCancelOrder(String uuid,int type) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .grabCancelOrders(MyApplication.getUserToken(), uuid,type)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsSubstanceBean -> goodsSubstanceBean);
    }

    @Override
    public Observable<DefaultBean> sceneOrderConfirm(SceneOrderConfirmReq sceneOrderConfirmReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .sceneOrderConfirm(MyApplication.getUserToken(), sceneOrderConfirmReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsSubstanceBean -> goodsSubstanceBean);
    }
}
