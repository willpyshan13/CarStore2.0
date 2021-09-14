package com.car.order.client.response.order;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "OrderVehicleStationRes", description = "C端车主发起的工位订单VO")
public class OrderVehicleStationRes {

	@ApiModelProperty("uuid")
	private String uuid;

	@ApiModelProperty("创建时间")
	private Date createdTime;
	@ApiModelProperty("创建人名称")
	private String createdBy;
	@ApiModelProperty("更新时间")
	private Date lastUpdatedTime;
	@ApiModelProperty("更新人名称")
	private String lastUpdatedBy;
	/**
	 * 
	 */
	@ApiModelProperty("订单编号")
	private String orderNum;
	/**
	 * 
	 */
	@ApiModelProperty("发单人（车主）")
	private String vehicleUserUuid;
	/**
	 *
	 */
	@ApiModelProperty("订单状态 :0 待支付 ;1 已支付  ;2: 已取消 ; 3:退款中 ;  4:退款成功 ;  5:退款失败  ;6：已完成")
	private Integer orderSts;

	/**
	 * 
	 */
	@ApiModelProperty("售后状态:0待抢单；1已抢单; 2买家申请退款 ;3已预约待上门(使用); 4退款中; 5已取消; 6卖家标记已完成 ;7买家标记已完成")
	private Integer afterSaleSts;
	/**
	 * 
	 */
	@ApiModelProperty("申请退款时间")
	private Date refundApplyDate;
	/**
	 * 
	 */
	@ApiModelProperty("退款状态:0 同意退款 1 拒绝退款 2 取消退款")
	private Integer refundSts;
	/**
	 * 
	 */
	@ApiModelProperty("真实退款时间")
	private Date backTime;
	/**
	 * 
	 */
	@ApiModelProperty("退款金额")
	private BigDecimal backFee;
	/**
	 * 退款账户
	 */
	@ApiModelProperty("back_account")
	private String backAccount;

	/**
	 * 
	 */
	@ApiModelProperty("接单人（店铺人员）")
	private String storeUserUuid;
	@ApiModelProperty("接单店铺")
	private String storeUuid;
	/**
	 *
	 */
	@ApiModelProperty(" 车辆品牌uuid")
	private String vehicleBrand;
	/**
	 * 
	 */
	@ApiModelProperty("车型类型uuid")
	private String vehicleModel;

	/**
	 * 
	 */
	@ApiModelProperty("需求描述")
	private String remark;
	@ApiModelProperty("视频/图片")
	private String imgVideo;
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
	@ApiModelProperty("实际服务时间(已完成时间点),卖家标记为已完成的时间点")
	private Date actualDate;
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
	/**
	 *
	 */
	@ApiModelProperty("总价")
	private BigDecimal price;

	/**
	 * 
	 */
	@ApiModelProperty("平台服务费用")
	private BigDecimal platformServiceMoney;

	/**
	 *
	 */
	@ApiModelProperty("本单应结算给店铺金额")
	private BigDecimal storeFee;

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

	//
	@ApiModelProperty("车辆品牌名称")
	private String vehicleBrandName;
	@ApiModelProperty("车型类型名称")
	private String vehicleModelName;
	@ApiModelProperty("发单人名称")
	private String vehicleUserName;
	@ApiModelProperty("接单人名称")
	private String storeUserName;
	@ApiModelProperty("接单人所在店名")
	private String storeName;
	@ApiModelProperty("是否评价 0 未评论  1 已评论")
	private Integer evaluateSts;
}
