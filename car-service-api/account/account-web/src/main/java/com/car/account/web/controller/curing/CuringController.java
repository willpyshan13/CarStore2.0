package com.car.account.web.controller.curing;

import com.car.account.client.request.curing.AddCuringReq;
import com.car.account.client.request.curing.QueryCuringListReq;
import com.car.account.client.response.curing.QueryCuringRes;
import com.car.account.web.service.curing.CuringService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/5/30
 */
@Slf4j
@Api(value = "CuringController", tags = "养护管理（爱车讲堂）")
@RequestMapping(value = "curing", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class CuringController {

    @Autowired
    private CuringService curingService;

    @PostMapping(value = "/addCuring")
    @ApiOperation(value = "新增养护管理（爱车讲堂）信息", nickname = "addCuring")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "养护管理（爱车讲堂）", operType = OperEnum.ADD, operDesc = "新增养护管理（爱车讲堂）信息")
    public ResultRes<String> addCuring(@RequestBody @Valid AddCuringReq req){
        return curingService.addCuring(req);
    }

    @PostMapping(value = "/queryCuringList")
    @ApiOperation(value = "查询养护管理（爱车讲堂）信息列表", nickname = "queryCuringList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "养护管理（爱车讲堂）", operType = OperEnum.SELECT, operDesc = "查询养护管理（爱车讲堂）信息列表")
    public PageRes<List<QueryCuringRes>> queryList(@RequestBody QueryCuringListReq req){
        return curingService.queryList(req);
    }

    @GetMapping(value = "/queryCuringInfo/{uuid}")
    @ApiOperation(value = "查询养护管理（爱车讲堂）信息详情", nickname = "queryCuringInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "养护管理（爱车讲堂）", operType = OperEnum.SELECT, operDesc = "查询养护管理（爱车讲堂）信息详情")
    public ResultRes<QueryCuringRes> queryCuringInfo(@PathVariable("uuid") String uuid){
        return curingService.queryCuringInfo(uuid);
    }

    @PutMapping(value = "/updateCuringInfo/{uuid}")
    @ApiOperation(value = "修改养护管理（爱车讲堂）信息", nickname = "updateCuringInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "养护管理（爱车讲堂）", operType = OperEnum.UPDATE, operDesc = "修改养护管理（爱车讲堂）信息")
    public ResultRes<String> updateCuringInfo(@PathVariable("uuid")String uuid, @RequestBody AddCuringReq req){
        return curingService.updateCuringInfo(uuid, req);
    }


    @DeleteMapping(value = "/deleteCuringInfo/{uuid}")
    @ApiOperation(value = "删除养护管理（爱车讲堂）信息", nickname = "deleteCuringInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "养护管理（爱车讲堂）", operType = OperEnum.UPDATE, operDesc = "删除养护管理（爱车讲堂）信息")
    public ResultRes<String> deleteCuringInfo(@PathVariable("uuid")String uuid){
        return curingService.deleteCuringInfo(uuid);
    }





}
