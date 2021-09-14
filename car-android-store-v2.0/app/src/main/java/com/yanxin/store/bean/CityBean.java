package com.yanxin.store.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;

public class CityBean {

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
         * uuid : 1
         * areaCode : 110000
         * areaName : 北京
         * areaNameEn : BeiJing
         * areaType : 1
         * cityCode : 010
         * parentCode : -1
         * parentUuid : -1
         * letter : B
         */

        private String uuid;
        private String areaCode;
        private String areaName;
        private String areaNameEn;
        private int areaType;
        private String cityCode;
        private String parentCode;
        private String parentUuid;
        private String letter;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAreaNameEn() {
            return areaNameEn;
        }

        public void setAreaNameEn(String areaNameEn) {
            this.areaNameEn = areaNameEn;
        }

        public int getAreaType() {
            return areaType;
        }

        public void setAreaType(int areaType) {
            this.areaType = areaType;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getParentUuid() {
            return parentUuid;
        }

        public void setParentUuid(String parentUuid) {
            this.parentUuid = parentUuid;
        }

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        @Override
        public String getPickerViewText() {
            return areaName;
        }
    }
}
