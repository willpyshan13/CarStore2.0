package com.car.order.web.model.goods;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.car.common.datasource.model.BaseModelInfo;

import lombok.Data;

/**
 * @author zhouz
 * @date 2020/12/22
 */
@Data
@Table(name = "goods")
public class Goods extends BaseModelInfo {

	/**
	 * 商品名称
	 */
	@Column(name = "goods_name")
	private String goodsName;

	/**
	 * 店铺uuid
	 */
	@Column(name = "store_uuid")
	private String storeUuid;

	/**
	 * 发布人uuid
	 */
	@Column(name = "store_user_uuid")
	private String storeUserUuid;

	/**
	 * 父类型 一级分类
	 */
	@Column(name = "parent_type")
	private String parentType;

	/**
	 * 二级分类
	 */
	@Column(name = "sub_type")
	private String subType;
	/**
	 *  三级分类
	 */
	@Column(name = "goods_type")
	private String goodsType;

	/**
	 * 工时费 / 服务费
	 */
	@Column(name = "man_hour_cost")
	private BigDecimal manHourCost;

	/**
	 * 材料费 / 快递费
	 */
	@Column(name = "materials_expenses")
	private BigDecimal materialsExpenses;

	/**
	 * 销量
	 */
	@Column(name = "sales_num")
	private Integer salesNum;

	/**
	 * 库存
	 */
	@Column(name = "surplus_num")
	private Integer surplusNum;

	/**
	 * 销售状态:0 库存 下架 1 在售 上架
	 */
	@Column(name = "sell_sts")
	private Integer sellSts;

	/**
	 * 描述
	 */
	@Column(name = "goods_describe")
	private String goodsDescribe;

	/**
	 * 购买须知
	 */
	@Column(name = "notes")
	private String notes;

	/**
	 * 售价
	 */
	@Column(name = "amt")
	private BigDecimal amt;
	/**
	 * 原价
	 */
	@Column(name = "source_amt")
	private BigDecimal sourceAmt;

	/**
	 * 平台服务费用
	 */
	@Column(name = "platform_service_money")
	private BigDecimal platformServiceMoney;

	/**
	 * 平台补贴
	 */
	@Column(name = "platform_subsidy")
	private BigDecimal platformSubsidy;

	/**
	 * 车辆品牌
	 */
	@Column(name = "vehicle_brand")
	private String vehicleBrand;
	/**
	 * 年款
	 */
	@Column(name = "vehicle_year")
	private String vehicleYear;

	/**
	 *排量
	 */
	@Column(name = "vehicle_capacity")
	private String vehicleCapacity;

	/**
	 * 车型类型
	 */
	@Column(name = "vehicle_model")
	private String vehicleModel;

	/**
	 * 审核状态：0待审核；1审核通过；2审核驳回
	 */
	@Column(name = "check_sts")
	private Integer checkSts;
	/**
	 *配送方式 0快递,1到店服务,2上门服务
	 */
	@Column(name = "receive_method")
	private Integer receiveMethod;

	/**
	 * 上午可预约数
	 */
	@Column(name = "am_serve_num")
	private Integer amServeNum;
	/**
	 * 下午可预约数
	 */
	@Column(name = "pm_serve_num")
	private Integer pmServeNum;
	/**
	 * 晚间可预约数
	 */
	@Column(name = "nm_serve_num")
	private Integer nmServeNum;

	/**
	 * 上门服务费
	 */
	@Column(name = "visit_fee")
	private BigDecimal visitFee;

	/**
	 * 使用时长
	 */
	@Column(name = "use_duration")
	private Integer useDuration;
	/**
	 * 纬度
	 */
	@Column(name = "latitude")
	private Float latitude;
	/**
	 * 经度
	 */
	@Column(name = "longitude")
	private Float longitude;
	/**
	 * 地址文本描述
	 */
	@Column(name = "addr")
	private String addr;
	/**
	 * 工位累计使用年限
	 */
	@Column(name = "years_use")
	private Integer yearsUse;
	/**
	 *定期维护:0=否；1=是
	 */
	@Column(name = "maintain_sts")
	private Integer maintainSts;
	/**
	 * 最近维护时间
	 */
	@Column(name = "maintain_date")
	private Date maintainDate;
	/**
	 * 维护方法:0=自行；1=第三方
	 */
	@Column(name = "maintain_fn")
	private Integer maintainFn;
}
