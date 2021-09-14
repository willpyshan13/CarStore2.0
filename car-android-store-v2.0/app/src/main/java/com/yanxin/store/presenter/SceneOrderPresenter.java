package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.SceneOrderContract;
import com.yanxin.store.model.SceneOrderModel;
import com.yanxin.store.req.GrabOrderReq;
import com.yanxin.store.req.SceneOrderReq;

public class SceneOrderPresenter extends SceneOrderContract.SceneOrderPresenter {

    public static BasePresenter newInstance() {
        return new SceneOrderPresenter();
    }


    @Override
    protected SceneOrderContract.SceneOrderModel getModel() {
        return SceneOrderModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void querySceneOrder(GrabOrderReq sceneOrderReq) {
        rxUtils.register(mIModel.querySceneOrder(sceneOrderReq)
                .subscribe(sceneOrderBean -> {
                            if (sceneOrderBean.isSuccess()) {
                                mIView.querySuccess(sceneOrderBean.getData());
                            } else {
                                mIView.queryFailed(sceneOrderBean.getMsg());
                            }
                        }
                        , throwable -> mIView.queryFailed(throwable.getMessage())));
    }
}
