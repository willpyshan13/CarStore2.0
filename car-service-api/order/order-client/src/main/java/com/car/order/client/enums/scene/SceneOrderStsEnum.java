package com.car.order.client.enums.scene;

import lombok.Getter;

/**
 * @author zhouz
 * @date 2020/12/30
 */
@Getter
public enum SceneOrderStsEnum {
    /**
     * 订单状态 0：待抢单  1:待支付 2：待上门,3:提交方案,4:待付款,5:服务中 6:待确认,7:完成,8:退款中,9:退款成功,10:退款失败,11 已取消 12, "接单取消"13, "服务取消"
     */
    HAVE_PAID(0, "待抢单"),
    UNPAID(1, "待支付"),
    WAIT_DOOR(2, "待上门"),
    SUBMIT_PLAN(3, "提交方案"),
    WAIT_PAYMENT (4, "待付款"),
    IN_SERVICE (5, "服务中"),
    WAIT_COMPLETED (6, "待确认"),
    COMPLETED(7, "已完成"),
    A_REFUND_OF(8, "退款中"),
    REFUND_SUCCESS(9, "退款成功"),
    REFUND_FAILURE(10, "退款失败"),
    CANCELED (11, "已取消"),
    HAVE_PAID_CANCELED (12, "接单取消"),
    SERVICE_CANCELED (13, "服务取消"),
    ;
    private Integer value;
    private String desc;

    SceneOrderStsEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String enumOfDesc(Integer value) {
        for (SceneOrderStsEnum enums :values()) {
            if (enums.value.equals(value)) {
                return enums.desc;
            }
        }
        return null;
    }
}
