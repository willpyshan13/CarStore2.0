package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.req.GroupBuyReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface CreateGroupContract {
    abstract class CreateGroupPresenter extends BasePresenter<CreateGroupModel, CreateGroupView> {
        public abstract void queryAllGroupMall(GroupBuyReq groupBuyReq);

    }

    interface CreateGroupModel extends IBaseModel {
        Observable<GroupCreateBean> queryAllGroupMall(GroupBuyReq groupBuyReq);

    }

    interface CreateGroupView extends IBaseActivity {
        void queryAllGroupMall(ArrayList<GroupCreateBean.DataBean> groupBean);
    }
}
