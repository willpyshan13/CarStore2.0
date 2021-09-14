package com.car.order.web.mapper.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.car.order.client.request.order.order.QueryOrderVehicleStationListReq;
import com.car.order.client.response.order.OrderVehicleStationRes;
import com.car.order.web.model.order.OrderVehicleStation;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderVehicleStationMapper extends Mapper<OrderVehicleStation> {

	List<OrderVehicleStationRes> queryOrderVehicleStationList(QueryOrderVehicleStationListReq param);

}
