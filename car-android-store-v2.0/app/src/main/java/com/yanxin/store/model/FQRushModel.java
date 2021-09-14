package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.RushAnswerBean;
import com.yanxin.store.contract.FQRushContract;
import com.yanxin.store.req.DefaultReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class FQRushModel extends BaseModel implements FQRushContract.FQRushModel {
    public static FQRushModel getInstance() {
        return new FQRushModel();
    }


    @Override
    public Observable<RushAnswerBean> queryAnswerAll(DefaultReq defaultReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryRushAnswerAll(MyApplication.getUserToken(), defaultReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushAnswerBean -> rushAnswerBean);

    }
}
