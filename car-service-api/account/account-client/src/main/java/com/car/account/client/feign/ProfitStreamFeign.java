package com.car.account.client.feign;


import com.car.account.client.request.profit.AddProfitReq;
import com.car.account.client.request.profit.ProfitStreamReq;
import com.car.account.client.request.profit.SceneOrderProfitReq;
import com.car.common.res.ResultRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2020/6/15 11:47
 */
@FeignClient(value = "account")
public interface ProfitStreamFeign {

    /**
     * {@code true}添加账户流水
     */
    @PostMapping(value = "/profitStream/addProfit")
    ResultRes<String> addProfit(@RequestBody AddProfitReq addProfitReq);

    @PostMapping(value = "/profitStream/statisticsAmount")
    ResultRes<BigDecimal> statisticsAmount(@RequestBody ProfitStreamReq profitStreamReq);

    @PostMapping(value = "/profitStream/statisticsAmountByType")
    ResultRes<List<Map>> statisticsAmountByType(@RequestBody SceneOrderProfitReq sceneOrderProfitReq);
}
