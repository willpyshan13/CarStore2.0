package com.car.account.client.request.groupbuy;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "更新团购请求VO")
public class UpdateGroupbuyReq {

	@NotBlank(message = "[UpdateGroupbuyReq] uuid is required！")
	@ApiModelProperty(value = "uuid", name = "uuid")
	private String uuid;

	@NotBlank(message = "请输入售价！")
	@ApiModelProperty("售价")
	private BigDecimal price;

	@NotBlank(message = "请输入成团数量！")
	@ApiModelProperty("成团数量")
	private Integer userNum;

	@NotBlank(message = "请输入团购开始时间！")
	@ApiModelProperty("团购开始时间")
	private Date startTime;

	@NotBlank(message = "请输入团购结束时间！")
	@ApiModelProperty("团购结束时间")
	private Date endTime;

	@NotBlank(message = "请输入商品id！")
	@ApiModelProperty("商品uuid集合")
	private String goodsUuids;

	@ApiModelProperty("平台补贴")
	private BigDecimal platformSubsidy;
}
