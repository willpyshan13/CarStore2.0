package com.yanxin.store.commont;

public class PayStatusConfig {
    private int payType; //订单类型 0:购买案例 1:知识问答-发起问题 2:广场-旁听 3:现场支持下单 4:dtc详情
    private String orderUuid;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }
}
