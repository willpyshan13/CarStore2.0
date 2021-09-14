package com.car.account.client.feign;


import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "account")
public interface UserFeign {


    /**
     *根据uuid判断用户类型
     * @param userUuid
     * @return 1车主2技师3店铺
     */
    @PostMapping(value = "/user/getUserType")
    public ResultRes<Integer> getUserType(String userUuid);
}
