package com.car.order.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.order.goods.UpdateAfterSaleStsOrderReq;
import com.car.order.client.request.order.goods.UpdateReserveOrderReq;
import com.car.order.client.request.order.groupbuy.CreateOrderGroupbuyReq;
import com.car.order.client.request.order.groupbuy.QueryOrderGroupbuyListReq;
import com.car.order.client.response.order.groupbuy.OrderGroupbuyRes;
import com.car.order.web.service.groupbuy.OrderGroupbuyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(tags = "团购订单管理")
@RequestMapping(value = "orderGroupbuy", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class OrderGroupbuyController {

	@Autowired
	private OrderGroupbuyService orderGroupbuyService;

	@PostMapping("create")
	@ApiOperation("创建团购订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "团购订单管理", operType = OperEnum.ADD, operDesc = "创建订单")
	public ResultRes<String> create(@RequestBody @Valid CreateOrderGroupbuyReq params) {
		return orderGroupbuyService.create(params);
	}

	@ApiOperation("订单预约")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "团购订单管理", operType = OperEnum.UPDATE, operModul = "订单预约")
	@RequestMapping(value = "updateReserve", method = RequestMethod.POST)
	public ResultRes<?> updateReserve(@RequestBody UpdateReserveOrderReq param) {
		return orderGroupbuyService.updateReserve(param);
	}

	@ApiOperation(value = "卖家修改状态", notes = "afterSaleSts:0卖家拒绝退款, 4卖家标记已上门 , 7卖家标记已完成")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "团购订单管理", operType = OperEnum.UPDATE, operModul = "卖家修改状态")
	@RequestMapping(value = "updateStoreSts", method = RequestMethod.POST)
	public ResultRes<?> updateStoreSts(@RequestBody UpdateAfterSaleStsOrderReq param) {
		return orderGroupbuyService.updateStoreSts(param);
	}

	@ApiOperation(value = "买家修改状态", notes = "afterSaleSts:1买家申请退款,  6买家标记已上门 ")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "团购订单管理", operType = OperEnum.UPDATE, operModul = "买家修改状态")
	@RequestMapping(value = "updateUserSts", method = RequestMethod.POST)
	public ResultRes<?> updateUserSts(@RequestBody UpdateAfterSaleStsOrderReq param) {
		return orderGroupbuyService.updateUserSts(param);
	}

	@PostMapping("queryOrderGroupbuy/{uuid}")
	@ApiOperation("查询单条团购订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "团购订单管理", operType = OperEnum.SELECT, operDesc = "查询单条团购订单")
	public ResultRes<OrderGroupbuyRes> queryOrderGroupbuy(@PathVariable(name = "uuid") String uuid) {
		return orderGroupbuyService.queryByUuid(uuid);
	}

	@PostMapping("queryOrderGroupbuyList")
	@ApiOperation("查询团购列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "团购订单管理", operType = OperEnum.SELECT, operDesc = "查询团购订单列表")
	public PageRes<List<OrderGroupbuyRes>> queryOrderGroupbuyList(
			@RequestBody @Valid QueryOrderGroupbuyListReq params) {
		return orderGroupbuyService.queryOrderGroupbuyList(params);
	}

	@GetMapping(value = "updateGroupbuyEnd/{groupbuyUuid}")
	@SysOperLog(operModul = "团购订单管理", operType = OperEnum.UPDATE, operDesc = "团购结束时订单处理")
	@ApiIgnore
	public ResultRes<String> updateGroupbuyEnd(@PathVariable(value = "groupbuyUuid") String groupbuyUuid) {
		return orderGroupbuyService.updateGroupbuyEnd(groupbuyUuid);
	}

}
