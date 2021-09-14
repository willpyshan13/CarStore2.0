package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.DrivingBean;
import com.yanxin.store.bean.EngineDisplacementBean;
import com.yanxin.store.bean.SiteFeeBean;
import com.yanxin.store.bean.SuperchargingBean;
import com.yanxin.store.bean.SupportOrderBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.TransmissionBean;
import com.yanxin.store.bean.TransmissonChildBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.commont.HttpUrl;
import com.yanxin.store.req.AddSiteReq;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AddSiteOrderContract {
    abstract class AddSiteOrderPresenter extends BasePresenter<AddSiteOrderModel, AddSiteOrderView> {

        public abstract void queryCity();

        public abstract void uploadFile(File mFile);

        public abstract void queryBaseFee(String uuid);

        public abstract void queryBaseServiceFee(String uuid);

        public abstract void queryAllBrand(String brandUuid);

        public abstract void queryAllModel(String modUuid);

        public abstract void queryTechnicianTypeBean(String technicianId);

        public abstract void queryEngineDisplacement(String type);

        public abstract void queryDriving(String type);

        public abstract void queryTransmission(String type);

        public abstract void querySupercharging(String type);

        public abstract void queryManualTransmission(String type);

        public abstract void addSite(AddSiteReq addSiteReq);
    }

    interface AddSiteOrderModel extends IBaseModel {

        Observable<UploadFileBean> uploadFile(File mFile);

        Observable<SiteFeeBean> queryBaseFee(String uuid);

        Observable<SiteFeeBean> queryBaseServiceFee(String uuid);

        Observable<CityBean> queryCity();

        Observable<TechnicianTypeBean> queryTechnicianType(String type);

        Observable<TransmissonChildBean> queryManualTransmission(String type);

        Observable<BrandBean> queryAllBrand(String brandUuid);

        Observable<BrandBean> queryAllModel(String modUuid);

        Observable<EngineDisplacementBean> queryEngineDisplacement(String type);

        Observable<DrivingBean> queryDriving(String type);

        Observable<TransmissionBean> queryTransmission(String type);

        Observable<SuperchargingBean> querySupercharging(String type);

        Observable<DefaultBean> addSite(AddSiteReq addSiteReq);
    }

    interface AddSiteOrderView extends IBaseActivity {
        void addSuccess(String data);

        void uploadSuccess(String msg);

        void queryTransmission(ArrayList<TransmissionBean.DataBean> transBean);

        void queryBaseFee(SiteFeeBean.DataBean siteFeeBean);

        void queryBaseServiceFee(SiteFeeBean.DataBean siteFeeBean);

        void querySupercharging(ArrayList<SuperchargingBean.DataBean> engineBean);

        void queryManualTransmission(ArrayList<TransmissonChildBean.DataBean> transChildBean);

        void queryEngineDisplacement(ArrayList<EngineDisplacementBean.DataBean> engineBean);

        void queryDriving(ArrayList<DrivingBean.DataBean> drivingBean);

        void queryTechnicianTypeSuccess(ArrayList<TechnicianTypeBean.DataBean> dataBeans);

        void queryCitySuccess(ArrayList<CityBean.DataBean> cityBean, ArrayList<ArrayList<CityBean.DataBean>> areaBean, ArrayList<ArrayList<ArrayList<CityBean.DataBean>>> addressBean);

        void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean);

    }
}
