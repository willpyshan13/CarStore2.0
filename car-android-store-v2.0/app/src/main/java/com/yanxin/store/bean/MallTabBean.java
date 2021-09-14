package com.yanxin.store.bean;

import java.util.ArrayList;

public class MallTabBean {

    /**
     * code : 0000
     * msg : success
     * data : [{"uuid":"66cd3b28536f4b7d807ff0c88a78e540","issuerUuid":"18dff17f449b485a8fab71fe6844b55f","issuerName":"崔师傅","issuerMobile":"15821133346","createdTime":"2021-06-08 19:05:19","buyerName":null,"buyerMobile":null,"brandName":"宝骐汽车","carModelName":"帅骐","faultDesc":"监控","payType":0,"orderSts":1,"totalAmount":1,"grabbingOrdersSts":0,"isOneself":false,"distance":12.73},{"uuid":"9c10e667da374db393656acca75fa8ac","issuerUuid":"18dff17f449b485a8fab71fe6844b55f","issuerName":"崔师傅","issuerMobile":"15821133346","createdTime":"2021-06-08 19:01:52","buyerName":null,"buyerMobile":null,"brandName":"华晨宝马","carModelName":"宝马2系","faultDesc":"哦你自己看看控制","payType":0,"orderSts":1,"totalAmount":1,"grabbingOrdersSts":0,"isOneself":false,"distance":12.73},{"uuid":"147784bba8ae46b980bc50723c4d019e","issuerUuid":"3de4a3bbb0fb465090c392a492cd9fc9","issuerName":"程金伟","issuerMobile":"13501805140","createdTime":"2021-06-04 01:27:20","buyerName":null,"buyerMobile":null,"brandName":"阿斯顿·马丁","carModelName":"阿斯顿·马丁DB11","faultDesc":"111","payType":0,"orderSts":1,"totalAmount":1,"grabbingOrdersSts":0,"isOneself":false,"distance":18.55},{"uuid":"917b768e951848df873601583c836fb7","issuerUuid":"3de4a3bbb0fb465090c392a492cd9fc9","issuerName":"程金伟","issuerMobile":"13501805140","createdTime":"2021-06-04 00:53:30","buyerName":null,"buyerMobile":null,"brandName":"阿斯顿·马丁","carModelName":"阿斯顿·马丁DB11","faultDesc":"5555555555","payType":null,"orderSts":1,"totalAmount":252,"grabbingOrdersSts":0,"isOneself":false,"distance":18.55},{"uuid":"2678428245274223852ee4b8d08d276b","issuerUuid":"3de4a3bbb0fb465090c392a492cd9fc9","issuerName":"程金伟","issuerMobile":"13501805140","createdTime":"2021-06-03 22:14:20","buyerName":null,"buyerMobile":null,"brandName":"ARCFOX极狐","carModelName":"ARCFOX αT","faultDesc":"123635","payType":0,"orderSts":1,"totalAmount":1,"grabbingOrdersSts":0,"isOneself":false,"distance":18.55}]
     * size : 10
     * total : 5
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
         * uuid : 66cd3b28536f4b7d807ff0c88a78e540
         * issuerUuid : 18dff17f449b485a8fab71fe6844b55f
         * issuerName : 崔师傅
         * issuerMobile : 15821133346
         * createdTime : 2021-06-08 19:05:19
         * buyerName : null
         * buyerMobile : null
         * brandName : 宝骐汽车
         * carModelName : 帅骐
         * faultDesc : 监控
         * payType : 0
         * orderSts : 1
         * totalAmount : 1
         * grabbingOrdersSts : 0
         * isOneself : false
         * distance : 12.73
         */

        private String uuid;
        private String issuerUuid;
        private String issuerName;
        private String issuerMobile;
        private String createdTime;
        private Object buyerName;
        private Object buyerMobile;
        private String brandName;
        private String carModelName;
        private String faultDesc;
        private int payType;
        private int orderSts;
        private int totalAmount;
        private int grabbingOrdersSts;
        private boolean isOneself;
        private double distance;

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

        public Object getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(Object buyerName) {
            this.buyerName = buyerName;
        }

        public Object getBuyerMobile() {
            return buyerMobile;
        }

        public void setBuyerMobile(Object buyerMobile) {
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
    }
}
