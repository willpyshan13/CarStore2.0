package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;

public class SupportOrderBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"2","issuerUuid":"13c55c78564641ff980ed3630aec68f7","issuerName":"ss","issuerMobile":"185654645","createdTime":"2021-07-21 11:10:06","buyerName":null,"buyerMobile":null,"brandName":null,"carModelName":null,"faultDesc":"0ghjghj","payType":0,"orderSts":1,"basicDoorAmount":10,"totalAmount":10,"grabbingOrdersSts":1,"isOneself":true,"distance":0,"isScore":0}]
     * size : 10
     * total : 1
     * pages : 1
     * success : true
     */

    private String code;
    private String msg;
    private ArrayList<DataBean> data;
    private int size;
    private int total;
    private int pages;
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

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * basicDoorAmount (number, optional): 基础上门费 ,
         * brandName (string, optional): 品牌名称 ,
         * buyerMobile (string, optional): 购买者手机号码 ,
         * buyerName (string, optional): 购买者姓名 ,
         * carModelName (string, optional): 车型名称 ,
         * createdTime (string, optional): 创建时间 ,
         * distance (number, optional): 距离(km) ,
         * faultDesc (string, optional): 故障描述 ,
         * grabbingOrdersSts (integer, optional): 抢单状态 0未抢，1已抢 ,
         * isOneself (boolean, optional): 是否是本人发布，true：是， false：否 ,
         * isScore (integer, optional): 是否评分0:未评分 1:已评分 ,
         * issuerMobile (string, optional): 发布者手机号码 ,
         * issuerName (string, optional): 发布者姓名 ,
         * issuerUuid (string, optional): 发布者uuid ,
         * orderSts (integer, optional): 订单状态 0：待抢单  1:待支付 2：待上门,3:提交方案,4:待付款,5:服务中 6:待确认,7:完成,8:退款中,9:退款成功,10:退款失败,
         * payType (integer, optional): 支付方式 0 微信支付 1 支付宝支付 ,
         * totalAmount (number, optional): 总支付费用 ,
         * uuid (string, optional): uuid
         */

        private String uuid;
        private String issuerUuid;
        private String issuerName;
        private String issuerMobile;
        private String createdTime;
        private String buyerName;
        private String buyerMobile;
        private String brandName;
        private String carModelName;
        private String faultDesc;
        private int payType;
        private int orderSts;
        private int basicDoorAmount;
        private int totalAmount;
        private int grabbingOrdersSts;
        private boolean isOneself;
        private double distance;
        private int isScore;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
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

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
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

        public String getCarModelName() {
            return carModelName;
        }

        public void setCarModelName(String carModelName) {
            this.carModelName = carModelName;
        }

        public String getFaultDesc() {
            return faultDesc;
        }

        public void setFaultDesc(String faultDesc) {
            this.faultDesc = faultDesc;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getOrderSts() {
            return orderSts;
        }

        public void setOrderSts(int orderSts) {
            this.orderSts = orderSts;
        }

        public int getBasicDoorAmount() {
            return basicDoorAmount;
        }

        public void setBasicDoorAmount(int basicDoorAmount) {
            this.basicDoorAmount = basicDoorAmount;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getGrabbingOrdersSts() {
            return grabbingOrdersSts;
        }

        public void setGrabbingOrdersSts(int grabbingOrdersSts) {
            this.grabbingOrdersSts = grabbingOrdersSts;
        }

        public boolean isIsOneself() {
            return isOneself;
        }

        public void setIsOneself(boolean isOneself) {
            this.isOneself = isOneself;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getIsScore() {
            return isScore;
        }

        public void setIsScore(int isScore) {
            this.isScore = isScore;
        }
    }
}
