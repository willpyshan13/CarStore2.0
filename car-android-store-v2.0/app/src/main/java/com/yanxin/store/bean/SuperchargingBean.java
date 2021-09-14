package com.yanxin.store.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class SuperchargingBean  extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"2001","lableType":"supercharging_system","lableTypeDesc":"增压系统","lableCode":"1","lableValue":"supercharging_system","lableDesc":"无增压","lableDescEn":"No pressurization","sortNum":0},{"uuid":"2002","lableType":"supercharging_system","lableTypeDesc":"增压系统","lableCode":"2","lableValue":"supercharging_system","lableDesc":"涡轮增压","lableDescEn":"Turbo","sortNum":0},{"uuid":"2003","lableType":"supercharging_system","lableTypeDesc":"增压系统","lableCode":"3","lableValue":"supercharging_system","lableDesc":"机械增压","lableDescEn":"Supercharging","sortNum":0},{"uuid":"2004","lableType":"supercharging_system","lableTypeDesc":"增压系统","lableCode":"4","lableValue":"supercharging_system","lableDesc":"电子增压","lableDescEn":"electron charge","sortNum":0},{"uuid":"2005","lableType":"supercharging_system","lableTypeDesc":"增压系统","lableCode":"5","lableValue":"supercharging_system","lableDesc":"组合增压","lableDescEn":"Combined supercharging","sortNum":0}]
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
         * uuid : 2001
         * lableType : supercharging_system
         * lableTypeDesc : 增压系统
         * lableCode : 1
         * lableValue : supercharging_system
         * lableDesc : 无增压
         * lableDescEn : No pressurization
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
