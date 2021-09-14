package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.RushDetailBean;
import com.yanxin.store.bean.RushOrderDetailBean;

import io.reactivex.Observable;

public interface RushDetailContract {
    abstract class RushDetailPresenter extends BasePresenter<RushDetailModel, RushDetailView> {
        public abstract void queryRushOrderDetail(String uuid);

        public abstract void queryRushDetail(String uuid);
    }

    interface RushDetailModel extends IBaseModel {
        Observable<RushOrderDetailBean> queryRushOrderDetail(String uuid);

        Observable<RushDetailBean> queryRushDetail(String uuid);
    }

    interface RushDetailView extends IBaseActivity {
        void queryRushOrderDetail(RushOrderDetailBean.DataBean rushDetailBean);

        void queryRushDetail(RushDetailBean.DataBean rushDetailBean);
    }
}
