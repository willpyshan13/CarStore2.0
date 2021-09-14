package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.req.GroupBuyReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface MallGroupBuyContract {
    abstract class MallGroupBuyPresenter extends BasePresenter<MallGroupBuyModel, MallGroupBuyView> {
        public abstract void queryAllGroupMall(GroupBuyReq groupBuyReq);
    }

    interface MallGroupBuyModel extends IBaseModel {
        Observable<GroupCreateBean> queryAllGroupMall(GroupBuyReq groupBuyReq);
    }

    interface MallGroupBuyView extends IBaseFragment {
        void queryGroupBuyList(ArrayList<GroupCreateBean.DataBean> dataBeans);

        void queryFailed(String msg);
    }
}
