package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

public class SiteFeeBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : {"uuid":"6206","lableType":"basic_set","lableTypeDesc":"基础设置","lableCode":"val6","lableValue":"6","lableDesc":"现场支持费用设置->统一基础上门费","lableDescEn":"basic_set_val6","sortNum":0}
     * success : true
     */

    private String code;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * uuid : 6206
         * lableType : basic_set
         * lableTypeDesc : 基础设置
         * lableCode : val6
         * lableValue : 6
         * lableDesc : 现场支持费用设置->统一基础上门费
         * lableDescEn : basic_set_val6
         * sortNum : 0
         */

        private String uuid;
        private String lableType;
        private String lableTypeDesc;
        private String lableCode;
        private float lableValue;
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

        public float getLableValue() {
            return lableValue;
        }

        public void setLableValue(float lableValue) {
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
