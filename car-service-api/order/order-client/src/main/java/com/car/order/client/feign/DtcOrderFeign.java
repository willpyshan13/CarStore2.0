package com.car.order.client.feign;

import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author niushuaixiang
 * @date 2021/1/31 15:18
 */
@FeignClient(value = "order")
public interface DtcOrderFeign {

    @GetMapping(value = "/dtcOrder/getNumberByUuid/{uuid}")
    public ResultRes<HashMap> getNumberByUuid(@PathVariable(name = "uuid", required = true) String uuid);
}
