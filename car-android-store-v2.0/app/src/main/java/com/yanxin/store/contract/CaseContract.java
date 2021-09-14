package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BuyCaseBean;
import com.yanxin.store.bean.MineCaseBean;
import com.yanxin.store.bean.MyOrderBean;
import com.yanxin.store.req.DefaultReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface CaseContract {
    abstract class CasePresenter extends BasePresenter<CaseModel, CaseView> {
        public abstract void getMineCase(DefaultReq defaultReq);

        public abstract void getMyBuyCase(DefaultReq defaultReq);

        public abstract void getBuyCase(DefaultReq defaultReq);
    }

    interface CaseModel extends IBaseModel {
        Observable<MineCaseBean> getMineCase(DefaultReq defaultReq);

        Observable<MyOrderBean> getMyBuyCase(DefaultReq defaultReq);

        Observable<BuyCaseBean> getBuyCase(DefaultReq defaultReq);
    }

    interface CaseView extends IBaseActivity {
        void myBuyCaseSuccess(ArrayList<MyOrderBean.DataBean> dataBeans);

        void mineCaseSuccess(ArrayList<MineCaseBean.DataBean> dataBeans);

        void buyCaseSuccess(ArrayList<BuyCaseBean.DataBean> dataBeans);


    }
}
