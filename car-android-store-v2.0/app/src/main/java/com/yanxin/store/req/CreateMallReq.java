package com.yanxin.store.req;

import java.util.ArrayList;
import java.util.List;

public class CreateMallReq {

    /**
     * amServeNum : 0
     * detailList : [{"actAmt":0,"name":"string"}]
     * goodsDescribe : string
     * goodsImgListReq : [{"imgPath":"string","imgType":0}]
     * goodsName : string
     * goodsType : string
     * manHourCost : 0
     * materialsExpenses : 0
     * nmServeNum : 0
     * notes : string
     * parentType : string
     * platformServiceMoney : 0
     * platformSubsidy : 0
     * pmServeNum : 0
     * receiveMethod : 0
     * sellSts : 0
     * storeUuid : string
     * subType : string
     * surplusNum : 0
     * tyreNo : string
     * vehicleBrand : string
     * vehicleModel : string
     * visitFee : 0
     */

    private Integer amServeNum;
    private ArrayList<DetailListBean> detailList;
    private String goodsDescribe;
    private ArrayList<GoodsImgListReqBean> goodsImgListReq;
    private String goodsName;
    private String goodsType;
    private Integer manHourCost;
    private Integer materialsExpenses;
    private Integer nmServeNum;
    private String notes;
    private String parentType;
    private Integer platformServiceMoney;
    private Integer platformSubsidy;
    private Integer pmServeNum;
    private Integer receiveMethod;
    private Integer sellSts;
    private String storeUuid;
    private String subType;
    private Integer surplusNum;
    private String tyreNo;
    private String vehicleBrand;
    private String vehicleModel;
    private Float visitFee;
    private String addr;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getAmServeNum() {
        return amServeNum;
    }

    public void setAmServeNum(Integer amServeNum) {
        this.amServeNum = amServeNum;
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

    public ArrayList<GoodsImgListReqBean> getGoodsImgListReq() {
        return goodsImgListReq;
    }

    public void setGoodsImgListReq(ArrayList<GoodsImgListReqBean> goodsImgListReq) {
        this.goodsImgListReq = goodsImgListReq;
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

    public Integer getManHourCost() {
        return manHourCost;
    }

    public void setManHourCost(Integer manHourCost) {
        this.manHourCost = manHourCost;
    }

    public Integer getMaterialsExpenses() {
        return materialsExpenses;
    }

    public void setMaterialsExpenses(Integer materialsExpenses) {
        this.materialsExpenses = materialsExpenses;
    }

    public Integer getNmServeNum() {
        return nmServeNum;
    }

    public void setNmServeNum(Integer nmServeNum) {
        this.nmServeNum = nmServeNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public Integer getPlatformServiceMoney() {
        return platformServiceMoney;
    }

    public void setPlatformServiceMoney(Integer platformServiceMoney) {
        this.platformServiceMoney = platformServiceMoney;
    }

    public Integer getPlatformSubsidy() {
        return platformSubsidy;
    }

    public void setPlatformSubsidy(Integer platformSubsidy) {
        this.platformSubsidy = platformSubsidy;
    }

    public Integer getPmServeNum() {
        return pmServeNum;
    }

    public void setPmServeNum(Integer pmServeNum) {
        this.pmServeNum = pmServeNum;
    }

    public Integer getReceiveMethod() {
        return receiveMethod;
    }

    public void setReceiveMethod(Integer receiveMethod) {
        this.receiveMethod = receiveMethod;
    }

    public Integer getSellSts() {
        return sellSts;
    }

    public void setSellSts(Integer sellSts) {
        this.sellSts = sellSts;
    }

    public String getStoreUuid() {
        return storeUuid;
    }

    public void setStoreUuid(String storeUuid) {
        this.storeUuid = storeUuid;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Integer surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getTyreNo() {
        return tyreNo;
    }

    public void setTyreNo(String tyreNo) {
        this.tyreNo = tyreNo;
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

    public Float getVisitFee() {
        return visitFee;
    }

    public void setVisitFee(Float visitFee) {
        this.visitFee = visitFee;
    }

    public static class DetailListBean {
        /**
         * actAmt : 0
         * name : string
         */

        private Float actAmt;
        private String name;

        public Float getActAmt() {
            return actAmt;
        }

        public void setActAmt(Float actAmt) {
            this.actAmt = actAmt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class GoodsImgListReqBean {
        /**
         * imgPath : string
         * imgType : 0
         */

        private String imgPath;
        private Integer imgType;

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public Integer getImgType() {
            return imgType;
        }

        public void setImgType(Integer imgType) {
            this.imgType = imgType;
        }
    }
}
