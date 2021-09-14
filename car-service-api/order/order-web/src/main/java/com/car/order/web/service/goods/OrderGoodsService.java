package com.car.order.web.service.goods;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.order.client.request.order.goods.CreateOrderReq;
import com.car.order.client.request.order.goods.PreOrderReq;
import com.car.order.client.request.order.goods.QueryOrderGoodsListReq;
import com.car.order.client.request.order.goods.UpdateAfterSaleStsOrderReq;
import com.car.order.client.request.order.goods.UpdateDeliveryOrder;
import com.car.order.client.request.order.goods.UpdateReserveOrderReq;
import com.car.order.client.request.order.goods.UpdateServerOrderReq;
import com.car.order.client.response.order.goods.OrderGoodsGroupRes;
import com.car.order.client.response.order.goods.OrderGoodsListRes;
import com.car.order.client.response.order.goods.OrderGoodsRes;
import com.car.order.client.response.order.goods.OrderGoodsReserveRes;
import com.car.order.client.response.order.goods.PreOrderRes;

/**
 * @author zhouz
 * @date 2020/12/30
 */
public interface OrderGoodsService {

	/**
	 * 订单预约量查询
	 * @param orderUuid
	 * @param reserveDate
	 * @return
	 */
	public ResultRes<OrderGoodsReserveRes> queryReserveNum(String orderUuid, String reserveDate);

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

	/**
	 * 查询商品订单列表
	 * @param param
	 * @return
	 */
	PageRes<List<OrderGoodsListRes>> queryOrderGoodsList(QueryOrderGoodsListReq param);

	/**
	 * 查询商品订单详情
	 * @param uuid
	 * @return
	 */
	ResultRes<OrderGoodsRes> queryOrderGoodsDetail(String uuid);

	/**
	 * 商品订单信息导出
	 * @param exportReq
	 * @param response
	 */
	void exportOrderGoodsList(QueryOrderGoodsListReq exportReq, HttpServletResponse response);

	/**
	 * 预下单
	 * @param params
	 * @return
	 */
	ResultRes<PreOrderRes> preOrder(PreOrderReq params);

	/**
	 * 下单 成功返回订单号
	 * @param params
	 * @return
	 */
	String createOrder(CreateOrderReq params);

	/**
	 * 修改订单服务信息
	 * @param req
	 * @return
	 */
	ResultRes<String> updateGoodsOrder(UpdateServerOrderReq req);

	/**
	 * 修改订单物流信息
	 * @param req
	 * @return
	 */
	ResultRes<String> updateGoodsDeliveryOrder(UpdateDeliveryOrder req);

	/**
	 * 当前登录账户所属门店，指定月份的订单数统计
	 * @param yearMonth
	 * @return
	 */
	ResultRes<OrderGoodsGroupRes> queryGoodsGroupCount(String yearMonth);

	/**
	 * 当前登录账户所属门店，全部的订单数统计
	 * @return
	 */
	public ResultRes<OrderGoodsGroupRes> queryGoodsGroupCountUser(String storeUserUuid);

	/**
	 * 当前登录账户所属门店，指定月份的订单金额统计
	 * @param yearMonth
	 * @return
	 */
	ResultRes<OrderGoodsGroupRes> queryGoodsGroupAmount(String yearMonth);
}
