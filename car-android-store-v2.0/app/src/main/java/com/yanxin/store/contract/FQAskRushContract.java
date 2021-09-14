package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.MyRushBean;
import com.yanxin.store.bean.MyRushConsultBean;
import com.yanxin.store.req.MyRushReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface FQAskRushContract {
    abstract class FQAskRushPresenter extends BasePresenter<FQAskRushModel, FQAskRushView> {
        public abstract void getMineRush(MyRushReq myRushReq);
    }

    interface FQAskRushModel extends IBaseModel {
        Observable<MyRushBean> getMineRush(MyRushReq myRushReq);
    }

    interface FQAskRushView extends IBaseFragment {
        void mineRushSuccess(ArrayList<MyRushBean.DataBean> dtos);

        void failed(String msg);
    }
}
