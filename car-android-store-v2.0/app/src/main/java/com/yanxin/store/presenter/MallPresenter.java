package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MallContract;
import com.yanxin.store.model.MallModel;

public class MallPresenter extends MallContract.MallPresenter {

    public static BasePresenter newInstance() {
        return new MallPresenter();
    }


    @Override
    protected MallContract.MallModel getModel() {
        return MallModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
