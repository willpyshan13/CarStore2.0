package com.car.order.web.mapper.goods;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.car.order.client.request.order.goods.QueryOrderGoodsListReq;
import com.car.order.client.request.order.goods.UpdateDeliveryOrder;
import com.car.order.client.request.order.goods.UpdateServerOrderReq;
import com.car.order.client.response.order.goods.OrderGoodsGroupRes;
import com.car.order.client.response.order.goods.OrderGoodsListRes;
import com.car.order.client.response.order.goods.OrderGoodsRes;
import com.car.order.client.response.order.goods.OrderGoodsReserveRes;
import com.car.order.web.model.goods.OrderGoods;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author zhouz
 * @date 2020/12/30
 */
@Repository
public interface OrderGoodsMapper extends Mapper<OrderGoods> {

	/**
	 * 统计一个商品的某一条的预约量
	 * @param goodsUuid
	 * @param reserveServiceDate
	 * @return
	 */
	OrderGoodsReserveRes queryReserveNum(@Param("goodsUuid") String goodsUuid,
			@Param("reserveServiceDate") String reserveServiceDate);

	/**
	 * 统计商铺完成的订单数量
	 * @param start 开始时间	
	 * @param end 结束时间
	 * @param storeUserUuid 店铺任意账户的uuid
	 * @return
	 */
	OrderGoodsGroupRes queryGoodsGroupCount(@Param("start") Date start, @Param("end") Date end,
			@Param("storeUserUuid") String storeUserUuid);

	/**
	 * 统计商铺完成的订单额
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param storeUserUuid 店铺任意账户的uuid
	 * @return
	 */
	OrderGoodsGroupRes queryGoodsGroupAmount(@Param("start") Date start, @Param("end") Date end,
			@Param("storeUserUuid") String storeUserUuid);

	/**
	 * 查询商品订单列表
	 * @param param
	 * @return
	 */
	List<OrderGoodsListRes> queryOrderGoodsList(QueryOrderGoodsListReq param);

	/**
	 * 查询商品订单详情
	 * @param uuid
	 * @return
	 */
	OrderGoodsRes queryOrderGoods(String uuid);

	/**
	 * 修改订单服务信息
	 * @param req
	 * @return
	 */
	int updateGoodsOrder(@Param("req") UpdateServerOrderReq req, @Param("userName") String userName);

	/**
	 * 修改订单物流信息
	 * @param req
	 * @return
	 */
	int updateGoodsDeliveryOrder(@Param("req") UpdateDeliveryOrder req, @Param("userName") String userName);
}
