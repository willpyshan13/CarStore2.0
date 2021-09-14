package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.FQuizContract;
import com.yanxin.store.model.FQuizModel;

public class FQuizPresenter extends FQuizContract.FQuizPresenter {

    public static BasePresenter newInstance() {
        return new FQuizPresenter();
    }


    @Override
    protected FQuizContract.FQuizModel getModel() {
        return FQuizModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
