package com.car.order.web.service.order;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.order.order.AddOrderVehicleStationReq;
import com.car.order.client.request.order.order.QueryOrderVehicleStationListReq;
import com.car.order.client.request.order.order.UpdateOrderVehicleStationAfterSaleStsReq;
import com.car.order.client.response.order.OrderVehicleStationRes;

public interface OrderVehicleStationService {

	ResultRes<OrderVehicleStationRes> queryByUuid(String uuid);

	PageRes<List<OrderVehicleStationRes>> queryOrderVehicleStationList(QueryOrderVehicleStationListReq params);

	ResultRes<?> createOrder(AddOrderVehicleStationReq param);

	/**
	 * 店铺抢单
	 * @param uuid
	 * @return
	 */
	ResultRes<?> updateGiveMe(String uuid);

	/**
	 * 店铺修改状态
	 * @param uuid
	 * @param afterSaleSts
	 * @return
	 */
	public ResultRes<?> updateStoreSts(UpdateOrderVehicleStationAfterSaleStsReq param);

	/**
	 * 车主修改状态
	 * @param uuid
	 * @param afterSaleSts
	 * @return
	 */
	public ResultRes<?> updateUserSts(UpdateOrderVehicleStationAfterSaleStsReq param);
}
