package com.car.order.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.order.goods.CreateOrderReq;
import com.car.order.client.request.order.goods.PreOrderReq;
import com.car.order.client.request.order.goods.QueryOrderGoodsListReq;
import com.car.order.client.request.order.goods.UpdateAfterSaleStsOrderReq;
import com.car.order.client.request.order.goods.UpdateDeliveryOrder;
import com.car.order.client.request.order.goods.UpdateReserveOrderReq;
import com.car.order.client.request.order.goods.UpdateServerOrderReq;
import com.car.order.client.response.order.goods.OrderGoodsGroupRes;
import com.car.order.client.response.order.goods.OrderGoodsListRes;
import com.car.order.client.response.order.goods.OrderGoodsRes;
import com.car.order.client.response.order.goods.OrderGoodsReserveRes;
import com.car.order.client.response.order.goods.PreOrderRes;
import com.car.order.web.service.goods.OrderGoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouz
 * @date 2020/12/30
 */
@Slf4j
@Api(value = "OrderGoodsController", tags = "??????????????????")
@RequestMapping(value = "/orderGoods", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class OrderGoodsController {

	@Autowired
	OrderGoodsService orderGoodsService;

	/**
	 * ????????????????????????
	 * @param queryStoreListReq
	 * @return
	 */
	@PostMapping(value = "/queryOrderGoodsList")
	@ApiOperation(value = "????????????????????????", nickname = "queryOrderGoodsList")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "??????????????????", operType = OperEnum.SELECT, operDesc = "????????????????????????")
	public PageRes<List<OrderGoodsListRes>> queryOrderGoodsList(
			@RequestBody @Valid QueryOrderGoodsListReq queryStoreListReq) {
		return orderGoodsService.queryOrderGoodsList(queryStoreListReq);
	}

	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@GetMapping(value = "/queryOrderGoodsDetail/{uuid}")
	@ApiOperation(value = "????????????????????????", nickname = "queryOrderGoodsDetail")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "??????????????????", operType = OperEnum.SELECT, operDesc = "????????????????????????")
	public ResultRes<OrderGoodsRes> queryOrderGoodsDetail(@PathVariable(name = "uuid") String uuid) {
		return orderGoodsService.queryOrderGoodsDetail(uuid);
	}

	@ApiOperation(value = "?????????????????????", notes = "reserveDate?????????2021-01-01")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "??????????????????", operType = OperEnum.UPDATE, operModul = "?????????????????????")
	@RequestMapping(value = "queryReserveNum", method = RequestMethod.POST)
	public ResultRes<OrderGoodsReserveRes> queryReserveNum(String uuid, String reserveDate) {
		return orderGoodsService.queryReserveNum(uuid, reserveDate);
	}

	@ApiOperation("????????????")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "??????????????????", operType = OperEnum.UPDATE, operModul = "????????????")
	@RequestMapping(value = "updateReserve", method = RequestMethod.POST)
	public ResultRes<?> updateReserve(@RequestBody UpdateReserveOrderReq param) {
		return orderGoodsService.updateReserve(param);
	}

	@ApiOperation(value = "??????????????????", notes = "afterSaleSts:0??????????????????, 4????????????????????? , 7?????????????????????")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "??????????????????", operType = OperEnum.UPDATE, operModul = "??????????????????")
	@RequestMapping(value = "updateStoreSts", method = RequestMethod.POST)
	public ResultRes<?> updateStoreSts(@RequestBody UpdateAfterSaleStsOrderReq param) {
		return orderGoodsService.updateStoreSts(param);
	}

	@ApiOperation(value = "??????????????????", notes = "afterSaleSts:1??????????????????,  6????????????????????? ")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "??????????????????", operType = OperEnum.UPDATE, operModul = "??????????????????")
	@RequestMapping(value = "updateUserSts", method = RequestMethod.POST)
	public ResultRes<?> updateUserSts(@RequestBody UpdateAfterSaleStsOrderReq param) {
		return orderGoodsService.updateUserSts(param);
	}

	/**
	 * ????????????????????????
	 * @param exportReq
	 * @return
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "??????????????????", operType = OperEnum.SELECT, operModul = "????????????????????????")
	@ApiOperation(value = "????????????????????????", nickname = "exportOrderGoodsList")
	@RequestMapping(value = "/exportOrderGoodsList", method = RequestMethod.POST)
	public void exportStoreList(@RequestBody QueryOrderGoodsListReq exportReq, HttpServletResponse response)
			throws IOException, IllegalAccessException {
		orderGoodsService.exportOrderGoodsList(exportReq, response);
	}

	@PostMapping(value = "/confirmOrder")
	@ApiOperation(value = "????????????", nickname = "confirmOrder")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "????????????")
	public ResultRes<PreOrderRes> confirmOrder(@RequestBody @Valid PreOrderReq params) {
		return orderGoodsService.preOrder(params);
	}

	@PostMapping(value = "/order")
	@ApiOperation(value = "????????????", nickname = "order")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.ADD, operDesc = "??????")
	public ResultRes<String> order(@RequestBody @Valid CreateOrderReq params) {
		String orderUuid = orderGoodsService.createOrder(params);
		return ResultRes.success(orderUuid);
	}

	@PostMapping(value = "/updateGoodsServerOrder")
	@ApiOperation(value = "????????????????????????", nickname = "updateGoodsOrder")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.UPDATE, operDesc = "????????????????????????")
	public ResultRes<String> updateGoodsServerOrder(@RequestBody @Valid UpdateServerOrderReq req) {
		return orderGoodsService.updateGoodsOrder(req);
	}

	@PostMapping(value = "/updateGoodsDeliveryOrder")
	@ApiOperation(value = "????????????????????????", nickname = "updateGoodsDeliveryOrder")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.UPDATE, operDesc = "????????????????????????")
	public ResultRes<String> updateGoodsDeliveryOrder(@RequestBody @Valid UpdateDeliveryOrder req) {
		return orderGoodsService.updateGoodsDeliveryOrder(req);
	}

	@PostMapping(value = "/queryGoodsGroupCountUserApi")
	@ApiOperation("??????????????????API:????????????")
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "??????????????????API:????????????")
	public ResultRes<OrderGoodsGroupRes> queryGoodsGroupCountUserApi(
			@RequestParam("storeUserUuid") String storeUserUuid) {
		return orderGoodsService.queryGoodsGroupCountUser(storeUserUuid);
	}

	@PostMapping(value = "/queryGoodsGroupCount")
	@ApiOperation("??????????????????")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "??????????????????")
	public ResultRes<OrderGoodsGroupRes> queryGoodsGroupCount(
			@ApiParam(value = "yearMonth", format = "2021???8???") @RequestParam("yearMonth") String yearMonth) {
		return orderGoodsService.queryGoodsGroupCount(yearMonth);
	}

	@PostMapping(value = "/queryGoodsGroupAmount")
	@ApiOperation("??????????????????")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "??????????????????")
	public ResultRes<OrderGoodsGroupRes> queryGoodsGroupAmount(
			@ApiParam(value = "yearMonth", format = "2021???8???") @RequestParam("yearMonth") String yearMonth) {
		return orderGoodsService.queryGoodsGroupAmount(yearMonth);
	}
}
