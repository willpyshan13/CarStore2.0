package com.car.account.web.controller.vehicle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.car.account.client.response.vehicle.config.ConfigRes;
import com.car.account.web.service.vehicle.VehicleConfigService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统用户
 * @author xlj
 */
@Slf4j
@Api(value = "VehicleConfigController", tags = "车辆配置管理")
@RequestMapping(value = "/vehicleConfig", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class VehicleConfigController {

	@Autowired
	VehicleConfigService vehicleConfigService;

	/**
	 * 查询所有区域信息
	 * @return
	 */
	@RequestMapping(value = "/queryAllList", method = RequestMethod.GET)
	@ApiOperation(value = "查询所有车辆配置信息", nickname = "queryAllList")
	@SysOperLog(operModul = "车辆配置管理", operType = OperEnum.SELECT, operDesc = "查询所有车辆配置信息")
	public ResultRes<List<ConfigRes>> queryAllList() {
		return vehicleConfigService.queryAllList();
	}

	/**
	 * 查询父节点所有子节点信息
	 * @return
	 */
	@RequestMapping(value = "/queryList/{parentUuid}", method = RequestMethod.GET)
	@ApiOperation(value = "查询父节点下车辆子节点，根节点传-1", nickname = "queryListByParent")
	@SysOperLog(operModul = "车辆配置管理", operType = OperEnum.SELECT, operDesc = "查询父节点下车辆子节点")
	public ResultRes<List<ConfigRes>> queryListByParent(@PathVariable(name = "parentUuid") String parentUuid) {
		return vehicleConfigService.queryListByParent(parentUuid);
	}

	/**
	 * 查询父节点所有孙子节点信息
	 * @return
	 */
	@RequestMapping(value = "/queryListNextByParent/{parentUuid}", method = RequestMethod.GET)
	@ApiOperation(value = "查询父节点下孙子节点，根节点传-1", nickname = "queryListNextByParent")
	@SysOperLog(operModul = "车辆配置管理", operType = OperEnum.SELECT, operDesc = "查询父节点所有孙子节点信息")
	public ResultRes<List<ConfigRes>> queryListNextByParent(@PathVariable(name = "parentUuid") String parentUuid) {
		return vehicleConfigService.queryListNextByParent(parentUuid);
	}

	/**
	 * 根据uuid查询车辆节点信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/queryConfig/{uuid}", method = RequestMethod.GET)
	@ApiOperation(value = "根据uuid查询车辆节点信息", nickname = "queryConfig")
	@SysOperLog(operModul = "车辆配置管理", operType = OperEnum.SELECT, operDesc = "根据uuid查询车辆节点信息")
	public ResultRes<ConfigRes> queryConfig(@PathVariable(name = "uuid") String uuid) {
		return vehicleConfigService.queryConfig(uuid);
	}

	/**
	 * 批量导入车辆配置信息
	 * @param
	 * @return
	 */
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@RequestMapping(value = "/batchImport", method = RequestMethod.POST)
	@ApiOperation(value = "批量导入车辆配置信息", nickname = "batchImport")
	@SysOperLog(operModul = "车辆配置管理", operType = OperEnum.UPLOAD, operDesc = "批量导入车辆配置信息")
	public ResultRes batchImport(@RequestParam("file") MultipartFile file) {
		return vehicleConfigService.batchImport(file);
	}

	/**
	 * logo文件夹处理
	 * @param uuid
	 * @return
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header") })
	@RequestMapping(value = "/logoRenameToJpg", method = RequestMethod.GET)
	@ApiOperation(value = "logo文件夹处理")
	@SysOperLog(operModul = "车辆配置管理", operType = OperEnum.SELECT, operDesc = "logo文件夹处理")
	public ResultRes<?> logoRenameToJpg() {
		vehicleConfigService.logoRenameToJpg();
		return ResultRes.success();
	}
}
