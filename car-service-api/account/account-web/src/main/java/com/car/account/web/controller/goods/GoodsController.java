package com.car.account.web.controller.goods;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.car.account.client.request.goods.AddGoodsReq;
import com.car.account.client.request.goods.CalGoodsReq;
import com.car.account.client.request.goods.QueryGoodsListReq;
import com.car.account.client.request.goods.QueryStoreGoodsListReq;
import com.car.account.client.request.goods.UpdateGoodsReq;
import com.car.account.client.response.goods.CalGoodsRes;
import com.car.account.client.response.goods.GoodsRes;
import com.car.account.client.response.store.QueryStoreListRes;
import com.car.account.web.mapper.store.StoreUserMapper;
import com.car.account.web.model.store.StoreUser;
import com.car.account.web.service.goods.GoodsService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.TokenHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouz
 * @date 2020/12/22
 */
@Slf4j
@Api(value = "GoodsController", tags = "????????????")
@RequestMapping(value = "/goods", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class GoodsController {

	@Autowired
	GoodsService goodsService;
	@Resource
	StoreUserMapper storeUserMapper;

	/**
	 * ????????????
	 * @param addGoodsReq
	 * @return
	 */
	@PostMapping(value = "/addGoods")
	@ApiOperation(value = "????????????", nickname = "addGoods")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.ADD, operDesc = "????????????")
	public ResultRes<String> addGoods(@RequestBody @Valid AddGoodsReq addGoodsReq) {
		return goodsService.addGoods(addGoodsReq);
	}

	/**
	 * ????????????
	 * @param uuid
	 * @return
	 */
	@DeleteMapping(value = "/deleteGoods/{uuid}")
	@ApiOperation(value = "????????????", nickname = "deleteGoods")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.DELETE, operDesc = "????????????")
	public ResultRes<String> deleteGoods(@PathVariable(name = "uuid") String uuid) {
		return goodsService.deleteGoods(uuid);
	}

	/**
	 * ????????????
	 * @param updateGoodsReq
	 * @return
	 */
	@PutMapping(value = "/updateGoods")
	@ApiOperation(value = "????????????(?????????)", nickname = "updateGoods")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.UPDATE, operDesc = "????????????")
	public ResultRes<GoodsRes> updateGoods(@RequestBody @Valid UpdateGoodsReq updateGoodsReq) {
		return goodsService.updateGoods(updateGoodsReq);
	}

	/**
	 * ????????????
	 * @param updateGoodsReq
	 * @return
	 */
	@PutMapping(value = "/updateGoodsSimplified")
	@ApiOperation(value = "????????????(??????)", nickname = "updateGoods", notes = "?????????????????????????????????????????????????????????platformSubsidy???????????????????????????????????????????????????")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.UPDATE, operDesc = "????????????")
	public ResultRes<GoodsRes> updateGoodsSimplified(@RequestBody @Valid UpdateGoodsReq updateGoodsReq) {
		return goodsService.updateGoodsSimplified(updateGoodsReq);
	}

	/**
	 * ??????????????????
	 * @param queryGoodsListReq
	 * @return
	 */
	@PostMapping(value = "/queryGoodsList")
	@ApiOperation(value = "??????????????????", nickname = "queryGoodsList")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "??????????????????")
	public PageRes<List<GoodsRes>> queryGoodsList(@RequestBody @Valid QueryGoodsListReq queryGoodsListReq) {
		return goodsService.queryGoodsList(queryGoodsListReq);
	}

	@PostMapping("queryStoreList")
	@ApiOperation("???????????????????????????????????????")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "??????????????????????????? ????????????")
	public PageRes<List<QueryStoreListRes>> queryStoreList(@RequestBody @Valid QueryGoodsListReq queryGoodsListReq) {
		return goodsService.queryStoreList(queryGoodsListReq);
	}

	@PostMapping(value = "/queryStoreGoodsList")
	@ApiOperation(value = "???????????????????????????", nickname = "queryStoreGoodsList")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "????????????????????????")
	public PageRes<List<GoodsRes>> queryStoreGoodsList(@RequestBody @Valid QueryStoreGoodsListReq queryGoodsListReq) {
		Integer sellSts = queryGoodsListReq.getSellSts();

		String userUuid = TokenHelper.getUserUuid();
		Integer userType = TokenHelper.getUserType();

		QueryGoodsListReq params = new QueryGoodsListReq();
		BeanUtils.copyProperties(queryGoodsListReq, params);
		params.setSellSts(sellSts);
		if (UserTypeEnum.store.getType().equals(userType)) {
			// ????????????uuid
			log.info("??????????????????>>>userType:{}", userType);
			StoreUser s = new StoreUser();
			s.setSts(StsEnum.ACTIVE.getValue());
			s.setUuid(userUuid);
			StoreUser storeUser = storeUserMapper.selectOne(s);
			if (null == storeUser) {
				log.error("???????????????????????????????????????>>>userUuid:{}", userUuid);
				throw new BusinessException(ResEnum.STORE_CONTACT_NOT_EXIST);
			}
			params.setStoreUuid(storeUser.getStoreUuid());

		} else if (Arrays.asList(UserTypeEnum.vehicle.getType(), UserTypeEnum.technician.getType())
				.contains(userType)) {

			log.info("?????????????????????????????????????????????????????????");
			return PageRes.success(Collections.emptyList(), 0, 0, 0);
		}
		return queryGoodsList(params);
	}

	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@GetMapping(value = "/queryGoodsDetail/{uuid}")
	@ApiOperation(value = "??????????????????", nickname = "queryGoodsDetail")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "??????????????????")
	public ResultRes<GoodsRes> queryGoodsDetail(@PathVariable(name = "uuid") String uuid) {
		return goodsService.queryGoods(uuid);
	}

	@PostMapping(value = "/calGoods")
	@ApiOperation(value = "????????????????????????", nickname = "calGoods")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "????????????", operType = OperEnum.SELECT, operDesc = "????????????????????????")
	public ResultRes<CalGoodsRes> calGoods(@RequestBody @Valid CalGoodsReq params) {
		CalGoodsRes res = goodsService.calGoods(params);
		return ResultRes.success(res);
	}

	/**
	 * ??????????????????
	 * @param exportReq
	 * @return
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@SysOperLog(operDesc = "????????????", operType = OperEnum.SELECT, operModul = "??????????????????")
	@ApiOperation(value = "??????????????????", nickname = "exportGoodsList")
	@RequestMapping(value = "/exportGoodsList", method = RequestMethod.POST)
	public void exportGoodsList(@RequestBody QueryGoodsListReq exportReq, HttpServletResponse response)
			throws IOException, IllegalAccessException {
		goodsService.exportGoodsList(exportReq, response);
	}
}
