package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.contract.CreateGoodsContract;
import com.yanxin.store.model.CreateGoodsModel;
import com.yanxin.store.req.CreateMallReq;

import java.io.File;
import java.util.ArrayList;

public class CreateGoodsPresenter extends CreateGoodsContract.CreateGoodsPresenter {
    public static BasePresenter newInstance() {
        return new CreateGoodsPresenter();
    }


    @Override
    protected CreateGoodsContract.CreateGoodsModel getModel() {
        return CreateGoodsModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void queryCity() {
        rxUtils.register(mIModel.queryCity()
                .subscribe(bean -> {
                            if (bean.isSuccess()) {
                                ArrayList<CityBean.DataBean> mCityAll = bean.getData();
                                ArrayList<CityBean.DataBean> cityBean = new ArrayList<>();
                                ArrayList<ArrayList<CityBean.DataBean>> areaBean = new ArrayList<>();
                                ArrayList<ArrayList<ArrayList<CityBean.DataBean>>> provinceBean = new ArrayList<>();
                                //先获取一级的所有省份
                                for (int i = 0; i < mCityAll.size(); i++) {
                                    if (mCityAll.get(i).getParentUuid().equals("-1")) {
                                        cityBean.add(mCityAll.get(i));
                                    }
                                }
                                for (int i = 0; i < cityBean.size(); i++) {
                                    ArrayList<CityBean.DataBean> area = new ArrayList<>();//该省的城市列表（第二级）
                                    ArrayList<ArrayList<CityBean.DataBean>> province_AreaList = new ArrayList<>(); //保存第三级区联动信息
                                    //获取二级所有市
                                    for (int j = cityBean.size(); j < mCityAll.size(); j++) {//遍历该省份的所有城市
                                        if (cityBean.get(i).getUuid().equals(mCityAll.get(j).getParentUuid())) {
                                            area.add(mCityAll.get(j));
                                            ArrayList<CityBean.DataBean> city_areaList = new ArrayList<>();//该城市的所有地区列表
                                            //获取三级所有区
                                            for (int k = cityBean.size(); k < mCityAll.size(); k++) {
                                                if (mCityAll.get(j).getUuid().equals(mCityAll.get(k).getParentUuid())) {
                                                    city_areaList.add(mCityAll.get(k));
                                                }
                                            }
                                            province_AreaList.add(city_areaList);
                                        }
                                    }
                                    /***
                                     * 添加城市数据
                                     */
                                    areaBean.add(area);
                                    /**
                                     * 添加地区数据
                                     */
                                    provinceBean.add(province_AreaList);
                                }
                                mIView.queryCitySuccess(cityBean, areaBean, provinceBean);
                            } else {
                                mIView.failed(bean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void uploadFile(File mFile) {
        rxUtils.register(mIModel.uploadFile(mFile)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.uploadSuccess(defaultBean.getData());
                            } else {
                                mIView.failed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryAllBrand(String brandUuid) {
        rxUtils.register(mIModel.queryAllBrand(brandUuid)
                .subscribe(brandBean -> {
                            if (brandBean.isSuccess()) {
                                mIView.queryAllBrandSuccess(brandBean.getData());
                            } else {
                                mIView.failed(brandBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));

    }

    @Override
    public void queryAllModel(String modUuid) {
        rxUtils.register(mIModel.queryAllModel(modUuid)
                .subscribe(modeBean -> {
                            if (modeBean.isSuccess()) {
                                mIView.queryAllModelSuccess(modeBean.getData());
                            } else {
                                mIView.failed(modeBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));

    }

    @Override
    public void queryTechnicianTypeBean(String technicianType) {
        rxUtils.register(mIModel.queryTechnicianType(technicianType)
                .subscribe(modeBean -> {
                            if (modeBean.isSuccess()) {
                                mIView.queryTechnicianTypeSuccess(modeBean.getData());
                            } else {
                                mIView.failed(modeBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryOneTypeBean(String technicianId) {
        rxUtils.register(mIModel.queryOneTypeBean(technicianId)
                .subscribe(modeBean -> {
                            if (modeBean.isSuccess()) {
                                mIView.queryOneTypeBean(modeBean.getData());
                            } else {
                                mIView.failed(modeBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryTwoTypeBean(String technicianId) {
        rxUtils.register(mIModel.queryTwoTypeBean(technicianId)
                .subscribe(modeBean -> {
                            if (modeBean.isSuccess()) {
                                mIView.queryTwoTypeBean(modeBean.getData());
                            } else {
                                mIView.failed(modeBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void addMallGoods(CreateMallReq createMallReq) {
        rxUtils.register(mIModel.addMallGoods(createMallReq)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.addMallGoods(defaultBean.getData());
                            } else {
                                mIView.failed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void updateMallGoods(CreateMallReq createMallReq) {
        rxUtils.register(mIModel.updateMallGoods(createMallReq)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.updateMallGoods(defaultBean.getData());
                            } else {
                                mIView.failed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryGoodsDetail(String uuid) {
        rxUtils.register(mIModel.queryGoodsDetail(uuid)
                .subscribe(goodsDetailBean -> {
                            if (goodsDetailBean.isSuccess()) {
                                mIView.queryGoodsDetail(goodsDetailBean.getData());
                            } else {
                                mIView.failed(goodsDetailBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
