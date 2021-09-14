package com.car.order.client.request.order.order;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AddOrderVehicleStationReq", description = "车主新增工位需求")
public class AddOrderVehicleStationReq {

	@ApiModelProperty(" 车辆品牌uuid")
	private String vehicleBrand;
	/**
	 * 
	 */
	@ApiModelProperty("车型类型uuid")
	private String vehicleModel;

	@ApiModelProperty("需求描述")
	private String remark;
	@ApiModelProperty("视频/图片")
	private String imgVideo;

	@ApiModelProperty("需要服务日期")
	private Date useDate;
	/**
	 * 
	 */
	@ApiModelProperty("需要服务时间段")
	private String useTime;

	@ApiModelProperty("时长")
	private Integer duration;

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
