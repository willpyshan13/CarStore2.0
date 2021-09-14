package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.req.SceneOrderReq;
import com.yanxin.store.req.SupportReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface SupportContract {
    abstract class SupportPresenter extends BasePresenter<SupportModel, SupportView> {
        public abstract void getSupportOrder(GrabOrderReq supportReq);

        public abstract void getWaitOrder(GrabOrderReq supportReq);

        public abstract void getCompletedOrder(GrabOrderReq supportReq);
    }

    interface SupportModel extends IBaseModel {
        Observable<SupportOrderBean> getSupportOrder(GrabOrderReq supportReq);

        Observable<SupportOrderBean> getWaitOrder(GrabOrderReq supportReq);

        Observable<SupportOrderBean> getCompletedOrder(GrabOrderReq supportReq);
    }

    interface SupportView extends IBaseFragment {
        void supportOrderSuccess(ArrayList<SupportOrderBean.DataBean> supportBeans);

        void waitOrderSuccess(ArrayList<SupportOrderBean.DataBean> waitBeans);

        void completedOrderSuccess(ArrayList<SupportOrderBean.DataBean> completedBeans);

        void failed(String msg);
    }
}
