package com.car.order.client.response.order.goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhouz
 * @date 2020/12/30
 */
@Data
@ApiModel(value = "OrderGoodsRes", description = "订单商品VO")
public class OrderGoodsRes {

	@ApiModelProperty(value = "uuid", name = "uuid")
	private String uuid;

	@ApiModelProperty(value = "店铺uuid", name = "storeUuid")
	private String storeUuid;

	@ApiModelProperty(value = "订单号", name = "orderNum")
	private String orderNum;

	@ApiModelProperty(value = "下单时间 yyyy-MM-dd", name = "createdTime", example = "2020-12-30 21:35:00")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String createdTime;

	@ApiModelProperty(value = "服务地区,保存具体地址", name = "serviceArea")
	private String serviceArea;

	@ApiModelProperty(value = "服务单号", name = "serviceNum")
	private String serviceNum;

	@ApiModelProperty(value = "实收金额", name = "actualAmount")
	private BigDecimal actualAmount;

	@ApiModelProperty(value = "应收金额", name = "receivableAmount")
	private BigDecimal receivableAmount;

	@ApiModelProperty(value = "联系人", name = "contacts")
	private String contacts;

	@ApiModelProperty(value = "手机号", name = "mobile")
	private String mobile;

	@ApiModelProperty(value = "支付方式", name = "payType")
	private Integer payType;

	@ApiModelProperty(value = "订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败 6：已完成", name = "orderSts")
	private Integer orderSts;

	@ApiModelProperty(value = "配送方式", name = "deliveryMode")
	private Integer deliveryMode;

	@ApiModelProperty(value = "配送地址", name = "deliveryAddress")
	private String deliveryAddress;

	@ApiModelProperty(value = "订单备注信息", name = "orderRemark")
	private String orderRemark;

	@ApiModelProperty(value = "退款类型: 0 线上退款 1 线下退款", name = "refundType")
	private Integer refundType;

	@ApiModelProperty(value = "退款金额", name = "refundAmount")
	private BigDecimal refundAmount;

	@ApiModelProperty(value = "售后原因", name = "afterSaleCause")
	private String afterSaleCause;

	@ApiModelProperty(value = "售后状态:0 待预约; 1买家申请退款; 2已预约待上门(使用); 3退款中 ;4卖家标记已上门; 5已取消 ;6买家标记已上门; 7卖家标记已完成; 8买家标记已完成", name = "afterSaleSts")
	private Integer afterSaleSts;

	@ApiModelProperty(value = "退款状态:0 同意退款 1 拒绝退款 2 取消退款", name = "refundSts")
	private Integer refundSts;

	@ApiModelProperty(value = "售后说明", name = "afterSaleRemark")
	private String afterSaleRemark;

	@ApiModelProperty(value = "店铺评分", name = "storeScore")
	private BigDecimal storeScore;

	@ApiModelProperty(value = "技师评分", name = "technicianScore")
	private BigDecimal technicianScore;

	@ApiModelProperty(value = "配送方式 0快递,1到店服务,2上门服务", name = "receiveMethod")
	private Integer receiveMethod;

	/**
	 * 
	 *
	 */
	@ApiModelProperty("优惠券id")
	private String couponUuid;
	/**
	 * 
	 *
	 */
	@ApiModelProperty("优惠券抵扣的金额")
	private BigDecimal couponFee;
	/**
	 *
	 *
	 */
	@ApiModelProperty("本单应结算给店铺金额")
	private BigDecimal storeFee;
	/**
	 * 
	 *
	 */
	@ApiModelProperty("商品原价")
	private BigDecimal goodsFee;

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
	private Integer reserveAddr;

	/**
	 *
	 */
	@ApiModelProperty("预约服务车辆")
	private Integer reserveVehicleUuid;
	/**
	 * 
	 *
	 */
	@ApiModelProperty("卖家标记已完成时间点")
	private Date storeOverTime;

	@ApiModelProperty("申请退款时间")
	private Date refundApplyDate;

	/**
	 * 
	 *
	 */
	@ApiModelProperty("使用说明")
	private String useRemark;
	/**
	 * 
	 *
	 */
	@ApiModelProperty("使用说明附带的图")
	private String useImg;
	/**
	 *
	 *	
	 */
	@ApiModelProperty("到达说明")
	private String reachRemark;
	/**
	 * 
	 *
	 */
	@ApiModelProperty("到达说明附带的图")
	private String reachImg;

	@ApiModelProperty(value = "商品列表", name = "orderGoodsDetailListRes")
	private List<OrderGoodsDetailRes> orderGoodsDetailListRes;

	@ApiModelProperty(value = "评价状态: 0 未评论  1 已评论", name = "evaluateSts")
	private Integer evaluateSts;

}
