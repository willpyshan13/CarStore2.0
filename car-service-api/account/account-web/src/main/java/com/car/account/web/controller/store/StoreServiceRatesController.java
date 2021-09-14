package com.car.account.web.controller.store;


import com.car.account.client.response.store.StoreServiceRatesRes;
import com.car.account.web.service.store.StoreServiceRatesService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "StoreServiceRatesController", tags = "店铺服务费")
@RequestMapping(value = "/storeServiceRates", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class StoreServiceRatesController {

    @Autowired
    private StoreServiceRatesService storeServiceRatesService;

    /**
     * 查询一个特定店铺的特定分类的 有效 配置，如果没有有效的，则返回null
     * @param
     * @return
     */
    @GetMapping(value = "/queryStoreServiceRates")
    @ApiOperation(value = "查询店铺详情", nickname = "queryStoreServiceRates")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "店铺管理", operType = OperEnum.SELECT, operDesc = "查询店铺详情")
    ResultRes<StoreServiceRatesRes> queryStoreServiceRates(@RequestParam("storeUuid") String storeUuid,
                                                           @RequestParam("goodsParentUuid") String goodsParentUuid){

        return storeServiceRatesService.queryStoreServiceRates(storeUuid,goodsParentUuid);
    }

}
