package com.car.order.client.request.order.groupbuy;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CreateOrderGroupbuyReq {

	@NotEmpty
	@ApiModelProperty("团购ID")
	private String groupbuyUuid;

	@NotNull
	@Min(value = 1)
	@Max(value = 1000)
	@ApiModelProperty(value = "购买数量")
	private Integer num;

	@Length(max = 200)
	@ApiModelProperty(value = "订单备注")
	private String remark;

	@ApiModelProperty(value = "使用的优惠券uuid")
	private String couponUuid;

}
