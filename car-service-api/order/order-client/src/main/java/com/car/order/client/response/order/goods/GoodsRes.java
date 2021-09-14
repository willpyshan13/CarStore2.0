package com.car.order.client.response.order.goods;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodsRes {

	/**
	 * 商品名称
	 */
	@ApiModelProperty("商品名称")
	private String goodsName;

	/**
	 * 
	 */
	@ApiModelProperty("店铺uuid")
	private String storeUuid;
	/**
	 * 
	 */
	@ApiModelProperty("发布人uuid")
	private String storeUserUuid;

	/**
	 * 
	 */
	@ApiModelProperty("父类型 一级分类")
	private String parentType;

	/**
	 * 
	 */
	@ApiModelProperty("二级分类")
	private String subType;

	/**
	 * 
	 */
	@ApiModelProperty("三级分类")
	private String goodsType;

	/**
	 *
	 */
	@ApiModelProperty(" 工时费 / 服务费")
	private BigDecimal manHourCost;

	/**
	 * 
	 */
	@ApiModelProperty("材料费 / 快递费")
	private BigDecimal materialsExpenses;

	@ApiModelProperty(value = "平台补贴", name = "platformSubsidy")
	private BigDecimal platformSubsidy;

	/**
	 * 
	 */
	@ApiModelProperty("销量")
	private Integer salesNum;

	/**
	 * 
	 */
	@ApiModelProperty("库存")
	private Integer surplusNum;

	/**
	 * 
	 */
	@ApiModelProperty("销售状态:0 库存 下架 1 在售 上架")
	private Integer sellSts;

	/**
	 * 
	 */
	@ApiModelProperty("描述")
	private String goodsDescribe;

	@ApiModelProperty(value = "售价")
	private BigDecimal amt;

	@ApiModelProperty(value = "原价")
	private BigDecimal sourceAmt;

	/**
	 * 
	 */
	@ApiModelProperty("平台服务费用")
	private BigDecimal platformServiceMoney;

	/**
	 * 
	 */
	@ApiModelProperty("车辆品牌")
	private BigDecimal vehicleBrand;

	/**
	 * 
	 */
	@ApiModelProperty("车型类型")
	private BigDecimal vehicleModel;

	/**
	 * 
	 */
	@ApiModelProperty("年款")
	private String vehicleYear;

	/**
	 *
	 */
	@ApiModelProperty("排量")
	private String vehicleCapacity;

	@ApiModelProperty("商品图")
	private String imgUrl;

	@ApiModelProperty("审核状态：0待审核；1审核通过；2审核驳回")
	private Integer checkSts;
	@ApiModelProperty("配送方式 0快递,1到店服务,2上门服务")
	private Integer receiveMethod;

	/**
	 * 
	 */
	@ApiModelProperty("上午可预约数")
	private Integer amServeNum;
	/**
	 *
	 */
	@ApiModelProperty(" 下午可预约数")
	private Integer pmServeNum;
	/**
	 * 
	 */
	@ApiModelProperty("晚间可预约数")
	private Integer nmServeNum;

	@ApiModelProperty("上门服务费")
	private BigDecimal visitFee;

	/**
	 *
	 */
	@ApiModelProperty(" 购买须知")
	private String notes;

	/**
	 * 
	 */
	@ApiModelProperty("使用时长")
	private Integer useDuration;
	/**
	 * 
	 */
	@ApiModelProperty("纬度")
	private Float latitude;
	/**
	 * 
	 */
	@ApiModelProperty("经度")
	private Float longitude;
	/**
	 *
	 */
	@ApiModelProperty(" 地址文本描述")
	private String addr;
	/**
	 * 
	 */
	@ApiModelProperty("工位累计使用年限")
	private Integer yearsUse;
	/**
	 *
	 */
	@ApiModelProperty("定期维护:0=否；1=是")
	private Integer maintainSts;
	/**
	 *
	 */
	@ApiModelProperty(" 最近维护时间")
	private Date maintainDate;
	/**
	 * 
	 */
	@ApiModelProperty("维护方法:0=自行；1=第三方")
	private Integer maintainFn;
}
