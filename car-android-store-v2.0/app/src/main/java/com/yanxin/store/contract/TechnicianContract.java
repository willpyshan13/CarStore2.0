package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DepositBankBean;
import com.yanxin.store.bean.RegisterBean;
import com.yanxin.store.bean.TechnicianDetailBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.req.TechnicianRegisterReq;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;

public interface TechnicianContract {
    abstract class TechnicianPresenter extends BasePresenter<TechnicianModel, TechnicianView> {
        public abstract void getDepositBank();

        public abstract void uploadFile(File mFile);

        public abstract void queryCity();

        public abstract void queryBrand(String uuid);

        public abstract void queryTechnicianType();

        public abstract void registerTechnician(TechnicianRegisterReq technicianRegisterReq);

        public abstract void updateTechnician(String token,TechnicianRegisterReq technicianRegisterReq);

        public abstract void getTechnicianDetail(String token);
    }

    interface TechnicianModel extends IBaseModel {
        Observable<DepositBankBean> getDepositBank();

        Observable<UploadFileBean> uploadFile(File mFile);

        Observable<CityBean> queryCity();

        Observable<BrandBean> queryBrand(String uuid);

        Observable<TechnicianTypeBean> queryTechnicianType();

        Observable<RegisterBean> registerTechnician(TechnicianRegisterReq technicianRegisterReq);

        Observable<RegisterBean> updateTechnician(String token,TechnicianRegisterReq technicianRegisterReq);

        Observable<TechnicianDetailBean> getTechnicianDetail(String token);
    }

    interface TechnicianView extends IBaseActivity {
        void getTechnicianDetail(TechnicianDetailBean.DataBean dataBean);

        void registerTechnician(String token);

        void updateTechnician(String token);

        void queryTechnicianTypeSuccess(ArrayList<TechnicianTypeBean.DataBean> dataBeans);

        void queryTechnicianTypeFailed(String msg);

        void queryBrandSuccess(ArrayList<BrandBean.DataBean> dataBeans);

        void queryBrandFailed(String msg);

        void uploadFileSuccess(String path);

        void queryCitySuccess(ArrayList<CityBean.DataBean> cityBean, ArrayList<ArrayList<CityBean.DataBean>> areaBean, ArrayList<ArrayList<ArrayList<CityBean.DataBean>>> addressBean);

        void queryCityFailed(String path);

        void uploadFileFailed(String msg);

        void depositBankSuccess(ArrayList<DepositBankBean.DataBean> dataBeans);

        void depositBankFailed(String msg);
    }
}
