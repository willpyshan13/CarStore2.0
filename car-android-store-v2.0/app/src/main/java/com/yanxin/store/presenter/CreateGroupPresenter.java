package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.contract.CreateGroupContract;
import com.yanxin.store.model.CreateGroupModel;
import com.yanxin.store.req.CreateMallReq;
import com.yanxin.store.req.GroupBuyReq;

import java.io.File;
import java.util.ArrayList;

public class CreateGroupPresenter extends CreateGroupContract.CreateGroupPresenter {
    public static BasePresenter newInstance() {
        return new CreateGroupPresenter();
    }


    @Override
    protected CreateGroupContract.CreateGroupModel getModel() {
        return CreateGroupModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void queryAllGroupMall(GroupBuyReq groupBuyReq) {
        rxUtils.register(mIModel.queryAllGroupMall(groupBuyReq)
                .subscribe(groupBuyBean -> {
                            if (groupBuyBean.isSuccess()) {
                                mIView.queryAllGroupMall(groupBuyBean.getData());
                            } else {
                                mIView.failed(groupBuyBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
