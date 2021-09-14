package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class SGrabContentBean extends BaseBean {

    /**
     * code : 0000
     * data : [{"actualDate":"2021-08-02T01:47:03.432Z","addressCity":"string","addressCounty":"string","addressProvince":"string","createdBy":"string","createdTime":"2021-08-02T01:47:03.432Z","duration":0,"durationPrice":0,"lastUpdatedBy":"string","lastUpdatedTime":"2021-08-02T01:47:03.432Z","orderNum":"string","orderSts":0,"price":0,"remark":"string","storeName":"string","storeUserName":"string","storeUserUuid":"string","useDate":"2021-08-02T01:47:03.432Z","useTime":"string","uuid":"string","vehicleBrand":"string","vehicleBrandName":"string","vehicleModel":"string","vehicleModelName":"string","vehicleUserName":"string","vehicleUserUuid":"string"}]
     * msg : 操作成功
     * pages : 10
     * size : 10
     * success : true
     * total : 100
     */

    private String code;
    private ArrayList<DataBean> data;
    private String msg;
    private int pages;
    private int size;
    private boolean success;
    private int total;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
        /**
         * actualDate : 2021-08-02T01:47:03.432Z
         * addressCity : string
         * addressCounty : string
         * addressProvince : string
         * createdBy : string
         * createdTime : 2021-08-02T01:47:03.432Z
         * duration : 0
         * durationPrice : 0
         * lastUpdatedBy : string
         * lastUpdatedTime : 2021-08-02T01:47:03.432Z
         * orderNum : string
         * orderSts : 0
         * price : 0
         * remark : string
         * storeName : string
         * storeUserName : string
         * storeUserUuid : string
         * useDate : 2021-08-02T01:47:03.432Z
         * useTime : string
         * uuid : string
         * vehicleBrand : string
         * vehicleBrandName : string
         * vehicleModel : string
         * vehicleModelName : string
         * vehicleUserName : string
         * vehicleUserUuid : string
         */

        private String actualDate;
        private String addressCity;
        private String addressCounty;
        private String addressProvince;
        private String createdBy;
        private String createdTime;
        private int duration;
        private float durationPrice;
        private String lastUpdatedBy;
        private String lastUpdatedTime;
        private String orderNum;
        private int orderSts;
        private float price;
        private String remark;
        private String storeName;
        private String storeUserName;
        private String storeUserUuid;
        private String useDate;
        private String useTime;
        private String uuid;
        private String vehicleBrand;
        private String vehicleBrandName;
        private String vehicleModel;
        private String vehicleModelName;
        private String vehicleUserName;
        private String vehicleUserUuid;

        public String getActualDate() {
            return actualDate;
        }

        public void setActualDate(String actualDate) {
            this.actualDate = actualDate;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(String addressCity) {
            this.addressCity = addressCity;
        }

        public String getAddressCounty() {
            return addressCounty;
        }

        public void setAddressCounty(String addressCounty) {
            this.addressCounty = addressCounty;
        }

        public String getAddressProvince() {
            return addressProvince;
        }

        public void setAddressProvince(String addressProvince) {
            this.addressProvince = addressProvince;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public float getDurationPrice() {
            return durationPrice;
        }

        public void setDurationPrice(float durationPrice) {
            this.durationPrice = durationPrice;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public String getLastUpdatedTime() {
            return lastUpdatedTime;
        }

        public void setLastUpdatedTime(String lastUpdatedTime) {
            this.lastUpdatedTime = lastUpdatedTime;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreUserName() {
            return storeUserName;
        }

        public void setStoreUserName(String storeUserName) {
            this.storeUserName = storeUserName;
        }

        public String getStoreUserUuid() {
            return storeUserUuid;
        }

        public void setStoreUserUuid(String storeUserUuid) {
            this.storeUserUuid = storeUserUuid;
        }

        public String getUseDate() {
            return useDate;
        }

        public void setUseDate(String useDate) {
            this.useDate = useDate;
        }

        public String getUseTime() {
            return useTime;
        }

        public void setUseTime(String useTime) {
            this.useTime = useTime;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getVehicleBrand() {
            return vehicleBrand;
        }

        public void setVehicleBrand(String vehicleBrand) {
            this.vehicleBrand = vehicleBrand;
        }

        public String getVehicleBrandName() {
            return vehicleBrandName;
        }

        public void setVehicleBrandName(String vehicleBrandName) {
            this.vehicleBrandName = vehicleBrandName;
        }

        public String getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
        }

        public String getVehicleModelName() {
            return vehicleModelName;
        }

        public void setVehicleModelName(String vehicleModelName) {
            this.vehicleModelName = vehicleModelName;
        }

        public String getVehicleUserName() {
            return vehicleUserName;
        }

        public void setVehicleUserName(String vehicleUserName) {
            this.vehicleUserName = vehicleUserName;
        }

        public String getVehicleUserUuid() {
            return vehicleUserUuid;
        }

        public void setVehicleUserUuid(String vehicleUserUuid) {
            this.vehicleUserUuid = vehicleUserUuid;
        }
    }
}
