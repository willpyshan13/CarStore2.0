package com.car.order.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.car.order.client.request.order.order.AddOrderVehicleStationReq;
import com.car.order.client.request.order.order.QueryOrderVehicleStationListReq;
import com.car.order.client.request.order.order.UpdateOrderVehicleStationAfterSaleStsReq;
import com.car.order.client.response.order.OrderVehicleStationRes;
import com.car.order.web.service.order.OrderVehicleStationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "orderVehicleStatio", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
@Api(value = "OrderVehicleStationController", tags = "C端车主发起的工位订单")
public class OrderVehicleStationController {

	@Autowired
	private OrderVehicleStationService orderVehicleStationService;

	@ApiOperation(value = "车主发起订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "C端车主发起的工位订单", operType = OperEnum.UPDATE, operModul = "店铺修改状态")
	@RequestMapping(value = "createOrder", method = RequestMethod.POST)
	public ResultRes<?> createOrder(@RequestBody AddOrderVehicleStationReq param) {
		return orderVehicleStationService.createOrder(param);
	}

	@ApiOperation(value = "店铺修改状态", notes = "afterSaleSts:3卖家拒绝退款,6卖家标记已完成")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "C端车主发起的工位订单", operType = OperEnum.UPDATE, operModul = "店铺修改状态")
	@RequestMapping(value = "updateStoreSts", method = RequestMethod.POST)
	public ResultRes<?> updateStoreSts(@RequestBody UpdateOrderVehicleStationAfterSaleStsReq param) {
		return orderVehicleStationService.updateStoreSts(param);
	}

	@ApiOperation(value = "车主修改状态", notes = "afterSaleSts:2买家申请退款，5买家取消订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "C端车主发起的工位订单", operType = OperEnum.UPDATE, operModul = "车主修改状态")
	@RequestMapping(value = "updateUserSts", method = RequestMethod.POST)
	public ResultRes<?> updateUserSts(@RequestBody UpdateOrderVehicleStationAfterSaleStsReq param) {
		return orderVehicleStationService.updateUserSts(param);
	}

	@PostMapping("updateGiveMe/{uuid}")
	@ApiOperation("店铺抢单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "C端车主发起的工位订单", operType = OperEnum.SELECT, operDesc = "店铺抢单")
	public ResultRes<?> updateGiveMe(@PathVariable(name = "uuid") String uuid) {
		return orderVehicleStationService.updateGiveMe(uuid);
	}

	@PostMapping("queryByUuid/{uuid}")
	@ApiOperation("查询单条工位订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "C端车主发起的工位订单", operType = OperEnum.SELECT, operDesc = "查询单条工位订单")
	public ResultRes<OrderVehicleStationRes> queryByUuid(@PathVariable(name = "uuid") String uuid) {
		return orderVehicleStationService.queryByUuid(uuid);
	}

	@PostMapping("queryOrderVehicleStationList")
	@ApiOperation("查询工位列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "C端车主发起的工位订单", operType = OperEnum.SELECT, operDesc = "查询工位列表")
	public PageRes<List<OrderVehicleStationRes>> queryOrderVehicleStationList(
			@RequestBody @Valid QueryOrderVehicleStationListReq params) {
		return orderVehicleStationService.queryOrderVehicleStationList(params);
	}

}
