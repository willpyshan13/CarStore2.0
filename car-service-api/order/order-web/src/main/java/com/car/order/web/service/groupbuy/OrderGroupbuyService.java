package com.car.order.web.service.groupbuy;

import java.util.List;

import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.order.goods.UpdateAfterSaleStsOrderReq;
import com.car.order.client.request.order.goods.UpdateReserveOrderReq;
import com.car.order.client.request.order.groupbuy.CreateOrderGroupbuyReq;
import com.car.order.client.request.order.groupbuy.QueryOrderGroupbuyListReq;
import com.car.order.client.response.order.groupbuy.OrderGroupbuyRes;

public interface OrderGroupbuyService {

	ResultRes<String> create(CreateOrderGroupbuyReq params);

	ResultRes<OrderGroupbuyRes> queryByUuid(String uuid);

	ResultRes<String> updateGroupbuyEnd(String uuid);

	PageRes<List<OrderGroupbuyRes>> queryOrderGroupbuyList(QueryOrderGroupbuyListReq params);

	/**
	 * 卖家修改状态
	 * @param uuid
	 * @param afterSaleSts
	 * @return
	 */
	public ResultRes<?> updateStoreSts(UpdateAfterSaleStsOrderReq param);

	/**
	 * 买家修改状态
	 * @param uuid
	 * @param afterSaleSts
	 * @return
	 */
	public ResultRes<?> updateUserSts(UpdateAfterSaleStsOrderReq param);

	/**
	 * 订单预约
	 * @param param
	 * @return
	 */
	public ResultRes<?> updateReserve(UpdateReserveOrderReq param);
}
