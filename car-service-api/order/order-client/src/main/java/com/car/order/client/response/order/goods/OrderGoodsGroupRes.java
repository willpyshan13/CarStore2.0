package com.car.order.client.response.order.goods;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "OrderGoodsGroupRes", description = "商品订单统计VO")
public class OrderGoodsGroupRes {

	@ApiModelProperty("单品订单")
	private BigDecimal goodsTotal;
	@ApiModelProperty("拼团订单")
	private BigDecimal groupbuyTotal;
	@ApiModelProperty("工位订单")
	private BigDecimal stationTotal;
	@ApiModelProperty("总数")
	private BigDecimal allTotal;
}
