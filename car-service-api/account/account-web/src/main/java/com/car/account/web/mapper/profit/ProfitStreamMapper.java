package com.car.account.web.mapper.profit;

import com.car.account.client.request.profit.ProfitStreamReq;
import com.car.account.web.dto.profit.ClassifyProfitDto;
import com.car.account.web.model.profit.ProfitStream;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface ProfitStreamMapper extends Mapper<ProfitStream> {


    /**
     * 统计收益
     * @param userUuid
     * @param userType
     * @param streamType
     * @return
     */
    List<ClassifyProfitDto> staticsClassifyProfitAmt(@Param("userUuid") String userUuid,@Param("userType") Integer userType,@Param("streamType") Integer streamType);


    /**
     * 查询现场订单折线图
     * @param year
     * @param month
     * @param userUuId
     */
    List<Map> statisticsAmountByTypeList(@Param("year") Integer year, @Param("month") Integer month, @Param("userUuId") String userUuId);

    /**
     * 查根据类型查询总金额
     * @param
     * @return
     */
    BigDecimal statisticsAmount(@Param("streamType") Integer streamType,@Param("classify") String classify,@Param("userUuId") String userUuId,@Param("year") Integer year, @Param("month") Integer month);

    /**
     * 查询入账超过7天的金额，进入待提现
     * @return
     */
    List<ProfitStream> selectNoCashSts();

}