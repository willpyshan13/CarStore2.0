package com.car.order.client.enums.consult;

import lombok.Getter;

@Getter
public enum ConsultOrderTypeEnum {
    /**
     * 采纳结果 0 满意 1 不满意
     */
    No_SERVICE(0, "未服务"),
    AKREADY_SERVICE (1, "已服务"),
    ;
    private Integer value;
    private String desc;

    ConsultOrderTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String enumOfDesc(Integer value) {
        for (ConsultOrderTypeEnum enums :values()) {
            if (enums.value.equals(value)) {
                return enums.desc;
            }
        }
        return null;
    }
}