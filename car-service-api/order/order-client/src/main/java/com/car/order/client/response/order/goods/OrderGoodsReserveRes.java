package com.car.order.client.response.order.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "订单可预约量VO")
public class OrderGoodsReserveRes {

	@ApiModelProperty("是否无限量预约")
	private boolean unlimited;

	@ApiModelProperty("上午限制量")
	private Integer amLimit;
	@ApiModelProperty("下午限制量")
	private Integer pmLimit;
	@ApiModelProperty("晚上限制量")
	private Integer nmLimit;
	@ApiModelProperty("上午已占用量")
	private Integer amOccupy;
	@ApiModelProperty("下午已占用量")
	private Integer pmOccupy;
	@ApiModelProperty("晚上已占用量")
	private Integer nmOccupy;
}
