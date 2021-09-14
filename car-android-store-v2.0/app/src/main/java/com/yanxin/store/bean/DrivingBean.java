package com.yanxin.store.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class DrivingBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"1801","lableType":"driving_mode","lableTypeDesc":"驱动方式","lableCode":"1","lableValue":"driving_mode","lableDesc":"前置前驱","lableDescEn":"Front and front","sortNum":0},{"uuid":"1802","lableType":"driving_mode","lableTypeDesc":"驱动方式","lableCode":"2","lableValue":"driving_mode","lableDesc":"前置中驱","lableDescEn":"Front middle drive","sortNum":0},{"uuid":"1803","lableType":"driving_mode","lableTypeDesc":"驱动方式","lableCode":"3","lableValue":"driving_mode","lableDesc":"前置后驱","lableDescEn":"Front and rear drive","sortNum":0},{"uuid":"1804","lableType":"driving_mode","lableTypeDesc":"驱动方式","lableCode":"4","lableValue":"driving_mode","lableDesc":"中置后驱","lableDescEn":"Center rear drive","sortNum":0},{"uuid":"1805","lableType":"driving_mode","lableTypeDesc":"驱动方式","lableCode":"5","lableValue":"driving_mode","lableDesc":"后置后驱","lableDescEn":"Rear drive","sortNum":0},{"uuid":"1806","lableType":"driving_mode","lableTypeDesc":"驱动方式","lableCode":"6","lableValue":"driving_mode","lableDesc":"全时四驱","lableDescEn":"All time 4WD","sortNum":0},{"uuid":"1807","lableType":"driving_mode","lableTypeDesc":"驱动方式","lableCode":"7","lableValue":"driving_mode","lableDesc":"分时四驱","lableDescEn":"Time sharing 4WD","sortNum":0},{"uuid":"1808","lableType":"driving_mode","lableTypeDesc":"驱动方式","lableCode":"8","lableValue":"driving_mode","lableDesc":"其它","lableDescEn":"other","sortNum":0}]
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

    public static class DataBean implements IPickerViewData {
        /**
         * uuid : 1801
         * lableType : driving_mode
         * lableTypeDesc : 驱动方式
         * lableCode : 1
         * lableValue : driving_mode
         * lableDesc : 前置前驱
         * lableDescEn : Front and front
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

        @Override
        public String getPickerViewText() {
            return lableDesc;
        }
    }
}
