package com.yanxin.store.req;

public class PayAuditReq {

    /**
     * carOwnerUuid : string
     * orderUuid : string
     * payType : 0
     */

    private String orderUuid;

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }
}
