package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.AuditBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.contract.FQSquareContract;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.PayAuditReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class FQSquareModel extends BaseModel implements FQSquareContract.FQSquareModel {
    public static FQSquareModel getInstance() {
        return new FQSquareModel();
    }


    @Override
    public Observable<AuditBean> queryAnswerAll(AuditReq auditReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryConsultList(MyApplication.getUserToken(), auditReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(squareBean -> squareBean);
    }

    @Override
    public Observable<DefaultBean> addAuditorOrder(PayAuditReq payAuditReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .addAuditorOrder(MyApplication.getUserToken(), payAuditReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(squareBean -> squareBean);
    }
}
