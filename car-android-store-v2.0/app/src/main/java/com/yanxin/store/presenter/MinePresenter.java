package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MineContract;
import com.yanxin.store.model.MineModel;

public class MinePresenter extends MineContract.MinePresenter {

    public static BasePresenter newInstance() {
        return new MinePresenter();
    }


    @Override
    protected MineContract.MineModel getModel() {
        return MineModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
