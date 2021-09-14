package com.car.account.client.feign;

import com.car.account.client.request.vehicle.VehicleUserListReq;
import com.car.account.client.response.vehicle.config.ConfigRes;
import com.car.account.client.response.vehicle.vehicleUser.VehicleUserRes;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author zhangyp
 * @date 2021/1/21 21:54
 */
@FeignClient(value = "account")
public interface VehicleFegin {

    /**
     * 查询车主详情
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/vehicleUser/queryDetail/{uuid}", method = RequestMethod.GET)
    public ResultRes<VehicleUserRes> queryDetail(@PathVariable("uuid") String uuid);


    /**
     * 根据uuid查询车辆节点信息
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/vehicleConfig/queryConfig/{uuid}", method = RequestMethod.GET)
    ResultRes<ConfigRes> queryConfig(@PathVariable(name = "uuid") String uuid);

    /**
     * 查询车主信息列表
     * @return
     */

    @RequestMapping(value = "/vehicleUser/queryList", method = RequestMethod.POST)
    public PageRes<List<VehicleUserRes>> queryList(@RequestBody VehicleUserListReq req);

}
