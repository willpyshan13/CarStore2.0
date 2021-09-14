package com.car.account.web.controller.banner;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.account.client.request.banner.BannerReq;
import com.car.account.client.request.banner.QueryBannerListReq;
import com.car.account.client.response.banner.BannerRes;
import com.car.account.web.service.banner.BannerService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "banner管理")
@RequestMapping(value = "/banner", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class BannerController {

	@Autowired
	private BannerService bannerService;

	@PostMapping(value = "/addBanner")
	@ApiOperation(value = "新增banner", nickname = "addBanner")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "banner管理", operType = OperEnum.ADD, operDesc = "新增banner")
	public ResultRes<BannerRes> addBanner(@RequestBody @Valid BannerReq params) {
		BannerRes receiveBannerRes = bannerService.addBanner(params);
		return ResultRes.success(receiveBannerRes);
	}

	@DeleteMapping(value = "/deleteBanner/{uuid}")
	@ApiOperation(value = "删除banner", nickname = "deleteBanner")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "banner管理", operType = OperEnum.DELETE, operDesc = "删除banner")
	public ResultRes<Void> deleteBanner(@PathVariable(name = "uuid") String uuid) {
		bannerService.disableBanner(uuid);
		return ResultRes.success();
	}

	@PutMapping(value = "/updateBanner")
	@ApiOperation(value = "修改banner", nickname = "updateBanner")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "banner管理", operType = OperEnum.UPDATE, operDesc = "修改banner")
	public ResultRes<BannerRes> updateBanner(@RequestBody @Valid BannerReq params) {
		BannerRes receiveBannerRes = bannerService.updateBanner(params);
		return ResultRes.success(receiveBannerRes);
	}

	@PostMapping("queryBannerList")
	@ApiOperation("查询Banner列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "banner管理", operType = OperEnum.SELECT, operDesc = "查询Banner列表")
	public PageRes<List<BannerRes>> queryBannerList(@RequestBody @Valid QueryBannerListReq queryBannerListReq) {
		return bannerService.queryBannerList(queryBannerListReq);
	}

	@GetMapping(value = "/bannerDetail/{uuid}")
	@ApiOperation(value = "查询banner详情", nickname = "bannerDetail")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "header") })
	@SysOperLog(operModul = "banner管理", operType = OperEnum.SELECT, operDesc = "查询banner详情")
	public ResultRes<BannerRes> bannerDetail(@PathVariable(name = "uuid") String uuid) {

		BannerRes receiveBannerRes = bannerService.queryBannerDetail(uuid);
		return ResultRes.success(receiveBannerRes);
	}

}
