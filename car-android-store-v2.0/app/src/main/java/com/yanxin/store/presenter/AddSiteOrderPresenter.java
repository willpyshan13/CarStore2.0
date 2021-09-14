package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.contract.AddSiteOrderContract;
import com.yanxin.store.model.AddSiteOrderModel;
import com.yanxin.store.req.AddSiteReq;

import java.io.File;
import java.util.ArrayList;

public class AddSiteOrderPresenter extends AddSiteOrderContract.AddSiteOrderPresenter {

    public static BasePresenter newInstance() {
        return new AddSiteOrderPresenter();
    }


    @Override
    protected AddSiteOrderContract.AddSiteOrderModel getModel() {
        return AddSiteOrderModel.getInstance();
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
    public void queryBaseFee(String uuid) {
        rxUtils.register(mIModel.queryBaseFee(uuid)
                .subscribe(feeBean -> {
                            if (feeBean.isSuccess()) {
                                mIView.queryBaseFee(feeBean.getData());
                            } else {
                                mIView.failed(feeBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryBaseServiceFee(String uuid) {
        rxUtils.register(mIModel.queryBaseServiceFee(uuid)
                .subscribe(feeBean -> {
                            if (feeBean.isSuccess()) {
                                mIView.queryBaseServiceFee(feeBean.getData());
                            } else {
                                mIView.failed(feeBean.getMsg());
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
    public void queryTechnicianTypeBean(String technicianId) {
        rxUtils.register(mIModel.queryTechnicianType(technicianId)
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
    public void queryEngineDisplacement(String type) {
        rxUtils.register(mIModel.queryEngineDisplacement(type)
                .subscribe(displacementBean -> {
                            if (displacementBean.isSuccess()) {
                                mIView.queryEngineDisplacement(displacementBean.getData());
                            } else {
                                mIView.failed(displacementBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryDriving(String type) {
        rxUtils.register(mIModel.queryDriving(type)
                .subscribe(drivingBean -> {
                            if (drivingBean.isSuccess()) {
                                mIView.queryDriving(drivingBean.getData());
                            } else {
                                mIView.failed(drivingBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryTransmission(String type) {
        rxUtils.register(mIModel.queryTransmission(type)
                .subscribe(transmissionBean -> {
                            if (transmissionBean.isSuccess()) {
                                mIView.queryTransmission(transmissionBean.getData());
                            } else {
                                mIView.failed(transmissionBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void querySupercharging(String type) {
        rxUtils.register(mIModel.querySupercharging(type)
                .subscribe(supportOrderBean -> {
                            if (supportOrderBean.isSuccess()) {
                                mIView.querySupercharging(supportOrderBean.getData());
                            } else {
                                mIView.failed(supportOrderBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryManualTransmission(String type) {
        rxUtils.register(mIModel.queryManualTransmission(type)
                .subscribe(transmissonChildBean -> {
                            if (transmissonChildBean.isSuccess()) {
                                mIView.queryManualTransmission(transmissonChildBean.getData());
                            } else {
                                mIView.failed(transmissonChildBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void addSite(AddSiteReq addSiteReq) {
        rxUtils.register(mIModel.addSite(addSiteReq)
                .subscribe(defaultBean -> {
                            if (defaultBean.isSuccess()) {
                                mIView.addSuccess(defaultBean.getData());
                            } else {
                                mIView.failed(defaultBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
