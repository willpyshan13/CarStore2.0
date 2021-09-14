package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseFragment;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.RushAnswerBean;
import com.yanxin.store.bean.SquareBean;
import com.yanxin.store.req.DefaultReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface FQRushContract {
    abstract class FQRushPresenter extends BasePresenter<FQRushModel, FQRushView> {
        //public abstract void getOrderSnapUp()
        public abstract void queryAnswerAll(DefaultReq defaultReq);

    }

    interface FQRushModel extends IBaseModel {
        Observable<RushAnswerBean> queryAnswerAll(DefaultReq defaultReq);

    }

    interface FQRushView extends IBaseFragment {
        void queryAnswerSuccess(ArrayList<RushAnswerBean.DataBean> dataBeans);

        void failed(String msg);
    }
}
