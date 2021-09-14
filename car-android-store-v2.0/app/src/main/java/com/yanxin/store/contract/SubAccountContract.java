package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.req.GrabOrderReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface SubAccountContract {
    abstract class SubAccountPresenter extends BasePresenter<SubAccountModel, SubAccountView> {
        public abstract void getCompletedOrder(GrabOrderReq supportReq);

        public abstract void getSupportOrder(GrabOrderReq supportReq);
    }

    interface SubAccountModel extends IBaseModel {
        Observable<SupportOrderBean> getSupportOrder(GrabOrderReq supportReq);

        Observable<SupportOrderBean> getCompletedOrder(GrabOrderReq supportReq);
    }

    interface SubAccountView extends IBaseActivity {
        void supportOrderSuccess(ArrayList<SupportOrderBean.DataBean> supportBeans);

        void completedOrderSuccess(ArrayList<SupportOrderBean.DataBean> completedBeans);
    }
}
