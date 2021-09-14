package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.GoodsBean;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.req.GoodsReq;
import com.yanxin.store.req.GroupBuyReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface MallOrderContract {
    abstract class MallTabPresenter extends BasePresenter<MallOrderModel, MallOrderView> {
        public abstract void queryGoodsOrderData(GoodsReq mallTabReq);

        public abstract void queryGroupBuyOrderList(GroupBuyReq groupBuyBean);

        public abstract void queryStationOrderList(GroupBuyReq groupBuyBean);
    }

    interface MallOrderModel extends IBaseModel {
        Observable<GoodsBean> queryGoodsOrderList(GoodsReq goodsReq);

        Observable<GroupCreateBean> queryGroupBuyOrderList(GroupBuyReq groupBuyBean);

        Observable<GroupCreateBean> queryStationOrderList(GroupBuyReq groupBuyBean);
    }

    interface MallOrderView extends IBaseFragment {
        void queryGoodsOrderList(ArrayList<GoodsBean.DataBean> goodsReq);

        void queryGroupBuyOrderList(ArrayList<GroupCreateBean.DataBean> goodsReq);

        void queryStationOrderList(ArrayList<GoodsBean.DataBean> goodsReq);

        void querySuccess(ArrayList<GoodsBean.DataBean> dataBeans);

        void queryFailed(String msg);
    }
}
