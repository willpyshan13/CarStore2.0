package com.car.account.client.response.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 卡券表Bo
 * @since jdk1.8
 */
@Data
@ApiModel(description = "卡券表Vo")
public class QueryCouponRes implements Serializable {

     @ApiModelProperty("")
     private String uuid ;


     @ApiModelProperty("卡券名称")
     private String cname ;


     @ApiModelProperty("面值")
     private BigDecimal remit ;


     @ApiModelProperty("订单类型 0 订单点评  1 咨询 2 回答 3 案例 4 旁听 5 维修保养 6 代驾 7 dtc故障 8 课程 9:现场下单 10：共享技师 11现场服务12团购")
     private String orderType ;


     @ApiModelProperty("领取后多少天")
     private Integer effectDay ;


     @ApiModelProperty("卡券状态")
     private Integer couponSts ;


     @ApiModelProperty("适用城市")
     private List<String> suitCity ;


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