package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MallGoodsContract;
import com.yanxin.store.model.MallGoodsModel;
import com.yanxin.store.req.CreateMallReq;
import com.yanxin.store.req.GoodsReq;
import com.yanxin.store.req.GroupBuyReq;

public class MallGoodsPresenter extends MallGoodsContract.MallGoodsPresenter {

    public static BasePresenter newInstance() {
        return new MallGoodsPresenter();
    }


    @Override
    protected MallGoodsContract.MallGoodsModel getModel() {
        return MallGoodsModel.getInstance();
    }

    @Override
    public void onStart() {

    }


    @Override
    public void updateGoodsStatus(CreateMallReq mallTabReq) {
        rxUtils.register(mIModel.updateGoodsStatus(mallTabReq)
                .subscribe(defaultBean -> {
                    if (defaultBean.isSuccess()) {
                        mIView.updateGoodsStatus(defaultBean.getData());
                    } else {
                        mIView.queryFailed(defaultBean.getMsg());
                    }
                }, throwable -> mIView.queryFailed(throwable.getMessage())));
    }

    @Override
    public void queryGoodsData(GoodsReq mallTabReq) {
        rxUtils.register(mIModel.queryGoodsList(mallTabReq)
                .subscribe(goodsBean -> {
                    if (goodsBean.isSuccess()) {
                        mIView.queryGoodsList(goodsBean.getData());
                    } else {
                        mIView.queryFailed(goodsBean.getMsg());
                    }
                }, throwable -> mIView.queryFailed(throwable.getMessage())));
    }
}
