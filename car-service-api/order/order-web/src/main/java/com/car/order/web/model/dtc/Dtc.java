package com.car.order.web.model.dtc;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import com.car.common.datasource.model.BaseModelInfo;

import lombok.Data;

/**
 * @author zhouz
 * @date 2021/2/17
 */
@Data
@Table(name = "dtc")
public class Dtc extends BaseModelInfo {

	/**
	 * 发布人uuid
	 */
	@Column(name = "dtc_issuer_uuid")
	private String dtcIssuerUuid;

	/**
	 * dtc故障代码前缀
	 */
	@Column(name = "dtc_code")
	private String dtcCode;
	/**
	 * dtc故障代码中缀
	 */
	@Column(name = "dtc_code2")
	private String dtcCode2;
	/**
	 * dtc故障代码后缀
	 */
	@Column(name = "dtc_code3")
	private String dtcCode3;

	/**
	 * dtc标题
	 */
	@Column(name = "dtc_definition")
	private String dtcDefinition;

	/**
	 * dtc类型，对应字典表
	 */
	@Column(name = "dtc_type")
	private String dtcType;

	/**
	 * dtc发布关联品牌(对应车辆品牌uuid)
	 */
	@Column(name = "dtc_brand_uuid")
	private String dtcBrandUuid;

	/**
	 * 车型
	 */
	@Column(name = "dtc_Model_Uuid")
	private String dtcModelUuid;

	/**
	 * dtc购买金额
	 */
	@Column(name = "dtc_amount")
	private BigDecimal dtcAmount;

	/**
	 * 发布人类型：0：后台发布，1：技师 ，2：店铺
	 */
	@Column(name = "dtc_issuer_type")
	private Integer dtcIssuerType;

	/**
	 * 审核状态:0 待审核 1 审核通过 2 审核驳回
	 */
	@Column(name = "dtc_check_sts")
	private Integer dtcCheckSts;

	/**
	 * 具体内容
	 */
	@Column(name = "dtc_remarks")
	private String dtcRemarks;
}
