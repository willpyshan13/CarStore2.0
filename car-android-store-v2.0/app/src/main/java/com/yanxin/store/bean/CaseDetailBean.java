package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;

public class CaseDetailBean extends BaseBean {

    /**
     * code : 0000
     * data : {"amt":0,"attachSys":"string","brandName":"string","brandUuid":"string","carOwnerType":"string","caseImgList":["string"],"createdTime":"string","cybAuth":0,"earnings":0,"faultDesc":"string","ideaProcess":"string","madeTime":"2020-12-30 21:35:00","mileage":0,"model":"string","modelName":"string","orderSts":0,"orderUuid":"string","powerInfo":"string","salesVolume":0,"score":0,"storeName":"string","storeType":"string","summary":"string","technicianUuid":"string","title":"string","userName":"string","uuid":"string","vin":"string","workingYear":"string"}
     * msg : 操作成功
     * success : true
     */

    private String code;
    private DataBean data;
    private String msg;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * amt : 0
         * attachSys : string
         * brandName : string
         * brandUuid : string
         * carOwnerType : string
         * caseImgList : ["string"]
         * createdTime : string
         * cybAuth : 0
         * earnings : 0
         * faultDesc : string
         * ideaProcess : string
         * madeTime : 2020-12-30 21:35:00
         * mileage : 0
         * model : string
         * modelName : string
         * orderSts : 0
         * orderUuid : string
         * powerInfo : string
         * salesVolume : 0
         * score : 0
         * storeName : string
         * storeType : string
         * summary : string
         * technicianUuid : string
         * title : string
         * userName : string
         * uuid : string
         * vin : string
         * workingYear : string
         */

        private float amt;
        private String attachSys;
        private String attachSysName;
        private String brandName;
        private int purchase; //1我购买的  0不是
        private String brandUuid;
        private int technicianType;
        private ArrayList<String> caseImgList;
        private String createdTime;
        private int cybAuth;
        private int earnings;
        private String faultDesc;
        private String ideaProcess;
        private String madeTime;
        private int mileage;
        private String model;
        private String pdfName;
        private String modelName;
        private int orderSts;
        private String orderUuid;
        private String powerInfo;
        private int salesVolume;
        private int score;
        private String storeName;
        private String storeType;
        private String summary;
        private String technicianUuid;
        private String title;
        private String userName;
        private String uuid;
        private String vin;
        private String workingYear;

        public String getPdfName() {
            return pdfName;
        }

        public void setPdfName(String pdfName) {
            this.pdfName = pdfName;
        }

        public String getAttachSysName() {
            return attachSysName;
        }

        public void setAttachSysName(String attachSysName) {
            this.attachSysName = attachSysName;
        }

        public int getPurchase() {
            return purchase;
        }

        public void setPurchase(int purchase) {
            this.purchase = purchase;
        }

        public String getAmt() {
            return BasicUtils.floatDecimalFormat(amt);
        }

        public void setAmt(float amt) {
            this.amt = amt;
        }

        public String getAttachSys() {
            return attachSys;
        }

        public void setAttachSys(String attachSys) {
            this.attachSys = attachSys;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandUuid() {
            return brandUuid;
        }

        public void setBrandUuid(String brandUuid) {
            this.brandUuid = brandUuid;
        }

        public int getTechnicianType() {
            return technicianType;
        }

        public void setTechnicianType(int technicianType) {
            this.technicianType = technicianType;
        }

        public ArrayList<String> getCaseImgList() {
            return caseImgList;
        }

        public void setCaseImgList(ArrayList<String> caseImgList) {
            this.caseImgList = caseImgList;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getCybAuth() {
            return cybAuth;
        }

        public void setCybAuth(int cybAuth) {
            this.cybAuth = cybAuth;
        }

        public int getEarnings() {
            return earnings;
        }

        public void setEarnings(int earnings) {
            this.earnings = earnings;
        }

        public String getFaultDesc() {
            return faultDesc;
        }

        public void setFaultDesc(String faultDesc) {
            this.faultDesc = faultDesc;
        }

        public String getIdeaProcess() {
            return ideaProcess;
        }

        public void setIdeaProcess(String ideaProcess) {
            this.ideaProcess = ideaProcess;
        }

        public String getMadeTime() {
            return madeTime;
        }

        public void setMadeTime(String madeTime) {
            this.madeTime = madeTime;
        }

        public int getMileage() {
            return mileage;
        }

        public void setMileage(int mileage) {
            this.mileage = mileage;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public String getOrderUuid() {
            return orderUuid;
        }

        public void setOrderUuid(String orderUuid) {
            this.orderUuid = orderUuid;
        }

        public String getPowerInfo() {
            return powerInfo;
        }

        public void setPowerInfo(String powerInfo) {
            this.powerInfo = powerInfo;
        }

        public int getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(int salesVolume) {
            this.salesVolume = salesVolume;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getTechnicianUuid() {
            return technicianUuid;
        }

        public void setTechnicianUuid(String technicianUuid) {
            this.technicianUuid = technicianUuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getVin() {
            return vin;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }

        public String getWorkingYear() {
            return workingYear;
        }

        public void setWorkingYear(String workingYear) {
            this.workingYear = workingYear;
        }
    }
}
