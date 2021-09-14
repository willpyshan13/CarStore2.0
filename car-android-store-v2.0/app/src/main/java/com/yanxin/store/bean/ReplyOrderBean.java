package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class ReplyOrderBean extends BaseBean {

    /**
     * code : 0000
     * data : [{"answerSts":0,"carOwnerMobile":"string","carOwnerName":"string","consultUuid":"string","createdTime":"string","imgType":"string","imgUrl":["string"],"orderAmount":0,"orderSts":0,"orderType":0,"payType":0,"technicianMobile":"string","technicianName":"string","title":"string","uuid":"string","vehicleBrand":"string","vehicleModel":"string"}]
     * msg : 操作成功
     * pages : 10
     * size : 10
     * success : true
     * total : 100
     */

    private String code;
    private ArrayList<DataBean> data;
    private String msg;
    private int pages;
    private int size;
    private boolean success;
    private int total;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
        /**
         * answerSts : 0
         * carOwnerMobile : string
         * carOwnerName : string
         * consultUuid : string
         * createdTime : string
         * imgType : string
         * imgUrl : ["string"]
         * orderAmount : 0
         * orderSts : 0
         * orderType : 0
         * payType : 0
         * technicianMobile : string
         * technicianName : string
         * title : string
         * uuid : string
         * vehicleBrand : string
         * vehicleModel : string
         */

        private int answerSts;
        private String carOwnerMobile;
        private String carOwnerName;
        private String consultUuid;
        private String createdTime;
        private String imgType;
        private ArrayList<String> imgUrl;
        private float orderAmount;
        private int orderSts;
        private int orderType;
        private int payType;
        private String technicianMobile;
        private String technicianName;
        private String title;
        private String uuid;
        private String vehicleBrand;
        private String vehicleModel;

        public int getAnswerSts() {
            return answerSts;
        }

        public void setAnswerSts(int answerSts) {
            this.answerSts = answerSts;
        }

        public String getCarOwnerMobile() {
            return carOwnerMobile;
        }

        public void setCarOwnerMobile(String carOwnerMobile) {
            this.carOwnerMobile = carOwnerMobile;
        }

        public String getCarOwnerName() {
            return carOwnerName;
        }

        public void setCarOwnerName(String carOwnerName) {
            this.carOwnerName = carOwnerName;
        }

        public String getConsultUuid() {
            return consultUuid;
        }

        public void setConsultUuid(String consultUuid) {
            this.consultUuid = consultUuid;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getImgType() {
            return imgType;
        }

        public void setImgType(String imgType) {
            this.imgType = imgType;
        }

        public ArrayList<String> getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(ArrayList<String> imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getOrderAmount() {
            return BasicUtils.floatDecimalFormat(orderAmount);
        }

        public void setOrderAmount(float orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getTechnicianMobile() {
            return technicianMobile;
        }

        public void setTechnicianMobile(String technicianMobile) {
            this.technicianMobile = technicianMobile;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
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
