package com.car.utility.client.feign;


import com.car.common.constant.ServiceNameConstant;
import com.car.common.res.ResultRes;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 * @date 2020/6/15 11:47
 */
@FeignClient(value = ServiceNameConstant.UTILITY)
public interface JiguangSmsFeign {


    @RequestMapping(value = "/jiguang/jpushAll",method = RequestMethod.POST)
    ResultRes jpushAll(@RequestParam(name = "uuid")String uuid,@RequestParam(name = "msg")String msg,@RequestParam(name = "title") String title,@RequestParam(name = "userId") String userId,@RequestParam(name = "type")String type,@RequestParam(name = "orderUuid")String orderUuid);
}
