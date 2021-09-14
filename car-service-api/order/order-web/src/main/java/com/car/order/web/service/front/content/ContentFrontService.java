package com.car.order.web.service.front.content;

import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.order.consult.QueryOrderConsultFrontListReq;
import com.car.order.client.response.order.consult.ConsultOrderFrontRes;
import com.car.order.client.response.order.consult.OrderConsultFrontListRes;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/28
 */
public interface ContentFrontService {

    /**
     * 查询付费咨询订单列表
     * @param param
     * @return
     */
    PageRes<List<OrderConsultFrontListRes>> queryOrderConsultList(QueryOrderConsultFrontListReq param);

    /**
     * 查询我提问的
     * @param param
     * @return
     */
     PageRes<List<OrderConsultFrontListRes>> queryOrderConsultListByUuid(QueryOrderConsultFrontListReq param);

    /**
     * 查询内容详情
     * @param uuid
     * @return
     */
    ResultRes<ConsultOrderFrontRes> queryOrderConsultDetail(String uuid);

    /**
     * 查询订单数量
     * @param uuid
     * @return
     */
    ResultRes<Integer>  orderConsultNum( String uuid);
}
