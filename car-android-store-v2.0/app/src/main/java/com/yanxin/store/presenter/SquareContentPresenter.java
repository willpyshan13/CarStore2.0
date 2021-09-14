package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.SquareContentContract;
import com.yanxin.store.model.SquareContentModel;
import com.yanxin.store.req.DefaultReq;

public class SquareContentPresenter extends SquareContentContract.SquareContentPresenter {

    public static BasePresenter newInstance() {
        return new SquareContentPresenter();
    }


    @Override
    protected SquareContentContract.SquareContentModel getModel() {
        return SquareContentModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
