package com.car.system.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.car.common.res.ResultRes;
import com.car.system.client.response.dict.DictionaryRes;
import com.car.system.web.model.BasicSet;
import com.car.system.web.service.SysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典管理
 * @author xlj
 */
@RestController
@RequestMapping("/dict")
@Slf4j
@Api(value = "DictionaryController", tags = "字典管理")
public class DictController {

	@Autowired
	SysDictService sysDictService;

	@GetMapping(value = "/queryListByType/{type}")
	@ApiOperation(value = "根据字典类型查询字典集合", nickname = "queryListByType")
	public ResultRes<List<DictionaryRes>> queryListByType(@PathVariable(name = "type") String type) {
		return sysDictService.queryListByType(type);
	}

	@GetMapping(value = "/queryByCode/{code}")
	@ApiOperation(value = "根据字典编码查询字典信息", nickname = "queryByCode")
	public ResultRes<DictionaryRes> queryByCode(@PathVariable(name = "code") String code) {
		return sysDictService.queryByCode(code);
	}

	@GetMapping(value = "/queryByUuid/{uuid}")
	@ApiOperation(value = "根据字典ID查询字典信息", nickname = "queryByUuid")
	public ResultRes<DictionaryRes> queryByUuid(@PathVariable(name = "uuid") String uuid) {
		return sysDictService.queryByUuid(uuid);
	}

	@GetMapping(value = "/queryByDescName")
	@ApiOperation(value = "根据字典描述查询字典信息", nickname = "queryByDescName")
	public ResultRes<String> queryByDescName(@RequestParam(name = "descName", required = true) String descName) {
		return sysDictService.queryByDescName(descName);
	}

	@GetMapping("/queryBasicSet")
	@ApiOperation("相关设置获取")
	public ResultRes<BasicSet> queryBasicSet() {
		return sysDictService.queryBasicSet();
	}

	@PostMapping("/updateBasicSet")
	@ApiOperation("相关设置更新")
	public ResultRes<?> updateBasicSet(@RequestBody BasicSet basicSet) {
		sysDictService.updateBasicSet(basicSet);
		return ResultRes.success();
	}
}
