package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.AuditBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.SquareBean;
import com.yanxin.store.req.AuditReq;
import com.yanxin.store.req.DefaultReq;
import com.yanxin.store.req.PayAuditReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface FQSquareContract {
    abstract class FQSquarePresenter extends BasePresenter<FQSquareModel, FQSquareView> {
        public abstract void queryAnswerAll(AuditReq defaultReq);

        public abstract void addAuditorOrder(PayAuditReq defaultReq);
    }

    interface FQSquareModel extends IBaseModel {
        Observable<AuditBean> queryAnswerAll(AuditReq defaultReq);

        Observable<DefaultBean> addAuditorOrder(PayAuditReq defaultReq);
    }

    interface FQSquareView extends IBaseActivity {
        void paySuccess(String uuid);

        void queryAnswerSuccess(ArrayList<AuditBean.DataBean> dataBeans);

        void failed(String msg);
    }
}
