package com.car.order.client.request.order.order;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UpdateOrderVehicleStationAfterSaleStsReq", description = "修改订单")
public class UpdateOrderVehicleStationAfterSaleStsReq {

	@ApiModelProperty(value = "订单uuid")
	@NotBlank(message = "请输入订单uuid")
	private String uuid;

	@ApiModelProperty(value = "售后状态:0待抢单；1已抢单； 2买家申请退款； 3已预约待上门(使用)； 4退款中； 5已取消； 6卖家标记已完成 ；7买家标记已完成", name = "afterSaleSts")
	private Integer afterSaleSts;
}
