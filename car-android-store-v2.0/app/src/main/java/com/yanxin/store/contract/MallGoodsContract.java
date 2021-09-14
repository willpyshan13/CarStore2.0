package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.GoodsBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.req.CreateMallReq;
import com.yanxin.store.req.GoodsReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface MallGoodsContract {
    abstract class MallGoodsPresenter extends BasePresenter<MallGoodsModel, MallGoodsView> {

        public abstract void updateGoodsStatus(CreateMallReq mallTabReq);

        public abstract void queryGoodsData(GoodsReq mallTabReq);
    }

    interface MallGoodsModel extends IBaseModel {
        Observable<GoodsBean> queryGoodsList(GoodsReq goodsReq);

        Observable<GoodsDetailBean> updateGoodsStatus(CreateMallReq mallTabReq);
    }

    interface MallGoodsView extends IBaseFragment {
        void updateGoodsStatus(GoodsDetailBean.DataBean dataBean);

        void queryGoodsList(ArrayList<GoodsBean.DataBean> goodsReq);

        void queryFailed(String msg);
    }
}
