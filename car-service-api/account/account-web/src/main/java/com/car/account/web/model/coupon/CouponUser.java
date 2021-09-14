package com.car.account.web.model.coupon;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类
 * @since jdk1.8
 */
@Data
public class CouponUser implements Serializable {
    /**
     * 主键uuid
     */
     private String uuid ;

    /**
     * 用户uuid
     */
     private String userUuid ;

    /**
     * 卡券uuid
     */
     private String couponUuid ;

    /**
     * 金额
     */
     private BigDecimal money ;

    /**
     * 状态
     */
     private Long status ;

    /**
     * 优惠券开始时间
     */
     private Date startDate ;

    /**
     * 优惠券结束时间
     */
     private Date endDate ;

    /**
     * 状态 0有效 1无效
     */
     private Byte sts ;

    /**
     * 创建时间
     */
     private Date createdTime ;

    /**
     * 修改时间
     */
     private Date lastUpdatedTime ;

    /**
     * 创建人
     */
     private String createdBy ;

    /**
     * 修改人
     */
     private String lastUpdatedBy ;

}