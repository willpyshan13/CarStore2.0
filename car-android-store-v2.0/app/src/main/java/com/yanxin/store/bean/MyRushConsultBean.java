package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class MyRushConsultBean extends BaseBean {

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
        private String consultUuid;
        private String title;
        private String carOwnerName;
        private String carOwnerMobile;
        private String createdTime;
        private String technicianName;
        private String technicianMobile;
        private Integer orderType;
        private float orderAmount;
        private int payType;
        private Integer orderSts;
        private Integer answerSts;
        private String vehicleBrand;
        private String vehicleModel;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getConsultUuid() {
            return consultUuid;
        }

        public void setConsultUuid(String consultUuid) {
            this.consultUuid = consultUuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCarOwnerName() {
            return carOwnerName;
        }

        public void setCarOwnerName(String carOwnerName) {
            this.carOwnerName = carOwnerName;
        }

        public String getCarOwnerMobile() {
            return carOwnerMobile;
        }

        public void setCarOwnerMobile(String carOwnerMobile) {
            this.carOwnerMobile = carOwnerMobile;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
        }

        public String getTechnicianMobile() {
            return technicianMobile;
        }

        public void setTechnicianMobile(String technicianMobile) {
            this.technicianMobile = technicianMobile;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public String getOrderAmount() {
            return BasicUtils.floatDecimalFormat(orderAmount);
        }

        public void setOrderAmount(float orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public Integer getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(Integer orderSts) {
            this.orderSts = orderSts;
        }

        public Integer getAnswerSts() {
            return answerSts;
        }

        public void setAnswerSts(Integer answerSts) {
            this.answerSts = answerSts;
        }

        public String getVehicleBrand() {
            return vehicleBrand;
        }

        public void setVehicleBrand(String vehicleBrand) {
            this.vehicleBrand = vehicleBrand;
        }

        public String getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
        }
    }
}
