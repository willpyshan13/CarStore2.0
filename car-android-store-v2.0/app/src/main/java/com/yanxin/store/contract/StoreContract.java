package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DepositBankBean;
import com.yanxin.store.bean.PersonTypeBean;
import com.yanxin.store.bean.RegisterBean;
import com.yanxin.store.bean.StoreDetailBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.req.StoreRegisterReq;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;

public interface StoreContract {
    abstract class StorePresenter extends BasePresenter<StoreModel, StoreView> {
        public abstract void getDepositBank();

        public abstract void getPersonType();

        public abstract void uploadFile(File mFile);

        public abstract void queryCity();

        public abstract void queryBrand(String uuid);

        public abstract void addStore(StoreRegisterReq storeRegisterReq);

        public abstract void updateStore(String token,StoreRegisterReq storeRegisterReq);

        public abstract void getStoreDetail(String token);
    }

    interface StoreModel extends IBaseModel {
        Observable<DepositBankBean> getDepositBank();

        Observable<PersonTypeBean> getPersonType();

        Observable<UploadFileBean> uploadFile(File mFile);

        Observable<CityBean> queryCity();

        Observable<BrandBean> queryBrand(String uuid);

        Observable<RegisterBean> addStore(StoreRegisterReq storeRegisterReq);

        Observable<RegisterBean> updateStore(String token,StoreRegisterReq storeRegisterReq);

        Observable<StoreDetailBean> getStoreDetail(String token);

    }

    interface StoreView extends IBaseActivity {
        void getStoreDetail(StoreDetailBean.DataBean dataBean);

        void addStoreSuccess(String dataStr);

        void updateStoreSuccess(String dataStr);

        void queryBrandSuccess(ArrayList<BrandBean.DataBean> dataBeans);

        void queryBrandFailed(String msg);

        void uploadFileSuccess(String path);

        void queryCitySuccess(ArrayList<CityBean.DataBean> cityBean, ArrayList<ArrayList<CityBean.DataBean>> areaBean, ArrayList<ArrayList<ArrayList<CityBean.DataBean>>> addressBean);

        void queryCityFailed(String path);

        void uploadFileFailed(String msg);

        void depositBankSuccess(ArrayList<DepositBankBean.DataBean> dataBeans);

        void depositBankFailed(String msg);

        void personTypeSuccess(ArrayList<PersonTypeBean.DataBean> dataBeans);

        void personTypeFailed(String msg);

    }
}
