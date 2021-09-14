package com.yanxin.store.model;

import com.yanxin.store.MyApplication;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseModel;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.contract.MallGroupBuyContract;
import com.yanxin.store.req.GroupBuyReq;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import io.reactivex.Observable;

public class MallGroupBuyModel extends BaseModel implements MallGroupBuyContract.MallGroupBuyModel {
    public static MallGroupBuyModel getInstance() {
        return new MallGroupBuyModel();
    }

    @Override
    public Observable<GroupCreateBean> queryAllGroupMall(GroupBuyReq groupBuyReq) {
        return RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryAllGroupMall(MyApplication.getUserToken(), groupBuyReq)
                .compose(RxHelper.rxSchedulerHelper())
                .map(goodsBean -> goodsBean);
    }

}
