package com.car.order.web.model.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.car.common.datasource.model.BaseModelInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "order_vehicle_station")
public class OrderVehicleStation extends BaseModelInfo {

	/**
	 * 订单编号
	 */
	@Column(name = "order_num")
	private String orderNum;
	/**
	 * 发单人（车主）
	 */
	@Column(name = "vehicle_user_uuid")
	private String vehicleUserUuid;
	/**
	 * 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败 6：已完成
	 */
	@Column(name = "order_sts")
	private Integer orderSts;

	/**
	 * 售后状态:0待抢单；1已抢单 2买家申请退款 3已预约待上门(使用) 4退款中 5已取消 6卖家标记已完成 7买家标记已完成
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
	 * 接单人（店铺）
	 */
	@Column(name = "store_user_uuid")
	private String storeUserUuid;

	/**
	 * 接单的店铺
	 */
	@Column(name = "store_Uuid")
	private String storeUuid;
	/**
	 * 车辆品牌
	 */
	@Column(name = "vehicle_brand")
	private String vehicleBrand;
	/**
	 * 车型类型
	 */
	@Column(name = "vehicle_model")
	private String vehicleModel;
	/**
	 * 需求描述
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 视频/图片
	 */
	@Column(name = "img_Video")
	private String imgVideo;
	/**
	 * 需要服务日期
	 */
	@Column(name = "use_date")
	private Date useDate;
	/**
	 * 需要服务时间段
	 */
	@Column(name = "use_time")
	private String useTime;
	/**
	 * 实际服务时间(已完成时间点)
	 */
	@Column(name = "actual_date")
	private Date actualDate;
	/**
	 * 时长
	 */
	@Column(name = "duration")
	private Integer duration;
	/**
	 * 时价
	 */
	@Column(name = "duration_price")
	private BigDecimal durationPrice;
	/**
	 *总价
	 */
	@Column(name = "price")
	private BigDecimal price;

	/**
	 *省,对应地区表uuid
	 */
	@Column(name = "address_province")
	private String addressProvince;
	/**
	 * 市,对应地区表uuid
	 */
	@Column(name = "address_city")
	private String addressCity;
	/**
	 * 县镇,对应地区表uuid
	 */
	@Column(name = "address_county")
	private String addressCounty;

	/**
	 * 平台服务费用
	 */
	@Column(name = "platform_service_money")
	private BigDecimal platformServiceMoney;

	/**
	 * 本单应结算给店铺金额
	 */
	@Column(name = "store_fee")
	private BigDecimal storeFee;
}
