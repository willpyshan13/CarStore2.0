package com.car.account.client.response.store;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺-平台服务-收费比率-补贴比率VO
 * @since jdk1.8
 */
@Data
@ApiModel(description = "店铺-平台服务-收费比率-补贴比率VO")
public class StoreServiceRatesRes {

     @ApiModelProperty("主键uuid")
     private String uuid ;


     @ApiModelProperty("店铺主键")
     private String storeUuid ;


     @ApiModelProperty("服务类型 ：1洗车   2:维修   3：美容  4:保养 5.其他  6.工位")
     private Byte serviceType ;

     @ApiModelProperty("是否启用")
     private Byte status ;

     @ApiModelProperty("服务费收费服务费收费比率")
     private BigDecimal serviceRates ;


     @ApiModelProperty("goods_parent表uuid")
     private String goodsParentUuid ;


     @ApiModelProperty("补贴比率")
     private BigDecimal subsidiesRates ;


     @ApiModelProperty("创建时间")
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date createdTime ;


     @ApiModelProperty("修改时间")
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date lastUpdatedTime ;


     @ApiModelProperty("创建人")
     private String createdBy ;


     @ApiModelProperty("修改人")
     private String lastUpdatedBy ;

}