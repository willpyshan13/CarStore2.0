package com.car.order.web.controller.sence;

import com.alibaba.fastjson.JSON;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.scene.*;
import com.car.order.client.response.scene.QuerySceneOrderInfoRes;
import com.car.order.client.response.scene.QuerySceneOrderListRes;
import com.car.order.client.response.scene.StatisticsSceneOrderRes;
import com.car.order.web.service.sence.SceneOrderService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 现场订单信息
 *
 * @author cjw
 * @date 2021-02-26 22:08:48
 */


@Slf4j
@Api(value = "SceneOrderController", tags = "现场下单管理---前端调用")
@RequestMapping(value = "/sceneOrder", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class SceneOrderController {

    @Autowired
    private SceneOrderService sceneOrderService;


    @PostMapping(value = "/addSceneOrder")
    @ApiOperation(value = "新增现场订单", nickname = "addSceneOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.ADD, operDesc = "新增现场订单")
    public ResultRes<String> addSceneOrder(@RequestBody @Valid AddSceneOrderReq req){
        //log.info("addSceneOrder---------"+JSON.toJSONString(req));
        return sceneOrderService.addSceneOrder(req);
    }

    @GetMapping(value = "/grabbingOrders/{sceneOrderUuid}")
    @ApiOperation(value = "现场订单抢单", nickname = "grabbingOrders")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.UPDATE, operDesc = "现场订单抢单")
    public ResultRes<String> grabbingOrders(@PathVariable(name = "sceneOrderUuid") String sceneOrderUuid){
        return sceneOrderService.grabbingOrders(sceneOrderUuid);
    }


    @PostMapping(value = "/sceneOrderDescribe")
    @ApiOperation(value = "现场订单--技师提交说明", nickname = "sceneOrderDescribe")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.ADD, operDesc = "技师提交说明")
    public ResultRes<String> sceneOrderDescribe(@RequestBody @Valid SceneOrderDescribeReq req){
        return sceneOrderService.sceneOrderDescribe(req);
    }

    @PostMapping(value = "/sceneOrderConfirm")
    @ApiOperation(value = "现场订单--客户确定", nickname = "sceneOrderConfirm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.UPDATE, operDesc = "客户确定")
    public ResultRes<String> sceneOrderConfirm(@RequestBody @Valid  SceneOrderConfirmReq req){
        return sceneOrderService.sceneOrderConfirm(req);
    }

    @PostMapping(value = "/sceneSubmitPlan")
    @ApiOperation(value = "现场订单--提交维修方案", nickname = "sceneSubmitPlan")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.UPDATE, operDesc = "提交维修方案")
    public ResultRes<String> sceneSubmitPlan(@RequestBody @Valid AddSceneOrderServiceReq req){
        //log.info("sceneSubmitPlan---------"+ JSON.toJSONString(req));
        return sceneOrderService.sceneSubmitPlan(req);
    }

    @PostMapping(value = "/sceneSubmitPlanTwo")
    @ApiOperation(value = "现场订单--提交维修方案2", nickname = "sceneSubmitPlanTwo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.UPDATE, operDesc = "提交维修方案")
    public ResultRes<String> sceneSubmitPlanTwo(@RequestBody @Valid AddSceneOrderServiceTwoReq req){
        //log.info("sceneSubmitPlan---------"+ JSON.toJSONString(req));
        return sceneOrderService.sceneSubmitPlanTwo(req);
    }

    @PostMapping(value = "/querySceneOrderList")
    @ApiOperation(value = "查询现场订单列表", nickname = "querySceneOrderList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.SELECT, operDesc = "查询现场订单列表")
    public PageRes<List<QuerySceneOrderListRes>> querySceneOrderList(@RequestBody QuerySceneOrderListReq req){
        return sceneOrderService.querySceneOrderList(req);
    }

    @GetMapping(value = "/querySceneOrderInfo/{uuid}")
    @ApiOperation(value = "查询现场订单详情", nickname = "querySceneOrderInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.SELECT, operDesc = "查询现场订单详情")
    public ResultRes<QuerySceneOrderInfoRes> querySceneOrderInfo(@PathVariable(name = "uuid") String uuid){
        return sceneOrderService.querySceneOrderInfo(uuid);
    }


    @GetMapping(value = "/cancelOrder/{sceneOrderUuid}")
    @ApiOperation(value = "取消现场订单", nickname = "cancelOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.UPDATE, operDesc = "完成现场订单")
    public ResultRes<String> cancelOrder(@PathVariable(name = "sceneOrderUuid") String sceneOrderUuid,
                                         @ApiParam(value = "取消类型1 直接取消，2 接单后取消 3 提交服务方案后取消", required = true)@RequestParam("type") Integer type){
        return sceneOrderService.cancelOrderOrder(sceneOrderUuid,type);
    }

    @GetMapping(value = "/completeOrder/{sceneOrderUuid}")
    @ApiOperation(value = "完成现场订单", nickname = "completeOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.UPDATE, operDesc = "完成现场订单")
    public ResultRes<String> completeOrder(@PathVariable(name = "sceneOrderUuid") String sceneOrderUuid){
        return sceneOrderService.completeOrder(sceneOrderUuid);
    }

    @PostMapping(value = "/sceneOrderStatistics")
    @ApiOperation(value = "现场订单统计", nickname = "sceneOrderStatistics")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.UPDATE, operDesc = "现场订单统计")
    public ResultRes<StatisticsSceneOrderRes> sceneOrderStatistics(@RequestBody SceneOrderStatisticsReq sceneOrderStatisticsReq){
        return sceneOrderService.sceneOrderStatistics(sceneOrderStatisticsReq.getYear(),sceneOrderStatisticsReq.getMonth());
    }


    @GetMapping(value = "/reminderOrders/{sceneOrderUuid}")
    @ApiOperation(value = "现场订单催单", nickname = "reminderOrders")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "现场订单管理", operType = OperEnum.UPDATE, operDesc = "现场订单催单")
    public ResultRes<String> reminderOrders(@PathVariable(name = "sceneOrderUuid") String sceneOrderUuid){


        return sceneOrderService.reminderOrders(sceneOrderUuid);
    }







}
