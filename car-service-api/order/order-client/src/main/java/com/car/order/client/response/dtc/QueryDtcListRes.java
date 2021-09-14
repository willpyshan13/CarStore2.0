package com.car.order.client.response.dtc;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/17
 */
@Data
@ApiModel(value = "QueryDtcListRes", description = "查询dtc故障列表，返回VO")
public class QueryDtcListRes {

	@ApiModelProperty(value = "uuid", name = "uuid")
	private String uuid;

	@ApiModelProperty(value = "发布人uuid", name = "dtcIssuerUuid")
	private String dtcIssuerUuid;

	@ApiModelProperty(value = "dtc故障代码_前缀", name = "dtcCode")
	private String dtcCode;
	@ApiModelProperty(value = "dtc故障代码_中缀", name = "dtcCode2")
	private String dtcCode2;
	@ApiModelProperty(value = "dtc故障代码_后缀", name = "dtcCode3")
	private String dtcCode3;

	@ApiModelProperty(value = "dtc类型", name = "dtcType")
	private String dtcType;

	@ApiModelProperty(value = "dtc类型名称", name = "dtcTypeName")
	private String dtcTypeName;

	@ApiModelProperty(value = "dtc标题", name = "dtcDefinition")
	private String dtcDefinition;

	@ApiModelProperty(value = "dtc发布关联品牌(对应车辆品牌uuid)", name = "dtcBrandUuid")
	private String dtcBrandUuid;

	@ApiModelProperty("车型uuid")
	private String dtcModelUuid;

	@ApiModelProperty("品牌名称")
	private String dtcBrandUuidName;

	@ApiModelProperty("车型名称")
	private String dtcModelUuidName;

	@ApiModelProperty(value = "dtc购买金额", name = "dtcAmount")
	private BigDecimal dtcAmount;

	@ApiModelProperty(value = "创建时间", name = "createdTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdTime;

	@ApiModelProperty(value = "创建人", name = "createdBy")
	private String createdBy;

	@ApiModelProperty(value = "品牌名称", name = "configName")
	private String configName;

	@ApiModelProperty(value = "审核状态", name = "dtc_check_sts")
	private String dtcCheckSts;

	@ApiModelProperty("当前人看到本记录时，是否需要购买:0不需要购买;1需要购买")
	private Integer needBuy;
}
