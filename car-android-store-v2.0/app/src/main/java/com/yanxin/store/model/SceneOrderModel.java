package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.SceneOrderBean;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.contract.SceneOrderContract;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.req.SceneOrderReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class SceneOrderModel extends BaseModel implements SceneOrderContract.SceneOrderModel {
    public static SceneOrderModel getInstance() {
        return new SceneOrderModel();
    }


    @Override
    public Observable<SupportOrderBean> querySceneOrder(GrabOrderReq sceneOrderReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .querySceneOrderList(MyApplication.getUserToken(), sceneOrderReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(sceneOrderBean -> sceneOrderBean);
    }
}
