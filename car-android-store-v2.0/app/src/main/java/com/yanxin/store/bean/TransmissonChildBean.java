package com.yanxin.store.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class TransmissonChildBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"1731","lableType":"manual_transmission","lableTypeDesc":"手动变速器","lableCode":"1","lableValue":"manual_transmission","lableDesc":"3速手动","lableDescEn":"3-speed manual","sortNum":0},{"uuid":"1732","lableType":"manual_transmission","lableTypeDesc":"手动变速器","lableCode":"2","lableValue":"manual_transmission","lableDesc":"4速手动","lableDescEn":"4-speed manual","sortNum":0},{"uuid":"1733","lableType":"manual_transmission","lableTypeDesc":"手动变速器","lableCode":"3","lableValue":"manual_transmission","lableDesc":"5速手动","lableDescEn":"5-speed manual","sortNum":0},{"uuid":"1734","lableType":"manual_transmission","lableTypeDesc":"手动变速器","lableCode":"4","lableValue":"manual_transmission","lableDesc":"6速手动","lableDescEn":"6-speed manual","sortNum":0},{"uuid":"1735","lableType":"manual_transmission","lableTypeDesc":"手动变速器","lableCode":"5","lableValue":"manual_transmission","lableDesc":"7速手动","lableDescEn":"7-speed manual","sortNum":0},{"uuid":"1736","lableType":"manual_transmission","lableTypeDesc":"手动变速器","lableCode":"6","lableValue":"manual_transmission","lableDesc":"8速手动","lableDescEn":"8-speed manual","sortNum":0}]
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
         * uuid : 1731
         * lableType : manual_transmission
         * lableTypeDesc : 手动变速器
         * lableCode : 1
         * lableValue : manual_transmission
         * lableDesc : 3速手动
         * lableDescEn : 3-speed manual
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
