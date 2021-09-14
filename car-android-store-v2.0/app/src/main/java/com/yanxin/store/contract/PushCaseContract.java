package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.DictBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.req.PushCaseReq;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;

public interface PushCaseContract {
    abstract class PushCasePresenter extends BasePresenter<PushCaseModel, PushCaseView> {
        public abstract void uploadFile(File mFile);

        public abstract void queryAllBrand(String brandUuid);

        public abstract void queryAllModel(String modUuid);

        public abstract void queryAllSystem(String sysUuid);

        public abstract void submitCase(PushCaseReq pushCaseReq);
    }

    interface PushCaseModel extends IBaseModel {
        Observable<UploadFileBean> uploadFile(File mFile);

        Observable<BrandBean> queryAllBrand(String brandUuid);

        Observable<BrandBean> queryAllModel(String modUuid);

        Observable<DictBean> queryAllSystem(String sysUuid);

        Observable<DefaultBean> submitCase(PushCaseReq pushCaseReq);

    }

    interface PushCaseView extends IBaseActivity {
        void queryAllSystemSuccess(ArrayList<DictBean.DataBean> brandBean);

        void queryAllSystemFailed(String msg);

        void submitSuccess(String msg);

        void uploadFileFailed(String msg);

        void uploadFileSuccess(String path);

        void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllBrandFailed(String msg);

        void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllModelFailed(String msg);

    }
}
