package com.yanxin.store.contract;

import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.base.IBaseActivity;
import com.yanxin.store.base.IBaseModel;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DefaultBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.bean.GoodsSubstanceBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.UploadFileBean;
import com.yanxin.store.req.CreateMallReq;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;

public interface CreateGoodsContract {
    abstract class CreateGoodsPresenter extends BasePresenter<CreateGoodsModel, CreateGoodsView> {

        public abstract void queryCity();

        public abstract void uploadFile(File mFile);

        public abstract void queryAllBrand(String brandUuid);

        public abstract void queryAllModel(String modUuid);

        public abstract void queryTechnicianTypeBean(String technicianId);

        public abstract void queryOneTypeBean(String technicianId);

        public abstract void queryTwoTypeBean(String technicianId);

        public abstract void addMallGoods(CreateMallReq createMallReq);

        public abstract void updateMallGoods(CreateMallReq createMallReq);

        public abstract void queryGoodsDetail(String uuid);

    }

    interface CreateGoodsModel extends IBaseModel {
        Observable<GoodsDetailBean> queryGoodsDetail(String uuid);

        Observable<DefaultBean> addMallGoods(CreateMallReq createMallReq);

        Observable<GoodsDetailBean> updateMallGoods(CreateMallReq createMallReq);

        Observable<UploadFileBean> uploadFile(File mFile);

        Observable<CityBean> queryCity();

        Observable<BrandBean> queryAllBrand(String brandUuid);

        Observable<BrandBean> queryAllModel(String modUuid);

        Observable<TechnicianTypeBean> queryTechnicianType(String type);

        Observable<GoodsSubstanceBean> queryOneTypeBean(String technicianId);

        Observable<GoodsSubstanceBean> queryTwoTypeBean(String technicianId);
    }

    interface CreateGoodsView extends IBaseActivity {

        void queryGoodsDetail(GoodsDetailBean.DataBean goodsBean);

        void uploadSuccess(String path);

        void addMallGoods(String uuid);

        void updateMallGoods(GoodsDetailBean.DataBean goodsBean);

        void queryTechnicianTypeSuccess(ArrayList<TechnicianTypeBean.DataBean> dataBeans);

        void queryCitySuccess(ArrayList<CityBean.DataBean> cityBean, ArrayList<ArrayList<CityBean.DataBean>> areaBean, ArrayList<ArrayList<ArrayList<CityBean.DataBean>>> addressBean);

        void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean);

        void queryOneTypeBean(ArrayList<GoodsSubstanceBean.DataBean> brandBean);

        void queryTwoTypeBean(ArrayList<GoodsSubstanceBean.DataBean> brandBean);

    }
}
