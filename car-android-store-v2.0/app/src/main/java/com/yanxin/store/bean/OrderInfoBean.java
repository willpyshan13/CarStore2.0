package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

public class OrderInfoBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : {"carOwnerName":"牛技师","orderType":1,"evaluateTime":null,"technicianScore":null,"carOwnerImgUrl":"","orderNum":"ZX1419942384564834304","receivableAmount":2,"evaluateSts":0,"uuid":"c0ea90c99e0a4e5d97f25f2d95a32489","carOwnerMobile":"13075801933","vehicleBrand":null,"orderAmount":2,"payType":null,"carOwnerUuid":"13c55c78564641ff980ed3630aec68f7","acceptResult":null,"orderSts":0,"createdTime":1627375620000,"vehicleModel":null,"consultRes":{"orderUuid":null,"consultCheckSts":0,"consultDesc":null,"answerTime":null,"answerDesc":null,"carOwnerImgUrl":"","title":"提问让大家抢答","uuid":"332b2446062c48f4a8cfa23da0266a91","imgurl":null,"vehicleBrand":"b39859f1ac95486aa546f88b07ae5fc1","carOwnerUuid":"13c55c78564641ff980ed3630aec68f7","technicianMobile":null,"technicianUuid":null,"orderSts":null,"createdTime":"2021-07-27 16:47:00","vehicleModel":"834cfb7c8bc4457ea5eb8f70c5befa66","carOwnerName":"牛技师","consultImgList":null,"consultType":2,"answerSts":0,"technicianName":null,"yesOrNo":null,"carOwnerMobile":"13075801933","answerCheckSts":0,"consultAmt":null,"answerImgList":null,"technicianImgUrl":null}}
     * success : true
     */

    private String code;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * carOwnerName : 牛技师
         * orderType : 1
         * evaluateTime : null
         * technicianScore : null
         * carOwnerImgUrl :
         * orderNum : ZX1419942384564834304
         * receivableAmount : 2
         * evaluateSts : 0
         * uuid : c0ea90c99e0a4e5d97f25f2d95a32489
         * carOwnerMobile : 13075801933
         * vehicleBrand : null
         * orderAmount : 2
         * payType : null
         * carOwnerUuid : 13c55c78564641ff980ed3630aec68f7
         * acceptResult : null
         * orderSts : 0
         * createdTime : 1627375620000
         * vehicleModel : null
         * consultRes : {"orderUuid":null,"consultCheckSts":0,"consultDesc":null,"answerTime":null,"answerDesc":null,"carOwnerImgUrl":"","title":"提问让大家抢答","uuid":"332b2446062c48f4a8cfa23da0266a91","imgurl":null,"vehicleBrand":"b39859f1ac95486aa546f88b07ae5fc1","carOwnerUuid":"13c55c78564641ff980ed3630aec68f7","technicianMobile":null,"technicianUuid":null,"orderSts":null,"createdTime":"2021-07-27 16:47:00","vehicleModel":"834cfb7c8bc4457ea5eb8f70c5befa66","carOwnerName":"牛技师","consultImgList":null,"consultType":2,"answerSts":0,"technicianName":null,"yesOrNo":null,"carOwnerMobile":"13075801933","answerCheckSts":0,"consultAmt":null,"answerImgList":null,"technicianImgUrl":null}
         */

        private String carOwnerName;
        private int orderType;
        private Object evaluateTime;
        private Object technicianScore;
        private String carOwnerImgUrl;
        private String orderNum;
        private int receivableAmount;
        private int evaluateSts;
        private String uuid;
        private String carOwnerMobile;
        private Object vehicleBrand;
        private float orderAmount;
        private Object payType;
        private String carOwnerUuid;
        private Object acceptResult;
        private int orderSts;
        private long createdTime;
        private Object vehicleModel;
        private ConsultResBean consultRes;

        public String getCarOwnerName() {
            return carOwnerName;
        }

        public void setCarOwnerName(String carOwnerName) {
            this.carOwnerName = carOwnerName;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public Object getEvaluateTime() {
            return evaluateTime;
        }

        public void setEvaluateTime(Object evaluateTime) {
            this.evaluateTime = evaluateTime;
        }

        public Object getTechnicianScore() {
            return technicianScore;
        }

        public void setTechnicianScore(Object technicianScore) {
            this.technicianScore = technicianScore;
        }

        public String getCarOwnerImgUrl() {
            return carOwnerImgUrl;
        }

        public void setCarOwnerImgUrl(String carOwnerImgUrl) {
            this.carOwnerImgUrl = carOwnerImgUrl;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getReceivableAmount() {
            return receivableAmount;
        }

        public void setReceivableAmount(int receivableAmount) {
            this.receivableAmount = receivableAmount;
        }

        public int getEvaluateSts() {
            return evaluateSts;
        }

        public void setEvaluateSts(int evaluateSts) {
            this.evaluateSts = evaluateSts;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getCarOwnerMobile() {
            return carOwnerMobile;
        }

        public void setCarOwnerMobile(String carOwnerMobile) {
            this.carOwnerMobile = carOwnerMobile;
        }

        public Object getVehicleBrand() {
            return vehicleBrand;
        }

        public void setVehicleBrand(Object vehicleBrand) {
            this.vehicleBrand = vehicleBrand;
        }

        public String getOrderAmount() {
            return BasicUtils.floatDecimalFormat(orderAmount);
        }

        public void setOrderAmount(float orderAmount) {
            this.orderAmount = orderAmount;
        }

        public Object getPayType() {
            return payType;
        }

        public void setPayType(Object payType) {
            this.payType = payType;
        }

        public String getCarOwnerUuid() {
            return carOwnerUuid;
        }

        public void setCarOwnerUuid(String carOwnerUuid) {
            this.carOwnerUuid = carOwnerUuid;
        }

        public Object getAcceptResult() {
            return acceptResult;
        }

        public void setAcceptResult(Object acceptResult) {
            this.acceptResult = acceptResult;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(long createdTime) {
            this.createdTime = createdTime;
        }

        public Object getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(Object vehicleModel) {
            this.vehicleModel = vehicleModel;
        }

        public ConsultResBean getConsultRes() {
            return consultRes;
        }

        public void setConsultRes(ConsultResBean consultRes) {
            this.consultRes = consultRes;
        }

        public static class ConsultResBean {
            /**
             * orderUuid : null
             * consultCheckSts : 0
             * consultDesc : null
             * answerTime : null
             * answerDesc : null
             * carOwnerImgUrl :
             * title : 提问让大家抢答
             * uuid : 332b2446062c48f4a8cfa23da0266a91
             * imgurl : null
             * vehicleBrand : b39859f1ac95486aa546f88b07ae5fc1
             * carOwnerUuid : 13c55c78564641ff980ed3630aec68f7
             * technicianMobile : null
             * technicianUuid : null
             * orderSts : null
             * createdTime : 2021-07-27 16:47:00
             * vehicleModel : 834cfb7c8bc4457ea5eb8f70c5befa66
             * carOwnerName : 牛技师
             * consultImgList : null
             * consultType : 2
             * answerSts : 0
             * technicianName : null
             * yesOrNo : null
             * carOwnerMobile : 13075801933
             * answerCheckSts : 0
             * consultAmt : null
             * answerImgList : null
             * technicianImgUrl : null
             */

            private Object orderUuid;
            private int consultCheckSts;
            private Object consultDesc;
            private Object answerTime;
            private Object answerDesc;
            private String carOwnerImgUrl;
            private String title;
            private String uuid;
            private Object imgurl;
            private String vehicleBrand;
            private String carOwnerUuid;
            private Object technicianMobile;
            private Object technicianUuid;
            private Object orderSts;
            private String createdTime;
            private String vehicleModel;
            private String carOwnerName;
            private Object consultImgList;
            private int consultType;
            private int answerSts;
            private Object technicianName;
            private Object yesOrNo;
            private String carOwnerMobile;
            private int answerCheckSts;
            private Object consultAmt;
            private Object answerImgList;
            private Object technicianImgUrl;

            public Object getOrderUuid() {
                return orderUuid;
            }

            public void setOrderUuid(Object orderUuid) {
                this.orderUuid = orderUuid;
            }

            public int getConsultCheckSts() {
                return consultCheckSts;
            }

            public void setConsultCheckSts(int consultCheckSts) {
                this.consultCheckSts = consultCheckSts;
            }

            public Object getConsultDesc() {
                return consultDesc;
            }

            public void setConsultDesc(Object consultDesc) {
                this.consultDesc = consultDesc;
            }

            public Object getAnswerTime() {
                return answerTime;
            }

            public void setAnswerTime(Object answerTime) {
                this.answerTime = answerTime;
            }

            public Object getAnswerDesc() {
                return answerDesc;
            }

            public void setAnswerDesc(Object answerDesc) {
                this.answerDesc = answerDesc;
            }

            public String getCarOwnerImgUrl() {
                return carOwnerImgUrl;
            }

            public void setCarOwnerImgUrl(String carOwnerImgUrl) {
                this.carOwnerImgUrl = carOwnerImgUrl;
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

            public Object getImgurl() {
                return imgurl;
            }

            public void setImgurl(Object imgurl) {
                this.imgurl = imgurl;
            }

            public String getVehicleBrand() {
                return vehicleBrand;
            }

            public void setVehicleBrand(String vehicleBrand) {
                this.vehicleBrand = vehicleBrand;
            }

            public String getCarOwnerUuid() {
                return carOwnerUuid;
            }

            public void setCarOwnerUuid(String carOwnerUuid) {
                this.carOwnerUuid = carOwnerUuid;
            }

            public Object getTechnicianMobile() {
                return technicianMobile;
            }

            public void setTechnicianMobile(Object technicianMobile) {
                this.technicianMobile = technicianMobile;
            }

            public Object getTechnicianUuid() {
                return technicianUuid;
            }

            public void setTechnicianUuid(Object technicianUuid) {
                this.technicianUuid = technicianUuid;
            }

            public Object getOrderSts() {
                return orderSts;
            }

            public void setOrderSts(Object orderSts) {
                this.orderSts = orderSts;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public String getVehicleModel() {
                return vehicleModel;
            }

            public void setVehicleModel(String vehicleModel) {
                this.vehicleModel = vehicleModel;
            }

            public String getCarOwnerName() {
                return carOwnerName;
            }

            public void setCarOwnerName(String carOwnerName) {
                this.carOwnerName = carOwnerName;
            }

            public Object getConsultImgList() {
                return consultImgList;
            }

            public void setConsultImgList(Object consultImgList) {
                this.consultImgList = consultImgList;
            }

            public int getConsultType() {
                return consultType;
            }

            public void setConsultType(int consultType) {
                this.consultType = consultType;
            }

            public int getAnswerSts() {
                return answerSts;
            }

            public void setAnswerSts(int answerSts) {
                this.answerSts = answerSts;
            }

            public Object getTechnicianName() {
                return technicianName;
            }

            public void setTechnicianName(Object technicianName) {
                this.technicianName = technicianName;
            }

            public Object getYesOrNo() {
                return yesOrNo;
            }

            public void setYesOrNo(Object yesOrNo) {
                this.yesOrNo = yesOrNo;
            }

            public String getCarOwnerMobile() {
                return carOwnerMobile;
            }

            public void setCarOwnerMobile(String carOwnerMobile) {
                this.carOwnerMobile = carOwnerMobile;
            }

            public int getAnswerCheckSts() {
                return answerCheckSts;
            }

            public void setAnswerCheckSts(int answerCheckSts) {
                this.answerCheckSts = answerCheckSts;
            }

            public Object getConsultAmt() {
                return consultAmt;
            }

            public void setConsultAmt(Object consultAmt) {
                this.consultAmt = consultAmt;
            }

            public Object getAnswerImgList() {
                return answerImgList;
            }

            public void setAnswerImgList(Object answerImgList) {
                this.answerImgList = answerImgList;
            }

            public Object getTechnicianImgUrl() {
                return technicianImgUrl;
            }

            public void setTechnicianImgUrl(Object technicianImgUrl) {
                this.technicianImgUrl = technicianImgUrl;
            }
        }
    }
}
