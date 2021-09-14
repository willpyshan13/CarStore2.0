package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.ServingContract;
import com.yanxin.store.model.ServingModel;

public class ServingPresenter extends ServingContract.ServingPresenter {

    public static BasePresenter newInstance() {
        return new ServingPresenter();
    }


    @Override
    protected ServingContract.ServingModel getModel() {
        return ServingModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
