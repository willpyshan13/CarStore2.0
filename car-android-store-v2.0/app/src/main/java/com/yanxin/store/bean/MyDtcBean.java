package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class MyDtcBean extends BaseBean {

    private String code;
    private String msg;
    private ArrayList<DataDTO> data;
    private Integer size;
    private Integer total;
    private Integer pages;
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class DataDTO {
        private String uuid;
        private String dtcUuid;
        private String dtcCode;
        private String dtcCode2;
        private String dtcCode3;
        private String orderNum;
        private String dtcBrandUuidName;
        private float orderAmount;
        private Integer payType;
        private Integer orderSts;
        private Integer readCount;
        private String buyerUuid;
        private String buyerName;
        private String buyerMobile;
        private Integer dtcIssuerType;
        private String createdTime;
        private String dtcExplain;
        private int dtcCheckSts;
        private String dtcDefinition;
        private int dtcType;

        public int getDtcCheckSts() {
            return dtcCheckSts;
        }

        public void setDtcCheckSts(int dtcCheckSts) {
            this.dtcCheckSts = dtcCheckSts;
        }

        public String getDtcDefinition() {
            return dtcDefinition;
        }

        public void setDtcDefinition(String dtcDefinition) {
            this.dtcDefinition = dtcDefinition;
        }

        public String getDtcUuid() {
            return dtcUuid;
        }

        public void setDtcUuid(String dtcUuid) {
            this.dtcUuid = dtcUuid;
        }

        public String getDtcBrandUuidName() {
            return dtcBrandUuidName;
        }

        public void setDtcBrandUuidName(String dtcBrandUuidName) {
            this.dtcBrandUuidName = dtcBrandUuidName;
        }

        public String getDtcCode2() {
            return dtcCode2 == null ? "" : dtcCode2;
        }

        public void setDtcCode2(String dtcCode2) {
            this.dtcCode2 = dtcCode2;
        }

        public String getDtcCode3() {
            return dtcCode3 == null ? "" : dtcCode3;
        }

        public void setDtcCode3(String dtcCode3) {
            this.dtcCode3 = dtcCode3;
        }

        public int getDtcType() {
            return dtcType;
        }

        public void setDtcType(int dtcType) {
            this.dtcType = dtcType;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getDtcCode() {
            return dtcCode;
        }

        public void setDtcCode(String dtcCode) {
            this.dtcCode = dtcCode;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getOrderAmount() {
            return BasicUtils.floatDecimalFormat(orderAmount);
        }

        public void setOrderAmount(float orderAmount) {
            this.orderAmount = orderAmount;
        }

        public Integer getPayType() {
            return payType;
        }

        public void setPayType(Integer payType) {
            this.payType = payType;
        }

        public Integer getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(Integer orderSts) {
            this.orderSts = orderSts;
        }

        public Integer getReadCount() {
            return readCount;
        }

        public void setReadCount(Integer readCount) {
            this.readCount = readCount;
        }

        public String getBuyerUuid() {
            return buyerUuid;
        }

        public void setBuyerUuid(String buyerUuid) {
            this.buyerUuid = buyerUuid;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getBuyerMobile() {
            return buyerMobile;
        }

        public void setBuyerMobile(String buyerMobile) {
            this.buyerMobile = buyerMobile;
        }

        public Integer getDtcIssuerType() {
            return dtcIssuerType;
        }

        public void setDtcIssuerType(Integer dtcIssuerType) {
            this.dtcIssuerType = dtcIssuerType;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getDtcExplain() {
            return dtcExplain;
        }

        public void setDtcExplain(String dtcExplain) {
            this.dtcExplain = dtcExplain;
        }
    }
}
