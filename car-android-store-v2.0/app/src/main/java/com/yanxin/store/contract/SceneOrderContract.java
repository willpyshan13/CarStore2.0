package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.SceneOrderBean;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.req.SceneOrderReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface SceneOrderContract {
    abstract class SceneOrderPresenter extends BasePresenter<SceneOrderModel, SceneOrderView> {
        public abstract void querySceneOrder(GrabOrderReq sceneOrderReq);
    }

    interface SceneOrderModel extends IBaseModel {
        Observable<SupportOrderBean> querySceneOrder(GrabOrderReq sceneOrderReq);
    }

    interface SceneOrderView extends IBaseFragment {
        void querySuccess(ArrayList<SupportOrderBean.DataBean> dataBeans);

        void queryFailed(String msg);
    }
}
