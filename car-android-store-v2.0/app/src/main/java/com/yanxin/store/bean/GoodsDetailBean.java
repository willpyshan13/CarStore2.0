package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;

public class GoodsDetailBean extends BaseBean {

    /**
     * code : 0000
     * data : {"addr":"string","amServeNum":0,"amt":0,"checkSts":0,"commentNum":0,"createdTime":"string","detailList":[{"actAmt":0,"bak1":"string","bak2":"string","bak3":"string","bak4":"string","name":"string","uuid":"string"}],"goodsDescribe":"string","goodsName":"string","goodsType":"string","goodsTypeUuid":"string","imgList":[{"goodsUuid":"string","imgPath":"string","imgType":0,"uuid":"string"}],"latitude":0,"levelOne":"string","levelOneUuid":"string","levelTwo":"string","levelTwoUuid":"string","longitude":0,"maintainDate":"2021-08-12T10:19:35.931Z","maintainFn":0,"maintainSts":0,"manHourCost":0,"materialsExpenses":0,"nmServeNum":0,"notes":"string","platformServiceMoney":0,"platformSubsidy":0,"pmServeNum":0,"receiveMethod":0,"salesNum":0,"score":0,"sellSts":0,"sourceAmt":0,"storeName":"string","storeUuid":"string","surplusNum":0,"tyreNo":"string","useDuration":0,"uuid":"string","vehicleBrand":"string","vehicleModel":"string","visitFee":0,"yearsUse":0}
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
         * addr : string
         * amServeNum : 0
         * amt : 0
         * checkSts : 0
         * commentNum : 0
         * createdTime : string
         * detailList : [{"actAmt":0,"bak1":"string","bak2":"string","bak3":"string","bak4":"string","name":"string","uuid":"string"}]
         * goodsDescribe : string
         * goodsName : string
         * goodsType : string
         * goodsTypeUuid : string
         * imgList : [{"goodsUuid":"string","imgPath":"string","imgType":0,"uuid":"string"}]
         * latitude : 0
         * levelOne : string
         * levelOneUuid : string
         * levelTwo : string
         * levelTwoUuid : string
         * longitude : 0
         * maintainDate : 2021-08-12T10:19:35.931Z
         * maintainFn : 0
         * maintainSts : 0
         * manHourCost : 0
         * materialsExpenses : 0
         * nmServeNum : 0
         * notes : string
         * platformServiceMoney : 0
         * platformSubsidy : 0
         * pmServeNum : 0
         * receiveMethod : 0
         * salesNum : 0
         * score : 0
         * sellSts : 0
         * sourceAmt : 0
         * storeName : string
         * storeUuid : string
         * surplusNum : 0
         * tyreNo : string
         * useDuration : 0
         * uuid : string
         * vehicleBrand : string
         * vehicleModel : string
         * visitFee : 0
         * yearsUse : 0
         */

        private String addr;
        private int amServeNum;
        private float amt;
        private int checkSts;
        private int commentNum;
        private String createdTime;
        private ArrayList<DetailListBean> detailList;
        private String goodsDescribe;
        private String goodsName;
        private String vehicleBrandName;
        private String vehicleModelName;
        private String goodsType;
        private String goodsTypeName;
        private ArrayList<ImgListBean> imgList;
        private int latitude;
        private String levelOne;
        private String levelOneUuid;
        private String levelTwo;
        private String levelTwoUuid;
        private int longitude;
        private String maintainDate;
        private int maintainFn;
        private int maintainSts;
        private float manHourCost;
        private int materialsExpenses;
        private int nmServeNum;
        private String notes;
        private float platformServiceMoney;
        private int platformSubsidy;
        private int pmServeNum;
        private int receiveMethod;
        private int salesNum;
        private float score;
        private int sellSts;
        private float sourceAmt;
        private String storeName;
        private String storeUuid;
        private int surplusNum;
        private String tyreNo;
        private int useDuration;
        private String uuid;
        private String vehicleBrand;
        private String vehicleModel;
        private float visitFee;
        private int yearsUse;

        public String getVehicleModelName() {
            return vehicleModelName;
        }

        public void setVehicleModelName(String vehicleModelName) {
            this.vehicleModelName = vehicleModelName;
        }

        public String getVehicleBrandName() {
            return vehicleBrandName;
        }

