package com.car.account.web.service.goods;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.car.account.client.request.goods.AddGoodsReq;
import com.car.account.client.request.goods.CalGoodsReq;
import com.car.account.client.request.goods.QueryGoodsListReq;
import com.car.account.client.request.goods.UpdateGoodsReq;
import com.car.account.client.request.store.StoreServiceRatesReq;
import com.car.account.client.response.goods.CalGoodsRes;
import com.car.account.client.response.goods.GoodsRes;
import com.car.account.client.response.store.QueryStoreListRes;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;

/**
 * @author zhouz
 * @date 2020/12/22
 */
public interface GoodsService {

	/**
	 *商品修改： 简版不修改任何价格相关字段，不能修改图片，不能修改物料明细
	 * @param updateGoodsReq
	 * @return
	 */
	ResultRes<GoodsRes> updateGoodsSimplified(UpdateGoodsReq updateGoodsReq);

	/**
	 * 根据店铺配置，刷新平台服务费
	 * @param rateList
	 */
	void updateRefreshPlatformServiceMoney(List<StoreServiceRatesReq> rateList);

	/**
	 * 添加商品
	 * @param addGoodsReq
	 * @return
	 */
	ResultRes<String> addGoods(AddGoodsReq addGoodsReq);

	/**
	 * 删除商品
	 * @param goodId 商品唯一标识
	 * @return
	 */
	ResultRes<String> deleteGoods(String goodId);

	/**
	 * 修改商品
	 * @param updateGoodsReq
	 * @return
	 */
	ResultRes<GoodsRes> updateGoods(UpdateGoodsReq updateGoodsReq);

	/**
	 * 查询商品列表
	 * @param param
	 * @return
	 */
	PageRes<List<GoodsRes>> queryGoodsList(QueryGoodsListReq param);

	/**
	 * 查询商品信息
	 * @param uuid 商品主键
	 * @return
	 */
	ResultRes<GoodsRes> queryGoods(String uuid);

	/**
	 * 商品信息导出
	 * @param exportReq
	 * @param response
	 */
	void exportGoodsList(QueryGoodsListReq exportReq, HttpServletResponse response);

	/**
	 * 计算商品订单信息
	 * @param params
	 * @return
	 */
	CalGoodsRes calGoods(CalGoodsReq params);

	public PageRes<List<QueryStoreListRes>> queryStoreList(QueryGoodsListReq param);

}
