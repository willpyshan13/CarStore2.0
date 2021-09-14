package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.bean.MyRushConsultBean;
import com.yanxin.store.req.MyRushReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface FQMyRushContract {
    abstract class FQMyRushPresenter extends BasePresenter<FQMyRushModel, FQMyRushView> {
        public abstract void getMineRush(MyRushReq myRushReq);
    }

    interface FQMyRushModel extends IBaseModel {
        Observable<MyRushBean> getMineRush(MyRushReq myRushReq);
    }

    interface FQMyRushView extends IBaseFragment {
        void mineRushSuccess(ArrayList<MyRushBean.DataBean> dtos);

        void failed(String msg);
    }
}
