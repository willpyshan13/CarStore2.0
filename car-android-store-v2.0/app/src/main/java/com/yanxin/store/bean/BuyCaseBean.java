package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class BuyCaseBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"323383ece5df44d3b125544cbc866deb","orderNum":"AL1423240648588398592","createdTime":"2021-08-05 19:13:08","orderAmount":10,"payType":1,"orderSts":6,"caseName":"案例茶母咕咕咕咕","caseImgUrl":null,"caseNum":null,"faultDesc":"ummmm...","ideaProcess":null,"summary":"龙膜...","carOwnerName":"迪嘉","technicianName":null,"cybAuth":"1","score":"5","techniciantype":2,"technicianUuid":"1aae21a524c44953bfeeb70bbcd061d6","userName":"专门修车胎"},{"uuid":"2576d4b53bc8452b89ec0e8cc25becbb","orderNum":"AL1423237616672837632","createdTime":"2021-08-05 19:01:05","orderAmount":10,"payType":1,"orderSts":6,"caseName":"惊喜","caseImgUrl":null,"caseNum":null,"faultDesc":"48488...","ideaProcess":null,"summary":"让我去...","carOwnerName":"迪嘉","technicianName":null,"cybAuth":"1","score":"5","techniciantype":2,"technicianUuid":"c25fb11a894348618769476e5583ba74","userName":"8刘文"},{"uuid":"41262323456f4ebb85f8608685ec5ffe","orderNum":"AL1423179351054225408","createdTime":"2021-08-05 15:09:33","orderAmount":10,"payType":1,"orderSts":6,"caseName":"略略略路","caseImgUrl":null,"caseNum":null,"faultDesc":"可控呢饿...","ideaProcess":null,"summary":"哦哦恐龙妹...","carOwnerName":"迪嘉","technicianName":null,"cybAuth":"1","score":"5","techniciantype":2,"technicianUuid":"5d54357eab744e6290303fef1b9c8095","userName":"泰罗奥特曼"},{"uuid":"cedda8dd1c5b45f98e5612a101636448","orderNum":"AL1423175009731481600","createdTime":"2021-08-05 14:52:18","orderAmount":10,"payType":1,"orderSts":6,"caseName":"略略略路","caseImgUrl":null,"caseNum":null,"faultDesc":"可控呢饿...","ideaProcess":null,"summary":"哦哦恐龙妹...","carOwnerName":"迪嘉","technicianName":null,"cybAuth":"1","score":"5","techniciantype":2,"technicianUuid":"5d54357eab744e6290303fef1b9c8095","userName":"泰罗奥特曼"}]
     * size : 50
     * total : 4
     * pages : 1
     * success : true
     */

    private String code;
    private String msg;
    private ArrayList<DataBean> data;
    private int size;
    private int total;
    private int pages;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * uuid : 323383ece5df44d3b125544cbc866deb
         * orderNum : AL1423240648588398592
         * createdTime : 2021-08-05 19:13:08
         * orderAmount : 10
         * payType : 1
         * orderSts : 6
         * caseName : 案例茶母咕咕咕咕
         * caseImgUrl : null
         * caseNum : null
         * faultDesc : ummmm...
         * ideaProcess : null
         * summary : 龙膜...
         * carOwnerName : 迪嘉
         * technicianName : null
         * cybAuth : 1
         * score : 5
         * techniciantype : 2
         * technicianUuid : 1aae21a524c44953bfeeb70bbcd061d6
         * userName : 专门修车胎
         */

        private String uuid;
        private String orderNum;
        private String createdTime;
        private float orderAmount;
        private int payType;
        private int orderSts;
        private String caseName;
        private String caseImgUrl;
        private int caseNum;
        private int purchaseNumber;
        private String faultDesc;
        private Object ideaProcess;
        private String summary;
        private String carOwnerName;
        private String technicianName;
        private String cybAuth;
        private String score;
        private int techniciantype;
        private String technicianUuid;
        private String userName;

        public int getPurchaseNumber() {
            return purchaseNumber;
        }

        public void setPurchaseNumber(int purchaseNumber) {
            this.purchaseNumber = purchaseNumber;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getOrderAmount() {
            return BasicUtils.floatDecimalFormat(orderAmount);
        }

        public void setOrderAmount(float orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public String getCaseName() {
            return caseName;
        }

        public void setCaseName(String caseName) {
            this.caseName = caseName;
        }

        public String getCaseImgUrl() {
            return caseImgUrl;
        }

        public void setCaseImgUrl(String caseImgUrl) {
            this.caseImgUrl = caseImgUrl;
        }

        public int getCaseNum() {
            return caseNum;
        }

        public void setCaseNum(int caseNum) {
            this.caseNum = caseNum;
        }

        public String getFaultDesc() {
            return faultDesc;
        }

        public void setFaultDesc(String faultDesc) {
            this.faultDesc = faultDesc;
        }

        public Object getIdeaProcess() {
            return ideaProcess;
        }

        public void setIdeaProcess(Object ideaProcess) {
            this.ideaProcess = ideaProcess;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getCarOwnerName() {
            return carOwnerName;
        }

        public void setCarOwnerName(String carOwnerName) {
            this.carOwnerName = carOwnerName;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
        }

        public String getCybAuth() {
            return cybAuth;
        }

        public void setCybAuth(String cybAuth) {
            this.cybAuth = cybAuth;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getTechniciantype() {
            return techniciantype;
        }

        public void setTechniciantype(int techniciantype) {
            this.techniciantype = techniciantype;
        }

        public String getTechnicianUuid() {
            return technicianUuid;
        }

        public void setTechnicianUuid(String technicianUuid) {
            this.technicianUuid = technicianUuid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
