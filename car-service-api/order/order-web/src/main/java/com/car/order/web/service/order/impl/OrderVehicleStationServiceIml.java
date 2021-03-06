package com.car.order.web.service.order.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.car.account.client.feign.StoreUserFeign;
import com.car.account.client.response.store.StoreUserRes;
import com.car.common.enums.OrderPrefixEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.DigitUtils;
import com.car.common.utils.OrderUtils;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.RefundStsEnum;
import com.car.order.client.request.order.order.AddOrderInfoReq;
import com.car.order.client.request.order.order.AddOrderVehicleStationReq;
import com.car.order.client.request.order.order.QueryOrderVehicleStationListReq;
import com.car.order.client.request.order.order.UpdateOrderVehicleStationAfterSaleStsReq;
import com.car.order.client.response.order.OrderVehicleStationRes;
import com.car.order.web.mapper.order.OrderVehicleStationMapper;
import com.car.order.web.model.order.OrderVehicleStation;
import com.car.order.web.service.order.OrderInfoService;
import com.car.order.web.service.order.OrderVehicleStationService;
import com.car.system.client.feign.SystemFeign;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderVehicleStationServiceIml implements OrderVehicleStationService {

	@Autowired
	private OrderVehicleStationMapper orderVehicleStationMapper;
	@Autowired
	private StoreUserFeign storeUserFeign;
	@Autowired
	private SystemFeign systemFeign;
	@Autowired
	private OrderInfoService orderInfoService;

	public ResultRes<?> createOrder(AddOrderVehicleStationReq param) {

		String userUuid = TokenHelper.getUserUuid();
		String userName = TokenHelper.getUserName();
		Integer userType = TokenHelper.getUserType();

		OrderVehicleStation station = new OrderVehicleStation();
		BeanUtils.copyProperties(param, station);

		station.setUuid(UuidUtils.getUuid());
		station.setSts(StsEnum.ACTIVE.getValue());
		station.setCreatedBy(userName);
		station.setCreatedTime(new Date());
		station.setVehicleUserUuid(userUuid);
		station.setOrderNum(OrderUtils.GenOrderNo(OrderPrefixEnum.GW));

		station.setOrderSts(OrderStsEnum.UNPAID.getValue());
		station.setAfterSaleSts(0);

		BigDecimal durationPrice = new BigDecimal(systemFeign.queryByUuid("6204").getData().getLableValue());
		BigDecimal price = DigitUtils.multiply(durationPrice, new BigDecimal(param.getDuration()));
		BigDecimal platformServiceMoney = DigitUtils.multiply(
				DigitUtils.multiply(price, new BigDecimal(systemFeign.queryByUuid("6205").getData().getLableValue())),
				new BigDecimal("0.01"));
		BigDecimal storeFee = DigitUtils.subtract(price, platformServiceMoney);

		station.setDurationPrice(durationPrice);
		station.setPrice(price);
		station.setPlatformServiceMoney(platformServiceMoney);
		station.setStoreFee(storeFee);

		orderVehicleStationMapper.insert(station);

		AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
		addOrderInfoReq.setOrderType(OrderTypeEnum.VEHICLE_STATION.getValue());
		addOrderInfoReq.setOrderUuid(station.getUuid());
		orderInfoService.addOrder(addOrderInfoReq);

		return ResultRes.success(station.getUuid());
	}

	@Override
	public ResultRes<OrderVehicleStationRes> queryByUuid(String uuid) {

		QueryOrderVehicleStationListReq req = new QueryOrderVehicleStationListReq();
		req.setUuid(uuid);
		List<OrderVehicleStationRes> goodsList = orderVehicleStationMapper.queryOrderVehicleStationList(req);

		if (!CollectionUtils.isEmpty(goodsList)) {
			return ResultRes.success(goodsList.get(0));
		}

		return ResultRes.error();
	}

	@Override
	public PageRes<List<OrderVehicleStationRes>> queryOrderVehicleStationList(QueryOrderVehicleStationListReq param) {

		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<OrderVehicleStationRes> goodsList = orderVehicleStationMapper.queryOrderVehicleStationList(param);
		if (!CollectionUtils.isEmpty(goodsList)) {
		}

		PageInfo<OrderVehicleStationRes> pageInfo = new PageInfo<>(goodsList);
		return PageRes.success(goodsList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());

	}

	@Override
	public ResultRes<?> updateStoreSts(UpdateOrderVehicleStationAfterSaleStsReq param) {

		String uuid = param.getUuid();
		Integer afterSaleSts = param.getAfterSaleSts();

		if (afterSaleSts != 3 && afterSaleSts != 6) {
			return ResultRes.error("9999", "?????????????????????");
		}

		OrderVehicleStation orderGoods = orderVehicleStationMapper.selectByPrimaryKey(uuid);
		if (orderGoods == null || !StsEnum.ACTIVE.getValue().equals(orderGoods.getSts())) {
			return ResultRes.error("9999", "???????????????");
		}
		if (!orderGoods.getOrderSts().equals(OrderStsEnum.HAVE_PAID.getValue())) {
			return ResultRes.error("9999", "?????????????????????????????????");
		}

		StoreUserRes user = storeUserFeign.queryStoreUserInfo(TokenHelper.getUserUuid()).getData();
		if (!user.getStoreUuid().equals(orderGoods.getStoreUuid())) {
			return ResultRes.error("9999", "?????????????????????????????????");
		}

		if (afterSaleSts == 3 && orderGoods.getAfterSaleSts() != 2) {
			return ResultRes.error("9999", "?????????????????????????????????");
		}
		if (afterSaleSts == 6 && orderGoods.getAfterSaleSts() != 3) {
			return ResultRes.error("9999", "?????????????????????????????????");
		}

		orderGoods.setAfterSaleSts(afterSaleSts);
		if (afterSaleSts == 6) {
			orderGoods.setActualDate(new Date());
		} else if (afterSaleSts == 3) {
			orderGoods.setRefundSts(RefundStsEnum.REFUSE_REFUND.getValue());
		}

		orderVehicleStationMapper.updateByPrimaryKey(orderGoods);
		return ResultRes.success();
	}

	@Override
	public ResultRes<?> updateUserSts(UpdateOrderVehicleStationAfterSaleStsReq param) {

		String uuid = param.getUuid();
		Integer afterSaleSts = param.getAfterSaleSts();

		if (afterSaleSts != 2 && afterSaleSts != 5) {
			return ResultRes.error("9999", "?????????????????????");
		}

		OrderVehicleStation orderGoods = orderVehicleStationMapper.selectByPrimaryKey(uuid);
		if (orderGoods == null || !StsEnum.ACTIVE.getValue().equals(orderGoods.getSts())) {
			return ResultRes.error("9999", "???????????????");
		}

		if (!TokenHelper.getUserUuid().equals(orderGoods.getVehicleUserUuid())) {
			return ResultRes.error("9999", "??????????????????");
		}

		if (afterSaleSts == 2 && (orderGoods.getAfterSaleSts() != 3
				|| !orderGoods.getOrderSts().equals(OrderStsEnum.HAVE_PAID.getValue()))) {
			return ResultRes.error("9999", "?????????????????????????????????");
		}
		if (afterSaleSts == 5 && (!(orderGoods.getAfterSaleSts() == 0 || orderGoods.getAfterSaleSts() == 1)
				|| !orderGoods.getOrderSts().equals(OrderStsEnum.UNPAID.getValue()))) {
			return ResultRes.error("9999", "?????????????????????????????????");
		}

		orderGoods.setAfterSaleSts(afterSaleSts);
		if (afterSaleSts == 2) {
			orderGoods.setRefundApplyDate(new Date());
		} else if (afterSaleSts == 5) {
			orderGoods.setOrderSts(OrderStsEnum.CANCELED.getValue());
		}
		orderVehicleStationMapper.updateByPrimaryKey(orderGoods);
		return ResultRes.success();
	}

	@Override
	public ResultRes<?> updateGiveMe(String uuid) {
		OrderVehicleStation ovs = new OrderVehicleStation();
		ovs.setSts(StsEnum.ACTIVE.getValue());
		ovs.setUuid(uuid);

		OrderVehicleStation dbOrder = orderVehicleStationMapper.selectOne(ovs);

		if (dbOrder == null) {
			return ResultRes.error("??????????????????");
		}
		if (dbOrder.getOrderSts() != 0 && dbOrder.getAfterSaleSts() != 0) {
			return ResultRes.error("??????????????????????????????????????????");
		}

		int userType = TokenHelper.getUserType();
		if (UserTypeEnum.store.getType() != userType) {
			return ResultRes.error("????????????????????????");
		}
		StoreUserRes user = storeUserFeign.queryStoreUserInfo(TokenHelper.getUserUuid()).getData();

		dbOrder.setStoreUuid(user.getStoreUuid());
		dbOrder.setStoreUserUuid(TokenHelper.getUserUuid());
		dbOrder.setAfterSaleSts(1);
		dbOrder.setLastUpdatedTime(new Date());
		dbOrder.setLastUpdatedBy(TokenHelper.getUserName());
		orderVehicleStationMapper.updateByPrimaryKey(dbOrder);

		// TODO ??????????????? dbOrder.getVehicleUserUuid() ????????????????????????????????????????????????????????????????????????????????????????????????
		return ResultRes.success("???????????????????????????????????????");
	}

}
