package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class BrandBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"ba0628f9d98a44b98698f80a05abe7d0","configName":"ARCFOX极狐","parentCode":"0001","configType":"2","sortNum":1},{"uuid":"98aea6bcd2674b3190e1dd71062ee96f","configName":"AUXUN傲旋","parentCode":"0001","configType":"2","sortNum":2},{"uuid":"9a79a6b6436649ae8e22be368a27cc14","configName":"阿尔法·罗密欧","parentCode":"0001","configType":"2","sortNum":3},{"uuid":"22f72f047035447db228cd05acec439e","configName":"阿斯顿·马丁","parentCode":"0001","configType":"2","sortNum":4},{"uuid":"ef6108cb717c477f889733c678cbc37e","configName":"爱驰","parentCode":"0001","configType":"2","sortNum":5},{"uuid":"b144fcd919de4154a289717dfe20a1c0","configName":"奥迪","parentCode":"0001","configType":"2","sortNum":6},{"uuid":"706b29bba3b649b9bcb82df4d7d454d6","configName":"BEIJING汽车","parentCode":"0001","configType":"2","sortNum":7},{"uuid":"c5cd601591d14d438786447341e54bf2","configName":"巴博斯","parentCode":"0001","configType":"2","sortNum":8},{"uuid":"eab5bf1376654d5283b3c7007ece058b","configName":"宝骏/上汽通用五菱","parentCode":"0001","configType":"2","sortNum":9},{"uuid":"3a2a5e60289d4372933dcac4d75ec3ef","configName":"华晨宝马","parentCode":"0001","configType":"2","sortNum":10},{"uuid":"809273b92fad4e10988a7eb7694b36e0","configName":"宝骐汽车","parentCode":"0001","configType":"2","sortNum":11},{"uuid":"6e8adb2116a8407298211f6c549ca4fb","configName":" 宝沃汽车","parentCode":"0001","configType":"2","sortNum":12},{"uuid":"f5b2f2da5bc6445ea2e448c6859b932f","configName":"保时捷","parentCode":"0001","configType":"2","sortNum":13},{"uuid":"7a626a882c354eb59d3da3cb79f29eae","configName":"北京","parentCode":"0001","configType":"2","sortNum":14}]
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

    public static class DataBean extends BaseBean {
        /**
         * uuid : ba0628f9d98a44b98698f80a05abe7d0
         * configName : ARCFOX极狐
         * parentCode : 0001
         * configType : 2
         * sortNum : 1
         */

        private String uuid;
        private String configName;
        private String parentCode;
        private String configType;
        private int sortNum;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getConfigType() {
            return configType;
        }

        public void setConfigType(String configType) {
            this.configType = configType;
        }

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }
    }
}
