package com.car.order.web.controller.sence;

import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.scene.*;
import com.car.order.client.response.scene.QuerySceneOrderInfoRes;
import com.car.order.client.response.scene.QuerySceneOrderListRes;
import com.car.order.client.response.scene.StatisticsSceneOrderRes;
import com.car.order.web.service.sence.SceneOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 现场订单信息
 *
 * @author cjw
 * @date 2021-02-26 22:08:48
 */


@Slf4j
@Api(value = "SceneOrderBackController", tags = "现场下单管理---后台调用")
@RequestMapping(value = "/sceneOrderBack", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class SceneOrderBackController {

    @Autowired
    private SceneOrderService sceneOrderService;



    @PostMapping(value = "/querySceneOrderList")
    @ApiOperation(value = "查询现场订单列表", nickname = "querySceneOrderList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.SELECT, operDesc = "查询现场订单列表")
    public PageRes<List<QuerySceneOrderListRes>> querySceneOrderList(@RequestBody QuerySceneOrderListsReq req){
        return sceneOrderService.querySceneOrderLists(req);
    }



    @GetMapping(value = "/querySceneOrderInfo/{uuid}")
    @ApiOperation(value = "查询现场订单详情", nickname = "querySceneOrderList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.SELECT, operDesc = "查询现场订单列表")
    public ResultRes<QuerySceneOrderInfoRes> querySceneOrderInfo(@PathVariable(name = "uuid") String uuid){
        return sceneOrderService.querySceneOrderInfo(uuid);
    }




}
