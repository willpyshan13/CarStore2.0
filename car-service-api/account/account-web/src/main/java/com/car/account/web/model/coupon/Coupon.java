package com.car.account.web.model.coupon;

import com.car.common.datasource.model.BaseModelInfo;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 卡券表实体类
 * @since jdk1.8
 */
@Data
public class Coupon extends BaseModelInfo {

    /**
     * 卡券名称
     */
    @Column(name = "cname")
    private String cname;

    /**
     * 面值
     */
    @Column(name = "remit")
    private BigDecimal remit;

    /**
     * 订单类型 0 订单点评  1 咨询 2 回答 3 案例 4 旁听 5 维修保养 6 代驾 7 dtc故障 8 课程 9:现场下单 10：共享技师 11现场服务12团购
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 领取后多少天
     */
    @Column(name = "effect_day")
    private Integer effectDay;

    /**
     * 卡券状态
     */
    @Column(name = "coupon_sts")
    private Integer couponSts;



}