package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

public class PayOrderBean extends BaseBean {

    /**
     * code : 0000
     * data : {"appKey":"string","body":"string","channelReturnCode":"string","channelReturnDesc":"string","codeUrl":"string","mwebUrl":"string","nonceStr":"string","orderNo":"string","partnerId":"string","payOrderNo":"string","paySign":"string","prepayId":"string","timeStamp":"string"}
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
         * appKey : string
         * body : string
         * channelReturnCode : string
         * channelReturnDesc : string
         * codeUrl : string
         * mwebUrl : string
         * nonceStr : string
         * orderNo : string
         * partnerId : string
         * payOrderNo : string
         * paySign : string
         * prepayId : string
         * timeStamp : string
         */

        private String appKey;
        private String body;
        private String channelReturnCode;
        private String channelReturnDesc;
        private String codeUrl;
        private String mwebUrl;
        private String nonceStr;
        private String orderNo;
        private String partnerId;
        private String payOrderNo;
        private String paySign;
        private String prepayId;
        private String timeStamp;

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getChannelReturnCode() {
            return channelReturnCode;
        }

        public void setChannelReturnCode(String channelReturnCode) {
            this.channelReturnCode = channelReturnCode;
        }

        public String getChannelReturnDesc() {
            return channelReturnDesc;
        }

        public void setChannelReturnDesc(String channelReturnDesc) {
            this.channelReturnDesc = channelReturnDesc;
        }

        public String getCodeUrl() {
            return codeUrl;
        }

        public void setCodeUrl(String codeUrl) {
            this.codeUrl = codeUrl;
        }

        public String getMwebUrl() {
            return mwebUrl;
        }

        public void setMwebUrl(String mwebUrl) {
            this.mwebUrl = mwebUrl;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPayOrderNo() {
            return payOrderNo;
        }

        public void setPayOrderNo(String payOrderNo) {
            this.payOrderNo = payOrderNo;
        }

        public String getPaySign() {
            return paySign;
        }

        public void setPaySign(String paySign) {
            this.paySign = paySign;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
