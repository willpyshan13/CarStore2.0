package com.car.account.client.feign;


import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author
 * @date 2020/6/15 11:47
 */
@FeignClient(value = "account")
public interface MessageFeign {

    /**
     * 发送消息
     * @param id
     * @param param
     * @param userUuid
     * @param userType
     * @return
     */
    @PostMapping(value = "/message/sendMsg")
    public ResultRes sendMsg(@RequestParam("id") String id, @RequestBody Map<String,String> param,
                             @RequestParam("userUuid")String  userUuid, @RequestParam("userType")Integer userType,@RequestParam(name = "orderUuid")String orderUuid);

}
