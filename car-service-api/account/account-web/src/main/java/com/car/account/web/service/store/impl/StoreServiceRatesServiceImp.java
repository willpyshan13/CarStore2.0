package com.car.account.web.service.store.impl;

import com.car.account.client.response.store.StoreServiceRatesRes;
import com.car.account.web.mapper.store.StroeServiceRatesMapper;
import com.car.account.web.service.store.StoreServiceRatesService;
import com.car.common.res.ResultRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StoreServiceRatesServiceImp implements StoreServiceRatesService {

    @Autowired
    private StroeServiceRatesMapper stroeServiceRatesMapper;


    /**
     * 查询一个特定店铺的特定分类的 有效 配置，如果没有有效的，则返回null
     * @param storeUuid
     * @param goodsParentUuid
     * @return
     */
    @Override
    public ResultRes<StoreServiceRatesRes> queryStoreServiceRates(String storeUuid, String goodsParentUuid) {
        return ResultRes.success(stroeServiceRatesMapper.queryStoreServiceRates(storeUuid,goodsParentUuid));
    }
}
