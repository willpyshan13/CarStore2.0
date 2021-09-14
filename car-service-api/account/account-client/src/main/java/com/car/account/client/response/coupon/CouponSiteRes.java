package com.car.account.client.response.coupon;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * VO
 * @since jdk1.8
 */
@Data
@ApiModel(description = "VO")
public class CouponSiteRes implements Serializable {

     @ApiModelProperty("")
     private String uuid ;


     @ApiModelProperty("优惠券uuid")
     private String couponUuid ;


     @ApiModelProperty("地址uuid")
     private String sysAreaUuid ;


     @ApiModelProperty("状态 0有效1无效")
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