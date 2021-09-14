package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MsgContract;
import com.yanxin.store.model.MsgModel;

public class MsgPresenter extends MsgContract.MsgPresenter {

    public static BasePresenter newInstance() {
        return new MsgPresenter();
    }


    @Override
    protected MsgContract.MsgModel getModel() {
        return MsgModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
