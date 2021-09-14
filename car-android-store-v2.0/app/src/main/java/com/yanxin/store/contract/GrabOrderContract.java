package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.SceneInfoBean;
import com.yanxin.store.req.SceneOrderConfirmReq;

import io.reactivex.Observable;

public interface GrabOrderContract {
    abstract class GrabOrderPresenter extends BasePresenter<GrabOrderModel, GrabOrderView> {
        public abstract void getSceneOrderDetail(String uuid);

        public abstract void grabBingOrder(String uuid);

        public abstract void grabCancelOrder(String uuid,int type);

        public abstract void sceneOrderConfirm(SceneOrderConfirmReq sceneOrderConfirmReq);

        public abstract void reminderOrders(String uuid);

    }

    interface GrabOrderModel extends IBaseModel {
        Observable<DefaultBean> reminderOrders(String uuid);

        Observable<SceneInfoBean> getSceneOrderDetail(String uuid);

        Observable<DefaultBean> grabBingOrder(String uuid);

        Observable<DefaultBean> grabCancelOrder(String uuid,int type);

        Observable<DefaultBean> sceneOrderConfirm(SceneOrderConfirmReq sceneOrderConfirmReq);
    }

    interface GrabOrderView extends IBaseActivity {
        void reminderOrders(String msg);

        void getSceneOrderDetail(SceneInfoBean.DataBean dataBean);

        void sceneOrderConfirm(String dataBean);

        void grabBingOrder(String msg);

        void grabCancelOrder(String msg);
    }
}
