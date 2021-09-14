package com.car.order.web.model.groupbuy;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.car.common.datasource.model.BaseModelInfo;

import lombok.Data;

@Data
@Table(name = "order_groupbuy")
public class OrderGroupbuy extends BaseModelInfo {

	/**
	 * 订单编号
	 */
	@Column(name = "order_num")
	private String orderNum;

	/**
	 * 团购uuid
	 */
	@Column(name = "groupbuy_uuid")
	private String groupbuyUuid;

	/**
	 * 商品数量
	 */
	@Column(name = "groupbuy_num")
	private Integer groupbuyNum;

	/**
	 * 购买人
	 */
	@Column(name = "user_uuid")
	private String userUuid;
	/**
	 * 服务提供店铺
	 */
	@Column(name = "store_uuid")
	private String storeUuid;

	/**
	 * 实付金额
	 */
	@Column(name = "pay_fee")
	private BigDecimal payFee;

	/**
	 * 支付方式 0 微信支付 1 支付宝支付
	 */
	@Column(name = "pay_type")
	private Integer payType;

	/**
	 * 支付时间
	 */
	@Column(name = "pay_time")
	private Date payTime;

	/**
	 * 真实退款时间
	 */
	@Column(name = "back_time")
	private Date backTime;

	/**
	 * 退款金额
	 */
	@Column(name = "back_fee")
	private BigDecimal backFee;

	/**
	 * 退款账户
	 */
	@Column(name = "back_account")
	private String backAccount;

	/**
	 *订单状态 0 待支付 ； 1 已支付（待成团）； 2: 已取消；  3:退款中 ；  4:退款成功 ；  5:退款失败 ； 6：已完成；7已成团
	 */
	@Column(name = "order_sts")
	private Integer orderSts;

	/**
	 * 售后状态:0 待预约 1买家申请退款 2已预约待上门(使用) 3退款中 4卖家标记已上门 5已取消 6买家标记已上门 7卖家标记已完成 8买家标记已完成
	 */
	@Column(name = "after_sale_sts")
	private Integer afterSaleSts;

	/**
	 * 申请退款时间
	 */
	@Column(name = "refund_apply_date")
	private Date refundApplyDate;

	/**
	 * 退款状态:0 同意退款 1 拒绝退款 2 取消退款
	 */
	@Column(name = "refund_sts")
	private Integer refundSts;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 平台补贴
	 */
	@Column(name = "sys_subsidy")
	private BigDecimal sysSubsidy;

	/**
	 * 平台服务费
	 */
	@Column(name = "platform_service_money")
	private BigDecimal platformServiceMoney;

	/**
	 * 商品售价
	 */
	@Column(name = "receivable_amount")
	private BigDecimal receivableAmount;

	/**
	 * 优惠券id
	 */
	@Column(name = "coupon_uuid")
	private String couponUuid;

	/**
	 * 优惠券抵扣的金额
	 */
	@Column(name = "coupon_fee")
	private BigDecimal couponFee;

	/**
	 * 本单应结算给店铺金额
	 */
	@Column(name = "store_fee")
	private BigDecimal storeFee;

	/**
	 * 商品原价
	 */
	@Column(name = "groupbuy_fee")
	private BigDecimal groupbuyFee;

	/**
	 * 预约服务日期
	 */
	@Column(name = "reserve_service_date")
	private String reserveServiceDate;

	/**
	 * 预约服务时段开始
	 */
	@Column(name = "reserve_part_start")
	private String reservePartStart;

	/**
	 * 预约服务时段结束
	 */
	@Column(name = "reserve_part_end")
	private String reservePartEnd;

	/**
	 *预约服务时段:0=上午;1=下午;2=晚上
	 */
	@Column(name = "reserve_part_type")
	private Integer reservePartType;

	/**
	 *预约服务地址
	 */
	@Column(name = "reserve_addr")
	private String reserveAddr;

	/**
	 *预约服务车辆
	 */
	@Column(name = "reserve_vehicle_uuid")
	private String reserveVehicleUuid;

	/**
	 * 卖家标记已完成时间点
	 */
	@Column(name = "store_over_time")
	private Date storeOverTime;

	/**
	 * 使用说明
	 */
	@Column(name = "use_remark")
	private String useRemark;

	/**
	 *使用说明附带的图
	 */
	@Column(name = "use_img")
	private String useImg;

	/**
	 * 到达说明
	 */
	@Column(name = "reach_remark")
	private String reachRemark;

	/**
	 * 到达说明附带的图
	 */
	@Column(name = "reach_img")
	private String reachImg;

}
