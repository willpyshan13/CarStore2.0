package com.car.account.client.feign;

import com.car.account.client.request.store.UpdateStoreAccountReq;
import com.car.account.client.response.store.StoreDetailRes;
import com.car.common.res.ResultRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangyp
 * @date 2021/1/21 21:39
 */
@FeignClient(value = "account")
public interface StoreFegin {

    /**
     * 根据登录用户token查询店铺详情
     *
     * @return
     */
    @GetMapping(value = "/store/queryStoreDetailByUser")
    ResultRes<StoreDetailRes> queryStoreDetailByUser();

    /**
     * 根据用户uuId查询店铺详情
     *
     * @return
     */
    @GetMapping(value = "/store/queryStoreDetailByUserUuid/{storeUserUuid}")
    ResultRes<StoreDetailRes> queryStoreDetailByUserUuid(@PathVariable(name = "storeUserUuid") String storeUserUuid);

    /**
     * 查询店铺详情
     *
     * @param uuid
     * @return
     */
    @GetMapping(value = "/store/queryStoreDetail/{uuid}")
    ResultRes<StoreDetailRes> queryStoreDetail(@PathVariable(name = "uuid") String uuid);

    /**
     * 修改店铺账户信息
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/storeAccount/updateStoreAccount")
    ResultRes<String> updateStoreAccount(@RequestBody @Valid UpdateStoreAccountReq req);

    /**
     * 修改技师评分
     *
     * @param uuid
     * @param score
     * @return
     */
    @PostMapping(value = "/store/updateScore")
    ResultRes<String> updateScore(@RequestParam("uuid")  String uuid, @RequestParam("score") BigDecimal score);

    /**
     * 查询店铺品牌uuid
     * @param uuid
     * @return
     */
    @GetMapping(value = "/store/queryStoreBrand/{uuid}")
    public ResultRes<List<String>> queryStoreBrand(@PathVariable("uuid") String uuid);
}
