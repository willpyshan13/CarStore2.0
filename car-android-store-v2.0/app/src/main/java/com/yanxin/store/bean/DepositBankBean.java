package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class DepositBankBean extends BaseBean {


    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"613","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"13","lableValue":"deposit_bank","lableDesc":"中国招商银行","lableDescEn":"China Merchants Bank","sortNum":0},{"uuid":"601","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"1","lableValue":"deposit_bank","lableDesc":"中国工商银行","lableDescEn":"ICBC","sortNum":1},{"uuid":"602","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"2","lableValue":"deposit_bank","lableDesc":"平安银行","lableDescEn":"Ping_An_Bank","sortNum":1},{"uuid":"603","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"3","lableValue":"deposit_bank","lableDesc":"中国农业银行","lableDescEn":"Agricultural_Bank_of_China","sortNum":1},{"uuid":"604","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"4","lableValue":"deposit_bank","lableDesc":"中国建设银行","lableDescEn":"CCB","sortNum":1},{"uuid":"605","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"5","lableValue":"deposit_bank","lableDesc":"交通银行","lableDescEn":"Bank_of_Communications","sortNum":1},{"uuid":"606","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"6","lableValue":"deposit_bank","lableDesc":"中信银行","lableDescEn":"China_Citic_Bank","sortNum":1},{"uuid":"607","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"7","lableValue":"deposit_bank","lableDesc":"上海浦东发展银行","lableDescEn":"SPDB","sortNum":1},{"uuid":"608","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"8","lableValue":"deposit_bank","lableDesc":"中国民生银行","lableDescEn":"China_Minsheng_Bank","sortNum":1},{"uuid":"609","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"9","lableValue":"deposit_bank","lableDesc":"中国银行","lableDescEn":"Bank_of_China","sortNum":1},{"uuid":"610","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"10","lableValue":"deposit_bank","lableDesc":"光大银行","lableDescEn":"CEBBANK","sortNum":1},{"uuid":"611","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"11","lableValue":"deposit_bank","lableDesc":"广东发展银行","lableDescEn":"China_Guangfa_Bank","sortNum":1},{"uuid":"612","lableType":"deposit_bank","lableTypeDesc":"开户银行","lableCode":"12","lableValue":"deposit_bank","lableDesc":"其他银行","lableDescEn":"other_bank","sortNum":1}]
     * success : true
     */

    private String code;
    private String msg;
    private ArrayList<DataBean> data;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * uuid : 613
         * lableType : deposit_bank
         * lableTypeDesc : 开户银行
         * lableCode : 13
         * lableValue : deposit_bank
         * lableDesc : 中国招商银行
         * lableDescEn : China Merchants Bank
         * sortNum : 0
         */

        private String uuid;
        private String lableType;
        private String lableTypeDesc;
        private String lableCode;
        private String lableValue;
        private String lableDesc;
        private String lableDescEn;
        private int sortNum;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getLableType() {
            return lableType;
        }

        public void setLableType(String lableType) {
            this.lableType = lableType;
        }

        public String getLableTypeDesc() {
            return lableTypeDesc;
        }

        public void setLableTypeDesc(String lableTypeDesc) {
            this.lableTypeDesc = lableTypeDesc;
        }

        public String getLableCode() {
            return lableCode;
        }

        public void setLableCode(String lableCode) {
            this.lableCode = lableCode;
        }

        public String getLableValue() {
            return lableValue;
        }

        public void setLableValue(String lableValue) {
            this.lableValue = lableValue;
        }

        public String getLableDesc() {
            return lableDesc;
        }

        public void setLableDesc(String lableDesc) {
            this.lableDesc = lableDesc;
        }

        public String getLableDescEn() {
            return lableDescEn;
        }

        public void setLableDescEn(String lableDescEn) {
            this.lableDescEn = lableDescEn;
        }

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }
    }
}
