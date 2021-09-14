package com.yanxin.store.presenter.mine;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.mine.StoreOrderReportContract;
import com.yanxin.store.model.mine.StoreOrderReportModel;

public class StoreOrderReportPresenter extends StoreOrderReportContract.StoreOrderReportPresenter {

    public static BasePresenter newInstance() {
        return new StoreOrderReportPresenter();
    }


    @Override
    protected StoreOrderReportContract.StoreOrderReportModel getModel() {
        return StoreOrderReportModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
