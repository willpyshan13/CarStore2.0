package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MallGroupBuyContract;
import com.yanxin.store.model.MallGroupBuyModel;
import com.yanxin.store.req.GroupBuyReq;

public class MallGroupBuyPresenter extends MallGroupBuyContract.MallGroupBuyPresenter {

    public static BasePresenter newInstance() {
        return new MallGroupBuyPresenter();
    }


    @Override
    protected MallGroupBuyContract.MallGroupBuyModel getModel() {
        return MallGroupBuyModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void queryAllGroupMall(GroupBuyReq groupBuyReq) {
        rxUtils.register(mIModel.queryAllGroupMall(groupBuyReq)
                .subscribe(groupBuyBean -> {
                    if (groupBuyBean.isSuccess()) {
                        mIView.queryGroupBuyList(groupBuyBean.getData());
                    } else {
                        mIView.queryFailed(groupBuyBean.getMsg());
                    }
                }, throwable -> mIView.queryFailed(throwable.getMessage())));
    }
}
