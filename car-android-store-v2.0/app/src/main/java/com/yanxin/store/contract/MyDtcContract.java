package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.DTCBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.MyDtcBean;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.DTCReq;
import com.yanxin.store.req.MyDtcReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface MyDtcContract {
    abstract class MyDtcPresenter extends BasePresenter<MyDtcModel, MyDtcView> {
        public abstract void getDtcList(MyDtcReq myDtcReq);

        public abstract void createDTCOrder(CreateDTCReq createDTCReq);
    }

    interface MyDtcModel extends IBaseModel {
        Observable<MyDtcBean> getDtcList(MyDtcReq dtcReq);
        Observable<DefaultBean> createDTCOrder(CreateDTCReq createDTCReq);

    }

    interface MyDtcView extends IBaseActivity {
        void getDTCSuccess(ArrayList<MyDtcBean.DataDTO> dataBeans);

        void createDTCOrder(String uuid);
        void getDTCFailed(String msg);

        void failed(String msg);
    }
}
