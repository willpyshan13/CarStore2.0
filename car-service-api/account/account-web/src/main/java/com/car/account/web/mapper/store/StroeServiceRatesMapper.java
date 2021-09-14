package com.car.account.web.mapper.store;


import com.car.account.client.response.store.StoreServiceRatesRes;
import com.car.account.web.model.store.StoreServiceRates;
import com.car.account.web.model.store.StoreUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface StroeServiceRatesMapper extends Mapper<StoreServiceRates> {

    //查询一个特定店铺的特定分类的 有效 配置，如果没有有效的，则返回null
    StoreServiceRatesRes queryStoreServiceRates(@Param("storeUuid") String storeUuid, @Param("goodsParentUuid") String goodsParentUuid);
}
