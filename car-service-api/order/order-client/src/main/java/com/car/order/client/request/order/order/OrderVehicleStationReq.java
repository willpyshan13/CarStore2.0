package com.car.order.client.request.order.order;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "OrderVehicleStationReq", description = "C端车主发起的工位订单BO")
public class OrderVehicleStationReq {

	@ApiModelProperty("uuid")
	private String uuid;
	/**
	 * 
	 */
	@ApiModelProperty("接单人（店铺）")
	private String storeUserUuid;
	/**
	 *
	 */
	@ApiModelProperty(" 车辆品牌")
	private String vehicleBrand;
	/**
	 * 
	 */
	@ApiModelProperty("车型类型")
	private String vehicleModel;
	/**
	 * 
	 */
	@ApiModelProperty("需求描述")
	private String remark;
	/**
	 * 
	 */
	@ApiModelProperty("需要服务日期")
	private Date useDate;
	/**
	 * 
	 */
	@ApiModelProperty("需要服务时间段")
	private String useTime;
	/**
	 * 
	 */
	@ApiModelProperty("时长")
	private Integer duration;
	/**
	 * 
	 */
	@ApiModelProperty("时价")
	private BigDecimal durationPrice;

	@ApiModelProperty("省,对应地区表uuid")
	private String addressProvince;
	/**
	 *
	 */
	@ApiModelProperty(" 市,对应地区表uuid")
	private String addressCity;
	/**
	 * 
	 */
	@ApiModelProperty("县镇,对应地区表uuid")
	private String addressCounty;
}
