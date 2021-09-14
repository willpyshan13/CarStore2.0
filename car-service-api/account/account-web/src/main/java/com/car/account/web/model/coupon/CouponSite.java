package com.car.account.web.model.coupon;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @since jdk1.8
 */
@Data
public class CouponSite implements Serializable {
    /**
     * 
     */
     private String uuid ;

    /**
     * 优惠券uuid
     */
     private String couponUuid ;

    /**
     * 地址uuid
     */
     private String sysAreaUuid ;

    /**
     * 状态 0有效1无效
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