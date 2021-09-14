package com.car.account.client.feign;

import com.car.account.client.response.store.StoreUserRes;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/19
 */
@FeignClient(value = "account")
public interface StoreUserFeign {


    /**
     * 查询店铺联系人详情
     * @param storeUserUuid
     * @return
     */
    @GetMapping(value = "/store/queryStoreUserInfo/{storeUserUuid}")
    ResultRes<StoreUserRes> queryStoreUserInfo(@PathVariable(name = "storeUserUuid") String storeUserUuid);


    /**
     *
     根据用户ID获取店铺主账号
     */
    @GetMapping(value = "/store/getStoreUset/{storeUuid}")
    public ResultRes<StoreUserRes> getStoreUset(@PathVariable(name = "storeUuid")String storeUuid);

}
