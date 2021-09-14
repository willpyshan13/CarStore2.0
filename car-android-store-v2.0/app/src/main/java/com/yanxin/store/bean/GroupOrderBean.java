package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class GroupOrderBean extends BaseBean {


    /**
     * code : 0000
     * data : [{"afterSaleSts":0,"backAccount":"string","backFee":0,"backTime":"2021-08-19T10:21:05.647Z","couponFee":0,"couponUuid":"string","createdTime":"2021-08-19T10:21:05.648Z","evaluateSts":0,"goodsRes":[{"addr":"string","amServeNum":0,"amt":0,"checkSts":0,"goodsDescribe":"string","goodsName":"string","goodsType":"string","imgUrl":"string","latitude":0,"longitude":0,"maintainDate":"2021-08-19T10:21:05.648Z","maintainFn":0,"maintainSts":0,"manHourCost":0,"materialsExpenses":0,"nmServeNum":0,"notes":"string","parentType":"string","platformServiceMoney":0,"platformSubsidy":0,"pmServeNum":0,"receiveMethod":0,"salesNum":0,"sellSts":0,"sourceAmt":0,"storeUuid":"string","subType":"string","surplusNum":0,"useDuration":0,"vehicleBrand":0,"vehicleModel":0,"visitFee":0,"yearsUse":0}],"groupbuyFee":0,"groupbuyNum":0,"groupbuyRes":{"endTime":"2021-08-19T10:21:05.648Z","goodsRes":[{"addr":"string","amServeNum":0,"amt":0,"checkSts":0,"goodsDescribe":"string","goodsName":"string","goodsType":"string","imgUrl":"string","latitude":0,"longitude":0,"maintainDate":"2021-08-19T10:21:05.648Z","maintainFn":0,"maintainSts":0,"manHourCost":0,"materialsExpenses":0,"nmServeNum":0,"notes":"string","parentType":"string","platformServiceMoney":0,"platformSubsidy":0,"pmServeNum":0,"receiveMethod":0,"salesNum":0,"sellSts":0,"sourceAmt":0,"storeUuid":"string","subType":"string","surplusNum":0,"useDuration":0,"vehicleBrand":0,"vehicleModel":0,"visitFee":0,"yearsUse":0}],"groupSts":0,"intoGroupTime":"2021-08-19T10:21:05.648Z","participateNum":0,"price":0,"receiveMethod":0,"startTime":"2021-08-19T10:21:05.648Z","userNum":0,"userUuid":"string","uuid":"string"},"groupbuyUuid":"string","lastUpdatedTime":"2021-08-19T10:21:05.648Z","orderNum":"string","orderSts":0,"payFee":0,"payTime":"2021-08-19T10:21:05.648Z","payType":0,"platformServiceMoney":0,"reachImg":"string","reachRemark":"string","receivableAmount":0,"refundApplyDate":"2021-08-19T10:21:05.648Z","refundSts":0,"remark":"string","reserveAddr":"string","reservePartEnd":"string","reservePartStart":"string","reservePartType":0,"reserveServiceDate":"string","reserveVehicleUuid":"string","storeFee":0,"storeName":"string","storeOverTime":"2021-08-19T10:21:05.648Z","storeUuid":"string","sysSubsidy":0,"useImg":"string","useRemark":"string","userUuid":"string","uuid":"string"}]
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
         * afterSaleSts : 0
         * backAccount : string
         * backFee : 0
         * backTime : 2021-08-19T10:21:05.647Z
         * couponFee : 0
         * couponUuid : string
         * createdTime : 2021-08-19T10:21:05.648Z
         * evaluateSts : 0
         * goodsRes : [{"addr":"string","amServeNum":0,"amt":0,"checkSts":0,"goodsDescribe":"string","goodsName":"string","goodsType":"string","imgUrl":"string","latitude":0,"longitude":0,"maintainDate":"2021-08-19T10:21:05.648Z","maintainFn":0,"maintainSts":0,"manHourCost":0,"materialsExpenses":0,"nmServeNum":0,"notes":"string","parentType":"string","platformServiceMoney":0,"platformSubsidy":0,"pmServeNum":0,"receiveMethod":0,"salesNum":0,"sellSts":0,"sourceAmt":0,"storeUuid":"string","subType":"string","surplusNum":0,"useDuration":0,"vehicleBrand":0,"vehicleModel":0,"visitFee":0,"yearsUse":0}]
         * groupbuyFee : 0
         * groupbuyNum : 0
         * groupbuyRes : {"endTime":"2021-08-19T10:21:05.648Z","goodsRes":[{"addr":"string","amServeNum":0,"amt":0,"checkSts":0,"goodsDescribe":"string","goodsName":"string","goodsType":"string","imgUrl":"string","latitude":0,"longitude":0,"maintainDate":"2021-08-19T10:21:05.648Z","maintainFn":0,"maintainSts":0,"manHourCost":0,"materialsExpenses":0,"nmServeNum":0,"notes":"string","parentType":"string","platformServiceMoney":0,"platformSubsidy":0,"pmServeNum":0,"receiveMethod":0,"salesNum":0,"sellSts":0,"sourceAmt":0,"storeUuid":"string","subType":"string","surplusNum":0,"useDuration":0,"vehicleBrand":0,"vehicleModel":0,"visitFee":0,"yearsUse":0}],"groupSts":0,"intoGroupTime":"2021-08-19T10:21:05.648Z","participateNum":0,"price":0,"receiveMethod":0,"startTime":"2021-08-19T10:21:05.648Z","userNum":0,"userUuid":"string","uuid":"string"}
         * groupbuyUuid : string
         * lastUpdatedTime : 2021-08-19T10:21:05.648Z
         * orderNum : string
         * orderSts : 0
         * payFee : 0
         * payTime : 2021-08-19T10:21:05.648Z
         * payType : 0
         * platformServiceMoney : 0
         * reachImg : string
         * reachRemark : string
         * receivableAmount : 0
         * refundApplyDate : 2021-08-19T10:21:05.648Z
         * refundSts : 0
         * remark : string
         * reserveAddr : string
         * reservePartEnd : string
         * reservePartStart : string
         * reservePartType : 0
         * reserveServiceDate : string
         * reserveVehicleUuid : string
         * storeFee : 0
         * storeName : string
         * storeOverTime : 2021-08-19T10:21:05.648Z
         * storeUuid : string
         * sysSubsidy : 0
         * useImg : string
         * useRemark : string
         * userUuid : string
         * uuid : string
         */

        private int afterSaleSts;
        private String backAccount;
        private int backFee;
        private String backTime;
        private int couponFee;
        private String couponUuid;
        private String createdTime;
        private int evaluateSts;
        private ArrayList<GoodsResBean> goodsRes;
        private int groupbuyFee;
        private int groupbuyNum;
        private GroupbuyResBean groupbuyRes;
        private String groupbuyUuid;
        private String lastUpdatedTime;
        private String orderNum;
        private int orderSts;
        private int payFee;
        private String payTime;
        private int payType;
        private int platformServiceMoney;
        private String reachImg;
        private String reachRemark;
        private int receivableAmount;
        private String refundApplyDate;
        private int refundSts;
        private String remark;
        private String reserveAddr;
        private String reservePartEnd;
        private String reservePartStart;
        private int reservePartType;
        private String reserveServiceDate;
        private String reserveVehicleUuid;
        private int storeFee;
        private String storeName;
        private String storeOverTime;
        private String storeUuid;
        private int sysSubsidy;
        private String useImg;
        private String useRemark;
        private String userUuid;
        private String uuid;

        public int getAfterSaleSts() {
            return afterSaleSts;
        }

        public void setAfterSaleSts(int afterSaleSts) {
            this.afterSaleSts = afterSaleSts;
        }

        public String getBackAccount() {
            return backAccount;
        }

        public void setBackAccount(String backAccount) {
            this.backAccount = backAccount;
        }

        public int getBackFee() {
            return backFee;
        }

        public void setBackFee(int backFee) {
            this.backFee = backFee;
        }

        public String getBackTime() {
            return backTime;
        }

        public void setBackTime(String backTime) {
            this.backTime = backTime;
        }

        public int getCouponFee() {
            return couponFee;
        }

        public void setCouponFee(int couponFee) {
            this.couponFee = couponFee;
        }

        public String getCouponUuid() {
            return couponUuid;
        }

        public void setCouponUuid(String couponUuid) {
            this.couponUuid = couponUuid;
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

        public ArrayList<GoodsResBean> getGoodsRes() {
            return goodsRes;
        }

        public void setGoodsRes(ArrayList<GoodsResBean> goodsRes) {
            this.goodsRes = goodsRes;
        }

        public int getGroupbuyFee() {
            return groupbuyFee;
        }

        public void setGroupbuyFee(int groupbuyFee) {
            this.groupbuyFee = groupbuyFee;
        }

        public int getGroupbuyNum() {
            return groupbuyNum;
        }

        public void setGroupbuyNum(int groupbuyNum) {
            this.groupbuyNum = groupbuyNum;
        }

        public GroupbuyResBean getGroupbuyRes() {
            return groupbuyRes;
        }

        public void setGroupbuyRes(GroupbuyResBean groupbuyRes) {
            this.groupbuyRes = groupbuyRes;
        }

        public String getGroupbuyUuid() {
            return groupbuyUuid;
        }

        public void setGroupbuyUuid(String groupbuyUuid) {
            this.groupbuyUuid = groupbuyUuid;
        }

        public String getLastUpdatedTime() {
            return lastUpdatedTime;
        }

        public void setLastUpdatedTime(String lastUpdatedTime) {
            this.lastUpdatedTime = lastUpdatedTime;
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

        public int getPayFee() {
            return payFee;
        }

        public void setPayFee(int payFee) {
            this.payFee = payFee;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getPlatformServiceMoney() {
            return platformServiceMoney;
        }

        public void setPlatformServiceMoney(int platformServiceMoney) {
            this.platformServiceMoney = platformServiceMoney;
        }

        public String getReachImg() {
            return reachImg;
        }

        public void setReachImg(String reachImg) {
            this.reachImg = reachImg;
        }

        public String getReachRemark() {
            return reachRemark;
        }

        public void setReachRemark(String reachRemark) {
            this.reachRemark = reachRemark;
        }

        public int getReceivableAmount() {
            return receivableAmount;
        }

        public void setReceivableAmount(int receivableAmount) {
            this.receivableAmount = receivableAmount;
        }

        public String getRefundApplyDate() {
            return refundApplyDate;
        }

        public void setRefundApplyDate(String refundApplyDate) {
            this.refundApplyDate = refundApplyDate;
        }

        public int getRefundSts() {
            return refundSts;
        }

        public void setRefundSts(int refundSts) {
            this.refundSts = refundSts;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReserveAddr() {
            return reserveAddr;
        }

        public void setReserveAddr(String reserveAddr) {
            this.reserveAddr = reserveAddr;
        }

        public String getReservePartEnd() {
            return reservePartEnd;
        }

        public void setReservePartEnd(String reservePartEnd) {
            this.reservePartEnd = reservePartEnd;
        }

        public String getReservePartStart() {
            return reservePartStart;
        }

        public void setReservePartStart(String reservePartStart) {
            this.reservePartStart = reservePartStart;
        }

        public int getReservePartType() {
            return reservePartType;
        }

        public void setReservePartType(int reservePartType) {
            this.reservePartType = reservePartType;
        }

        public String getReserveServiceDate() {
            return reserveServiceDate;
        }

        public void setReserveServiceDate(String reserveServiceDate) {
            this.reserveServiceDate = reserveServiceDate;
        }

        public String getReserveVehicleUuid() {
            return reserveVehicleUuid;
        }

        public void setReserveVehicleUuid(String reserveVehicleUuid) {
            this.reserveVehicleUuid = reserveVehicleUuid;
        }

        public int getStoreFee() {
            return storeFee;
        }

        public void setStoreFee(int storeFee) {
            this.storeFee = storeFee;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreOverTime() {
            return storeOverTime;
        }

        public void setStoreOverTime(String storeOverTime) {
            this.storeOverTime = storeOverTime;
        }

        public String getStoreUuid() {
            return storeUuid;
        }

        public void setStoreUuid(String storeUuid) {
            this.storeUuid = storeUuid;
        }

        public int getSysSubsidy() {
            return sysSubsidy;
        }

        public void setSysSubsidy(int sysSubsidy) {
            this.sysSubsidy = sysSubsidy;
        }

        public String getUseImg() {
            return useImg;
        }

        public void setUseImg(String useImg) {
            this.useImg = useImg;
        }

        public String getUseRemark() {
            return useRemark;
        }

        public void setUseRemark(String useRemark) {
            this.useRemark = useRemark;
        }

        public String getUserUuid() {
            return userUuid;
        }

        public void setUserUuid(String userUuid) {
            this.userUuid = userUuid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public static class GroupbuyResBean {
            /**
             * endTime : 2021-08-19T10:21:05.648Z
             * goodsRes : [{"addr":"string","amServeNum":0,"amt":0,"checkSts":0,"goodsDescribe":"string","goodsName":"string","goodsType":"string","imgUrl":"string","latitude":0,"longitude":0,"maintainDate":"2021-08-19T10:21:05.648Z","maintainFn":0,"maintainSts":0,"manHourCost":0,"materialsExpenses":0,"nmServeNum":0,"notes":"string","parentType":"string","platformServiceMoney":0,"platformSubsidy":0,"pmServeNum":0,"receiveMethod":0,"salesNum":0,"sellSts":0,"sourceAmt":0,"storeUuid":"string","subType":"string","surplusNum":0,"useDuration":0,"vehicleBrand":0,"vehicleModel":0,"visitFee":0,"yearsUse":0}]
             * groupSts : 0
             * intoGroupTime : 2021-08-19T10:21:05.648Z
             * participateNum : 0
             * price : 0
             * receiveMethod : 0
             * startTime : 2021-08-19T10:21:05.648Z
             * userNum : 0
             * userUuid : string
             * uuid : string
             */

            private String endTime;
            private ArrayList<GoodsResBean> goodsRes;
            private int groupSts;
            private String intoGroupTime;
            private int participateNum;
            private int price;
            private int receiveMethod;
            private String startTime;
            private int userNum;
            private String userUuid;
            private String uuid;

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public ArrayList<GoodsResBean> getGoodsRes() {
                return goodsRes;
            }

            public void setGoodsRes(ArrayList<GoodsResBean> goodsRes) {
                this.goodsRes = goodsRes;
            }

            public int getGroupSts() {
                return groupSts;
            }

            public void setGroupSts(int groupSts) {
                this.groupSts = groupSts;
            }

            public String getIntoGroupTime() {
                return intoGroupTime;
            }

            public void setIntoGroupTime(String intoGroupTime) {
                this.intoGroupTime = intoGroupTime;
            }

            public int getParticipateNum() {
                return participateNum;
            }

            public void setParticipateNum(int participateNum) {
                this.participateNum = participateNum;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getReceiveMethod() {
                return receiveMethod;
            }

            public void setReceiveMethod(int receiveMethod) {
                this.receiveMethod = receiveMethod;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getUserNum() {
                return userNum;
            }

            public void setUserNum(int userNum) {
                this.userNum = userNum;
            }

            public String getUserUuid() {
                return userUuid;
            }

            public void setUserUuid(String userUuid) {
                this.userUuid = userUuid;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public static class GoodsResBean {
                /**
                 * addr : string
                 * amServeNum : 0
                 * amt : 0
                 * checkSts : 0
                 * goodsDescribe : string
                 * goodsName : string
                 * goodsType : string
                 * imgUrl : string
                 * latitude : 0
                 * longitude : 0
                 * maintainDate : 2021-08-19T10:21:05.648Z
                 * maintainFn : 0
                 * maintainSts : 0
                 * manHourCost : 0
                 * materialsExpenses : 0
                 * nmServeNum : 0
                 * notes : string
                 * parentType : string
                 * platformServiceMoney : 0
                 * platformSubsidy : 0
                 * pmServeNum : 0
                 * receiveMethod : 0
                 * salesNum : 0
                 * sellSts : 0
                 * sourceAmt : 0
                 * storeUuid : string
                 * subType : string
                 * surplusNum : 0
                 * useDuration : 0
                 * vehicleBrand : 0
                 * vehicleModel : 0
                 * visitFee : 0
                 * yearsUse : 0
                 */

                private String addr;
                private int amServeNum;
                private int amt;
                private int checkSts;
                private String goodsDescribe;
                private String goodsName;
                private String goodsType;
                private String imgUrl;
                private int latitude;
                private int longitude;
                private String maintainDate;
                private int maintainFn;
                private int maintainSts;
                private int manHourCost;
                private int materialsExpenses;
                private int nmServeNum;
                private String notes;
                private String parentType;
                private int platformServiceMoney;
                private int platformSubsidy;
                private int pmServeNum;
                private int receiveMethod;
                private int salesNum;
                private int sellSts;
                private int sourceAmt;
                private String storeUuid;
                private String subType;
                private int surplusNum;
                private int useDuration;
                private int vehicleBrand;
                private int vehicleModel;
                private int visitFee;
                private int yearsUse;

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

                public int getAmt() {
                    return amt;
                }

                public void setAmt(int amt) {
                    this.amt = amt;
                }

                public int getCheckSts() {
                    return checkSts;
                }

                public void setCheckSts(int checkSts) {
                    this.checkSts = checkSts;
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

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }

                public int getLatitude() {
                    return latitude;
                }

                public void setLatitude(int latitude) {
                    this.latitude = latitude;
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

                public int getManHourCost() {
                    return manHourCost;
                }

                public void setManHourCost(int manHourCost) {
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

                public String getParentType() {
                    return parentType;
                }

                public void setParentType(String parentType) {
                    this.parentType = parentType;
                }

                public int getPlatformServiceMoney() {
                    return platformServiceMoney;
                }

                public void setPlatformServiceMoney(int platformServiceMoney) {
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

                public int getSellSts() {
                    return sellSts;
                }

                public void setSellSts(int sellSts) {
                    this.sellSts = sellSts;
                }

                public int getSourceAmt() {
                    return sourceAmt;
                }

                public void setSourceAmt(int sourceAmt) {
                    this.sourceAmt = sourceAmt;
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

                public int getSurplusNum() {
                    return surplusNum;
                }

                public void setSurplusNum(int surplusNum) {
                    this.surplusNum = surplusNum;
                }

                public int getUseDuration() {
                    return useDuration;
                }

                public void setUseDuration(int useDuration) {
                    this.useDuration = useDuration;
                }

                public int getVehicleBrand() {
                    return vehicleBrand;
                }

                public void setVehicleBrand(int vehicleBrand) {
                    this.vehicleBrand = vehicleBrand;
                }

                public int getVehicleModel() {
                    return vehicleModel;
                }

                public void setVehicleModel(int vehicleModel) {
                    this.vehicleModel = vehicleModel;
                }

                public int getVisitFee() {
                    return visitFee;
                }

                public void setVisitFee(int visitFee) {
                    this.visitFee = visitFee;
                }

                public int getYearsUse() {
                    return yearsUse;
                }

                public void setYearsUse(int yearsUse) {
                    this.yearsUse = yearsUse;
                }
            }
        }

        public static class GoodsResBean {
            /**
             * addr : string
             * amServeNum : 0
             * amt : 0
             * checkSts : 0
             * goodsDescribe : string
             * goodsName : string
             * goodsType : string
             * imgUrl : string
             * latitude : 0
             * longitude : 0
             * maintainDate : 2021-08-19T10:21:05.648Z
             * maintainFn : 0
             * maintainSts : 0
             * manHourCost : 0
             * materialsExpenses : 0
             * nmServeNum : 0
             * notes : string
             * parentType : string
             * platformServiceMoney : 0
             * platformSubsidy : 0
             * pmServeNum : 0
             * receiveMethod : 0
             * salesNum : 0
             * sellSts : 0
             * sourceAmt : 0
             * storeUuid : string
             * subType : string
             * surplusNum : 0
             * useDuration : 0
             * vehicleBrand : 0
             * vehicleModel : 0
             * visitFee : 0
             * yearsUse : 0
             */

            private String addr;
            private int amServeNum;
            private int amt;
            private int checkSts;
            private String goodsDescribe;
            private String goodsName;
            private String goodsType;
            private String imgUrl;
            private int latitude;
            private int longitude;
            private String maintainDate;
            private int maintainFn;
            private int maintainSts;
            private int manHourCost;
            private int materialsExpenses;
            private int nmServeNum;
            private String notes;
            private String parentType;
            private int platformServiceMoney;
            private int platformSubsidy;
            private int pmServeNum;
            private int receiveMethod;
            private int salesNum;
            private int sellSts;
            private int sourceAmt;
            private String storeUuid;
            private String subType;
            private int surplusNum;
            private int useDuration;
            private int vehicleBrand;
            private int vehicleModel;
            private int visitFee;
            private int yearsUse;

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

            public int getAmt() {
                return amt;
            }

            public void setAmt(int amt) {
                this.amt = amt;
            }

            public int getCheckSts() {
                return checkSts;
            }

            public void setCheckSts(int checkSts) {
                this.checkSts = checkSts;
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

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getLatitude() {
                return latitude;
            }

            public void setLatitude(int latitude) {
                this.latitude = latitude;
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

            public int getManHourCost() {
                return manHourCost;
            }

            public void setManHourCost(int manHourCost) {
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

            public String getParentType() {
                return parentType;
            }

            public void setParentType(String parentType) {
                this.parentType = parentType;
            }

            public int getPlatformServiceMoney() {
                return platformServiceMoney;
            }

            public void setPlatformServiceMoney(int platformServiceMoney) {
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

            public int getSellSts() {
                return sellSts;
            }

            public void setSellSts(int sellSts) {
                this.sellSts = sellSts;
            }

            public int getSourceAmt() {
                return sourceAmt;
            }

            public void setSourceAmt(int sourceAmt) {
                this.sourceAmt = sourceAmt;
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

            public int getSurplusNum() {
                return surplusNum;
            }

            public void setSurplusNum(int surplusNum) {
                this.surplusNum = surplusNum;
            }

            public int getUseDuration() {
                return useDuration;
            }

            public void setUseDuration(int useDuration) {
                this.useDuration = useDuration;
            }

            public int getVehicleBrand() {
                return vehicleBrand;
            }

            public void setVehicleBrand(int vehicleBrand) {
                this.vehicleBrand = vehicleBrand;
            }

            public int getVehicleModel() {
                return vehicleModel;
            }

            public void setVehicleModel(int vehicleModel) {
                this.vehicleModel = vehicleModel;
            }

            public int getVisitFee() {
                return visitFee;
            }

            public void setVisitFee(int visitFee) {
                this.visitFee = visitFee;
            }

            public int getYearsUse() {
                return yearsUse;
            }

            public void setYearsUse(int yearsUse) {
                this.yearsUse = yearsUse;
            }
        }
    }
}
