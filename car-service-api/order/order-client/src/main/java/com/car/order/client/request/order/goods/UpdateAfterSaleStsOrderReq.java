package com.car.order.client.request.order.goods;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UpdateAfterSaleStsOrderReq", description = "修改商品订单")
public class UpdateAfterSaleStsOrderReq {

	@ApiModelProperty(value = "订单uuid")
	@NotBlank(message = "请输入订单uuid")
	private String uuid;

	@ApiModelProperty(value = "售后状态:0 待预约; 1买家申请退款; 2已预约待上门(使用); 3退款中 ;4卖家标记已上门; 5已取消 ;6买家标记已上门; 7卖家标记已完成; 8买家标记已完成", name = "afterSaleSts")
	private Integer afterSaleSts;
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
}
