package com.yanxin.store.req;

public class PayOrderReq {

    /**
     * channelType : alipay
     * orderUuid : string
     * paymentType : string
     * returnUrl : string
     */

    private String channelType;
    private String orderUuid;
    private String paymentType;
    private String returnUrl;

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
