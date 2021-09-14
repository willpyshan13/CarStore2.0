package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;

public class CaseVehicleBean extends BaseBean {

    /**
     * code : 0000
     * data : [{"amt":0,"carOwnertype":0,"cybAuth":"string","imgUrl":"string","score":"string","technicianName":"string","technicianUuid":"string","title":"string","uuid":"string","workingYear":0}]
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
         * amt : 0
         * carOwnertype : 0
         * cybAuth : string
         * imgUrl : string
         * score : string
         * technicianName : string
         * technicianUuid : string
         * title : string
         * uuid : string
         * workingYear : 0
         * purchaseNumber
         */

        private float amt;
        private int technicianType;
        private String cybAuth;
        private String imgUrl;
        private String score;
        private String technicianName;
        private String technicianUuid;
        private String title;
        private int purchaseNumber;
        private String uuid;
        private int workingYear;

        public int getPurchaseNumber() {
            return purchaseNumber;
        }

        public void setPurchaseNumber(int purchaseNumber) {
            this.purchaseNumber = purchaseNumber;
        }

        public String getAmt() {
            return BasicUtils.floatDecimalFormat(amt);
        }

        public void setAmt(float amt) {
            this.amt = amt;
        }

        public int getTechnicianType() {
            return technicianType;
        }

        public void setTechnicianType(int technicianType) {
            this.technicianType = technicianType;
        }

        public String getCybAuth() {
            return cybAuth;
        }

        public void setCybAuth(String cybAuth) {
            this.cybAuth = cybAuth;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
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

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getWorkingYear() {
            return workingYear;
        }

        public void setWorkingYear(int workingYear) {
            this.workingYear = workingYear;
        }
    }
}
