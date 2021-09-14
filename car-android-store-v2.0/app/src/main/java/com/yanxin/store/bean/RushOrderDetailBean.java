package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class RushOrderDetailBean extends BaseBean {

    /**
     * code : 0000
     * data : {"acceptResult":0,"answerCheckSts":0,"brandName":"string","carOwnerImgUrl":"string","carOwnerMobile":"string","carOwnerName":"string","carOwnerUuid":"string","consultCheckSts":0,"consultRes":{"answerCheckSts":0,"answerDesc":"string","answerImgList":["string"],"answerSts":0,"answerTime":"2021-08-13T03:11:20.198Z","carOwnerImgUrl":"string","carOwnerMobile":"string","carOwnerName":"string","carOwnerUuid":"string","consultAmt":0,"consultCheckSts":0,"consultDesc":"string","consultImgList":["string"],"consultType":0,"createdTime":"string","imgurl":["string"],"orderSts":0,"orderUuid":"string","technicalTypeName":"string","technicalTypeUuid":"string","technicianImgUrl":"string","technicianMobile":"string","technicianName":"string","technicianUuid":"string","title":"string","uuid":"string","vehicleBrand":"string","vehicleBrandName":"string","vehicleModel":"string","vehicleModelName":"string","yesOrNo":"string"},"createdTime":"2021-08-13T03:11:20.198Z","evaluateSts":0,"evaluateTime":"2021-08-13T03:11:20.198Z","modelName":"string","orderAmount":0,"orderNum":"string","orderSts":0,"payType":0,"receivableAmount":0,"technicianScore":0,"uuid":"string","vehicleBrand":"string","vehicleModel":"string"}
     * msg : 操作成功
     * success : true
     */

    private String code;
    private DataBean data;
    private String msg;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * acceptResult : 0
         * answerCheckSts : 0
         * brandName : string
         * carOwnerImgUrl : string
         * carOwnerMobile : string
         * carOwnerName : string
         * carOwnerUuid : string
         * consultCheckSts : 0
         * consultRes : {"answerCheckSts":0,"answerDesc":"string","answerImgList":["string"],"answerSts":0,"answerTime":"2021-08-13T03:11:20.198Z","carOwnerImgUrl":"string","carOwnerMobile":"string","carOwnerName":"string","carOwnerUuid":"string","consultAmt":0,"consultCheckSts":0,"consultDesc":"string","consultImgList":["string"],"consultType":0,"createdTime":"string","imgurl":["string"],"orderSts":0,"orderUuid":"string","technicalTypeName":"string","technicalTypeUuid":"string","technicianImgUrl":"string","technicianMobile":"string","technicianName":"string","technicianUuid":"string","title":"string","uuid":"string","vehicleBrand":"string","vehicleBrandName":"string","vehicleModel":"string","vehicleModelName":"string","yesOrNo":"string"}
         * createdTime : 2021-08-13T03:11:20.198Z
         * evaluateSts : 0
         * evaluateTime : 2021-08-13T03:11:20.198Z
         * modelName : string
         * orderAmount : 0
         * orderNum : string
         * orderSts : 0
         * payType : 0
         * receivableAmount : 0
         * technicianScore : 0
         * uuid : string
         * vehicleBrand : string
         * vehicleModel : string
         */

        private int acceptResult;
        private int answerCheckSts;
        private ArrayList<String> brandList;
        private String brandName;
        private String carOwnerImgUrl;
        private String carOwnerMobile;
        private String carOwnerName;
        private String carOwnerUuid;
        private int consultCheckSts;
        private ConsultResBean consultRes;
        private String createdTime;
        private int evaluateSts;
        private String evaluateTime;
        private String modelName;
        private float orderAmount;
        private String orderNum;
        private int orderSts;
        private int payType;
        private float receivableAmount;
        private float technicianScore;
        private String uuid;
        private String vehicleBrand;
        private String vehicleModel;
        private int byConsultNumber;

        public int getByConsultNumber() {
            return byConsultNumber;
        }

        public void setByConsultNumber(int byConsultNumber) {
            this.byConsultNumber = byConsultNumber;
        }

        public ArrayList<String> getBrandList() {
            return brandList;
        }

        public void setBrandList(ArrayList<String> brandList) {
            this.brandList = brandList;
        }

        public int getAcceptResult() {
            return acceptResult;
        }

        public void setAcceptResult(int acceptResult) {
            this.acceptResult = acceptResult;
        }

        public int getAnswerCheckSts() {
            return answerCheckSts;
        }

        public void setAnswerCheckSts(int answerCheckSts) {
            this.answerCheckSts = answerCheckSts;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
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

        public int getConsultCheckSts() {
            return consultCheckSts;
        }

        public void setConsultCheckSts(int consultCheckSts) {
            this.consultCheckSts = consultCheckSts;
        }

        public ConsultResBean getConsultRes() {
            return consultRes;
        }

        public void setConsultRes(ConsultResBean consultRes) {
            this.consultRes = consultRes;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getEvaluateSts() {
            return evaluateSts;
        }

        public void setEvaluateSts(int evaluateSts) {
            this.evaluateSts = evaluateSts;
        }

        public String getEvaluateTime() {
            return evaluateTime;
        }

        public void setEvaluateTime(String evaluateTime) {
            this.evaluateTime = evaluateTime;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getOrderAmount() {
            return BasicUtils.floatDecimalFormat(orderAmount);
        }

        public void setOrderAmount(float orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getReceivableAmount() {
            return BasicUtils.floatDecimalFormat(receivableAmount);
        }

        public void setReceivableAmount(float receivableAmount) {
            this.receivableAmount = receivableAmount;
        }

        public float getTechnicianScore() {
            return technicianScore;
        }

        public void setTechnicianScore(float technicianScore) {
            this.technicianScore = technicianScore;
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

        public static class ConsultResBean {
            /**
             * answerCheckSts : 0
             * answerDesc : string
             * answerImgList : ["string"]
             * answerSts : 0
             * answerTime : 2021-08-13T03:11:20.198Z
             * carOwnerImgUrl : string
             * carOwnerMobile : string
             * carOwnerName : string
             * carOwnerUuid : string
             * consultAmt : 0
             * consultCheckSts : 0
             * consultDesc : string
             * consultImgList : ["string"]
             * consultType : 0
             * createdTime : string
             * imgurl : ["string"]
             * orderSts : 0
             * orderUuid : string
             * technicalTypeName : string
             * technicalTypeUuid : string
             * technicianImgUrl : string
             * technicianMobile : string
             * technicianName : string
             * technicianUuid : string
             * title : string
             * uuid : string
             * vehicleBrand : string
             * vehicleBrandName : string
             * vehicleModel : string
             * vehicleModelName : string
             * yesOrNo : string
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
            private String technicalTypeName;
            private String technicalTypeUuid;
            private String technicianImgUrl;
            private String technicianMobile;
            private String technicianName;
            private String technicianUuid;
            private String title;
            private String uuid;
            private String vehicleBrand;
            private String vehicleBrandName;
            private String vehicleModel;
            private String vehicleModelName;
            private String yesOrNo;

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

            public String getTechnicalTypeName() {
                return technicalTypeName;
            }

            public void setTechnicalTypeName(String technicalTypeName) {
                this.technicalTypeName = technicalTypeName;
            }

            public String getTechnicalTypeUuid() {
                return technicalTypeUuid;
            }

            public void setTechnicalTypeUuid(String technicalTypeUuid) {
                this.technicalTypeUuid = technicalTypeUuid;
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

            public String getVehicleBrandName() {
                return vehicleBrandName;
            }

            public void setVehicleBrandName(String vehicleBrandName) {
                this.vehicleBrandName = vehicleBrandName;
            }

            public String getVehicleModel() {
                return vehicleModel;
            }

            public void setVehicleModel(String vehicleModel) {
                this.vehicleModel = vehicleModel;
            }

            public String getVehicleModelName() {
                return vehicleModelName;
            }

            public void setVehicleModelName(String vehicleModelName) {
                this.vehicleModelName = vehicleModelName;
            }

            public String getYesOrNo() {
                return yesOrNo;
            }

            public void setYesOrNo(String yesOrNo) {
                this.yesOrNo = yesOrNo;
            }
        }
    }
}
