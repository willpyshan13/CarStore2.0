package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.AuditBean;
import com.yanxin.store.bean.MyProblemBean;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.bean.ReplyOrderBean;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.MyRushReq;
import com.yanxin.store.req.ReplyOrderReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface RushOrderContract {
    abstract class RushOrderPresenter extends BasePresenter<RushOrderModel, RushOrderView> {
        //向我提问订单列表
        public abstract void querySqOrder(MyRushReq myRushReq);

        public abstract void queryMineRushOrder(int questionType);

        public abstract void queryMineRushOrder(MyRushReq myRushReq);

        public abstract void queryAuditOrder(AuditReq auditReq);

        public abstract void queryReplyOrder(ReplyOrderReq replyOrderReq);
    }

    interface RushOrderModel extends IBaseModel {

        Observable<MyProblemBean> queryMineRushOrder(int questionType);

        Observable<MyRushBean> queryMineRushOrder(MyRushReq myRushReq);

        Observable<MyRushBean> querySqOrder(MyRushReq myRushReq);

        Observable<AuditBean> queryAuditOrder(AuditReq auditReq);

        Observable<ReplyOrderBean> queryReplyOrder(ReplyOrderReq replyOrderReq);
    }

    interface RushOrderView extends IBaseActivity {
        void querySqOrder(ArrayList<MyRushBean.DataBean> dataBeans);

        void queryMineOrderRushList(ArrayList<MyProblemBean.DataBean> dataBeans);

        void queryMineRushList(ArrayList<MyRushBean.DataBean> dataBeans);

        void queryAuditOrder(ArrayList<AuditBean.DataBean> dataBeans);

        void queryReplyOrder(ArrayList<ReplyOrderBean.DataBean> dataBeans);

    }
}
