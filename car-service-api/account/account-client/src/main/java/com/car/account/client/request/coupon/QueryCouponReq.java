package com.car.account.client.request.coupon;

import com.car.common.req.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 卡券表Bo
 * @since jdk1.8
 */
@Data
@ApiModel(description = "卡券表Vo")
public class QueryCouponReq extends PageReq implements Serializable {

    
     @ApiModelProperty("卡券名称")
     private String cname ;

    
     @ApiModelProperty("面值")
     private BigDecimal remit ;

    
     @ApiModelProperty("订单类型 0 订单点评  1 咨询 2 回答 3 案例 4 旁听 5 维修保养 6 代驾 7 dtc故障 8 课程 9:现场下单 10：共享技师 11现场服务12团购")
     private String orderType ;

    
     @ApiModelProperty("领取后多少天")
     private Integer effectDay ;

     @ApiModelProperty("卡券状态")
     private String couponSts ;
    
     @ApiModelProperty("适用城市")
     private String suitCity ;


}