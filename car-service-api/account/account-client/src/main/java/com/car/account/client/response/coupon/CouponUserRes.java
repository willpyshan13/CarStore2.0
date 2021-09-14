package com.car.account.client.response.coupon;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * VO
 * @since jdk1.8
 */
@Data
@ApiModel(description = "VO")
public class CouponUserRes implements Serializable {

     @ApiModelProperty("主键uuid")
     private String uuid ;


     @ApiModelProperty("用户uuid")
     private String userUuid ;


     @ApiModelProperty("卡券uuid")
     private String couponUuid ;


     @ApiModelProperty("金额")
     private BigDecimal money ;


     @ApiModelProperty("状态")
     private Long status ;


     @ApiModelProperty("优惠券开始时间")
     private Date startDate ;


     @ApiModelProperty("优惠券结束时间")
     private Date endDate ;


     @ApiModelProperty("状态 0有效 1无效")
     private Byte sts ;


     @ApiModelProperty("创建时间")
     private Date createdTime ;


     @ApiModelProperty("修改时间")
     private Date lastUpdatedTime ;


     @ApiModelProperty("创建人")
     private String createdBy ;


     @ApiModelProperty("修改人")
     private String lastUpdatedBy ;

}