package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MallOrderContract;
import com.yanxin.store.model.MallOrderModel;
import com.yanxin.store.req.GoodsReq;
import com.yanxin.store.req.GroupBuyReq;

public class MallOrderPresenter extends MallOrderContract.MallTabPresenter {

    public static BasePresenter newInstance() {
        return new MallOrderPresenter();
    }


    @Override
    protected MallOrderContract.MallOrderModel getModel() {
        return MallOrderModel.getInstance();
    }

    @Override
    public void onStart() {

    }


    @Override
    public void queryGoodsOrderData(GoodsReq mallTabReq) {
        rxUtils.register(mIModel.queryGoodsOrderList(mallTabReq)
                .subscribe(goodsBean -> {
                    if (goodsBean.isSuccess()) {
                        mIView.queryGoodsOrderList(goodsBean.getData());
                    } else {
                        mIView.queryFailed(goodsBean.getMsg());
                    }
                }, throwable -> mIView.queryFailed(throwable.getMessage())));
    }

    @Override
    public void queryGroupBuyOrderList(GroupBuyReq groupBuyReq) {
        rxUtils.register(mIModel.queryGroupBuyOrderList(groupBuyReq)
                .subscribe(goodsBean -> {
                    if (goodsBean.isSuccess()) {
                        mIView.queryGroupBuyOrderList(goodsBean.getData());
                    } else {
                        mIView.queryFailed(goodsBean.getMsg());
                    }
                }, throwable -> mIView.queryFailed(throwable.getMessage())));
    }

    @Override
    public void queryStationOrderList(GroupBuyReq groupBuyBean) {

    }

}
