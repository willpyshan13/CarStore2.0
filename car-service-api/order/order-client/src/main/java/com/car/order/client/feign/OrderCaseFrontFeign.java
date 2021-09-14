package com.car.order.client.feign;

import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.order.instance.QueryOrderCaseFrontListReq;
import com.car.order.client.response.order.instance.OrderCaseFrontListRes;
import com.car.order.client.response.order.instance.OrderCaseFrontRes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: car-service
 * @description:
 * @author: niushuaixiang
 * @create: 2021-04-19 14:35
 */
@FeignClient(value = "order")
public interface OrderCaseFrontFeign {
    /**
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/orderCaseFront/queryOrderCaseList")
    public PageRes<List<OrderCaseFrontListRes>> queryOrderCaseList(@RequestBody @Valid QueryOrderCaseFrontListReq param);

    /**
     * 查询案例订单详情
     * @param uuid
     * @return
     */
    @GetMapping(value = "/orderCaseFront/queryOrderCaseDetail/{uuid}")
    public ResultRes<OrderCaseFrontRes> queryOrderCaseDetail(@PathVariable(name = "uuid") String uuid);

    /**
     * 查询案例订单数量
     * @param uuid
     * @return
     */
    @GetMapping(value = "/orderCaseFront/orderCaseNum/{uuid}")
    public ResultRes<Integer> orderCaseNum(@PathVariable(name = "uuid") String uuid);
}
