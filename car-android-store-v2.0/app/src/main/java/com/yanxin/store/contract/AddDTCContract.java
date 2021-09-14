package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.DtcTypeBean;
import com.yanxin.store.req.AddDTCReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface AddDTCContract {
    abstract class AddDTCPresenter extends BasePresenter<AddDTCModel, AddDTCView> {
        public abstract void uploadDTCInfo(AddDTCReq dtcReq);

        public abstract void queryAllBrand(String brandUuid);

        public abstract void queryAllModel(String modelUuid);

//        public abstract void queryDtcTypeList(String dtcType);

    }

    interface AddDTCModel extends IBaseModel {
        Observable<DefaultBean> uploadDTCInfo(AddDTCReq dtcReq);

        Observable<BrandBean> queryAllBrand(String brandUuid);

        Observable<BrandBean> queryAllModel(String modelUuid);

//        Observable<DtcTypeBean> queryDtcTypeList(String dtcType);
    }

    interface AddDTCView extends IBaseActivity {
//        void queryDtcTypeList(ArrayList<DtcTypeBean.DataDTO> dataDTOS);

        void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void success(String msg);
    }
}
