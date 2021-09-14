package com.car.account.web.controller.profit;

import com.alibaba.fastjson.JSON;
import com.car.account.client.request.platform.PlatformStreamReq;
import com.car.account.client.request.profit.AddProfitReq;
import com.car.account.client.request.profit.ProfitStreamReq;
import com.car.account.web.service.profit.ProfitService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import com.car.common.utils.TokenHelper;
import com.car.order.client.request.scene.SceneOrderStatisticsReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyp
 * @date 2021/1/27 21:27
 */
@Slf4j
@Api(value = "ProfitStreamController", tags = "收益管理")
@RequestMapping(value = "/profitStream", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class ProfitStreamController {

    @Autowired
    ProfitService profitService;
    /**
     * 添加平台流水
     * @param addProfitReq
     * @return
     */
    @PostMapping(value = "/addProfit")
    @ApiOperation(value = "添加账户流水", nickname = "addPlatfrom")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "账户流水", operType = OperEnum.ADD, operDesc = "添加账户流水")
    public ResultRes<String> addProfit(@RequestBody AddProfitReq addProfitReq){

        return  profitService.addProfit(addProfitReq);
    }

    /**
     * 统计收入总汇
     * @param
     * @return
     */
    @PostMapping(value = "/selectStatisticsIncomeAmount")
    @ApiOperation(value = "我的钱包-统计收入总汇 ", nickname = "selectStatisticsIncomeAmount")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "账户流水", operType = OperEnum.SELECT, operDesc = "统计收入总汇 ")
    public ResultRes<Map> selectStatisticsIncomeAmount(@RequestBody SceneOrderStatisticsReq sceneOrderStatisticsReq, HttpServletRequest httpServletRequest){
        log.info("token========="+httpServletRequest.getHeader("token"));
        String userUuid = TokenHelper.getUserUuid();
        log.info(userUuid+"+++++++TokenHelper   ===="+ JSON.toJSONString( TokenHelper.getLoginToken()));
        return  profitService.statisticsIncomeAmount(sceneOrderStatisticsReq);
    }



    /**
     * 统计支出总汇
     * @param
     * @return
     */
    @PostMapping(value = "/selectStatisticsPayAmount")
    @ApiOperation(value = "我的钱包-统计支出总汇 ", nickname = "statisticsPayAmount")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "账户流水", operType = OperEnum.SELECT, operDesc = "统计支出总汇 ")
    public ResultRes<Map> statisticsPayAmount(@RequestBody SceneOrderStatisticsReq sceneOrderStatisticsReq){

        return  profitService.statisticsPayAmount(sceneOrderStatisticsReq);
    }

    /**
     * 查询统计数据
     * @param
     * @return
     */
    @PostMapping(value = "/statisticsAmountByType")
    @ApiOperation(value = "查询统计数据 ", nickname = "statisticsAmountByType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "账户流水", operType = OperEnum.SELECT, operDesc = "查询统计数据 ")
    public ResultRes<List<Map>> statisticsAmountByType(@RequestBody SceneOrderStatisticsReq sceneOrderStatisticsReq){

        return  profitService.statisticsAmountByTypeList(sceneOrderStatisticsReq);
    }



    /**
     * 查询统计数据
     * @param
     * @return
     */
    @PostMapping(value = "/statisticsAmount")
    @ApiOperation(value = "查询金额 ", nickname = "statisticsAmount")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "账户流水", operType = OperEnum.SELECT, operDesc = "查询金额 ")
    public ResultRes<BigDecimal> statisticsAmount(@RequestBody ProfitStreamReq profitStreamReq){

        return  profitService.statisticsAmount(profitStreamReq);
    }


}
