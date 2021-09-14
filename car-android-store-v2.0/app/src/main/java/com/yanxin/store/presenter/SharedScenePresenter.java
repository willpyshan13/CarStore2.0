package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.SharedSceneContract;
import com.yanxin.store.model.SharedSceneModel;

public class SharedScenePresenter extends SharedSceneContract.SharedScenePresenter {

    public static BasePresenter newInstance() {
        return new SharedScenePresenter();
    }


    @Override
    protected SharedSceneContract.SharedSceneModel getModel() {
        return SharedSceneModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
