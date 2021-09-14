package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.AuditBean;
import com.yanxin.store.bean.MyProblemBean;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.bean.ReplyOrderBean;
import com.yanxin.store.contract.RushOrderContract;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.MyRushReq;
import com.yanxin.store.req.ReplyOrderReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class RushOrderModel extends BaseModel implements RushOrderContract.RushOrderModel {
    public static RushOrderModel getInstance() {
        return new RushOrderModel();
    }

    @Override
    public Observable<MyProblemBean> queryMineRushOrder(int questionType) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryMyQuestion(MyApplication.getUserToken(),questionType)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushDetailBean -> rushDetailBean);
    }

    @Override
    public Observable<MyRushBean> queryMineRushOrder(MyRushReq myRushReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryOrderFrontConsultList(MyApplication.getUserToken(),myRushReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushDetailBean -> rushDetailBean);
    }

    @Override
    public Observable<MyRushBean> querySqOrder(MyRushReq myRushReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .querySqOrderList(MyApplication.getUserToken(),myRushReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushDetailBean -> rushDetailBean);
    }

    @Override
    public Observable<AuditBean> queryAuditOrder(AuditReq auditReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryConsultList(MyApplication.getUserToken(), auditReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushDetailBean -> rushDetailBean);
    }

    @Override
    public Observable<ReplyOrderBean> queryReplyOrder(ReplyOrderReq replyOrderReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryOrderConsultList(MyApplication.getUserToken(), replyOrderReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(rushDetailBean -> rushDetailBean);
    }
}
