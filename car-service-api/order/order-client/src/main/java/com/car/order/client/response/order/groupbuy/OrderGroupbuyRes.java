package com.car.order.client.response.order.groupbuy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.car.order.client.response.order.goods.GoodsRes;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "OrderGroupbuyRes", description = "团购订单VO")
public class OrderGroupbuyRes {

	@ApiModelProperty("主键")
	private String uuid;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty("创建时间")
	private Date createdTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty("最近更新时间")
	private Date lastUpdatedTime;

	@ApiModelProperty("订单编号")
	private String orderNum;

	@ApiModelProperty("团购uuid")
	private String groupbuyUuid;
	@ApiModelProperty("团购商品数量")
	private Integer groupbuyNum;

	@ApiModelProperty("购买人")
	private String userUuid;
	@ApiModelProperty("服务提供店铺")
	private String storeUuid;

	@ApiModelProperty(" 实付金额")
	private BigDecimal payFee;

	@ApiModelProperty("支付方式 0 微信支付 1 支付宝支付")
	private Integer payType;

	@ApiModelProperty(" 支付时间")
	private Date payTime;

	@ApiModelProperty("真实退款时间")
	private Date backTime;

	@ApiModelProperty("退款金额")
	private BigDecimal backFee;

	@ApiModelProperty("退款账户")
	private String backAccount;

	@ApiModelProperty("订单状态 0 待支付; 1 已支付（待成团）; 2 已取消; 3退款中; 4退款成功;  5退款失败; 6已完成；7已成团")
	private Integer orderSts;

	@ApiModelProperty("售后状态:0 待预约;  1买家申请退款 ; 2已预约待上门(使用);  3退款中;  4卖家标记已上门;  5已取消;  6买家标记已上门 ; 7卖家标记已完成;  8买家标记已完成")
	private Integer afterSaleSts;

	@ApiModelProperty("申请退款时间")
	private Date refundApplyDate;

	@ApiModelProperty("退款状态:0 同意退款; 1 拒绝退款; 2 取消退款")
	private Integer refundSts;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("平台补贴")
	private BigDecimal sysSubsidy;

	@ApiModelProperty("平台服务费")
	private BigDecimal platformServiceMoney;

	/**
	 * 商品售价
	 */
	@ApiModelProperty(" 商品售价")
	private BigDecimal receivableAmount;

	/**
	 * 
	 */
	@ApiModelProperty("优惠券id")
	private String couponUuid;

	/**
	 * 
	 */
	@ApiModelProperty("优惠券抵扣的金额")
	private BigDecimal couponFee;

	/**
	 * 
	 */
	@ApiModelProperty("本单应结算给店铺金额")
	private BigDecimal storeFee;

	/**
	 * 
	 */
	@ApiModelProperty("商品原价")
	private BigDecimal groupbuyFee;

	/**
	 * 
	 */
	@ApiModelProperty("预约服务日期")
	private String reserveServiceDate;

	/**
	 * 
	 */
	@ApiModelProperty("预约服务时段开始")
	private String reservePartStart;

	/**
	 *
	 */
	@ApiModelProperty(" 预约服务时段结束")
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

	/**
	 * 
	 */
	@ApiModelProperty("卖家标记已完成时间点")
	private Date storeOverTime;

	/**
	 *
	 */
	@ApiModelProperty(" 使用说明")
	private String useRemark;

	/**
	 *
	 */
	@ApiModelProperty("使用说明附带的图")
	private String useImg;

	/**
	 *
	 */
	@ApiModelProperty(" 到达说明")
	private String reachRemark;

	/**
	 * 
	 */
	@ApiModelProperty("到达说明附带的图")
	private String reachImg;

	@ApiModelProperty("商品集合")
	private List<GoodsRes> goodsRes;

	@ApiModelProperty("团购")
	private GroupbuyRes groupbuyRes;
	@ApiModelProperty("店铺名称")
	private String storeName;
	@ApiModelProperty("评价状态: 0 未评论  1 已评论")
	private Integer evaluateSts;
}
