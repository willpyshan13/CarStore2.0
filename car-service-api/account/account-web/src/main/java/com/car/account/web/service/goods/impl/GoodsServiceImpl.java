package com.car.account.web.service.goods.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.car.account.client.enums.comm.UnitEnum;
import com.car.account.client.enums.goods.ImgTypeEnum;
import com.car.account.client.enums.goods.SellStatusEnum;
import com.car.account.client.feign.StoreServiceRatesFeign;
import com.car.account.client.request.goods.AddGoodsReq;
import com.car.account.client.request.goods.CalGoodsReq;
import com.car.account.client.request.goods.GoodsImgReq;
import com.car.account.client.request.goods.QueryGoodsListReq;
import com.car.account.client.request.goods.UpdateGoodsReq;
import com.car.account.client.request.goods.sub.GoodsDetailReq;
import com.car.account.client.request.store.StoreServiceRatesReq;
import com.car.account.client.response.addr.ReceiveAddrRes;
import com.car.account.client.response.goods.CalGoodsRes;
import com.car.account.client.response.goods.GoodsRes;
import com.car.account.client.response.goods.sub.GoodsDetailRes;
import com.car.account.client.response.goods.sub.GoodsImgRes;
import com.car.account.client.response.store.QueryStoreListRes;
import com.car.account.client.response.store.StoreServiceRatesRes;
import com.car.account.client.response.store.sub.ImgStoreRes;
import com.car.account.web.common.constants.GoodsConstants;
import com.car.account.web.mapper.addr.ReceiveAddrMapper;
import com.car.account.web.mapper.goods.GoodsDetailMapper;
import com.car.account.web.mapper.goods.GoodsImagesMapper;
import com.car.account.web.mapper.goods.GoodsMapper;
import com.car.account.web.mapper.goods.GoodsParentMapper;
import com.car.account.web.mapper.store.StoreBrandMapper;
import com.car.account.web.mapper.store.StoreImagesMapper;
import com.car.account.web.mapper.store.StoreMapper;
import com.car.account.web.model.addr.ReceiveAddr;
import com.car.account.web.model.goods.Goods;
import com.car.account.web.model.goods.GoodsDetail;
import com.car.account.web.model.goods.GoodsImages;
import com.car.account.web.model.goods.GoodsParent;
import com.car.account.web.model.store.Store;
import com.car.account.web.model.store.StoreImages;
import com.car.account.web.service.goods.GoodsService;
import com.car.account.web.service.store.StoreService;
import com.car.account.web.service.vehicle.VehicleConfigService;
import com.car.common.enums.CheckStatusEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.DateUtil;
import com.car.common.utils.DigitUtils;
import com.car.common.utils.ExcelUtils;
import com.car.common.utils.ExceptionUtils;
import com.car.common.utils.StringUtil;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.order.client.feign.CommentInfoFeign;
import com.car.order.client.feign.ScoreFeign;
import com.car.system.client.feign.SystemFeign;
import com.car.system.client.response.area.AreaRes;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouz
 * @date 2020/12/22
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	GoodsMapper goodsMapper;
	@Autowired
	GoodsParentMapper goodsParentMapper;
	@Autowired
	GoodsDetailMapper goodsDetailMapper;
	@Autowired
	GoodsImagesMapper goodsImagesMapper;
	@Autowired
	StoreMapper storeMapper;
	@Autowired
	private StoreService storeService;
	@Autowired
	ReceiveAddrMapper receiveAddrMapper;

	@Resource
	CommentInfoFeign commentInfoFeign;
	@Resource
	ScoreFeign scoreFeign;
	@Resource
	SystemFeign systemFeign;
	@Autowired
	private StoreServiceRatesFeign storeServiceRatesFeign;
	@Autowired
	private StoreImagesMapper storeImagesMapper;
	@Autowired
	private VehicleConfigService vehicleConfigService;
	@Autowired
	private StoreBrandMapper storeBrandMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> addGoods(AddGoodsReq addGoodsReq) {
		String storeUuid = addGoodsReq.getStoreUuid();

		Store store;
		if (StringUtils.isEmpty(storeUuid)) {
			store = storeService.getStore();
		} else {
			store = storeMapper.selectByPrimaryKey(storeUuid);
		}
		if (null == store || store.getSts().equals(StsEnum.INVALID.getValue())) {

			log.error("商铺不存在>>>storeUuid:{}", storeUuid);
			throw new BusinessException(ResEnum.NOT_STORE);
		}

		// 增加商品明细数据列表
		List<GoodsDetailReq> detailList = addGoodsReq.getDetailList();
		if (CollectionUtils.isEmpty(detailList)) {
			log.error("物料缺失");
			throw new BusinessException(ResEnum.ERROR_GOODS_EMPTY);
		}

		String goodsUuid = UuidUtils.getUuid();
		String userName = TokenHelper.getUserName();
		Date currDate = new Date();

		ResultRes<StoreServiceRatesRes> rate = storeServiceRatesFeign.queryStoreServiceRates(store.getUuid(),
				addGoodsReq.getParentType());
		if (rate == null || !rate.isSuccess() || rate.getData() == null) {
			log.error("店铺不存在此商品分类的配置");
			throw new BusinessException(ResEnum.NOT_STORE_RATES);
		}

		// 人工费
		BigDecimal manHourCost = (null != addGoodsReq.getManHourCost()) ? addGoodsReq.getManHourCost()
				: BigDecimal.ZERO;
		// 上门服务费
		BigDecimal visitFee = (null != addGoodsReq.getVisitFee()) ? addGoodsReq.getVisitFee() : BigDecimal.ZERO;
		// 物料总费用
		BigDecimal materialsExpenses = detailList.stream().map(GoodsDetailReq::getActAmt).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		// 原价
		BigDecimal sourceAmt = manHourCost.add(visitFee).add(materialsExpenses);

		// 平台服务费
		BigDecimal platformServiceMoney = DigitUtils.divide(sourceAmt.multiply(rate.getData().getServiceRates()),
				new BigDecimal("100"));

		// 平台返利
		BigDecimal platformSubsidy = (null != addGoodsReq.getPlatformSubsidy()) ? addGoodsReq.getPlatformSubsidy()
				: BigDecimal.ZERO;
		// 售价
		BigDecimal amt = DigitUtils.subtract(sourceAmt, platformSubsidy);

		Goods goods = new Goods();
		BeanUtils.copyProperties(addGoodsReq, goods);
		goods.setStoreUuid(store.getUuid());
		goods.setStoreUserUuid(TokenHelper.getUserUuid());
		goods.setManHourCost(manHourCost);
		goods.setVisitFee(visitFee);
		goods.setMaterialsExpenses(materialsExpenses);
		goods.setSourceAmt(sourceAmt);
		goods.setPlatformServiceMoney(platformServiceMoney);
		goods.setPlatformSubsidy(platformSubsidy);
		goods.setAmt(amt);
		goods.setUuid(goodsUuid);
		goods.setCreatedBy(userName);
		goods.setCreatedTime(currDate);
		goods.setSts(StsEnum.ACTIVE.getValue());
		goods.setTyreNo(addGoodsReq.getTyreNo());
		goods.setCheckSts(CheckStatusEnum.CHECK_PENDING.getValue());

		int relateNum = goodsMapper.insert(goods);
		if (relateNum != 1) {
			log.error("新增商品数据库失败,params:{}", JSON.toJSONString(goods));
			throw new BusinessException(ResEnum.DB_ERROR);
		}

		// 批量插入图片
		batchInsertGoodsImages(goodsUuid, addGoodsReq.getGoodsImgListReq());
		// 新增物料列表
		detailList.stream().forEach(s -> {
			String name = s.getName();
			BigDecimal actAmt = s.getActAmt();

			String uuid = UuidUtils.getUuid();
			GoodsDetail detail = new GoodsDetail();
			detail.setBak1(s.getBak1());
			detail.setBak2(s.getBak2());
			detail.setBak3(s.getBak3());
			detail.setBak4(s.getBak4());
			detail.setUuid(uuid);
			detail.setName(name);
			detail.setAmt(actAmt);
			detail.setActAmt(actAmt);
			detail.setGoodsUuid(goodsUuid);
			detail.setNum(1);
			detail.setUnit(UnitEnum.GE.getUnit());
			detail.setCreatedBy(userName);
			detail.setCreatedTime(currDate);
			detail.setSts(StsEnum.ACTIVE.getValue());
			goodsDetailMapper.insert(detail);
		});
		// 返回商品信息
		return ResultRes.success(goodsUuid);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> deleteGoods(String goodsId) {
		// 登录用户信息
		String userName = TokenHelper.getUserName();
		// 删除商品信息
		Goods deleteGoods = new Goods();
		deleteGoods.setUuid(goodsId);
		deleteGoods.setSts(StsEnum.INVALID.getValue());
		deleteGoods.setLastUpdatedTime(new Date());
		deleteGoods.setLastUpdatedBy(userName);

		int relateNum = goodsMapper.updateByPrimaryKeySelective(deleteGoods);
		if (relateNum == 0) {
			log.error("删除商品信息失败,未定位到商品信息>>>params:{}", JSON.toJSONString(deleteGoods));
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		// 删除商品相关图片信息
		int relateNum2 = goodsImagesMapper.deleteGoodsImages(goodsId, userName);
		if (relateNum2 == 0) {
			log.error("未定位到数据库中商品图片信息>>>goodsId:{}", goodsId);
		}
		// 删除商品关联物料列表信息
		int relateNum3 = goodsDetailMapper.disableGoodsDetailByGoodsId(goodsId, userName);
		if (relateNum3 == 0) {
			log.error("未定位到数据库中商品明细信息>>>goodsId:{}", goodsId);
		}
		return ResultRes.success(goodsId);
	}

	@Override
	public ResultRes<GoodsRes> updateGoodsSimplified(UpdateGoodsReq params) {

		params.setManHourCost(null);
		params.setVisitFee(null);
		params.setMaterialsExpenses(null);
		params.setPlatformServiceMoney(null);

		Goods goods = new Goods();
		BeanUtils.copyProperties(params, goods);

		if (params.getPlatformSubsidy() != null) {
			Goods dbGoods = goodsMapper.selectByPrimaryKey(params.getUuid());
			if (params.getPlatformSubsidy().compareTo(dbGoods.getPlatformSubsidy()) != 0) {

				// 售价
				BigDecimal amt = DigitUtils.subtract(dbGoods.getSourceAmt(), params.getPlatformSubsidy());
				goods.setPlatformSubsidy(params.getPlatformSubsidy());
				goods.setAmt(amt);
			} else {
				goods.setPlatformSubsidy(null);
			}
		}

		goodsMapper.updateByPrimaryKeySelective(goods);
		return queryGoods(params.getUuid());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<GoodsRes> updateGoods(UpdateGoodsReq updateGoodsReq) {

		String goodsId = updateGoodsReq.getUuid();
		String userName = TokenHelper.getUserName();

		Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
		if (goods == null || StsEnum.INVALID.getValue().equals(goods.getSts())) {
			log.error("未定位到商品信息>>>goodsUuid:{}", goodsId);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}

		String storeUuid = updateGoodsReq.getStoreUuid();
		Store store = storeMapper.selectByPrimaryKey(storeUuid);
		if (null == store || StsEnum.INVALID.getValue().equals(store.getSts())) {
			log.error("未定位到店铺信息>>>storeUuid:{}", storeUuid);
			throw new BusinessException(ResEnum.NOT_STORE);
		}

		// 增加商品明细数据列表
		List<GoodsDetailReq> detailList = updateGoodsReq.getDetailList();
		if (CollectionUtils.isEmpty(detailList)) {
			log.error("物料缺失");
			throw new BusinessException(ResEnum.ERROR_GOODS_EMPTY);
		}

		ResultRes<StoreServiceRatesRes> rate = storeServiceRatesFeign.queryStoreServiceRates(store.getUuid(),
				updateGoodsReq.getParentType());
		if (rate == null || !rate.isSuccess() || rate.getData() == null) {
			log.error("店铺不存在此商品分类的配置");
			throw new BusinessException(ResEnum.NOT_STORE_RATES);
		}

		Goods params = new Goods();
		BeanUtils.copyProperties(updateGoodsReq, params);

		// 人工费
		BigDecimal manHourCost = (null != updateGoodsReq.getManHourCost()) ? updateGoodsReq.getManHourCost()
				: BigDecimal.ZERO;
		// 上门服务费
		BigDecimal visitFee = (null != updateGoodsReq.getVisitFee()) ? updateGoodsReq.getVisitFee() : BigDecimal.ZERO;
		// 物料总费用
		BigDecimal materialsExpenses = detailList.stream().map(GoodsDetailReq::getActAmt).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		// 原价
		BigDecimal sourceAmt = manHourCost.add(visitFee).add(materialsExpenses);

		// 平台服务费
		BigDecimal platformServiceMoney = DigitUtils.divide(sourceAmt.multiply(rate.getData().getServiceRates()),
				new BigDecimal("100"));

		// 平台返利
		BigDecimal platformSubsidy = (null != updateGoodsReq.getPlatformSubsidy()) ? updateGoodsReq.getPlatformSubsidy()
				: BigDecimal.ZERO;
		// 售价
		BigDecimal amt = DigitUtils.subtract(sourceAmt, platformSubsidy);

		params.setManHourCost(manHourCost);
		params.setVisitFee(visitFee);
		params.setMaterialsExpenses(materialsExpenses);
		params.setSourceAmt(sourceAmt);
		params.setPlatformServiceMoney(platformServiceMoney);
		params.setPlatformSubsidy(platformSubsidy);
		params.setAmt(amt);
		params.setLastUpdatedBy(userName);
		params.setLastUpdatedTime(new Date());
		int i = goodsMapper.updateByPrimaryKeySelective(params);
		if (0 == i) {

			log.error("更新商品失败>>>params:{}", JSON.toJSONString(params));
			throw new BusinessException(ResEnum.GOODS_NOT_EXIST);
		}

		List<GoodsImgReq> goodsImgListReq = updateGoodsReq.getGoodsImgListReq();
		if (!CollectionUtils.isEmpty(goodsImgListReq)) {
			// 如果有更新图片,删除之前的
			GoodsImages goodsImages = new GoodsImages();
			goodsImages.setGoodsUuid(goodsId);
			goodsImagesMapper.delete(goodsImages);
			// 批量插入图片
			batchInsertGoodsImages(goodsId, goodsImgListReq);
		}

		// 批量修改物料列表
		List<GoodsDetailReq> goodsDetailReqList = updateGoodsReq.getDetailList();
		if (!CollectionUtils.isEmpty(goodsDetailReqList)) {
			// 历史数据失效
			goodsDetailMapper.disableGoodsDetailByGoodsId(goodsId, userName);
			goodsDetailReqList.stream().forEach(s -> {
				BigDecimal actAmt = s.getActAmt();
				String name = s.getName();
				GoodsDetail g = new GoodsDetail();
				g.setUuid(UuidUtils.getUuid());
				g.setGoodsUuid(updateGoodsReq.getUuid());
				g.setActAmt(actAmt);
				g.setAmt(actAmt);
				g.setName(name);
				g.setBak1(s.getBak1());
				g.setBak2(s.getBak2());
				g.setBak3(s.getBak3());
				g.setBak4(s.getBak4());
				g.setUnit(UnitEnum.GE.getUnit());
				g.setSts(StsEnum.ACTIVE.getValue());
				g.setCreatedTime(new Date());
				g.setCreatedBy(userName);
				g.setNum(1);
				goodsDetailMapper.insert(g);
			});
		}

		return queryGoods(goodsId);
	}

	@Override
	public PageRes<List<QueryStoreListRes>> queryStoreList(QueryGoodsListReq param) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(param.getCityName())) {
			ResultRes<AreaRes> areas = systemFeign.queryAreaName(param.getCityName(), 2);
			if (areas.isSuccess()) {
				param.setStoreCityUuid(areas.getData().getUuid());
			}
		}

		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<QueryStoreListRes> storeList = goodsMapper.queryStoreList(param);
		if (!CollectionUtils.isEmpty(storeList)) {
			storeList.stream().forEach(s -> {

				// 查询店铺上传图片列表
				String storeUuid = s.getUuid();
				StoreImages sm = new StoreImages();
				sm.setSts(StsEnum.ACTIVE.getValue());
				sm.setStoreUuid(storeUuid);
				List<StoreImages> smList = storeImagesMapper.select(sm);
				if (!CollectionUtils.isEmpty(smList)) {

					List<ImgStoreRes> igList = new ArrayList<>();
					smList.stream().forEach(ss -> {

						ImgStoreRes is = new ImgStoreRes();
						BeanUtils.copyProperties(ss, is);
						igList.add(is);
					});

					// 图片类型2 门店在前
					List<ImgStoreRes> collect = igList.stream()
							.sorted(Comparator.comparingInt(ImgStoreRes::getImageType).reversed())
							.collect(Collectors.toList());
					s.setImgList(collect);
				}
				// 查询店铺服务列表
				List<String> classifyList = storeService.queryStoreServices(storeUuid);
				s.setClassifyList(classifyList);

				List<String> configNameList = vehicleConfigService
						.queryListByUuid(storeBrandMapper.queryBrandByStoreUuid(storeUuid)).stream()
						.map(d -> d.getConfigName()).collect(Collectors.toList());
				s.setConfigNameList(configNameList);

//                //查询店铺评论条目和评分
//                CommentStaticsRes statics = queryStoreCommentStatics(storeUuid);
//                if(null != statics){
//
//                    s.setCommentNum(statics.getTotalNum());
//                    s.setCommentScore(statics.getScore());
//                }
			});
		}

		PageInfo<QueryStoreListRes> pageInfo = new PageInfo<>(storeList);
		return PageRes.success(storeList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
	}

	@Override
	public PageRes<List<GoodsRes>> queryGoodsList(QueryGoodsListReq param) {

		if (org.apache.commons.lang3.StringUtils.isNotBlank(param.getCityName())) {
			ResultRes<AreaRes> areas = systemFeign.queryAreaName(param.getCityName(), 2);
			if (areas.isSuccess()) {
				param.setStoreCityUuid(areas.getData().getUuid());
			}
		}

		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<GoodsRes> goodsList = goodsMapper.queryGoodsList(param);
		if (!CollectionUtils.isEmpty(goodsList)) {
			goodsList.stream().forEach(good -> {

				String uuid = good.getUuid();

				// 评分信息
				ResultRes<String> scoreRes = scoreFeign.queryGoodsScore(good.getUuid());
				if (scoreRes.isSuccess()) {
					good.setScore(Float.valueOf(scoreRes.getData()));
				}
				// 评论数量
				ResultRes<Integer> commentRes = commentInfoFeign.queryGoodsCommentCount(uuid);
				if (commentRes.isSuccess()) {
					good.setCommentNum(commentRes.getData());
				}

				// 查询物料列表
				String goodsUuid = good.getUuid();
				GoodsDetail detailParams = new GoodsDetail();
				detailParams.setSts(StsEnum.ACTIVE.getValue());
				detailParams.setGoodsUuid(goodsUuid);
				List<GoodsDetail> detailList = goodsDetailMapper.select(detailParams);

				List<GoodsDetailRes> dstDetails = new ArrayList<>();
				if (!CollectionUtils.isEmpty(detailList)) {

					detailList.stream().forEach(d -> {

						GoodsDetailRes r = new GoodsDetailRes();
						BeanUtils.copyProperties(d, r);
						dstDetails.add(r);

					});
				}
				good.setDetailList(dstDetails);

				// 查询图片列表
				GoodsImages imgParams = new GoodsImages();
				imgParams.setSts(StsEnum.ACTIVE.getValue());
				imgParams.setGoodsUuid(goodsUuid);

				List<GoodsImages> imgList = goodsImagesMapper.selectImgList(imgParams);

				List<GoodsImgRes> dstImgs = new ArrayList<>();
				if (!CollectionUtils.isEmpty(imgList)) {

					imgList.stream().forEach(g -> {

						GoodsImgRes dst = new GoodsImgRes();
						BeanUtils.copyProperties(g, dst);
						dstImgs.add(dst);
					});
				}
				good.setImgList(dstImgs);
			});
		}
		PageInfo<GoodsRes> pageInfo = new PageInfo<>(goodsList);
		return PageRes.success(goodsList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
	}

	@Override
	public ResultRes<GoodsRes> queryGoods(String uuid) {
		Goods goods = goodsMapper.selectByPrimaryKey(uuid);
		if (goods == null || StsEnum.INVALID.getValue().equals(goods.getSts())) {
			log.error("未定位到商品信息,goodsUuid:{}", uuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}

		GoodsRes rst = new GoodsRes();
		BeanUtils.copyProperties(goods, rst);
		String parentType = goods.getParentType();
		String subType = goods.getSubType();
		String goodsTypeUuid = goods.getGoodsType();

		// TODO 待优化
		if (org.apache.commons.lang3.StringUtils.isNotBlank(parentType)) {
			GoodsParent levelOne = goodsParentMapper.selectByPrimaryKey(parentType);
			rst.setLevelOneUuid(levelOne.getUuid());
			rst.setLevelOne(levelOne.getGroupName());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(subType)) {
			GoodsParent levelTwo = goodsParentMapper.selectByPrimaryKey(subType);
			rst.setLevelTwoUuid(levelTwo.getUuid());
			rst.setLevelTwo(levelTwo.getGroupName());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(goodsTypeUuid)) {
			GoodsParent level3 = goodsParentMapper.selectByPrimaryKey(goodsTypeUuid);
			rst.setGoodsTypeName(level3.getGroupName());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(goods.getVehicleBrand())) {
			rst.setVehicleBrandName(
					vehicleConfigService.queryConfig(goods.getVehicleBrand()).getData().getConfigName());
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(goods.getVehicleModel())) {
			rst.setVehicleModelName(
					vehicleConfigService.queryConfig(goods.getVehicleModel()).getData().getConfigName());
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(goods.getVehicleYear())) {
			rst.setVehicleYearName(vehicleConfigService.queryConfig(goods.getVehicleYear()).getData().getConfigName());
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(goods.getVehicleCapacity())) {
			rst.setVehicleCapacityName(
					vehicleConfigService.queryConfig(goods.getVehicleCapacity()).getData().getConfigName());
		}

		/*ResultRes<DictionaryRes> dictRes = systemFeign.queryByUuid(goodsTypeUuid);
		if(dictRes.isSuccess()){
		    String goodsTypeName = (null != dictRes.getData()) ? dictRes.getData().getLableDesc() : null;
		    rst.setGoodsType(goodsTypeName);
		}*/

		// 查询商品物料列表信息
		GoodsDetail dtParams = new GoodsDetail();
		dtParams.setSts(StsEnum.ACTIVE.getValue());
		dtParams.setGoodsUuid(uuid);
		List<GoodsDetail> dtList = goodsDetailMapper.select(dtParams);
		if (!CollectionUtils.isEmpty(dtList)) {

			List<GoodsDetailRes> gList = new ArrayList<>();
			dtList.stream().forEach(d -> {

				GoodsDetailRes g = new GoodsDetailRes();
				BeanUtils.copyProperties(d, g);
				gList.add(g);
			});

			rst.setDetailList(gList);
		}

		// 查询相关图片
		GoodsImages imgParams = new GoodsImages();
		imgParams.setGoodsUuid(uuid);
		imgParams.setSts(StsEnum.ACTIVE.getValue());
		List<GoodsImages> goodsImagesList = goodsImagesMapper.select(imgParams);
		if (!CollectionUtils.isEmpty(goodsImagesList)) {

			List<GoodsImgRes> imgList = new ArrayList<>();
			goodsImagesList.stream().forEach(s -> {
				GoodsImgRes d = new GoodsImgRes();
				BeanUtils.copyProperties(s, d);
				imgList.add(d);
			});
			rst.setImgList(imgList);
		}
		return ResultRes.success(rst);
	}

	@Override
	public void exportGoodsList(QueryGoodsListReq exportReq, HttpServletResponse response) {
		try {
			List<GoodsRes> goodsList = goodsMapper.queryGoodsList(exportReq);
			// 对象转化
//            List<QueryStoreListRes> exportStoreList = convertToRes(storeList);
			// 读取模板文件
			InputStream resourceAsStream = getClass().getClassLoader()
					.getResourceAsStream(GoodsConstants.GOODS_INFO_EXPORT_TEMPLATE);
			// 设置空行默认属性
			List<GoodsRes> excelList = ExcelUtils.setFieldValue(goodsList);
			Workbook wb = new XSSFWorkbook(resourceAsStream);
			Sheet sheet = wb.getSheetAt(0);
			// 从第三行开始写入
			int firstRowIndex = sheet.getFirstRowNum() + 2;
			GoodsRes exportDto;
			for (int rowIndex = firstRowIndex; rowIndex < excelList.size() + 2; rowIndex++) {
				// 行样式
				Row rowStyle = (rowIndex % 2) == 0 ? sheet.getRow(2) : sheet.getRow(3);
				// 单列样式
				CellStyle cellStyle = ExcelUtils.getExcelFormat(rowStyle.getCell(1));
				CellStyle cellStyle1 = ExcelUtils.getExcelFormat(rowStyle.getCell(0));
				Row row = sheet.getRow(rowIndex);
				if (row == null) {
					row = sheet.createRow(rowIndex);
				}
				row.setHeight(rowStyle.getHeight());
				exportDto = excelList.get(rowIndex - 2);
				ExcelUtils.setCell(row, cellStyle1, 0, rowIndex - 1);
				ExcelUtils.setCell(row, cellStyle, 1, exportDto.getGoodsName());
				ExcelUtils.setCell(row, cellStyle, 2, exportDto.getStoreName());

				BigDecimal materialsExpenses = (null != exportDto.getMaterialsExpenses())
						? exportDto.getMaterialsExpenses()
						: BigDecimal.ZERO;
				ExcelUtils.setCell(row, cellStyle, 3, materialsExpenses.toString());

				BigDecimal manHourCost = (null != exportDto.getManHourCost()) ? exportDto.getManHourCost()
						: BigDecimal.ZERO;
				ExcelUtils.setCell(row, cellStyle, 4, manHourCost.toString());
				ExcelUtils.setCell(row, cellStyle, 5,
						StringUtils.isEmpty(exportDto.getSurplusNum()) ? 0 : exportDto.getSurplusNum());
				ExcelUtils.setCell(row, cellStyle, 6,
						StringUtils.isEmpty(exportDto.getSalesNum()) ? 0 : exportDto.getSalesNum());
				ExcelUtils.setCell(row, cellStyle, 7,
						DateUtil.dateToStr(exportDto.getCreatedTime(), DateUtil.YYYY_MM_DD));
				ExcelUtils.setCell(row, cellStyle, 8, SellStatusEnum.enumOfDesc(exportDto.getSellSts()));
			}
			ExcelUtils.responseWrite(wb, response, GoodsConstants.GOODS_INFO_EXPORT_TEMPLATE);
		} catch (Exception ex) {
			log.error("商品信息导出异常，异常原因：{}", ExceptionUtils.stackTraceToString(ex));
		}

	}

	@Override
	public CalGoodsRes calGoods(CalGoodsReq params) {

		String goodsUuid = params.getGoodsUuid();
		ResultRes<GoodsRes> rst = queryGoods(goodsUuid);
		GoodsRes data = rst.getData();

		CalGoodsRes res = new CalGoodsRes();
		res.setGoodsRes(data);

		String receiveAddrUuid = params.getReceiveAddrUuid();
		if (StringUtil.isBlank(receiveAddrUuid)) {

			String userUuid = TokenHelper.getUserUuid();

			ReceiveAddr r = new ReceiveAddr();
			r.setSts(StsEnum.ACTIVE.getValue());
			r.setUserId(userUuid);
			List<ReceiveAddr> list = receiveAddrMapper.queryAddrList(r);
			if (!CollectionUtils.isEmpty(list)) {
				ReceiveAddr addr = list.get(0);

				ReceiveAddrRes addrRes = new ReceiveAddrRes();
				BeanUtils.copyProperties(addr, addrRes);
				res.setReceiveAddrRes(addrRes);
			}
		} else {

			ReceiveAddr addr = receiveAddrMapper.selectByPrimaryKey(receiveAddrUuid);
			ReceiveAddrRes addrRes = new ReceiveAddrRes();
			BeanUtils.copyProperties(addr, addrRes);
			res.setReceiveAddrRes(addrRes);
		}
		return res;
	}

	/**
	 * 批量插入商品图片
	 * @param goodsUuid
	 * @param goodsImgListReq
	 */
	private void batchInsertGoodsImages(String goodsUuid, List<GoodsImgReq> goodsImgListReq) {
		if (CollectionUtils.isEmpty(goodsImgListReq)) {
			throw new BusinessException(ResEnum.NOT_ADD_MAIN_GRAPH);
		}

		// 验证主图是否存在
		List<GoodsImgReq> collect = goodsImgListReq.stream()
				.filter(s -> s.getImgType().equals(ImgTypeEnum.MAIN_GRAPH.getValue())).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(collect)) {
			throw new BusinessException(ResEnum.NOT_ADD_MAIN_GRAPH);
		}

		// 主图不能大于2张 [正面/反面]
		if (collect.size() > 2) {
			throw new BusinessException(ResEnum.MORE_MAIN_GRAPH);
		}

		Date currDate = new Date();
		String userName = TokenHelper.getUserName();

		List<GoodsImages> imagesList = new ArrayList<>();
		goodsImgListReq.stream().forEach(s -> {
			GoodsImages goodsImages = new GoodsImages();
			BeanUtils.copyProperties(s, goodsImages);
			goodsImages.setUuid(UuidUtils.getUuid());
			goodsImages.setGoodsUuid(goodsUuid);
			goodsImages.setSts(StsEnum.ACTIVE.getValue());
			goodsImages.setCreatedBy(userName);
			goodsImages.setCreatedTime(currDate);
			imagesList.add(goodsImages);
		});

		goodsImagesMapper.batchInsertGoodsImages(imagesList);
	}

	@Override
	public void updateRefreshPlatformServiceMoney(List<StoreServiceRatesReq> rateList) {

		rateList.forEach(rate -> {
			if (1 == rate.getStatus()) {
				Goods query = new Goods();
				query.setSts(StsEnum.ACTIVE.getValue());
				query.setParentType(rate.getGoodsParentUuid());
				query.setStoreUuid(rate.getStoreUuid());
				List<Goods> goodsList = goodsMapper.select(query);

				if (!CollectionUtils.isEmpty(goodsList)) {

					goodsList.forEach(g -> {

						BigDecimal platformServiceMoney = g.getPlatformServiceMoney();
						// 平台服务费
						BigDecimal newPlatformServiceMoney = DigitUtils
								.divide(g.getSourceAmt().multiply(rate.getServiceRates()), new BigDecimal("100"));

						if (platformServiceMoney.compareTo(newPlatformServiceMoney) != 0) {
							g.setPlatformServiceMoney(newPlatformServiceMoney);
							goodsMapper.updateByPrimaryKey(g);
						}
					});
				}

			}
		});

	}

}
