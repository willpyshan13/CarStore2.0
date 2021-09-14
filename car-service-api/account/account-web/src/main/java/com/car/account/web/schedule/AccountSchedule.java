package com.car.account.web.schedule;


import com.car.account.client.response.store.StoreUserRes;
import com.car.account.web.mapper.profit.ProfitStreamMapper;
import com.car.account.web.mapper.store.StoreAccountMapper;
import com.car.account.web.mapper.store.StoreMapper;
import com.car.account.web.mapper.technician.TechnicianAccountMapper;
import com.car.account.web.model.profit.ProfitStream;
import com.car.account.web.model.store.StoreAccount;
import com.car.account.web.model.technician.TechnicianAccount;
import com.car.account.web.service.store.StoreService;
import com.car.common.enums.StreamTypeEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.res.ResultRes;
import com.car.common.utils.DigitUtils;
import com.car.common.utils.TokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: car-service
 * @description:
 * @author: niushuaixiang
 * @create: 2021-04-16 14:12
 */
@Slf4j
@Configuration
@EnableScheduling
public class AccountSchedule {

    @Autowired
    ProfitStreamMapper profitStreamMapper;
    @Autowired
    TechnicianAccountMapper technicianAccountMapper;
    @Autowired
    StoreAccountMapper storeAccountMapper;
    @Autowired
    StoreService storeService;

    /**
     * **查询入账超过7天的金额，进入待提现
     */
    @Scheduled(cron = "0 */1 * * * *")
    public void updateAccountWithdrawal() {
        List<ProfitStream> list = profitStreamMapper.selectNoCashSts();
        for (ProfitStream profitStream :list) {
            try {
                if (StreamTypeEnum.IN.getType().equals( profitStream.getStreamType())) {
                    if (UserTypeEnum.technician.getType().equals(profitStream.getUserType())) {
                        TechnicianAccount technicianAccount = new TechnicianAccount();
                        technicianAccount.setTechnicianUuid(profitStream.getUserUuid());
                        TechnicianAccount account = technicianAccountMapper.selectOne(technicianAccount);
                        technicianAccount.setWaitAmount(account.getWaitAmount().add(profitStream.getAmt()));
                        technicianAccount.setLastUpdatedTime(new Date());
                        technicianAccount.setLastUpdatedBy("0");
                        technicianAccount.setUuid(account.getUuid());
                        int updateNum = technicianAccountMapper.updateByPrimaryKeySelective(technicianAccount);
                    } else if (UserTypeEnum.store.getType().equals(profitStream.getUserType())) {
                        ResultRes<StoreUserRes> userResResultRes  = storeService.queryStoreUserInfo(profitStream.getUserUuid());
                        StoreAccount storeAccount = new StoreAccount();
                        storeAccount.setStoreUuid(userResResultRes.getData().getStoreUuid());
                        StoreAccount account = storeAccountMapper.selectOne(storeAccount);
                        storeAccount.setWaitAmount(account.getWaitAmount().add(profitStream.getAmt()));
                        storeAccount.setLastUpdatedBy("0");
                        storeAccount.setLastUpdatedTime(new Date());
                        storeAccount.setUuid(account.getUuid());
                        int updateNum = storeAccountMapper.updateByPrimaryKeySelective(storeAccount);
                    } else if (UserTypeEnum.vehicle.getType().equals(profitStream.getUserType())) {

                    }
                }
                ProfitStream stream = new ProfitStream();
                stream.setCashSts(1);
                stream.setUuid(profitStream.getUuid());
                profitStreamMapper.updateByPrimaryKeySelective(stream);
            } catch (Exception e) {
                log.info("入账待提现失败! profitStream::"+profitStream.toString());
                e.printStackTrace();
            }
        }


    }


}