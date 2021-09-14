package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.req.GrabOrderReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface MySiteContract {
    abstract class MySitePresenter extends BasePresenter<MySiteModel, MySiteView> {
        public abstract void getSiteOrder(GrabOrderReq supportReq);
    }

    interface MySiteModel extends IBaseModel {
        Observable<SupportOrderBean> getSiteOrder(GrabOrderReq supportReq);

    }

    interface MySiteView extends IBaseActivity {
        void orderSuccess(ArrayList<SupportOrderBean.DataBean> supportBeans);

    }
}
