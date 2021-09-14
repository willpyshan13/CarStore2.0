package com.car.system.web.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasicSet {

	/**
	 * 本对象在数据库中的LABLE_TYPE值
	 */
	public static final String LABLE_TYPE = "basic_set";

	@ApiModelProperty("共享技师费用设置->专家基础上门费")
	private BigDecimal val1;
	@ApiModelProperty("共享技师费用设置->普通技师基础上门费")
	private BigDecimal val2;
	@ApiModelProperty("共享技师费用设置->平台服务费收统一取比例")
	private BigDecimal val3;
	@ApiModelProperty("紧急需求工位费用设置->单小时费用")
	private BigDecimal val4;
	@ApiModelProperty("紧急需求工位费用设置->平台服务费收统一取比例")
	private BigDecimal val5;
	@ApiModelProperty("现场支持费用设置->统一基础上门费")
	private BigDecimal val6;
	@ApiModelProperty("现场支持费用设置->平台服务费统一收取比例")
	private BigDecimal val7;
	@ApiModelProperty("向专家提问费用设置->向专家提问统一费用")
	private BigDecimal val8;
	@ApiModelProperty("向专家提问费用设置->平台服务费收取金额")
	private BigDecimal val9;
	@ApiModelProperty("向大家提问抢答费用设置->发布抢单提问费用")
	private BigDecimal val10;
	@ApiModelProperty("向大家提问抢答费用设置->平台服务费统一收取金额")
	private BigDecimal val11;
	@ApiModelProperty("技术案例费用设置->购买技术案例统一价格")
	private BigDecimal val12;
	@ApiModelProperty("技术案例费用设置->平台服务费统一收取金额")
	private BigDecimal val13;
	@ApiModelProperty("DTC查询费用设置->单条DTC查询统一费用")
	private BigDecimal val14;
	@ApiModelProperty("补贴比例设置->提问")
	private BigDecimal val15;
	@ApiModelProperty("补贴比例设置->技术案例")
	private BigDecimal val16;
	@ApiModelProperty("补贴比例设置->DTC查询")
	private BigDecimal val17;
	@ApiModelProperty("补贴比例设置->共享技师订单")
	private BigDecimal val18;
	@ApiModelProperty("补贴比例设置->现场支持下单")
	private BigDecimal val19;

}
