package com.yanxin.store.bean;

import com.google.gson.annotations.SerializedName;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;

public class SceneInfoBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : {"uuid":"0f1a9ac5e15145b187d727310536b53e","orderNum":"XC1421069874662346752","createdTime":"2021-07-30 19:27:15","payType":null,"issuerUuid":"03f3006d51e34732b3f071d5354444a5","issuerName":"王雯姐","issuerMobile":"13075801933","buyerName":"7fbd5d1e14644a55aec35caa7a9e20bc","brandUuid":"8eaa7310fd0c404aa9c5e2d28f5d4ad4","buyerMobile":"13075801922","brandName":"新特汽车","carModelUuid":"725868f2fcdf4059a1380511cf354276","carModelName":"启能GEV 1","carStyle":"2020","vinCode":"6431646H64YI94","transmissionOneLevel":"自动变速器（手自一体）","transmissionOneLevelUuid":"1702","transmissionTwoLevel":"5速自动","transmissionTwoLevelUuid":"1752","engineDisplacement":"1.3L","engineDisplacementUuid":"1908","drivingMode":"后置后驱","drivingModeUuid":"1805","boosterSystem":"机械增压","boosterSystemUuid":"2003","warrantySts":0,"otherSts":null,"faultDesc":"2003","repairType":"喷涂技师","repairTypeUuid":"1202","alreadyInspect":null,"dtcCode":"P2500,P2505","basicDoorAmount":6,"basicDoorAmountUuid":"6206","orderServiceAmount":0,"orderServiceAmountUuid":null,"totalAmount":6,"serviceDate":"2021-09-30 19:00:00","grabbingDate":null,"payDate":null,"detailedAddr":"上扣扣上课","sceneOrderTechnicianUuid":null,"sceneOrderTechnicianRes":null,"sceneOrderServiceRes":null,"dtcImageList":["https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-07-30/efac6444e9bf4cd1be1fbe2e1476299b.jpg","https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-07-30/ec5fb5e3fbda41958bccb93f2f8c1be6.jpg"],"faultImageList":["https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-07-30/efac6444e9bf4cd1be1fbe2e1476299b.jpg","https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-07-30/ec5fb5e3fbda41958bccb93f2f8c1be6.jpg"],"orderSts":1,"isOneself":true,"grabbingOrdersSts":1}
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
         * uuid : 0f1a9ac5e15145b187d727310536b53e
         * orderNum : XC1421069874662346752
         * createdTime : 2021-07-30 19:27:15
         * payType : null
         * issuerUuid : 03f3006d51e34732b3f071d5354444a5
         * issuerName : 王雯姐
         * issuerMobile : 13075801933
         * buyerName : 7fbd5d1e14644a55aec35caa7a9e20bc
         * brandUuid : 8eaa7310fd0c404aa9c5e2d28f5d4ad4
         * buyerMobile : 13075801922
         * brandName : 新特汽车
         * carModelUuid : 725868f2fcdf4059a1380511cf354276
         * carModelName : 启能GEV 1
         * carStyle : 2020
         * vinCode : 6431646H64YI94
         * transmissionOneLevel : 自动变速器（手自一体）
         * transmissionOneLevelUuid : 1702
         * transmissionTwoLevel : 5速自动
         * transmissionTwoLevelUuid : 1752
         * engineDisplacement : 1.3L
         * engineDisplacementUuid : 1908
         * drivingMode : 后置后驱
         * drivingModeUuid : 1805
         * boosterSystem : 机械增压
         * boosterSystemUuid : 2003
         * warrantySts : 0
         * otherSts : null
         * faultDesc : 2003
         * repairType : 喷涂技师
         * repairTypeUuid : 1202
         * alreadyInspect : null
         * dtcCode : P2500,P2505
         * basicDoorAmount : 6
         * basicDoorAmountUuid : 6206
         * orderServiceAmount : 0
         * orderServiceAmountUuid : null
         * totalAmount : 6
         * serviceDate : 2021-09-30 19:00:00
         * grabbingDate : null
         * payDate : null
         * detailedAddr : 上扣扣上课
         * sceneOrderTechnicianUuid : null
         * sceneOrderTechnicianRes : null
         * sceneOrderServiceRes : null
         * dtcImageList : ["https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-07-30/efac6444e9bf4cd1be1fbe2e1476299b.jpg","https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-07-30/ec5fb5e3fbda41958bccb93f2f8c1be6.jpg"]
         * faultImageList : ["https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-07-30/efac6444e9bf4cd1be1fbe2e1476299b.jpg","https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-07-30/ec5fb5e3fbda41958bccb93f2f8c1be6.jpg"]
         * orderSts : 1
         * isOneself : true
         * grabbingOrdersSts : 1
         * lastUpdatedTime
         */

        private String uuid;
        private String orderNum;
        private String createdTime;
        private int payType;
        private String issuerUuid;
        private String issuerName;
        private String issuerMobile;
        private String buyerName;
        private String brandUuid;
        private String buyerMobile;
        private String brandName;
        private String carModelUuid;
        private String carModelName;
        private String carStyle;
        private String vinCode;
        private String transmissionOneLevel;
        private String transmissionOneLevelUuid;
        private String transmissionTwoLevel;
        private String transmissionTwoLevelUuid;
        private String engineDisplacement;
        private String engineDisplacementUuid;
        private String drivingMode;
        private String drivingModeUuid;
        private String boosterSystem;
        private String boosterSystemUuid;
        private int warrantySts;
        private String otherSts;
        private String faultDesc;
        private String repairType;
        private String repairTypeUuid;
        private String alreadyInspect;
        private String dtcCode;
        private float basicDoorAmount;
        private String basicDoorAmountUuid;
        private float orderServiceAmount;
        private String orderServiceAmountUuid;
        private float totalAmount;
        private String serviceDate;
        private String grabbingDate;
        private String payDate;
        private String detailedAddr;
        private String sceneOrderTechnicianUuid;
        private String grabUpdateTime;
        private SceneOrderTechnicianResBean sceneOrderTechnicianRes;
        private SceneOrderServiceResBean sceneOrderServiceRes;
        private ArrayList<String> dtcImageList;
        private ArrayList<String> faultImageList;
        private int orderSts;
        private boolean isOneself;
        private int grabbingOrdersSts;
        private String lastUpdatedTime;

        /**
         * sceneOrderServiceRes : {"uuid":"55b9b06133dd47c68511e208b2d39e2f","orderNum":"XC1422095762908647424","faultDesc":"2002","solution":"是计算机技术时间的人物形象生动形象生动形象生动形象思维敏捷类药物流产品质量法律保护作用了！","basicInspectAmount":1,"basicInspectAmountUuid":"3000","lineInspectAmount":50,"lineInspectAmountUuid":"3501","diagnosisInstrumentAmount":50,"diagnosisInstrumentAmountUuid":"4001","carSheetMetalAmount":50,"carSheetMetalAmountUuid":"5001","carPaintRepairAmount":50,"carPaintRepairAmountUuid":"5501","otherAmount":800,"otherAmountUuid":"4508","orderServiceAmount":0,"orderServiceAmountUuid":"6207","totalAmount":1001,"payType":null,"orderSts":null,"sts":null,"createdTime":"2021-08-02 22:12:40","lastUpdatedTime":null,"createdBy":"f2bf4e9ac70c41a5aa22db70ebc0529e","lastUpdatedBy":null}
         */


        public String getGrabUpdateTime() {
            return grabUpdateTime;
        }

        public String getLastUpdatedTime() {
            return lastUpdatedTime;
        }

        public void setLastUpdatedTime(String lastUpdatedTime) {
            this.lastUpdatedTime = lastUpdatedTime;
        }

        public void setGrabUpdateTime(String grabUpdateTime) {
            this.grabUpdateTime = grabUpdateTime;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getIssuerUuid() {
            return issuerUuid;
        }

        public void setIssuerUuid(String issuerUuid) {
            this.issuerUuid = issuerUuid;
        }

        public String getIssuerName() {
            return issuerName;
        }

        public void setIssuerName(String issuerName) {
            this.issuerName = issuerName;
        }

        public String getIssuerMobile() {
            return issuerMobile;
        }

        public void setIssuerMobile(String issuerMobile) {
            this.issuerMobile = issuerMobile;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getBrandUuid() {
            return brandUuid;
        }

        public void setBrandUuid(String brandUuid) {
            this.brandUuid = brandUuid;
        }

        public String getBuyerMobile() {
            return buyerMobile;
        }

        public void setBuyerMobile(String buyerMobile) {
            this.buyerMobile = buyerMobile;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCarModelUuid() {
            return carModelUuid;
        }

        public void setCarModelUuid(String carModelUuid) {
            this.carModelUuid = carModelUuid;
        }

        public String getCarModelName() {
            return carModelName;
        }

        public void setCarModelName(String carModelName) {
            this.carModelName = carModelName;
        }

        public String getCarStyle() {
            return carStyle;
        }

        public void setCarStyle(String carStyle) {
            this.carStyle = carStyle;
        }

        public String getVinCode() {
            return vinCode.length() < 8 ? vinCode : BasicUtils.setStringValue(vinCode.length() - 8, vinCode, '*');
        }

        public void setVinCode(String vinCode) {
            this.vinCode = vinCode;
        }

        public String getTransmissionOneLevel() {
            return transmissionOneLevel;
        }

        public void setTransmissionOneLevel(String transmissionOneLevel) {
            this.transmissionOneLevel = transmissionOneLevel;
        }

        public String getTransmissionOneLevelUuid() {
            return transmissionOneLevelUuid;
        }

        public void setTransmissionOneLevelUuid(String transmissionOneLevelUuid) {
            this.transmissionOneLevelUuid = transmissionOneLevelUuid;
        }

        public String getTransmissionTwoLevel() {
            return transmissionTwoLevel;
        }

        public void setTransmissionTwoLevel(String transmissionTwoLevel) {
            this.transmissionTwoLevel = transmissionTwoLevel;
        }

        public String getTransmissionTwoLevelUuid() {
            return transmissionTwoLevelUuid;
        }

        public void setTransmissionTwoLevelUuid(String transmissionTwoLevelUuid) {
            this.transmissionTwoLevelUuid = transmissionTwoLevelUuid;
        }

        public String getEngineDisplacement() {
            return engineDisplacement;
        }

        public void setEngineDisplacement(String engineDisplacement) {
            this.engineDisplacement = engineDisplacement;
        }

        public String getEngineDisplacementUuid() {
            return engineDisplacementUuid;
        }

        public void setEngineDisplacementUuid(String engineDisplacementUuid) {
            this.engineDisplacementUuid = engineDisplacementUuid;
        }

        public String getDrivingMode() {
            return drivingMode;
        }

        public void setDrivingMode(String drivingMode) {
            this.drivingMode = drivingMode;
        }

        public String getDrivingModeUuid() {
            return drivingModeUuid;
        }

        public void setDrivingModeUuid(String drivingModeUuid) {
            this.drivingModeUuid = drivingModeUuid;
        }

        public String getBoosterSystem() {
            return boosterSystem;
        }

        public void setBoosterSystem(String boosterSystem) {
            this.boosterSystem = boosterSystem;
        }

        public String getBoosterSystemUuid() {
            return boosterSystemUuid;
        }

        public void setBoosterSystemUuid(String boosterSystemUuid) {
            this.boosterSystemUuid = boosterSystemUuid;
        }

        public int getWarrantySts() {
            return warrantySts;
        }

        public void setWarrantySts(int warrantySts) {
            this.warrantySts = warrantySts;
        }

        public String getOtherSts() {
            return otherSts;
        }

        public void setOtherSts(String otherSts) {
            this.otherSts = otherSts;
        }

        public String getFaultDesc() {
            return faultDesc;
        }

        public void setFaultDesc(String faultDesc) {
            this.faultDesc = faultDesc;
        }

        public String getRepairType() {
            return repairType;
        }

        public void setRepairType(String repairType) {
            this.repairType = repairType;
        }

        public String getRepairTypeUuid() {
            return repairTypeUuid;
        }

        public void setRepairTypeUuid(String repairTypeUuid) {
            this.repairTypeUuid = repairTypeUuid;
        }

        public String getAlreadyInspect() {
            return alreadyInspect;
        }

        public void setAlreadyInspect(String alreadyInspect) {
            this.alreadyInspect = alreadyInspect;
        }

        public String getDtcCode() {
            return dtcCode;
        }

        public void setDtcCode(String dtcCode) {
            this.dtcCode = dtcCode;
        }

        public String getBasicDoorAmount() {
            return BasicUtils.floatDecimalFormat(basicDoorAmount);
        }

        public void setBasicDoorAmount(float basicDoorAmount) {
            this.basicDoorAmount = basicDoorAmount;
        }

        public String getBasicDoorAmountUuid() {
            return basicDoorAmountUuid;
        }

        public void setBasicDoorAmountUuid(String basicDoorAmountUuid) {
            this.basicDoorAmountUuid = basicDoorAmountUuid;
        }

        public String getOrderServiceAmount() {
            return BasicUtils.floatDecimalFormat(orderServiceAmount);
        }

        public void setOrderServiceAmount(float orderServiceAmount) {
            this.orderServiceAmount = orderServiceAmount;
        }

        public String getOrderServiceAmountUuid() {
            return orderServiceAmountUuid;
        }

        public void setOrderServiceAmountUuid(String orderServiceAmountUuid) {
            this.orderServiceAmountUuid = orderServiceAmountUuid;
        }

        public String getTotalAmount() {
            return BasicUtils.floatDecimalFormat(totalAmount);
        }

        public void setTotalAmount(float totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getServiceDate() {
            return serviceDate;
        }

        public void setServiceDate(String serviceDate) {
            this.serviceDate = serviceDate;
        }

        public String getGrabbingDate() {
            return grabbingDate;
        }

        public void setGrabbingDate(String grabbingDate) {
            this.grabbingDate = grabbingDate;
        }

        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        public String getDetailedAddr() {
            return detailedAddr;
        }

        public void setDetailedAddr(String detailedAddr) {
            this.detailedAddr = detailedAddr;
        }

        public String getSceneOrderTechnicianUuid() {
            return sceneOrderTechnicianUuid;
        }

        public void setSceneOrderTechnicianUuid(String sceneOrderTechnicianUuid) {
            this.sceneOrderTechnicianUuid = sceneOrderTechnicianUuid;
        }

        public SceneOrderTechnicianResBean getSceneOrderTechnicianRes() {
            return sceneOrderTechnicianRes;
        }

        public void setSceneOrderTechnicianRes(SceneOrderTechnicianResBean sceneOrderTechnicianRes) {
            this.sceneOrderTechnicianRes = sceneOrderTechnicianRes;
        }

        public SceneOrderServiceResBean getSceneOrderServiceRes() {
            return sceneOrderServiceRes;
        }

        public void setSceneOrderServiceRes(SceneOrderServiceResBean sceneOrderServiceRes) {
            this.sceneOrderServiceRes = sceneOrderServiceRes;
        }

        public ArrayList<String> getDtcImageList() {
            return dtcImageList;
        }

        public void setDtcImageList(ArrayList<String> dtcImageList) {
            this.dtcImageList = dtcImageList;
        }

        public ArrayList<String> getFaultImageList() {
            return faultImageList;
        }

        public void setFaultImageList(ArrayList<String> faultImageList) {
            this.faultImageList = faultImageList;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public boolean isIsOneself() {
            return isOneself;
        }

        public void setIsOneself(boolean isOneself) {
            this.isOneself = isOneself;
        }

        public int getGrabbingOrdersSts() {
            return grabbingOrdersSts;
        }

        public void setGrabbingOrdersSts(int grabbingOrdersSts) {
            this.grabbingOrdersSts = grabbingOrdersSts;
        }

        public static class SceneOrderTechnicianResBean {
            /**
             * uuid : e4a6b988e70048f383256f529fafe2df
             * orderUuid : 268169fa2d02488baf9337a17ac289c4
             * technicianUuid : f2bf4e9ac70c41a5aa22db70ebc0529e
             * technicianName : f2bf4e9ac70c41a5aa22db70ebc0529e
             * technicianMobile : 18895332222
             * desc : null
             * alreadyInspect : null
             * dtcCode : null
             * repairSummary : null
             * faultSolve : null
             * sts : null
             * createdTime : 2021-08-02 19:39:20
             * lastUpdatedTime : null
             * createdBy : f2bf4e9ac70c41a5aa22db70ebc0529e
             * lastUpdatedBy : null
             * endImageList : null
             * doorImageList : ["https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-08-02/e4447ee978da4e44afec5388f0a15854.png"]
             */

            private String uuid;
            private String orderUuid;
            private String technicianUuid;
            private String technicianName;
            private String technicianMobile;
            private String describes;
            private String alreadyInspect;
            private String dtcCode;
            private String repairSummary;
            private int faultSolve;
            private int sts;
            private String createdTime;
            private String lastUpdatedTime;
            private String createdBy;
            private String lastUpdatedBy;
            private ArrayList<String> endImageList;
            private ArrayList<String> doorImageList;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getOrderUuid() {
                return orderUuid;
            }

            public void setOrderUuid(String orderUuid) {
                this.orderUuid = orderUuid;
            }

            public String getTechnicianUuid() {
                return technicianUuid;
            }

            public void setTechnicianUuid(String technicianUuid) {
                this.technicianUuid = technicianUuid;
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

            public String getDescribes() {
                return describes;
            }

            public void setDescribes(String describes) {
                this.describes = describes;
            }

            public String getAlreadyInspect() {
                return alreadyInspect;
            }

            public void setAlreadyInspect(String alreadyInspect) {
                this.alreadyInspect = alreadyInspect;
            }

            public String getDtcCode() {
                return dtcCode;
            }

            public void setDtcCode(String dtcCode) {
                this.dtcCode = dtcCode;
            }

            public String getRepairSummary() {
                return repairSummary;
            }

            public void setRepairSummary(String repairSummary) {
                this.repairSummary = repairSummary;
            }

            public int getFaultSolve() {
                return faultSolve;
            }

            public void setFaultSolve(int faultSolve) {
                this.faultSolve = faultSolve;
            }

            public int getSts() {
                return sts;
            }

            public void setSts(int sts) {
                this.sts = sts;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public String getLastUpdatedTime() {
                return lastUpdatedTime;
            }

            public void setLastUpdatedTime(String lastUpdatedTime) {
                this.lastUpdatedTime = lastUpdatedTime;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getLastUpdatedBy() {
                return lastUpdatedBy;
            }

            public void setLastUpdatedBy(String lastUpdatedBy) {
                this.lastUpdatedBy = lastUpdatedBy;
            }

            public ArrayList<String> getEndImageList() {
                return endImageList;
            }

            public void setEndImageList(ArrayList<String> endImageList) {
                this.endImageList = endImageList;
            }

            public ArrayList<String> getDoorImageList() {
                return doorImageList;
            }

            public void setDoorImageList(ArrayList<String> doorImageList) {
                this.doorImageList = doorImageList;
            }
        }

        public static class SceneOrderServiceResBean {
            /**
             * uuid : 55b9b06133dd47c68511e208b2d39e2f
             * orderNum : XC1422095762908647424
             * faultDesc : 2002
             * solution : 是计算机技术时间的人物形象生动形象生动形象生动形象思维敏捷类药物流产品质量法律保护作用了！
             * basicInspectAmount : 1
             * basicInspectAmountUuid : 3000
             * lineInspectAmount : 50
             * lineInspectAmountUuid : 3501
             * diagnosisInstrumentAmount : 50
             * diagnosisInstrumentAmountUuid : 4001
             * carSheetMetalAmount : 50
             * carSheetMetalAmountUuid : 5001
             * carPaintRepairAmount : 50
             * carPaintRepairAmountUuid : 5501
             * otherAmount : 800
             * otherAmountUuid : 4508
             * orderServiceAmount : 0
             * orderServiceAmountUuid : 6207
             * totalAmount : 1001
             * payType : null
             * orderSts : null
             * sts : null
             * createdTime : 2021-08-02 22:12:40
             * lastUpdatedTime : null
             * createdBy : f2bf4e9ac70c41a5aa22db70ebc0529e
             * lastUpdatedBy : null
             */

            @SerializedName("uuid")
            private String uuidX;
            @SerializedName("orderNum")
            private String orderNumX;
            @SerializedName("faultDesc")
            private String faultDescX;
            private String solution;
            private float basicInspectAmount;
            private String basicInspectAmountUuid;
            private float lineInspectAmount;
            private String lineInspectAmountUuid;
            private float diagnosisInstrumentAmount;
            private String diagnosisInstrumentAmountUuid;
            private float carSheetMetalAmount;
            private String carSheetMetalAmountUuid;
            private float repairAmount;
            private String carPaintRepairAmountUuid;
            private float otherAmount;
            private String otherAmountUuid;
            @SerializedName("orderServiceAmount")
            private float orderServiceAmountX;
            @SerializedName("orderServiceAmountUuid")
            private String orderServiceAmountUuidX;
            @SerializedName("totalAmount")
            private float totalAmountX;
            @SerializedName("payType")
            private int payTypeX;
            @SerializedName("orderSts")
            private int orderStsX;
            private String sts;
            @SerializedName("createdTime")
            private String createdTimeX;
            private String lastUpdatedTime;
            private String createdBy;
            private String lastUpdatedBy;
            private ArrayList<String> imageList;
            private String checkData;

            public String getCheckData() {
                return checkData;
            }

            public void setCheckData(String checkData) {
                this.checkData = checkData;
            }

            public ArrayList<String> getImageList() {
                return imageList;
            }

            public void setImageList(ArrayList<String> imageList) {
                this.imageList = imageList;
            }

            public String getUuidX() {
                return uuidX;
            }

            public void setUuidX(String uuidX) {
                this.uuidX = uuidX;
            }

            public String getOrderNumX() {
                return orderNumX;
            }

            public void setOrderNumX(String orderNumX) {
                this.orderNumX = orderNumX;
            }

            public String getFaultDescX() {
                return faultDescX;
            }

            public void setFaultDescX(String faultDescX) {
                this.faultDescX = faultDescX;
            }

            public String getSolution() {
                return solution;
            }

            public void setSolution(String solution) {
                this.solution = solution;
            }

            public String getBasicInspectAmount() {
                return BasicUtils.floatDecimalFormat(basicInspectAmount);
            }

            public void setBasicInspectAmount(float basicInspectAmount) {
                this.basicInspectAmount = basicInspectAmount;
            }

            public String getBasicInspectAmountUuid() {
                return basicInspectAmountUuid;
            }

            public void setBasicInspectAmountUuid(String basicInspectAmountUuid) {
                this.basicInspectAmountUuid = basicInspectAmountUuid;
            }

            public float getLineInspectAmount() {
                return lineInspectAmount;
            }

            public void setLineInspectAmount(float lineInspectAmount) {
                this.lineInspectAmount = lineInspectAmount;
            }

            public String getLineInspectAmountUuid() {
                return lineInspectAmountUuid;
            }

            public void setLineInspectAmountUuid(String lineInspectAmountUuid) {
                this.lineInspectAmountUuid = lineInspectAmountUuid;
            }

            public float getDiagnosisInstrumentAmount() {
                return diagnosisInstrumentAmount;
            }

            public void setDiagnosisInstrumentAmount(float diagnosisInstrumentAmount) {
                this.diagnosisInstrumentAmount = diagnosisInstrumentAmount;
            }

            public String getDiagnosisInstrumentAmountUuid() {
                return diagnosisInstrumentAmountUuid;
            }

            public void setDiagnosisInstrumentAmountUuid(String diagnosisInstrumentAmountUuid) {
                this.diagnosisInstrumentAmountUuid = diagnosisInstrumentAmountUuid;
            }

            public float getCarSheetMetalAmount() {
                return carSheetMetalAmount;
            }

            public void setCarSheetMetalAmount(float carSheetMetalAmount) {
                this.carSheetMetalAmount = carSheetMetalAmount;
            }

            public String getCarSheetMetalAmountUuid() {
                return carSheetMetalAmountUuid;
            }

            public void setCarSheetMetalAmountUuid(String carSheetMetalAmountUuid) {
                this.carSheetMetalAmountUuid = carSheetMetalAmountUuid;
            }


            public String getRepairAmount() {
                return BasicUtils.floatDecimalFormat(repairAmount);
            }

            public void setRepairAmount(float repairAmount) {
                this.repairAmount = repairAmount;
            }

            public String getCarPaintRepairAmountUuid() {
                return carPaintRepairAmountUuid;
            }

            public void setCarPaintRepairAmountUuid(String carPaintRepairAmountUuid) {
                this.carPaintRepairAmountUuid = carPaintRepairAmountUuid;
            }

            public String getOtherAmount() {
                return BasicUtils.floatDecimalFormat(otherAmount);
            }

            public void setOtherAmount(float otherAmount) {
                this.otherAmount = otherAmount;
            }

            public String getOtherAmountUuid() {
                return otherAmountUuid;
            }

            public void setOtherAmountUuid(String otherAmountUuid) {
                this.otherAmountUuid = otherAmountUuid;
            }

            public float getOrderServiceAmountX() {
                return orderServiceAmountX;
            }

            public void setOrderServiceAmountX(float orderServiceAmountX) {
                this.orderServiceAmountX = orderServiceAmountX;
            }

            public String getOrderServiceAmountUuidX() {
                return orderServiceAmountUuidX;
            }

            public void setOrderServiceAmountUuidX(String orderServiceAmountUuidX) {
                this.orderServiceAmountUuidX = orderServiceAmountUuidX;
            }

            public String getTotalAmountX() {
                return BasicUtils.floatDecimalFormat(totalAmountX);
            }

            public void setTotalAmountX(float totalAmountX) {
                this.totalAmountX = totalAmountX;
            }

            public Object getPayTypeX() {
                return payTypeX;
            }

            public void setPayTypeX(int payTypeX) {
                this.payTypeX = payTypeX;
            }

            public int getOrderStsX() {
                return orderStsX;
            }

            public void setOrderStsX(int orderStsX) {
                this.orderStsX = orderStsX;
            }

            public String getSts() {
                return sts;
            }

            public void setSts(String sts) {
                this.sts = sts;
            }

            public String getCreatedTimeX() {
                return createdTimeX;
            }

            public void setCreatedTimeX(String createdTimeX) {
                this.createdTimeX = createdTimeX;
            }

            public String getLastUpdatedTime() {
                return lastUpdatedTime;
            }

            public void setLastUpdatedTime(String lastUpdatedTime) {
                this.lastUpdatedTime = lastUpdatedTime;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getLastUpdatedBy() {
                return lastUpdatedBy;
            }

            public void setLastUpdatedBy(String lastUpdatedBy) {
                this.lastUpdatedBy = lastUpdatedBy;
            }
        }
    }
}
