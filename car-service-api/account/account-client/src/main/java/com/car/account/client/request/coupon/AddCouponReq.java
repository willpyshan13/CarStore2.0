package com.car.account.client.request.coupon;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 卡券表Bo
 * @since jdk1.8
 */
@Data
@ApiModel(description = "卡券表Bo")
public class AddCouponReq implements Serializable {

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


     @ApiModelProperty("卡券状态 0废弃，1发放")
     private Integer couponSts;

     @ApiModelProperty("适用城市")
     private List<String> suitCity ;





}