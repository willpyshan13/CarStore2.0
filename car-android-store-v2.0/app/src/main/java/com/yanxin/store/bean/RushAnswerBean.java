package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class RushAnswerBean extends BaseBean {

    /**
     * code : 0000
     * data : [{"answerCheckSts":0,"answerDesc":"string","answerImgList":["string"],"answerSts":0,"answerTime":"2021-07-23T10:46:11.424Z","carOwnerImgUrl":"string","carOwnerMobile":"string","carOwnerName":"string","carOwnerUuid":"string","consultAmt":0,"consultCheckSts":0,"consultDesc":"string","consultImgList":["string"],"consultType":0,"createdTime":"string","imgurl":["string"],"orderSts":0,"orderUuid":"string","technicianImgUrl":"string","technicianMobile":"string","technicianName":"string","technicianUuid":"string","title":"string","uuid":"string","vehicleBrand":"string","vehicleModel":"string","yesOrNo":"string"}]
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
         * answerCheckSts (integer, optional): 回答审核状态 0 待审核 1 审核通过 2 审核驳回 ,
         * answerDesc (string, optional): 回答描述 ,
         * answerImgList (Array[string], optional): 回答图片列表 ,
         * answerSts (integer, optional): 答复状态 0 未答复 1 已答复 ,
         * answerTime (string, optional): 答复时间 ,
         * carOwnerImgUrl (string, optional): 车主头像 ,
         * carOwnerMobile (string, optional): 车主手机号 ,
         * carOwnerName (string, optional): 车主姓名 ,
         * carOwnerUuid (string, optional): 车主uuid ,
         * consultAmt (number, optional): 咨询金额 ,
         * consultCheckSts (integer, optional): 支咨询审核状态 0 待审核 1 审核通过 2 审核驳回 ,
         * consultDesc (string, optional): 咨询描述 ,
         * consultImgList (Array[string], optional): 咨询图片列表 ,
         * consultType (integer, optional): 咨询类型 1：技师提问 2：全国技师提问 ,
         * createdTime (string, optional): 咨询时间 ,
         * imgurl (Array[string], optional): 图片集 ,
         * orderSts (integer, optional): 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 ,
         * orderUuid (string, optional): 订单uuid ,
         * technicianImgUrl (string, optional): 技师头像 ,
         * technicianMobile (string, optional): 技师手机号 ,
         * technicianName (string, optional): 技师姓名 ,
         * technicianUuid (string, optional): 技师uuid ,
         * title (string, optional): 咨询标题 ,
         * uuid (string, optional): 咨询uuid ,
         * vehicleBrand (string, optional): 车辆品牌 ,
         * vehicleModel (string, optional): 车型类型 ,
         * yesOrNo (string, optional): 1：已经旁听过 2：未被旁听
         */

        private int answerCheckSts;
        private String answerDesc;
        private ArrayList<String> answerImgList;
        private int answerSts;
        private String answerTime;
        private String carOwnerImgUrl;
        private String carOwnerMobile;
        private String carOwnerName;
        private String carOwnerUuid;
        private float consultAmt;
        private int consultCheckSts;
        private String consultDesc;
        private ArrayList<String> consultImgList;
        private int consultType;
        private String createdTime;
        private ArrayList<String> imgurl;
        private int orderSts;
        private String orderUuid;
        private String technicianImgUrl;
        private String technicianMobile;
        private String technicianName;
        private String technicianUuid;
        private String title;
        private String uuid;
        private String vehicleBrand;
        private String vehicleBrandName;
        private String vehicleModelName;
        private String vehicleModel;
        private String yesOrNo;

        public String getVehicleBrandName() {
            return vehicleBrandName;
        }

        public void setVehicleBrandName(String vehicleBrandName) {
            this.vehicleBrandName = vehicleBrandName;
        }

        public String getVehicleModelName() {
            return vehicleModelName;
        }

        public void setVehicleModelName(String vehicleModelName) {
            this.vehicleModelName = vehicleModelName;
        }

        public int getAnswerCheckSts() {
            return answerCheckSts;
        }

        public void setAnswerCheckSts(int answerCheckSts) {
            this.answerCheckSts = answerCheckSts;
        }

        public String getAnswerDesc() {
            return answerDesc;
        }

        public void setAnswerDesc(String answerDesc) {
            this.answerDesc = answerDesc;
        }

        public ArrayList<String> getAnswerImgList() {
            return answerImgList;
        }

        public void setAnswerImgList(ArrayList<String> answerImgList) {
            this.answerImgList = answerImgList;
        }

        public int getAnswerSts() {
            return answerSts;
        }

        public void setAnswerSts(int answerSts) {
            this.answerSts = answerSts;
        }

        public String getAnswerTime() {
            return answerTime;
        }

        public void setAnswerTime(String answerTime) {
            this.answerTime = answerTime;
        }

        public String getCarOwnerImgUrl() {
            return carOwnerImgUrl;
        }

        public void setCarOwnerImgUrl(String carOwnerImgUrl) {
            this.carOwnerImgUrl = carOwnerImgUrl;
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

        public String getCarOwnerUuid() {
            return carOwnerUuid;
        }

        public void setCarOwnerUuid(String carOwnerUuid) {
            this.carOwnerUuid = carOwnerUuid;
        }

        public String getConsultAmt() {
            return BasicUtils.floatDecimalFormat(consultAmt);
        }

        public void setConsultAmt(float consultAmt) {
            this.consultAmt = consultAmt;
        }

        public int getConsultCheckSts() {
            return consultCheckSts;
        }

        public void setConsultCheckSts(int consultCheckSts) {
            this.consultCheckSts = consultCheckSts;
        }

        public String getConsultDesc() {
            return consultDesc;
        }

        public void setConsultDesc(String consultDesc) {
            this.consultDesc = consultDesc;
        }

        public ArrayList<String> getConsultImgList() {
            return consultImgList;
        }

        public void setConsultImgList(ArrayList<String> consultImgList) {
            this.consultImgList = consultImgList;
        }

        public int getConsultType() {
            return consultType;
        }

        public void setConsultType(int consultType) {
            this.consultType = consultType;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public ArrayList<String> getImgurl() {
            return imgurl;
        }

        public void setImgurl(ArrayList<String> imgurl) {
            this.imgurl = imgurl;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public String getOrderUuid() {
            return orderUuid;
        }

        public void setOrderUuid(String orderUuid) {
            this.orderUuid = orderUuid;
        }

        public String getTechnicianImgUrl() {
            return technicianImgUrl;
        }

        public void setTechnicianImgUrl(String technicianImgUrl) {
            this.technicianImgUrl = technicianImgUrl;
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

        public String getTechnicianUuid() {
            return technicianUuid;
        }

        public void setTechnicianUuid(String technicianUuid) {
            this.technicianUuid = technicianUuid;
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

        public String getYesOrNo() {
            return yesOrNo;
        }

        public void setYesOrNo(String yesOrNo) {
            this.yesOrNo = yesOrNo;
        }
    }
}
