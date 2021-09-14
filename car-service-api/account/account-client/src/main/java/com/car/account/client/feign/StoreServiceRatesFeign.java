package com.car.account.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.car.account.client.response.store.StoreServiceRatesRes;
import com.car.common.res.ResultRes;

@FeignClient(value = "account")
public interface StoreServiceRatesFeign {

	/**
	 * 查询一个特定店铺的特定分类的 有效 配置，如果没有有效的，则返回null
	 * @return
	 */
	@GetMapping(value = "/storeServiceRates/queryStoreServiceRates")
	ResultRes<StoreServiceRatesRes> queryStoreServiceRates(@RequestParam("storeUuid") String storeUuid,
			@RequestParam("goodsParentUuid") String goodsParentUuid);
}
