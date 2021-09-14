package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DTCBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.req.CreateDTCReq;
import com.yanxin.store.req.DTCReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface FDTCContract {
    abstract class FDTCPresenter extends BasePresenter<FDTCModel, FDTCView> {
        public abstract void getDtcList(DTCReq dtcReq);

        public abstract void createDTCOrder(CreateDTCReq createDTCReq);

        public abstract void queryAllBrand(String brandUuid);
    }

    interface FDTCModel extends IBaseModel {
        Observable<DTCBean> getDtcList(DTCReq dtcReq);

        Observable<DefaultBean> createDTCOrder(CreateDTCReq createDTCReq);

        Observable<BrandBean> queryAllBrand(String brandUuid);

    }

    interface FDTCView extends IBaseActivity {
        void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void getDTCSuccess(ArrayList<DTCBean.DataBean> dataBeans);

        void createDTCOrder(String uuid);

        void getDTCFailed(String msg);

        void failed(String msg);
    }
}