        public void setVehicleBrandName(String vehicleBrandName) {
            this.vehicleBrandName = vehicleBrandName;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getAmServeNum() {
            return amServeNum;
        }

        public void setAmServeNum(int amServeNum) {
            this.amServeNum = amServeNum;
        }

        public String getAmt() {
            return BasicUtils.floatDecimalFormat(amt);
        }

        public void setAmt(float amt) {
            this.amt = amt;
        }

        public int getCheckSts() {
            return checkSts;
        }

        public void setCheckSts(int checkSts) {
            this.checkSts = checkSts;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public ArrayList<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(ArrayList<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public String getGoodsDescribe() {
            return goodsDescribe;
        }

        public void setGoodsDescribe(String goodsDescribe) {
            this.goodsDescribe = goodsDescribe;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public String getGoodsTypeName() {
            return goodsTypeName;
        }

        public void setGoodsTypeName(String goodsTypeName) {
            this.goodsTypeName = goodsTypeName;
        }

        public ArrayList<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(ArrayList<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        public String getLevelOne() {
            return levelOne;
        }

        public void setLevelOne(String levelOne) {
            this.levelOne = levelOne;
        }

        public String getLevelOneUuid() {
            return levelOneUuid;
        }

        public void setLevelOneUuid(String levelOneUuid) {
            this.levelOneUuid = levelOneUuid;
        }

        public String getLevelTwo() {
            return levelTwo;
        }

        public void setLevelTwo(String levelTwo) {
            this.levelTwo = levelTwo;
        }

        public String getLevelTwoUuid() {
            return levelTwoUuid;
        }

        public void setLevelTwoUuid(String levelTwoUuid) {
            this.levelTwoUuid = levelTwoUuid;
        }

        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public String getMaintainDate() {
            return maintainDate;
        }

        public void setMaintainDate(String maintainDate) {
            this.maintainDate = maintainDate;
        }

        public int getMaintainFn() {
            return maintainFn;
        }

        public void setMaintainFn(int maintainFn) {
            this.maintainFn = maintainFn;
        }

        public int getMaintainSts() {
            return maintainSts;
        }

        public void setMaintainSts(int maintainSts) {
            this.maintainSts = maintainSts;
        }

        public float getManHourCost() {
            return manHourCost;
        }

        public void setManHourCost(float manHourCost) {
            this.manHourCost = manHourCost;
        }

        public int getMaterialsExpenses() {
            return materialsExpenses;
        }

        public void setMaterialsExpenses(int materialsExpenses) {
            this.materialsExpenses = materialsExpenses;
        }

        public int getNmServeNum() {
            return nmServeNum;
        }

        public void setNmServeNum(int nmServeNum) {
            this.nmServeNum = nmServeNum;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public float getPlatformServiceMoney() {
            return platformServiceMoney;
        }

        public void setPlatformServiceMoney(float platformServiceMoney) {
            this.platformServiceMoney = platformServiceMoney;
        }

        public int getPlatformSubsidy() {
            return platformSubsidy;
        }

        public void setPlatformSubsidy(int platformSubsidy) {
            this.platformSubsidy = platformSubsidy;
        }

        public int getPmServeNum() {
            return pmServeNum;
        }

        public void setPmServeNum(int pmServeNum) {
            this.pmServeNum = pmServeNum;
        }

        public int getReceiveMethod() {
            return receiveMethod;
        }

        public void setReceiveMethod(int receiveMethod) {
            this.receiveMethod = receiveMethod;
        }

        public int getSalesNum() {
            return salesNum;
        }

        public void setSalesNum(int salesNum) {
            this.salesNum = salesNum;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public int getSellSts() {
            return sellSts;
        }

        public void setSellSts(int sellSts) {
            this.sellSts = sellSts;
        }

        public float getSourceAmt() {
            return sourceAmt;
        }

        public void setSourceAmt(float sourceAmt) {
            this.sourceAmt = sourceAmt;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreUuid() {
            return storeUuid;
        }

        public void setStoreUuid(String storeUuid) {
            this.storeUuid = storeUuid;
        }

        public int getSurplusNum() {
            return surplusNum;
        }

        public void setSurplusNum(int surplusNum) {
            this.surplusNum = surplusNum;
        }

        public String getTyreNo() {
            return tyreNo;
        }

        public void setTyreNo(String tyreNo) {
            this.tyreNo = tyreNo;
        }

        public int getUseDuration() {
            return useDuration;
        }

        public void setUseDuration(int useDuration) {
            this.useDuration = useDuration;
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

        public String getVisitFee() {
            return BasicUtils.floatDecimalFormat(visitFee);
        }

        public void setVisitFee(float visitFee) {
            this.visitFee = visitFee;
        }

        public int getYearsUse() {
            return yearsUse;
        }

        public void setYearsUse(int yearsUse) {
            this.yearsUse = yearsUse;
        }

        public static class DetailListBean {
            /**
             * actAmt : 0
             * bak1 : string
             * bak2 : string
             * bak3 : string
             * bak4 : string
             * name : string
             * uuid : string
             */

            private float actAmt;
            private String name;
            private String uuid;

            public String getActAmt() {
                return BasicUtils.floatDecimalFormat(actAmt);
            }

            public void setActAmt(float actAmt) {
                this.actAmt = actAmt;
            }


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }

        public static class ImgListBean {
            /**
             * goodsUuid : string
             * imgPath : string
             * imgType : 0
             * uuid : string
             */

            private String goodsUuid;
            private String imgPath;
            private int imgType;
            private String uuid;

            public String getGoodsUuid() {
                return goodsUuid;
            }

            public void setGoodsUuid(String goodsUuid) {
                this.goodsUuid = goodsUuid;
            }

            public String getImgPath() {
                return imgPath;
            }

            public void setImgPath(String imgPath) {
                this.imgPath = imgPath;
            }

            public int getImgType() {
                return imgType;
            }

            public void setImgType(int imgType) {
                this.imgType = imgType;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }
    }
}
