package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.OrderInfoBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.req.AskExpertReq;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;

public interface AskEveryoneContract {
    abstract class AskEveryonePresenter extends BasePresenter<AskEveryoneModel, AskEveryoneView> {
        public abstract void uploadFile(File mFile);

        public abstract void queryAllBrand(String brandUuid);

        public abstract void queryAllModel(String modUuid);

        public abstract void queryTechnicianTypeBean(String technicianId);

        public abstract void submit(AskExpertReq askExpertReq);

    }

    interface AskEveryoneModel extends IBaseModel {
        Observable<UploadFileBean> uploadFile(File mFile);

        Observable<BrandBean> queryAllBrand(String brandUuid);

        Observable<BrandBean> queryAllModel(String modUuid);

        Observable<TechnicianTypeBean> queryTechnicianTypeBean(String technicianId);

        Observable<DefaultBean> submit(AskExpertReq askExpertReq);
    }

    interface AskEveryoneView extends IBaseActivity {

        void submitSuccess(String msg);

        void submitFailed(String msg);

        void uploadFileFailed(String msg);

        void uploadFileSuccess(String path);

        void queryTechnicianTypeSuccess(ArrayList<TechnicianTypeBean.DataBean> brandBean);

        void queryTechnicianTypeFailed(String msg);

        void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllBrandFailed(String msg);

        void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllModelFailed(String msg);

    }
}
