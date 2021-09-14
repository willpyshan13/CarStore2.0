package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CaseVehicleBean;
import com.yanxin.store.bean.DictBean;
import com.yanxin.store.req.CaseQueryVehicleReq;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface FCaseContract {
    abstract class FCasePresenter extends BasePresenter<FCaseModel, FCaseView> {
        public abstract void queryCaseForVehicleList(CaseQueryVehicleReq caseQueryVehicleReq);

        public abstract void queryAllBrand(String brandUuid);

        public abstract void queryAllModel(String modUuid);

        public abstract void queryAllSystem(String sysUuid);
    }

    interface FCaseModel extends IBaseModel {
        Observable<CaseVehicleBean> queryCaseForVehicleList(CaseQueryVehicleReq caseQueryVehicleReq);

        Observable<BrandBean> queryAllBrand(String brandUuid);

        Observable<BrandBean> queryAllModel(String modUuid);

        Observable<DictBean> queryAllSystem(String sysUuid);
    }

    interface FCaseView extends IBaseActivity {
        void queryAllSystemSuccess(ArrayList<DictBean.DataBean> brandBean);

        void queryAllSystemFailed(String msg);

        void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllBrandFailed(String msg);

        void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllModelFailed(String msg);

        void queryCaseForVehicleSuccess(ArrayList<CaseVehicleBean.DataBean> vehicleArray);

        void queryCaseForVehicleFailed(String msg);

        void failed(String msg);
    }
}
