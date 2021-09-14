package com.car.account.client.enums.message;

import lombok.Getter;

@Getter
public enum MessageTypeEnum {
    /**
     * 店铺图片类型枚举:1 营业执照  2 门店图片
     */
    WORK_NOTICE(1, "工位通知"),
    DISCOVER(2, "发现消息"),
    ON_SITE_SUPPORT(3, "现场支持"),
    STORE_MESSAGE(4, "商城消息"),
    WALLET_MESSAGE(5, "钱包消息"),
    ACCOUNT_MESSAGE(6, "账户消息");


    private Integer value;
    private String desc;

    MessageTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String enumOfDesc(Integer value) {
        for (MessageTypeEnum enums :values()) {
            if (enums.value.equals(value)) {
                return enums.desc;
            }
        }
        return null;
    }
}
