package com.yanxin.store.presenter;


import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.contract.TechnicianContract;
import com.yanxin.store.model.TechnicianModel;
import com.yanxin.store.req.TechnicianRegisterReq;

import java.io.File;
import java.util.ArrayList;

public class TechnicianPresenter extends TechnicianContract.TechnicianPresenter {

    public static BasePresenter newInstance() {
        return new TechnicianPresenter();
    }


    @Override
    protected TechnicianContract.TechnicianModel getModel() {
        return TechnicianModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getDepositBank() {
        rxUtils.register(mIModel.getDepositBank()
                .subscribe(depositBankBean -> {
                            if (depositBankBean.isSuccess()) {
                                mIView.depositBankSuccess(depositBankBean.getData());
                            } else {
                                mIView.depositBankFailed(depositBankBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void uploadFile(File mFile) {
        rxUtils.register(mIModel.uploadFile(mFile)
                .subscribe(uploadFileBean -> {
                            if (uploadFileBean.isSuccess()) {
                                mIView.uploadFileSuccess(uploadFileBean.getData());
                            } else {
                                mIView.uploadFileFailed(uploadFileBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
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
                                mIView.queryCityFailed(bean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryBrand(String uuid) {
        rxUtils.register(mIModel.queryBrand(uuid)
                .subscribe(brandBean -> {
                            if (brandBean.isSuccess()) {
                                mIView.queryBrandSuccess(brandBean.getData());
                            } else {
                                mIView.queryBrandFailed(brandBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void queryTechnicianType() {
        rxUtils.register(mIModel.queryTechnicianType()
                .subscribe(technicianTypeBean -> {
                            if (technicianTypeBean.isSuccess()) {
                                mIView.queryTechnicianTypeSuccess(technicianTypeBean.getData());
                            } else {
                                mIView.queryTechnicianTypeFailed(technicianTypeBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void registerTechnician(TechnicianRegisterReq technicianRegisterReq) {
        rxUtils.register(mIModel.registerTechnician(technicianRegisterReq)
                .subscribe(registerBean -> {
                            if (registerBean.isSuccess()) {
                                mIView.registerTechnician(registerBean.getData());
                            } else {
                                mIView.failed(registerBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void updateTechnician(String token,TechnicianRegisterReq technicianRegisterReq) {
        rxUtils.register(mIModel.updateTechnician(token,technicianRegisterReq)
                .subscribe(registerBean -> {
                            if (registerBean.isSuccess()) {
                                mIView.updateTechnician(registerBean.getData());
                            } else {
                                mIView.failed(registerBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }

    @Override
    public void getTechnicianDetail(String token) {
        rxUtils.register(mIModel.getTechnicianDetail(token)
                .subscribe(technicianDetailBean -> {
                            if (technicianDetailBean.isSuccess()) {
                                mIView.getTechnicianDetail(technicianDetailBean.getData());
                            } else {
                                mIView.failed(technicianDetailBean.getMsg());
                            }
                        }
                        , throwable -> mIView.failed(throwable.getMessage())));
    }
}
