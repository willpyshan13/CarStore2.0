package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class PersonTypeBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"501","lableType":"person_type","lableTypeDesc":"人员类型","lableCode":"1","lableValue":"person_type","lableDesc":"管理员","lableDescEn":"admin","sortNum":0},{"uuid":"502","lableType":"person_type","lableTypeDesc":"人员类型","lableCode":"2","lableValue":"person_type","lableDesc":"技术支持","lableDescEn":"support","sortNum":0},{"uuid":"503","lableType":"person_type","lableTypeDesc":"人员类型","lableCode":"3","lableValue":"person_type","lableDesc":"财务","lableDescEn":"finance","sortNum":0},{"uuid":"504","lableType":"person_type","lableTypeDesc":"人员类型","lableCode":"4","lableValue":"person_type","lableDesc":"零件","lableDescEn":"part","sortNum":0}]
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
         * uuid : 501
         * lableType : person_type
         * lableTypeDesc : 人员类型
         * lableCode : 1
         * lableValue : person_type
         * lableDesc : 管理员
         * lableDescEn : admin
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
