package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;

public class GoodsBean extends BaseBean {

    /**
     * code : 0000
     * data : [{"amt":0,"checkSts":0,"commentNum":0,"createdTime":"string","detailArrayList":[{"actAmt":0,"bak1":"string","bak2":"string","bak3":"string","bak4":"string","name":"string","uuid":"string"}],"goodsDescribe":"string","goodsName":"string","goodsType":"string","goodsTypeUuid":"string","imgList":[{"goodsUuid":"string","imgPath":"string","imgType":0,"uuid":"string"}],"levelOne":"string","levelOneUuid":"string","levelTwo":"string","levelTwoUuid":"string","manHourCost":0,"materialsExpenses":0,"platformServiceMoney":0,"receiveMethod":0,"salesNum":0,"score":0,"sellSts":0,"storeName":"string","storeUuid":"string","surplusNum":0,"tyreNo":"string","uuid":"string","vehicleBrand":"string","vehicleModel":"string"}]
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
         * levelOne : string
         * levelOneUuid : string
         * levelTwo : string
         * levelTwoUuid : string
         * manHourCost : 0
         * materialsExpenses : 0
         * platformServiceMoney : 0
         * receiveMethod : 0
         * salesNum : 0
         * score : 0
         * sellSts : 0
         * storeName : string
         * storeUuid : string
         * surplusNum : 0
         * tyreNo : string
         * uuid : string
         * vehicleBrand : string
         * vehicleModel : string
         */

        private float amt;
        private int checkSts;
        private int commentNum;
        private String createdTime;
        private ArrayList<DetailListBean> detailList;
        private String goodsDescribe;
        private String goodsName;
        private String goodsType;
        private String goodsTypeUuid;
        private ArrayList<ImgListBean> imgList;
        private String levelOne;
        private String levelOneUuid;
        private String levelTwo;
        private String levelTwoUuid;
        private float manHourCost;
        private float materialsExpenses;
        private float platformServiceMoney;
        private int receiveMethod;
        private int salesNum;
        private float score;
        private int sellSts;
        private String storeName;
        private String storeUuid;
        private int surplusNum;
        private String tyreNo;
        private String uuid;
        private String vehicleBrand;
        private String vehicleModel;

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

        public String getGoodsTypeUuid() {
            return goodsTypeUuid;
        }

        public void setGoodsTypeUuid(String goodsTypeUuid) {
            this.goodsTypeUuid = goodsTypeUuid;
        }

        public ArrayList<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(ArrayList<ImgListBean> imgList) {
            this.imgList = imgList;
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

        public float getManHourCost() {
            return manHourCost;
        }

        public void setManHourCost(float manHourCost) {
            this.manHourCost = manHourCost;
        }

        public float getMaterialsExpenses() {
            return materialsExpenses;
        }

        public void setMaterialsExpenses(float materialsExpenses) {
            this.materialsExpenses = materialsExpenses;
        }

        public float getPlatformServiceMoney() {
            return platformServiceMoney;
        }

        public void setPlatformServiceMoney(float platformServiceMoney) {
            this.platformServiceMoney = platformServiceMoney;
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

            private int actAmt;
            private String bak1;
            private String bak2;
            private String bak3;
            private String bak4;
            private String name;
            private String uuid;

            public int getActAmt() {
                return actAmt;
            }

            public void setActAmt(int actAmt) {
                this.actAmt = actAmt;
            }

            public String getBak1() {
                return bak1;
            }

            public void setBak1(String bak1) {
                this.bak1 = bak1;
            }

            public String getBak2() {
                return bak2;
            }

            public void setBak2(String bak2) {
                this.bak2 = bak2;
            }

            public String getBak3() {
                return bak3;
            }

            public void setBak3(String bak3) {
                this.bak3 = bak3;
            }

            public String getBak4() {
                return bak4;
            }

            public void setBak4(String bak4) {
                this.bak4 = bak4;
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
