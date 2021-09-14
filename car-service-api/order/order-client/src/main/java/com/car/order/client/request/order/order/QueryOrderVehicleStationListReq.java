package com.car.order.client.request.order.order;

import java.util.List;

import com.car.common.req.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class QueryOrderVehicleStationListReq extends PageReq {

	@ApiModelProperty("主键")
	private String uuid;

	@ApiModelProperty("订单状态 :0 待支付 ;1 已支付  ;2: 已取消 ; 3:退款中 ;  4:退款成功 ;  5:退款失败  ;6：已完成")
	private List<Integer> orderSts;

	@ApiModelProperty("售后状态:0待抢单；1已抢单; 2买家申请退款 ;3已预约待上门(使用); 4退款中; 5已取消; 6卖家标记已完成 ;7买家标记已完成")
	private List<Integer> afterSaleSts;

	@ApiModelProperty("接单人（店铺）")
	private String storeUserUuid;

	@ApiModelProperty("发单人（车主）")
	private String vehicleUserUuid;

	@ApiModelProperty("与接单人（店铺）同城:null=不校验;0=不同城;1=同城")
	private Integer storeSameCity;
}
