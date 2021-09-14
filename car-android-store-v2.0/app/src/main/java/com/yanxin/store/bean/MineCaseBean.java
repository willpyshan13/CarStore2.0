package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class MineCaseBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":null,"caseUuid":"4049a58c7a1a496cbec5307d0c2d8c34","title":"叮咚","faultDesc":"想你想你自己","amt":null,"num":null,"profitType":null,"techniciantype":2,"technicianUuid":"6ca94718990c4d4d983f2852e90eef55","userName":"迪嘉","cybAuth":"1","score":"5"},{"uuid":null,"caseUuid":"f1ff9f54d5ad47a58900ab23dd828176","title":"安利","faultDesc":"等你到家多久","amt":null,"num":null,"profitType":null,"techniciantype":2,"technicianUuid":"6ca94718990c4d4d983f2852e90eef55","userName":"迪嘉","cybAuth":"1","score":"5"}]
     * size : 50
     * total : 2
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
         * uuid : null
         * caseUuid : 4049a58c7a1a496cbec5307d0c2d8c34
         * title : 叮咚
         * faultDesc : 想你想你自己
         * amt : null
         * num : null
         * profitType : null
         * techniciantype : 2
         * technicianUuid : 6ca94718990c4d4d983f2852e90eef55
         * userName : 迪嘉
         * cybAuth : 1
         * score : 5
         */

        private String uuid;
        private String caseUuid;
        private String title;
        private String faultDesc;
        private float amt;
        private int num;
        private int profitType;
        private int techniciantype;
        private String technicianUuid;
        private String userName;
        private String cybAuth;
        private String score;
        private int checkStatus;
        private String remarks;

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getCaseUuid() {
            return caseUuid;
        }

        public void setCaseUuid(String caseUuid) {
            this.caseUuid = caseUuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFaultDesc() {
            return faultDesc;
        }

        public void setFaultDesc(String faultDesc) {
            this.faultDesc = faultDesc;
        }

        public String getAmt() {
            return BasicUtils.floatDecimalFormat(amt);
        }

        public void setAmt(float amt) {
            this.amt = amt;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getProfitType() {
            return profitType;
        }

        public void setProfitType(int profitType) {
            this.profitType = profitType;
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
    }
}
