package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.AmountBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.commont.HttpUrl;
import com.yanxin.store.req.AddSceneOrderServiceReq;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MaintenanceProposalContract {
    abstract class MaintenanceProposalPresenter extends BasePresenter<MaintenanceProposalModel, MaintenanceProposalView> {
        //
//        public abstract void queryBasicInspectCost(String type);
//
//        public abstract void queryLineInspectCost(String type);
//
//        public abstract void querySheetMetalRepairCost(String type);
//
//        public abstract void queryDiagnosisInstrumentUseCost(String type);
//
//        public abstract void queryPaintRepairCost(String type);
//
//        public abstract void queryOtherCost(String type);
//
//        public abstract void queryOrderServiceCost(String type);
        public abstract void uploadFile(File mFile);

        public abstract void submitProposal(AddSceneOrderServiceReq addSceneOrderServiceReq);

    }

    interface MaintenanceProposalModel extends IBaseModel {
        //        Observable<AmountBean> queryBasicInspectCost(String type);
//
//        Observable<AmountBean> queryLineInspectCost(String type);
//
//        Observable<AmountBean> querySheetMetalRepairCost(String type);
//
//        Observable<AmountBean> queryDiagnosisInstrumentUseCost(String type);
//
//        Observable<AmountBean> queryPaintRepairCost(String type);
//
//        Observable<AmountBean> queryOtherCost(String type);
//
//        Observable<AmountBean> queryOrderServiceCost(String type);
        Observable<UploadFileBean> uploadFile(File mFile);

        Observable<DefaultBean> submitProposal(AddSceneOrderServiceReq addSceneOrderServiceReq);
    }

    interface MaintenanceProposalView extends IBaseActivity {
        //        void queryBasicInspectCost(ArrayList<AmountBean.DataBean> beans);
//
//        void queryLineInspectCost(ArrayList<AmountBean.DataBean> beans);
//
//        void querySheetMetalRepairCost(ArrayList<AmountBean.DataBean> beans);
//
//        void queryDiagnosisInstrumentUseCost(ArrayList<AmountBean.DataBean> beans);
//
//        void queryPaintRepairCost(ArrayList<AmountBean.DataBean> beans);
//
//        void queryOtherCost(ArrayList<AmountBean.DataBean> beans);
//
//        void queryOrderServiceCost(ArrayList<AmountBean.DataBean> beans);
        void uploadSuccess(String mPath);

        void submitProposal(String msg);

        void failed(String msg);

    }
}
