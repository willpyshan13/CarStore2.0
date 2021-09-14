package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class GroupCreateBean extends BaseBean {

    /**
     * code : 0000
     * data : [{"endTime":"2021-07-29T02:29:25.816Z","goodsRes":[{"amt":0,"checkSts":0,"commentNum":0,"createdTime":"string","detailList":[{"actAmt":0,"bak1":"string","bak2":"string","bak3":"string","bak4":"string","name":"string","uuid":"string"}],"goodsDescribe":"string","goodsName":"string","goodsType":"string","goodsTypeUuid":"string","imgList":[{"goodsUuid":"string","imgPath":"string","imgType":0,"uuid":"string"}],"levelOne":"string","levelOneUuid":"string","levelTwo":"string","levelTwoUuid":"string","manHourCost":0,"materialsExpenses":0,"platformServiceMoney":0,"receiveMethod":0,"salesNum":0,"score":0,"sellSts":0,"storeName":"string","storeUuid":"string","surplusNum":0,"tyreNo":"string","uuid":"string","vehicleBrand":"string","vehicleModel":"string"}],"groupSts":0,"intoGroupTime":"2021-07-29T02:29:25.816Z","participateNum":0,"price":0,"receiveMethod":0,"startTime":"2021-07-29T02:29:25.816Z","userNum":0,"userUuid":"string","uuid":"string"}]
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

    public static class DataBean extends BaseBean {
        /**
         * endTime : 2021-07-29T02:29:25.816Z
         * goodsRes : [{"amt":0,"checkSts":0,"commentNum":0,"createdTime":"string","detailList":[{"actAmt":0,"bak1":"string","bak2":"string","bak3":"string","bak4":"string","name":"string","uuid":"string"}],"goodsDescribe":"string","goodsName":"string","goodsType":"string","goodsTypeUuid":"string","imgList":[{"goodsUuid":"string","imgPath":"string","imgType":0,"uuid":"string"}],"levelOne":"string","levelOneUuid":"string","levelTwo":"string","levelTwoUuid":"string","manHourCost":0,"materialsExpenses":0,"platformServiceMoney":0,"receiveMethod":0,"salesNum":0,"score":0,"sellSts":0,"storeName":"string","storeUuid":"string","surplusNum":0,"tyreNo":"string","uuid":"string","vehicleBrand":"string","vehicleModel":"string"}]
         * groupSts : 0
         * intoGroupTime : 2021-07-29T02:29:25.816Z
         * participateNum : 0
         * price : 0
         * receiveMethod : 0
         * startTime : 2021-07-29T02:29:25.816Z
         * userNum : 0
         * userUuid : string
         * uuid : string
         */

        private String endTime;
        private ArrayList<GoodsResBean> goodsRes;
        private int groupSts;
        private String intoGroupTime;
        private int participateNum;
        private float price;
        private int receiveMethod;
        private String startTime;
        private int userNum;
        private String userUuid;
        private String uuid;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

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

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
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

        public static class GoodsResBean extends BaseBean {
            /**
             * amt (number, optional): 商品金额 ,
             * checkSts (integer, optional): 审核状态：0待审核；1审核通过；2审核驳回 ,
             * commentNum (integer, optional): 商品评论数量 ,
             * createdTime (string, optional): 创建时间 ,
             * detailList (Array[GoodsDetailRes], optional): 物料列表 ,
             * goodsDescribe (string, optional): 商品描述 ,
             * goodsName (string, optional): 商品名称 ,
             * goodsType (string, optional): 商品类型 ,
             * goodsTypeUuid (string, optional): 商品类型uuid ,
             * imgList (Array[GoodsImgRes], optional): 图片列表 ,
             * levelOne (string, optional): 一级分类 ,
             * levelOneUuid (string, optional): 一级分类uuid ,
             * levelTwo (string, optional): 二级分类 ,
             * levelTwoUuid (string, optional): 二级分类uuid ,
             * manHourCost (number, optional): 工时费 ,
             * materialsExpenses (number, optional): 材料费 ,
             * platformServiceMoney (number, optional): 平台服务费 ,
             * receiveMethod (integer, optional): 配送方式 ,
             * salesNum (integer, optional): 销量 ,
             * score (number, optional): 商品评分 ,
             * sellSts (integer, optional): 销售状态:0 库存 1 在售 ,
             * storeName (string, optional): 店铺名称 ,
             * storeUuid (string, optional): 店铺主键 ,
             * surplusNum (integer, optional): 库存 ,
             * tyreNo (string, optional): 轮胎编号 ,
             * uuid (string, optional): uuid ,
             * vehicleBrand (string, optional): 车辆品牌 ,
             * vehicleModel (string, optional): 车型类型
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
}
