package com.car.order.web.controller;

import com.alibaba.fastjson.JSON;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.order.instance.QueryOrderCaseFrontListReq;
import com.car.order.client.request.order.instance.QueryOrderCaseListReq;
import com.car.order.client.response.order.instance.OrderCaseDetailRes;
import com.car.order.client.response.order.instance.OrderCaseFrontListRes;
import com.car.order.client.response.order.instance.OrderCaseFrontRes;
import com.car.order.client.response.order.instance.OrderCaseInfoListRes;
import com.car.order.web.service.front.instance.OrderCaseFrontService;
import com.car.order.web.service.instance.OrderCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/31
 */
@Api(value = "OrderCaseFrontController", tags = "前端-案例订单管理")
@RequestMapping(value = "/orderCaseFront", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@Slf4j
public class OrderCaseFrontController {

    @Autowired
    OrderCaseFrontService orderCaseFrontService;

    /**
     * 查询案例订单列表
     * @param param
     * @return
     */
    @PostMapping(value = "/queryOrderCaseList")
    @ApiOperation(value = "查询案例订单列表", nickname = "queryOrderCaseList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "前端-案例订单管理", operType = OperEnum.SELECT, operDesc = "查询案例订单列表")
    public PageRes<List<OrderCaseFrontListRes>> queryOrderCaseList(@RequestBody @Valid QueryOrderCaseFrontListReq param){
        log.info("查询案例订单列表"+ JSON.toJSONString(param));
        return orderCaseFrontService.queryOrderCaseList(param);
    }

    /**
     * 查询案例订单详情
     * @param uuid
     * @return
     */
    @GetMapping(value = "/queryOrderCaseDetail/{uuid}")
    @ApiOperation(value = "查询案例订单详情", nickname = "queryOrderCaseDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "前端-案例订单管理", operType = OperEnum.SELECT, operDesc = "查询案例订单详情")
    public ResultRes<OrderCaseFrontRes> queryOrderCaseDetail(@PathVariable(name = "uuid") String uuid){
        return orderCaseFrontService.queryOrderCaseDetail(uuid);
    }

    @GetMapping(value = "/orderCase/{caseUuid}")
    @ApiOperation(value = "购买案例", nickname = "orderCase")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "前端-案例订单管理--------作废", operType = OperEnum.SELECT, operDesc = "购买案例")
    public ResultRes<String> order(@PathVariable(name = "caseUuid") String caseUuid){
        return orderCaseFrontService.order(caseUuid);
    }


    @GetMapping(value = "/orderCaseTwo/{caseUuid}")
    @ApiOperation(value = "购买案例", nickname = "orderCase")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "前端-案例订单管理", operType = OperEnum.SELECT, operDesc = "购买案例")
    public ResultRes<String> orderTwo(@PathVariable(name = "caseUuid") String caseUuid){
        return orderCaseFrontService.orderTwo(caseUuid);
    }

    @GetMapping(value = "/orderCaseNum/{uuid}")
    public ResultRes<Integer> orderCaseNum(@PathVariable(name = "uuid") String uuid){
        return orderCaseFrontService.orderCaseNum(uuid);
    }
}
