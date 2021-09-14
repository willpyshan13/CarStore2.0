package com.car.order.client.request.order.goods;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UpdateReserveOrderReq", description = "修改商品订单")
public class UpdateReserveOrderReq {

	@ApiModelProperty(value = "订单uuid")
	@NotBlank(message = "请输入订单uuid")
	private String uuid;

	/**
	 * 
	 *
	 */
	@ApiModelProperty("预约服务日期")
	private String reserveServiceDate;
	/**
	 * 
	 *
	 */
	@ApiModelProperty("预约服务时段开始")
	private String reservePartStart;
	/**
	 * 
	 *
	 */
	@ApiModelProperty("预约服务时段结束")
	private String reservePartEnd;
	/**
	 
	 *
	 */
	@ApiModelProperty("预约服务时段:0=上午;1=下午;2=晚上")
	private Integer reservePartType;

	/**
	 * 
	 */
	@ApiModelProperty("预约服务地址")
	private String reserveAddr;

	/**
	 *
	 */
	@ApiModelProperty("预约服务车辆")
	private String reserveVehicleUuid;

}
