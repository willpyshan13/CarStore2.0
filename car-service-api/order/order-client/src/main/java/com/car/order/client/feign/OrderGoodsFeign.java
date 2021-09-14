package com.car.order.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import com.car.order.client.response.order.goods.OrderGoodsGroupRes;

import io.swagger.annotations.ApiOperation;

@FeignClient(value = "order/orderGoods")
public interface OrderGoodsFeign {

	@PostMapping(value = "/queryGoodsGroupCountUserApi")
	@ApiOperation("订单数量统计API:内部使用")
	@SysOperLog(operModul = "订单管理", operType = OperEnum.SELECT, operDesc = "订单数量统计API:内部使用")
	public ResultRes<OrderGoodsGroupRes> queryGoodsGroupCountUserApi(
			@RequestParam("storeUserUuid") String storeUserUuid);
}
