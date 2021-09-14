package com.car.account.web.service.profit;

import com.car.account.client.request.profit.AddProfitReq;
import com.car.account.client.request.profit.ProfitStreamReq;
import com.car.account.client.response.account.AccountRes;
import com.car.account.client.response.account.QueryQuizCaseCarCountRes;
import com.car.account.client.response.profit.AccountAmtRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.scene.SceneOrderStatisticsReq;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyp
 * @date 2021/1/27 21:42
 */
public interface ProfitService {

    /**
     * 查询账户信息
     * @return
     */
    ResultRes<AccountRes> queryAccount();

    /**
     * 用户资金账户
     * @param params
     * @return
     */
    ResultRes<AccountAmtRes> queryProfitClassify();


    /**
     * 查询我的提问、案例、车辆数量
     * @return
     */
    ResultRes<QueryQuizCaseCarCountRes> queryQuizCaseCarCount();

    /**
     * 添加账户流水
     * @param addProfitReq
     * @return
     */
    ResultRes<String> addProfit(AddProfitReq addProfitReq);

    /**
     * 统计收入总汇
     * @param
     * @return
     */
    ResultRes<Map> statisticsIncomeAmount(SceneOrderStatisticsReq sceneOrderStatisticsReq);

    /**
     * 统计支出总汇
     * @param
     * @return
     */
    ResultRes<Map> statisticsPayAmount(SceneOrderStatisticsReq sceneOrderStatisticsReq);

    /**
     *
     * 查询统计数据
     * @return
     */
    ResultRes<List<Map>> statisticsAmountByTypeList(SceneOrderStatisticsReq sceneOrderStatisticsReq);


    /**
     * 查询总金额
     *
     * @return
     */
    ResultRes<BigDecimal> statisticsAmount(ProfitStreamReq profitStreamReq);

}
