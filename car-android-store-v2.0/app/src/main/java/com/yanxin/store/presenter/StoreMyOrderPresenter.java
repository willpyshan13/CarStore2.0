package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.StoreMyOrderContract;
import com.yanxin.store.model.StoreMyOrderModel;

public class StoreMyOrderPresenter extends StoreMyOrderContract.StoreMyOrderPresenter {

    public static BasePresenter newInstance() {
        return new StoreMyOrderPresenter();
    }


    @Override
    protected StoreMyOrderContract.StoreMyOrderModel getModel() {
        return StoreMyOrderModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
