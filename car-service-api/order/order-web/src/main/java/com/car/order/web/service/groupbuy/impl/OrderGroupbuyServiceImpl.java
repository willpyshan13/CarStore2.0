package com.car.order.web.service.groupbuy.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.car.account.client.enums.goods.ImgTypeEnum;
import com.car.account.client.feign.StoreUserFeign;
import com.car.account.client.response.store.StoreUserRes;
import com.car.common.enums.OrderPrefixEnum;
import com.car.common.enums.StsEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.DigitUtils;
import com.car.common.utils.OrderUtils;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.goods.EvaluateStsEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.RefundStsEnum;
import com.car.order.client.request.order.goods.UpdateAfterSaleStsOrderReq;
import com.car.order.client.request.order.goods.UpdateReserveOrderReq;
import com.car.order.client.request.order.groupbuy.CreateOrderGroupbuyReq;
import com.car.order.client.request.order.groupbuy.QueryOrderGroupbuyListReq;
import com.car.order.client.request.order.order.AddOrderInfoReq;
import com.car.order.client.response.order.goods.GoodsRes;
import com.car.order.client.response.order.groupbuy.GroupbuyRes;
import com.car.order.client.response.order.groupbuy.OrderGroupbuyRes;
import com.car.order.web.mapper.goods.GoodsImagesMapper;
import com.car.order.web.mapper.goods.GoodsMapper;
import com.car.order.web.mapper.groupbuy.GroupbuyGoodsMapper;
import com.car.order.web.mapper.groupbuy.GroupbuyMapper;
import com.car.order.web.mapper.groupbuy.OrderGroupbuyMapper;
import com.car.order.web.model.goods.GoodsImages;
import com.car.order.web.model.goods.OrderGoods;
import com.car.order.web.model.groupbuy.Groupbuy;
import com.car.order.web.model.groupbuy.GroupbuyGoods;
import com.car.order.web.model.groupbuy.OrderGroupbuy;
import com.car.order.web.service.groupbuy.OrderGroupbuyService;
import com.car.order.web.service.order.OrderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderGroupbuyServiceImpl implements OrderGroupbuyService {

	@Autowired
	private OrderGroupbuyMapper orderGroupbuyMapper;
	@Autowired
	private GroupbuyMapper groupbuyMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsImagesMapper goodsImagesMapper;
	@Autowired
	private GroupbuyGoodsMapper groupbuyGoodsMapper;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private StoreUserFeign storeUserFeign;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultRes<String> create(CreateOrderGroupbuyReq params) {

		String userUuid = TokenHelper.getUserUuid();
		String userName = TokenHelper.getUserName();
		Integer userType = TokenHelper.getUserType();

		Groupbuy groupbuy = groupbuyMapper.selectByPrimaryKey(params.getGroupbuyUuid());
		if (groupbuy.getParticipateNum() >= groupbuy.getUserNum()) {
			log.error("成团可参与人数用尽，创建团购单失败");
			return ResultRes.error("9999", "成团可参与人数用尽，创建团购单失败");
		}

		OrderGroupbuy orderBuy = new OrderGroupbuy();
		orderBuy.setUuid(UuidUtils.getUuid());
		orderBuy.setSts(StsEnum.ACTIVE.getValue());
		orderBuy.setCreatedBy(userName);
		orderBuy.setCreatedTime(new Date());
		orderBuy.setUserUuid(userUuid);
		orderBuy.setOrderNum(OrderUtils.GenOrderNo(OrderPrefixEnum.TG));
		orderBuy.setStoreUuid(groupbuy.getStoreUuid());

		orderBuy.setOrderSts(OrderStsEnum.UNPAID.getValue());
		orderBuy.setRemark(params.getRemark());
		orderBuy.setGroupbuyUuid(params.getGroupbuyUuid());
		orderBuy.setGroupbuyNum(params.getNum());

		orderBuy.setGroupbuyFee(DigitUtils.multiply(groupbuy.getPrice(), new BigDecimal(params.getNum())));// 原价
		orderBuy.setPlatformServiceMoney(
				DigitUtils.multiply(groupbuy.getPlatformServiceMoney(), new BigDecimal(params.getNum())));// 平台服务费
		orderBuy.setStoreFee(DigitUtils.subtract(orderBuy.getGroupbuyFee(), orderBuy.getPlatformServiceMoney()));// 结算给商户
		orderBuy.setSysSubsidy(DigitUtils.multiply(groupbuy.getPlatformSubsidy(), new BigDecimal(params.getNum())));// 平台补贴
		orderBuy.setReceivableAmount(DigitUtils.subtract(orderBuy.getGroupbuyFee(), orderBuy.getSysSubsidy()));// 售价

		BigDecimal payFee = orderBuy.getReceivableAmount();
		if (org.apache.commons.lang3.StringUtils.isNotBlank(params.getCouponUuid())) {
			ResultRes<BigDecimal> conRes = ResultRes.success(new BigDecimal("1"));// TODO 调用刘文开发的优惠券使用列表
			if (conRes.isSuccess() && conRes.getData().compareTo(BigDecimal.ZERO) == 1) {

				orderBuy.setCouponFee(conRes.getData());
				payFee = DigitUtils.subtract(payFee, conRes.getData());
			} else {
				orderBuy.setCouponUuid(null);
			}
		}
		if (payFee.compareTo(BigDecimal.ZERO) == -1) {
			payFee = BigDecimal.ZERO;
		}
		orderBuy.setPayFee(payFee);
		orderGroupbuyMapper.insertSelective(orderBuy);

		// 新增order_info信息
		AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
		addOrderInfoReq.setOrderType(OrderTypeEnum.GROUP_BUY.getValue());
		addOrderInfoReq.setOrderUuid(orderBuy.getUuid());
		orderInfoService.addOrder(addOrderInfoReq);

		return ResultRes.success(orderBuy.getUuid());
	}

	@Override
	public ResultRes<OrderGroupbuyRes> queryByUuid(String uuid) {

		QueryOrderGroupbuyListReq param = new QueryOrderGroupbuyListReq();
		param.setUuid(uuid);
		OrderGroupbuyRes res = orderGroupbuyMapper.queryOrderGroupbuyList(param).get(0);
		res.setGoodsRes(findGoodsByGroupId(res.getGroupbuyUuid()));

		return ResultRes.success(res);
	}

	@Override
	public PageRes<List<OrderGroupbuyRes>> queryOrderGroupbuyList(QueryOrderGroupbuyListReq param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<OrderGroupbuyRes> goodsList = orderGroupbuyMapper.queryOrderGroupbuyList(param);

		if (!CollectionUtils.isEmpty(goodsList)) {

			Optional<List<GoodsRes>> gList = Optional.empty();
			Optional<GroupbuyRes> gRes = Optional.empty();
			;
			if (StringUtils.isNotBlank(param.getGroupbuyUuid())) {
				gList = Optional.ofNullable(findGoodsByGroupId(param.getGroupbuyUuid()));
				gRes = Optional.ofNullable(findGroupbuyRes(param.getGroupbuyUuid()));
			}

			for (OrderGroupbuyRes orderGroupbuyRes : goodsList) {
				orderGroupbuyRes.setGoodsRes(gList.orElse(findGoodsByGroupId(orderGroupbuyRes.getGroupbuyUuid())));
				orderGroupbuyRes.setGroupbuyRes(gRes.orElse(findGroupbuyRes(orderGroupbuyRes.getGroupbuyUuid())));
			}
		}

		PageInfo<OrderGroupbuyRes> pageInfo = new PageInfo<>(goodsList);
		return PageRes.success(goodsList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
	}

	private GroupbuyRes findGroupbuyRes(String groupUuid) {
		Groupbuy buy = groupbuyMapper.selectByPrimaryKey(groupUuid);
		GroupbuyRes res = new GroupbuyRes();
		if (buy != null) {
			BeanUtils.copyProperties(buy, res);
		}
		return res;
	}

	@Override
	public ResultRes<?> updateStoreSts(UpdateAfterSaleStsOrderReq param) {

		String uuid = param.getUuid();
		Integer afterSaleSts = param.getAfterSaleSts();

		if (afterSaleSts != 4 && afterSaleSts != 7 && afterSaleSts != 0) {
			return ResultRes.error("9999", "不处理的状态码");
		}

		OrderGroupbuy orderGoods = orderGroupbuyMapper.selectByPrimaryKey(uuid);
		if (orderGoods == null || !StsEnum.ACTIVE.getValue().equals(orderGoods.getSts())) {
			return ResultRes.error("9999", "订单不存在");
		}
		if (!orderGoods.getOrderSts().equals(OrderStsEnum.GROUP_SUCCESS.getValue())) {
			return ResultRes.error("9999", "不在能执行此操作的状态");
		}

		StoreUserRes user = storeUserFeign.queryStoreUserInfo(TokenHelper.getUserUuid()).getData();
		if (!user.getStoreUuid().equals(orderGoods.getStoreUuid())) {
			return ResultRes.error("9999", "不是你的订单，无权操作");
		}

		if (afterSaleSts == 4 && orderGoods.getAfterSaleSts() != 2) {
			return ResultRes.error("9999", "不在能执行此操作的状态");
		}
		if (afterSaleSts == 7 && orderGoods.getAfterSaleSts() != 6) {
			return ResultRes.error("9999", "不在能执行此操作的状态");
		}
		if (afterSaleSts == 0 && orderGoods.getAfterSaleSts() != 1) {
			return ResultRes.error("9999", "不在能执行此操作的状态");
		}

		orderGoods.setAfterSaleSts(afterSaleSts);
		orderGoods.setUseRemark(param.getUseRemark());
		orderGoods.setUseImg(param.getUseImg());
		orderGoods.setReachImg(param.getReachImg());
		orderGoods.setReachRemark(param.getReachRemark());
		if (afterSaleSts == 7) {
			orderGoods.setStoreOverTime(new Date());
		} else if (afterSaleSts == 0) {
			orderGoods.setRefundSts(RefundStsEnum.REFUSE_REFUND.getValue());
		}

		orderGroupbuyMapper.updateByPrimaryKey(orderGoods);
		return ResultRes.success();
	}

	@Override
	public ResultRes<?> updateUserSts(UpdateAfterSaleStsOrderReq param) {

		String uuid = param.getUuid();
		Integer afterSaleSts = param.getAfterSaleSts();

		if (afterSaleSts != 6 && afterSaleSts != 1) {
			return ResultRes.error("9999", "不处理的状态码");
		}

		OrderGroupbuy orderGoods = orderGroupbuyMapper.selectByPrimaryKey(uuid);
		if (orderGoods == null || !StsEnum.ACTIVE.getValue().equals(orderGoods.getSts())) {
			return ResultRes.error("9999", "订单不存在");
		}

		if (!orderGoods.getOrderSts().equals(OrderStsEnum.GROUP_SUCCESS.getValue())
				|| !TokenHelper.getUserUuid().equals(orderGoods.getUserUuid())) {
			return ResultRes.error("9999", "不在能执行此操作的状态，或者不是您的订单");
		}

		if (afterSaleSts == 1 && orderGoods.getAfterSaleSts() != 0) {
			return ResultRes.error("9999", "不在能执行此操作的状态");
		}
		if (afterSaleSts == 6 && orderGoods.getAfterSaleSts() != 4) {
			return ResultRes.error("9999", "不在能执行此操作的状态");
		}

		orderGoods.setAfterSaleSts(afterSaleSts);
		orderGoods.setUseRemark(param.getUseRemark());
		orderGoods.setUseImg(param.getUseImg());
		orderGoods.setReachImg(param.getReachImg());
		orderGoods.setReachRemark(param.getReachRemark());
		if (afterSaleSts == 1) {
			orderGoods.setRefundApplyDate(new Date());
		}
		orderGroupbuyMapper.updateByPrimaryKey(orderGoods);
		return ResultRes.success();
	}

	@Override
	public ResultRes<?> updateReserve(UpdateReserveOrderReq param) {

		OrderGroupbuy orderGoods = orderGroupbuyMapper.selectByPrimaryKey(param.getUuid());
		if (orderGoods == null || !StsEnum.ACTIVE.getValue().equals(orderGoods.getSts())) {
			return ResultRes.error("9999", "订单不存在");
		}

		if (!orderGoods.getOrderSts().equals(OrderStsEnum.GROUP_SUCCESS.getValue())
				|| !orderGoods.getUserUuid().equals(TokenHelper.getUserUuid()) || orderGoods.getAfterSaleSts() != 0) {
			return ResultRes.error("9999", "不在能执行此操作的状态，或者不是您的订单");
		}

		orderGoods.setReserveServiceDate(param.getReserveServiceDate());
		orderGoods.setReservePartStart(param.getReservePartStart());
		orderGoods.setReservePartEnd(param.getReservePartEnd());
		orderGoods.setReservePartType(param.getReservePartType());
		orderGoods.setReserveAddr(param.getReserveAddr());
		orderGoods.setReserveVehicleUuid(param.getReserveVehicleUuid());
		orderGoods.setAfterSaleSts(2);

		orderGroupbuyMapper.updateByPrimaryKeySelective(orderGoods);

		return ResultRes.success();
	}

	private List<GoodsRes> findGoodsByGroupId(String groupUuid) {

		List<GoodsRes> retlist = new ArrayList<>();

		GroupbuyGoods query = new GroupbuyGoods();
		query.setSts(0);
		query.setGroupbuyUuid(groupUuid);
		List<GroupbuyGoods> gList = groupbuyGoodsMapper.select(query);
		for (GroupbuyGoods groupbuyGoods : gList) {
			GoodsRes res = new GoodsRes();
			BeanUtils.copyProperties(goodsMapper.selectByPrimaryKey(groupbuyGoods.getGoodsUuid()), res);

			// 查询商品对应图片
			List<GoodsImages> goodsImagesList = goodsImagesMapper.queryListByGoodsId(groupbuyGoods.getGoodsUuid());
			String imgUrl = null;
			if (!CollectionUtils.isEmpty(goodsImagesList)) {
				for (GoodsImages goodsImages : goodsImagesList) {
					if (ImgTypeEnum.MAIN_GRAPH.getValue().equals(goodsImages.getImgType())) {
						imgUrl = goodsImages.getImgPath();
						if (!StringUtils.isEmpty(imgUrl)) {
							break;
						}
					}
				}
			}

			res.setImgUrl(imgUrl);

			retlist.add(res);
		}

		return retlist;
	}

	@Override
	public ResultRes<String> updateGroupbuyEnd(String uuid) {

		orderGroupbuyMapper.updateGroupbuyEnd1To7(uuid);
		orderGroupbuyMapper.updateGroupbuyEnd0To2(uuid);

		return ResultRes.success();
	}
}
