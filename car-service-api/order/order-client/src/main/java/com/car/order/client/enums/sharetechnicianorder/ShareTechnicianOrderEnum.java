package com.car.order.client.enums.sharetechnicianorder;

import lombok.Getter;

/**
 * @author zhoujian
 * @PACKAGE_NAME: com.car.order.client.enums.sharetechnicianorder
 * @NAME: ShareTechnicianOrderEnum
 * @DATE: 2021/3/4 23:39
 */
@Getter
public enum ShareTechnicianOrderEnum {
    /**
     * 订单状态 0：待抢单  1:待支付 2：待上门,3:提交方案,4:待付款,5:服务中 6:待确认,7:完成,8:退款中,9:退款成功,10:退款失败,11 已取消12, 已拒绝
     */

    HAVE_PAID(0, "待接单"),
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
    REFUSE (12, "已拒绝"),
    HAVE_PAID_CANCELED (13, "接单取消"),
    SERVICE_CANCELED (14, "服务取消"),

    ;

    private Integer value;

    /**
     * 订单预约状态
     * 1：待付款
     * 2：待服务
     * 3：已完成
     * 4：退款中
     * 5：已退款
     */
    private String desc;

    ShareTechnicianOrderEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String enumOfDesc(Integer value) {
        for (ShareTechnicianOrderEnum enums :
                values()) {
            if (enums.value.equals(value)) {
                return enums.desc;
            }
        }
        return "订单错误";
    }
}
