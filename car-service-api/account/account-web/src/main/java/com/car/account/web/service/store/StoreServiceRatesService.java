package com.car.account.web.service.store;

import com.car.account.client.response.store.StoreServiceRatesRes;
import com.car.common.res.ResultRes;

public interface StoreServiceRatesService {


    /**
     * 查询一个特定店铺的特定分类的 有效 配置，如果没有有效的，则返回null
     * @param storeUuid
     * @param goodsParentUuid
     * @return
     */
    ResultRes<StoreServiceRatesRes> queryStoreServiceRates(String storeUuid, String goodsParentUuid);
}
