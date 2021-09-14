package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class DtcTypeBean extends BaseBean {

    private String code;
    private String msg;
    private ArrayList<DataDTO> data;
    private Boolean success;

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

    public ArrayList<DataDTO> getData() {
        return data;
    }

    public void setData(ArrayList<DataDTO> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class DataDTO {
        private String uuid;
        private String lableType;
        private String lableTypeDesc;
        private String lableCode;
        private String lableValue;
        private String lableDesc;
        private String lableDescEn;
        private Integer sortNum;

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

        public Integer getSortNum() {
            return sortNum;
        }

        public void setSortNum(Integer sortNum) {
            this.sortNum = sortNum;
        }
    }
}
