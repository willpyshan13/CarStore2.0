package com.car.order.web.service.pay.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.car.account.client.feign.*;
import com.car.account.client.response.store.StoreUserRes;
import com.car.order.web.mapper.sharetechnicianorder.ShareTechnicianServiceMapper;
import com.car.order.web.model.sharetechnicianorder.ShareTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.car.account.client.request.platform.PlatformStreamReq;
import com.car.account.client.request.profit.AddProfitReq;
import com.car.account.client.request.store.UpdateStoreAccountReq;
import com.car.account.client.request.technician.UpdateTechnicianAccountReq;
import com.car.account.client.response.goods.GoodsRes;
import com.car.account.client.response.store.StoreDetailRes;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StreamTypeEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.ResultRes;
import com.car.common.utils.DateUtil;
import com.car.common.utils.DigitUtils;
import com.car.common.utils.IpUtils;
import com.car.common.utils.TokenHelper;
import com.car.order.client.enums.consult.ConsultTypeEnum;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.goods.AfterSaleStsEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.PayMethodEnum;
import com.car.order.client.enums.platform.PlatformClassifyEnum;
import com.car.order.client.enums.scene.SceneOrderStsEnum;
import com.car.order.client.enums.sharetechnicianorder.ShareTechnicianOrderEnum;
import com.car.order.client.request.order.order.PayReq;
import com.car.order.web.common.constants.Constants;
import com.car.order.web.dto.PayOrderInfoDto;
import com.car.order.web.mapper.consult.ConsultMapper;
import com.car.order.web.mapper.consult.ConsultOrderMapper;
import com.car.order.web.mapper.course.CourseMapper;
import com.car.order.web.mapper.course.CourseOrderMapper;
import com.car.order.web.mapper.dtc.DtcOrderDetailMapper;
import com.car.order.web.mapper.dtc.DtcOrderMapper;
import com.car.order.web.mapper.goods.OrderGoodsMapper;
import com.car.order.web.mapper.instance.OrderCaseMapper;
import com.car.order.web.mapper.order.OrderInfoMapper;
import com.car.order.web.mapper.scene.SceneOrderMapper;
import com.car.order.web.mapper.scene.SceneOrderServicesMapper;
import com.car.order.web.mapper.sharetechnicianorder.ShareTechnicianOrderMapper;
import com.car.order.web.mapper.technician.TechnicianCaseMapper;
import com.car.order.web.model.consult.Consult;
import com.car.order.web.model.consult.ConsultOrder;
import com.car.order.web.model.course.Course;
import com.car.order.web.model.course.CourseOrder;
import com.car.order.web.model.dtc.DtcOrder;
import com.car.order.web.model.dtc.DtcOrderDetail;
import com.car.order.web.model.goods.OrderGoods;
import com.car.order.web.model.instance.OrderCase;
import com.car.order.web.model.order.OrderInfo;
import com.car.order.web.model.scene.SceneOrder;
import com.car.order.web.model.scene.SceneOrderServices;
import com.car.order.web.model.sharetechnicianorder.ShareTechnicianOrder;
import com.car.order.web.model.technician.cases.TechnicianCase;
import com.car.order.web.service.consult.OrderConsultService;
import com.car.order.web.service.pay.PayService;
import com.car.system.client.feign.SystemFeign;
import com.car.system.client.response.dict.DictionaryRes;
import com.car.utility.client.enums.ChannelTypeEnum;
import com.car.utility.client.feign.PayFeign;
import com.car.utility.client.request.pay.CreateOrderReq;
import com.car.utility.client.response.pay.CreateOrderRes;
import com.codingapi.txlcn.common.util.Maps;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Intellij IDEA.
 *
 * @author: ??cjw
 * Date: ??2021/2/7
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private OrderGoodsMapper orderGoodsMapper;

	@Autowired
	private ConsultOrderMapper consultOrderMapper;

	@Autowired
	private ConsultMapper consultMapper;

	@Autowired
	private OrderCaseMapper orderCaseMapper;

	@Autowired
	private DtcOrderMapper dtcOrderMapper;

	@Autowired
	private DtcOrderDetailMapper dtcOrderDetailMapper;

	@Autowired
	private TechnicianCaseMapper technicianCaseMapper;

	@Autowired
	private PayFeign payFeign;

	@Autowired
	private CourseOrderMapper courseOrderMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private StoreFegin storeFegin;

	@Autowired
	private SceneOrderMapper sceneOrderMapper;

	@Autowired
	private SceneOrderServicesMapper sceneOrderServiceMapper;

	@Autowired
	private ShareTechnicianOrderMapper shareTechnicianOrderMapper;

	@Autowired
	private TechnicianFegin technicianFegin;

	@Autowired
	private SystemFeign systemFeign;

	@Autowired
	private OrderConsultService orderConsultService;

	@Autowired
	private GoodsFeign goodsFeign;

	@Autowired
	private PlatformStreamFeign platformStreamFeign;

	@Autowired
	private ProfitStreamFeign profitStreamFeign;

	@Autowired
	private SceneOrderServicesMapper sceneOrderServicesMapper;
	@Autowired
	private StoreUserFeign storeUserFeign;

	@Autowired
	MessageFeign messageFeign;
	@Autowired
	ShareTechnicianServiceMapper shareTechnicianServiceMapper;
	/**
	 * ??????????????????
	 *
	 * @param req
	 * @param request
	 * @return
	 */
	@Override
	public ResultRes<CreateOrderRes> payConsultOrder(PayReq req, HttpServletRequest request) {
		// ??????????????????
		PayOrderInfoDto payOrderInfoDto = queryOrderInfo(req.getOrderUuid());
		if (null == payOrderInfoDto) {
			log.error("???????????????????????????");
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		// ?????????????????????null???0
		if (null == payOrderInfoDto.getPayAmount() || payOrderInfoDto.getPayAmount().compareTo(BigDecimal.ZERO) == 0) {
			log.error("?????????????????????0");
			throw new BusinessException(ResEnum.INVALID_ORDER_AMOUNT);
		}
		// ????????????????????????
		updateOrderPayType(req.getOrderUuid(), req.getChannelType());

		CreateOrderReq createOrderReq = new CreateOrderReq();
		createOrderReq.setOrderNo(req.getOrderUuid());
		createOrderReq.setPayAmount(payOrderInfoDto.getPayAmount());
		createOrderReq.setOrderTime(DateUtil.dateToStr(payOrderInfoDto.getOrderTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		createOrderReq.setSceneInfo(req.getSceneInfo());
		createOrderReq.setGoodsName(payOrderInfoDto.getGoodsName());
		createOrderReq.setGoodsDesc(Constants.PAY_GOODS_DESC);
		createOrderReq.setClientIp(IpUtils.getRequestIp(request));
		createOrderReq.setChannelType(req.getChannelType());
		createOrderReq.setPaymentType(req.getPaymentType());
		createOrderReq.setReturnUrl(req.getReturnUrl());
		ResultRes<CreateOrderRes> resResultRes = payFeign.createPayOrder(createOrderReq);
		if (!resResultRes.isSuccess()) {
			log.error("?????????????????????????????????{}??????????????????{}", JSON.toJSONString(createOrderReq), JSON.toJSONString(resResultRes));
			throw new BusinessException(ResEnum.PAY_ERROR);
		}
		return ResultRes.success(resResultRes.getData());
	}

	/**
	 * ????????????????????????
	 */
	private void updateOrderPayType(String orderUuid, String payType) {
		// ??????????????????????????????
		String userName = TokenHelper.getUserName();
		// ????????????
		Integer orderPayType = null;
		// ???????????????????????????
		if (ChannelTypeEnum.weixin.getValue().equals(payType)) {
			// ??????????????????
			orderPayType = PayMethodEnum.WE_CHAT_PAY.getValue();
		} else if (ChannelTypeEnum.alipay.getValue().equals(payType)) {
			// ?????????????????????
			orderPayType = PayMethodEnum.ALI_PAY.getValue();
		} else {
			log.error("???????????????????????????????????????uuid??????{}?????????????????????{}", orderUuid, payType);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		// ??????orderInfo????????????
		OrderInfo orderInfo = orderInfoMapper.queryOrderInfo(orderUuid);
		if (null == orderInfo) {
			log.error("?????????????????????????????? orderUuid {}", orderUuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		// ??????????????????????????????
		if (OrderTypeEnum.CONSULT.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????????????????
			this.updateConsultOrderPayType(orderInfo.getOrderUuid(), userName, orderPayType);

		} else if (OrderTypeEnum.GOOD.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????????????????
			this.updateGoodsOrderPayType(orderInfo.getOrderUuid(), userName, orderPayType);

		} else if (OrderTypeEnum.EXAMPLE.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			this.updateCaseOrderPayType(orderInfo.getOrderUuid(), userName, orderPayType);

		} else if (OrderTypeEnum.AUDITOR.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			this.updateConsultOrderPayType(orderInfo.getOrderUuid(), userName, orderPayType);

		} else if (OrderTypeEnum.DTC.getValue().equals(orderInfo.getOrderType())) {
			// ??????dtc????????????????????????
			this.updateDtcOrderPayType(orderInfo.getOrderUuid(), userName, orderPayType);

		} else if (OrderTypeEnum.COURSE.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			this.updateCourseOrderPayType(orderInfo.getOrderUuid(), userName, orderPayType);

		} else if (OrderTypeEnum.SCENE.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			this.updateSceneOrderPayType(orderInfo.getOrderUuid(), userName, orderPayType);

		} else if (OrderTypeEnum.SHARED_TECHNICIAN.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			this.updateShareTechnicianOrderPayType(orderInfo.getOrderUuid(), userName, orderPayType);

		} else if (OrderTypeEnum.SCENE_SERVICE.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			this.updateSceneOrderServicePayType(orderInfo.getOrderUuid(), userName, orderPayType);

		}
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param orderUuid ??????uuid
	 * @param userName  ????????????
	 * @param payType   ????????????
	 */
	private void updateConsultOrderPayType(String orderUuid, String userName, Integer payType) {
		ConsultOrder consultOrder = new ConsultOrder();
		consultOrder.setUuid(orderUuid);
		consultOrder.setPayType(payType);
		consultOrder.setLastUpdatedBy(userName);
		consultOrder.setLastUpdatedTime(new Date());
		int updateOrderGoodsNum = consultOrderMapper.updateByPrimaryKeySelective(consultOrder);
		if (updateOrderGoodsNum <= 0) {
			log.error("??????????????????/???????????????????????????????????????????????????{} ", JSON.toJSONString(consultOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}

	}

	/**
	 * ??????????????????????????????
	 */
	private void updateGoodsOrderPayType(String goodsOrderUuid, String userName, Integer payType) {
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setUuid(goodsOrderUuid);
		orderGoods.setPayType(payType);
		orderGoods.setLastUpdatedBy(userName);
		orderGoods.setLastUpdatedTime(new Date());
		int updateOrderGoodsNum = orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
		if (updateOrderGoodsNum <= 0) {
			log.error("?????????????????????????????????????????????????????????????????????{} ", JSON.toJSONString(orderGoods));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ????????????????????????
	 *
	 * @param caseOrderUuid
	 * @param userName
	 * @param payType
	 */
	private void updateCaseOrderPayType(String caseOrderUuid, String userName, Integer payType) {
		OrderCase orderCase = new OrderCase();
		orderCase.setUuid(caseOrderUuid);
		orderCase.setPayType(payType);
		orderCase.setLastUpdatedBy(userName);
		orderCase.setLastUpdatedTime(new Date());
		int updateOrderGoodsNum = orderCaseMapper.updateByPrimaryKeySelective(orderCase);
		if (updateOrderGoodsNum <= 0) {
			log.error("?????????????????????????????????????????????????????????????????????{} ", JSON.toJSONString(orderCase));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ??????DTC????????????
	 *
	 * @param dtcOrderUuid
	 * @param userName
	 * @param payType
	 */
	private void updateDtcOrderPayType(String dtcOrderUuid, String userName, Integer payType) {
		DtcOrder dtcOrder = new DtcOrder();
		dtcOrder.setUuid(dtcOrderUuid);
		dtcOrder.setPayType(payType);
		dtcOrder.setLastUpdatedBy(userName);
		dtcOrder.setLastUpdatedTime(new Date());
		int updateOrderDtcNum = dtcOrderMapper.updateByPrimaryKeySelective(dtcOrder);
		if (updateOrderDtcNum <= 0) {
			log.error("??????Dtc???????????????????????????????????????????????????{} ", JSON.toJSONString(dtcOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param courseOrderUuid
	 * @param userName
	 * @param payType
	 */
	private void updateCourseOrderPayType(String courseOrderUuid, String userName, Integer payType) {
		CourseOrder courseOrder = new CourseOrder();
		courseOrder.setUuid(courseOrderUuid);
		courseOrder.setPayType(payType);
		courseOrder.setLastUpdatedBy(userName);
		courseOrder.setLastUpdatedTime(new Date());
		int updateOrderDtcNum = courseOrderMapper.updateByPrimaryKeySelective(courseOrder);
		if (updateOrderDtcNum <= 0) {
			log.error("???????????????????????????????????????????????????????????????{} ", JSON.toJSONString(courseOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @param shareTechnicianUuid
	 * @param userName
	 * @param payType
	 */
	private void updateShareTechnicianOrderPayType(String shareTechnicianUuid, String userName, Integer payType) {
		ShareTechnicianOrder shareTechnicianOrder = new ShareTechnicianOrder();
		shareTechnicianOrder.setUuid(shareTechnicianUuid);
		shareTechnicianOrder.setPayType(payType);
		shareTechnicianOrder.setLastUpdatedBy(userName);
		shareTechnicianOrder.setLastUpdatedTime(new Date());
		int updateNum = shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
		if (updateNum <= 0) {
			log.error("???????????????????????????????????????????????????????????????{} ", JSON.toJSONString(shareTechnicianOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param sceneOrderUuid
	 * @param userName
	 * @param payType
	 */
	private void updateSceneOrderServicePayType(String sceneOrderUuid, String userName, Integer payType) {
		SceneOrderServices sceneOrderServices = new SceneOrderServices();
		sceneOrderServices.setUuid(sceneOrderUuid);
		sceneOrderServices.setPayType(payType);
		sceneOrderServices.setLastUpdatedBy(userName);
		sceneOrderServices.setLastUpdatedTime(new Date());
		int updateNum = sceneOrderServiceMapper.updateByPrimaryKeySelective(sceneOrderServices);
		if (updateNum <= 0) {
			log.error("?????????????????????????????????????????????????????????????????????{} ", JSON.toJSONString(sceneOrderServices));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param sceneOrderUuid
	 * @param userName
	 * @param payType
	 */
	private void updateSceneOrderPayType(String sceneOrderUuid, String userName, Integer payType) {
		SceneOrder sceneOrder = new SceneOrder();
		sceneOrder.setUuid(sceneOrderUuid);
		sceneOrder.setPayType(payType);
		sceneOrder.setLastUpdatedBy(userName);
		sceneOrder.setLastUpdatedTime(new Date());
		int updateNum = sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
		if (updateNum <= 0) {
			log.error("?????????????????????????????????????????????????????????????????????{} ", JSON.toJSONString(sceneOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ??????????????????
	 *
	 * @param orderUuid
	 * @return
	 */
	private PayOrderInfoDto queryOrderInfo(String orderUuid) {
		// ????????????????????????
		OrderInfo orderInfo = orderInfoMapper.queryOrderInfo(orderUuid);
		if (null == orderInfo) {
			log.error("?????????????????????????????? orderUuid {}", orderUuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		// ????????????????????????
		if (OrderTypeEnum.CONSULT.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			payOrderInfoDto = queryConsultInfo(orderUuid);

		} else if (OrderTypeEnum.GOOD.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			payOrderInfoDto = queryGoodOrder(orderUuid);

		} else if (OrderTypeEnum.EXAMPLE.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????
			payOrderInfoDto = queryCaseOrder(orderUuid);

		} else if (OrderTypeEnum.AUDITOR.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????
			payOrderInfoDto = queryConsultInfo(orderUuid);

		} else if (OrderTypeEnum.DTC.getValue().equals(orderInfo.getOrderType())) {
			// ??????dtc??????????????????
			payOrderInfoDto = queryDtcOrder(orderUuid);

		} else if (OrderTypeEnum.COURSE.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????
			payOrderInfoDto = queryCourseOrder(orderUuid);

		} else if (OrderTypeEnum.SCENE.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			payOrderInfoDto = querySceneOrder(orderUuid);

		} else if (OrderTypeEnum.SHARED_TECHNICIAN.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????????????????
			payOrderInfoDto = queryShareTechnicianOrder(orderUuid);

		} else if (OrderTypeEnum.SCENE_SERVICE.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????????????????
			payOrderInfoDto = querySceneOrderService(orderUuid);

		}
		return payOrderInfoDto;
	}

	/**
	 * ??????????????????
	 *
	 * @param orderUuid
	 * @return
	 */
	private PayOrderInfoDto queryCourseOrder(String orderUuid) {
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		CourseOrder courseOrderSelect = new CourseOrder();
		courseOrderSelect.setUuid(orderUuid);
		courseOrderSelect.setSts(StsEnum.ACTIVE.getValue());
		CourseOrder courseOrder = courseOrderMapper.selectOne(courseOrderSelect);
		if (null != courseOrder) {
			payOrderInfoDto.setPayAmount(courseOrder.getOrderAmount());
			payOrderInfoDto.setGoodsName(courseOrder.getCourseTitle());
			payOrderInfoDto.setOrderTime(courseOrder.getCreatedTime());
		}
		return payOrderInfoDto;
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param orderUuid
	 * @return
	 */
	private PayOrderInfoDto querySceneOrder(String orderUuid) {
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		SceneOrder sceneOrderSelect = new SceneOrder();
		sceneOrderSelect.setUuid(orderUuid);
		sceneOrderSelect.setSts(StsEnum.ACTIVE.getValue());
		SceneOrder sceneOrder = sceneOrderMapper.selectOne(sceneOrderSelect);
		if (null != sceneOrder) {
			payOrderInfoDto.setPayAmount(sceneOrder.getTotalAmount());
			payOrderInfoDto.setGoodsName(Constants.PAY_SCENE_NAME);
			payOrderInfoDto.setOrderTime(sceneOrder.getCreatedTime());
		}
		return payOrderInfoDto;
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @param orderUuid
	 * @return
	 */
	private PayOrderInfoDto querySceneOrderService(String orderUuid) {
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		SceneOrderServices sceneOrderServices = new SceneOrderServices();
		sceneOrderServices.setUuid(orderUuid);
		sceneOrderServices.setSts(StsEnum.ACTIVE.getValue());
		sceneOrderServices = sceneOrderServiceMapper.selectOne(sceneOrderServices);
		if (null != sceneOrderServices) {
			payOrderInfoDto.setPayAmount(sceneOrderServices.getTotalAmount());
			payOrderInfoDto.setGoodsName(Constants.PAY_SCENE_NAME);
			payOrderInfoDto.setOrderTime(sceneOrderServices.getCreatedTime());
		}
		return payOrderInfoDto;
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param orderUuid
	 * @return
	 */
	private PayOrderInfoDto queryShareTechnicianOrder(String orderUuid) {
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		ShareTechnicianOrder shareTechnicianOrder = shareTechnicianOrderMapper
				.queryOrderShareTechnicianOrderInfo(orderUuid);
		if (null == shareTechnicianOrder) {
			log.error("????????????????????????????????????????????????");
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		payOrderInfoDto.setGoodsName(Constants.PAY_SHARE_TECHNICIAN_NAME);
		payOrderInfoDto.setPayAmount(shareTechnicianOrder.getTotalAmount());
		payOrderInfoDto.setOrderTime(shareTechnicianOrder.getCreatedTime());
		return payOrderInfoDto;
	}

	/**
	 * ??????dtc??????????????????
	 *
	 * @param orderUuid
	 * @return
	 */
	private PayOrderInfoDto queryDtcOrder(String orderUuid) {
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		DtcOrder dtcOrder = new DtcOrder();
		dtcOrder.setSts(StsEnum.ACTIVE.getValue());
		dtcOrder.setUuid(orderUuid);
		dtcOrder = dtcOrderMapper.selectOne(dtcOrder);
		if (StringUtils.isEmpty(dtcOrder)) {
			log.error("??????????????????dtc??????????????????");
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		DtcOrderDetail orderDetail = new DtcOrderDetail();
		orderDetail.setSts(StsEnum.ACTIVE.getValue());
		orderDetail.setOrderUuid(orderUuid);
		orderDetail = dtcOrderDetailMapper.selectOne(orderDetail);
		if (StringUtils.isEmpty(orderDetail)) {
			log.error("??????????????????dtc????????????????????????");
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		payOrderInfoDto.setGoodsName(orderDetail.getDtcCode());
		payOrderInfoDto.setPayAmount(dtcOrder.getOrderAmount());
		payOrderInfoDto.setOrderTime(dtcOrder.getCreatedTime());
		return payOrderInfoDto;
	}

	/**
	 * ????????????????????????
	 */
	private PayOrderInfoDto queryCaseOrder(String orderUuid) {
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		OrderCase orderCaseSelect = new OrderCase();
		orderCaseSelect.setUuid(orderUuid);
		orderCaseSelect.setSts(StsEnum.ACTIVE.getValue());
		OrderCase orderCase = orderCaseMapper.selectOne(orderCaseSelect);
		if (null == orderCase) {
			log.error("????????????????????????????????????");
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		TechnicianCase technicianCaseSelect = new TechnicianCase();
		technicianCaseSelect.setUuid(orderCase.getCaseUuid());
		TechnicianCase technicianCase = technicianCaseMapper.selectOne(technicianCaseSelect);
		if (null != technicianCase) {
			payOrderInfoDto.setGoodsName(technicianCase.getTitle());
			payOrderInfoDto.setPayAmount(orderCase.getReceivableAmount());
			payOrderInfoDto.setOrderTime(orderCase.getCreatedTime());
		}
		return payOrderInfoDto;
	}

	/**
	 * ??????????????????????????????
	 */
	private PayOrderInfoDto queryGoodOrder(String orderUuid) {
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		OrderGoods orderGoodsSelect = new OrderGoods();
		orderGoodsSelect.setUuid(orderUuid);
		orderGoodsSelect.setSts(StsEnum.ACTIVE.getValue());
		OrderGoods orderGoods = orderGoodsMapper.selectOne(orderGoodsSelect);
		if (null != orderGoods) {
			BigDecimal amount = orderGoods.getActualAmount();
//            if (ReceiveMethodEnum.EXPRESS.getCode().equals(orderGoods.getDeliveryMode()) ) {
//                amount = DigitUtils.subtract(amount, orderGoods.getAmtService());
//            } else {
//                amount = orderGoods.getActualAmount();
//            }
			payOrderInfoDto.setGoodsName(orderGoods.getGoodsName());
			payOrderInfoDto.setPayAmount(amount);
			payOrderInfoDto.setOrderTime(orderGoods.getCreatedTime());
		}
		return payOrderInfoDto;
	}

	/**
	 * ????????????/??????????????????
	 *
	 * @param orderUuid
	 * @return
	 */
	private PayOrderInfoDto queryConsultInfo(String orderUuid) {
		PayOrderInfoDto payOrderInfoDto = new PayOrderInfoDto();
		ConsultOrder consultOrderSelect = new ConsultOrder();
		consultOrderSelect.setUuid(orderUuid);
		consultOrderSelect.setSts(StsEnum.ACTIVE.getValue());
		ConsultOrder consultOrder = consultOrderMapper.selectOne(consultOrderSelect);
		if (null == consultOrder) {
			log.error("??????????????????????????????????????????");
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		Consult consultSelect = new Consult();
		consultSelect.setUuid(consultOrder.getConsultUuid());
		// ????????????????????????
		Consult consult = consultMapper.selectOne(consultSelect);
		if (null != consult) {
			payOrderInfoDto.setGoodsName(consult.getTitle());
			payOrderInfoDto.setPayAmount(consultOrder.getReceivableAmount());
			payOrderInfoDto.setOrderTime(consultOrder.getCreatedTime());
		}
		return payOrderInfoDto;
	}

	/**
	 * ????????????????????????
	 *
	 * @param orderInfoUuid
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> updateOrderPaySts(String orderInfoUuid) {
		log.info("---------????????????-------" + orderInfoUuid);
		if (StringUtils.isEmpty(orderInfoUuid)) {
			log.error("??????orderInfoUuid????????????");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		boolean over = false;
		// ??????????????????????????????
		String userName = TokenHelper.getUserName();
		// ????????????id??????orderInfo??????
		OrderInfo orderInfo = this.selectOrderInfo(orderInfoUuid);
		// ??????????????????????????????
		if (OrderTypeEnum.GOOD.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????
			OrderGoodsBranchAccount(orderInfo);

			// ????????????????????????????????????
			this.updateGoodsOrder(orderInfo.getOrderUuid(), userName);
		} else if (OrderTypeEnum.CONSULT.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????
			consultOrderBranchAccount(orderInfo, OrderTypeEnum.CONSULT.getValue());

			// ????????????????????????????????????
			this.updateConsultOrder(orderInfo.getOrderUuid(), userName, OrderTypeEnum.CONSULT.getValue());
		} else if (OrderTypeEnum.EXAMPLE.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????
			caseOrderBranchAccount(orderInfo);
			// ??????????????????????????????
			this.updateCaseOrder(orderInfo.getOrderUuid(), userName);
			over = true;
		} else if (OrderTypeEnum.AUDITOR.getValue().equals(orderInfo.getOrderType())) {
			// ??????????????????
			consultOrderBranchAccount(orderInfo, OrderTypeEnum.AUDITOR.getValue());
			// ??????????????????????????????
			this.updateConsultOrder(orderInfo.getOrderUuid(), userName, OrderTypeEnum.AUDITOR.getValue());
			over = true;
		} else if (OrderTypeEnum.DTC.getValue().equals(orderInfo.getOrderType())) {
			// dtc??????????????????
			dtcOrderBranchAccount(orderInfo);
			// ??????dtc????????????????????????
			this.updateDtcOrder(orderInfo.getOrderUuid(), userName);
			over = true;
		} else if (OrderTypeEnum.COURSE.getValue().equals(orderInfo.getOrderType())) {
			// ?????????????????????????????? ?????????
			this.updateCourseOrder(orderInfo.getOrderUuid(), OrderTypeEnum.COURSE.getValue(), userName);
			over = true;
		} else if (OrderTypeEnum.SCENE.getValue().equals(orderInfo.getOrderType())) {
			// ?????????????????????????????????????????????
			this.updateSceneOrder(orderInfo.getOrderUuid(), userName, orderInfo.getOrderType());
			// sceneOrderBranchAccount(orderInfo);
			this.sceneOrderBranchAccount(orderInfo);
		} else if (OrderTypeEnum.SHARED_TECHNICIAN.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????
			shareTechnicianOrderBranchAccount(orderInfo);

			// ????????????????????????????????????
			this.updateShareTechnicianOrder(orderInfo.getOrderUuid(), userName,orderInfo.getOrderType());
		} else if (OrderTypeEnum.SCENE_SERVICE.getValue().equals(orderInfo.getOrderType())) {
			// ?????????????????????????????????????????????
			this.updateSceneOrder(orderInfo.getOrderUuid(), userName, orderInfo.getOrderType());
			this.sceneOrderServiceBranchAccount(orderInfo);
		}else if (OrderTypeEnum.SHARED_SERVICE.getValue().equals(orderInfo.getOrderType())) {
			// ????????????????????????
			shareTechnicianOrderServiceAccount(orderInfo);

			// ????????????????????????????????????
			this.updateShareTechnicianOrder(orderInfo.getOrderUuid(), userName,orderInfo.getOrderType());
		}
		// ??????orderInfo??????????????????
		this.updateOrderInfo(orderInfoUuid, userName, over);
		return ResultRes.success(orderInfoUuid);
	}

	/**
	 *  ???????????????????????????
	 * @param orderInfo
	 */
	private void sceneOrderBranchAccount(OrderInfo orderInfo) {
		SceneOrder sceneOrder = new SceneOrder();
		sceneOrder.setUuid(orderInfo.getOrderUuid());
		sceneOrder.setSts(StsEnum.ACTIVE.getValue());
		sceneOrder = sceneOrderMapper.selectOne(sceneOrder);
		if (null != sceneOrder) {
			this.addProfit(sceneOrder.getOrderNum(), OrderTypeEnum.SCENE.getValue(), sceneOrder.getTotalAmount(),
					sceneOrder.getIssuerUuid(), UserTypeEnum.technician.getType(), StreamTypeEnum.OUT.getType());
			this.addPlatfrom(sceneOrder.getOrderNum(), PlatformClassifyEnum.SERVICE.getValue(),
					orderInfo.getOrderType(), sceneOrder.getOrderServiceAmount());
			BigDecimal amt = sceneOrder.getTotalAmount().subtract(sceneOrder.getOrderServiceAmount());
			this.addPlatfrom(sceneOrder.getOrderNum(), PlatformClassifyEnum.ACCOUNT.getValue(),
					orderInfo.getOrderType(), amt);
		}

	}

	/**
	 * ????????????????????????
	 * @param orderInfo
	 */
	private void sceneOrderServiceBranchAccount(OrderInfo orderInfo) {

		SceneOrderServices sceneOrderServices = new SceneOrderServices();
		sceneOrderServices.setUuid(orderInfo.getOrderUuid());
		sceneOrderServices.setSts(StsEnum.ACTIVE.getValue());
		sceneOrderServices = sceneOrderServicesMapper.selectOne(sceneOrderServices);
		SceneOrder sceneOrder = new SceneOrder();
		sceneOrder.setOrderNum(sceneOrderServices.getOrderNum());
		sceneOrder.setSts(StsEnum.ACTIVE.getValue());
		sceneOrder = sceneOrderMapper.selectOne(sceneOrder);
		if (null != sceneOrderServices) {
			this.addProfit(sceneOrderServices.getOrderNum(), OrderTypeEnum.SCENE.getValue(),
					sceneOrderServices.getTotalAmount(), sceneOrder.getIssuerUuid(), UserTypeEnum.technician.getType(),
					StreamTypeEnum.OUT.getType());
			this.addPlatfrom(sceneOrderServices.getOrderNum(), PlatformClassifyEnum.SERVICE.getValue(),
					orderInfo.getOrderType(), sceneOrderServices.getOrderServiceAmount());
			BigDecimal amt = sceneOrderServices.getTotalAmount().subtract(sceneOrderServices.getOrderServiceAmount());
			this.addPlatfrom(sceneOrderServices.getOrderNum(), PlatformClassifyEnum.ACCOUNT.getValue(),
					orderInfo.getOrderType(), amt);
		}

	}

	/**
	 * ????????????????????????
	 */
	private void shareTechnicianOrderBranchAccount(OrderInfo orderInfo) {
		// ??????????????????????????????
		ShareTechnicianOrder shareTechnicianOrder = shareTechnicianOrderMapper
				.queryOrderShareTechnicianOrderInfo(orderInfo.getOrderUuid());
		if (null != shareTechnicianOrder) {
			// ?????????????????????????????????
			this.addPlatfrom(shareTechnicianOrder.getOrderNum(), PlatformClassifyEnum.SERVICE.getValue(),
					orderInfo.getOrderType(), shareTechnicianOrder.getPlatformMoney());
			this.addPlatfrom(shareTechnicianOrder.getOrderNum(), PlatformClassifyEnum.ACCOUNT.getValue(),
					orderInfo.getOrderType(), shareTechnicianOrder.getBasicDoorAmount());
			this.addProfit(shareTechnicianOrder.getOrderNum(), orderInfo.getOrderType(),
					shareTechnicianOrder.getTotalAmount(), shareTechnicianOrder.getOwnerUuid(),
					UserTypeEnum.vehicle.getType(), StreamTypeEnum.OUT.getType());
		}

	}

	/**
	 * ????????????????????????
	 */
	private void shareTechnicianOrderServiceAccount(OrderInfo orderInfo) {
		// ??????????????????????????????

		ShareTechnicianService shareTechnicianService = new ShareTechnicianService();
		shareTechnicianService.setUuid(orderInfo.getOrderUuid());
		shareTechnicianService.setSts(StsEnum.ACTIVE.getValue());
		shareTechnicianService = shareTechnicianServiceMapper.selectOne(shareTechnicianService);
		ShareTechnicianOrder shareTechnicianOrder = new ShareTechnicianOrder();
		shareTechnicianOrder.setOrderNum(shareTechnicianService.getOrderNum());
		shareTechnicianOrder.setSts(StsEnum.ACTIVE.getValue());
		shareTechnicianOrder = shareTechnicianOrderMapper.selectOne(shareTechnicianOrder);
		if (null != shareTechnicianOrder) {
			// ?????????????????????????????????
			this.addPlatfrom(shareTechnicianService.getOrderNum(), PlatformClassifyEnum.SERVICE.getValue(),
					orderInfo.getOrderType(), shareTechnicianService.getOrderServiceAmount());
			BigDecimal amt = shareTechnicianService.getTotalAmount().subtract(shareTechnicianService.getOrderServiceAmount());
			this.addPlatfrom(shareTechnicianOrder.getOrderNum(), PlatformClassifyEnum.ACCOUNT.getValue(),
					orderInfo.getOrderType(), amt);
			this.addProfit(shareTechnicianService.getOrderNum(), orderInfo.getOrderType(),
					shareTechnicianService.getTotalAmount(), shareTechnicianOrder.getOwnerUuid(),
					UserTypeEnum.vehicle.getType(), StreamTypeEnum.OUT.getType());
		}

	}
	/**
	 * dtc?????????????????? ?????????
	 *
	 * @param orderInfo
	 */
	private void dtcOrderBranchAccount(OrderInfo orderInfo) {
		// ??????dtc??????????????????
		DtcOrder dtcOrderSelect = new DtcOrder();
		dtcOrderSelect.setSts(StsEnum.ACTIVE.getValue());
		dtcOrderSelect.setUuid(orderInfo.getOrderUuid());
		DtcOrder dtcOrder = dtcOrderMapper.selectOne(dtcOrderSelect);

		this.addPlatfrom(dtcOrder.getOrderNum(), PlatformClassifyEnum.FEE.getValue(), orderInfo.getOrderType(),
				dtcOrder.getOrderAmount());
		this.addProfit(dtcOrder.getOrderNum(), orderInfo.getOrderType(), dtcOrder.getOrderAmount(),
				dtcOrder.getBuyerUuid(), null, StreamTypeEnum.OUT.getType());
		/* if (null != dtcOrder) {
		    if (UserTypeEnum.technician.getType().equals(dtcOrder.getDtcIssuerType())) {
		        //????????????????????????
		        this.updateTechnicianAccount(dtcOrder.getIssuerUuid(), dtcOrder.getOrderAmount());
		    } else if (UserTypeEnum.store.getType().equals(dtcOrder.getDtcIssuerType())) {
		        //????????????????????????
		        this.updateStoreAccount(dtcOrder.getIssuerUuid(), dtcOrder.getOrderAmount(),dtcOrder.getOrderNum(),orderInfo.getOrderType());
		    }
		}*/
	}

	/**
	 * ??????????????????
	 *
	 * @param orderInfo
	 */
	private void caseOrderBranchAccount(OrderInfo orderInfo) {
		// ????????????????????????
		OrderCase orderCaseSelect = new OrderCase();
		orderCaseSelect.setUuid(orderInfo.getOrderUuid());
		orderCaseSelect.setSts(StsEnum.ACTIVE.getValue());
		OrderCase orderCase = orderCaseMapper.selectOne(orderCaseSelect);
		if (null != orderCase) {

			// ??????????????????
			BigDecimal amount = orderCase.getReceivableAmount();
			BigDecimal casePlatform = queryPlatformCommissionByValue(Constants.CASE_COMMISSION);
			BigDecimal platformCommission = casePlatform.multiply(orderCase.getReceivableAmount()).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			if (orderCase.getReceivableAmount().compareTo(platformCommission) > 0) {
				// ??????????????????????????????????????????????????? = ???????????? - ????????????
				amount = DigitUtils.subtract(orderCase.getReceivableAmount(), platformCommission);

				this.addPlatfrom(orderCase.getOrderNum(), PlatformClassifyEnum.SERVICE.getValue(),
						orderInfo.getOrderType(), platformCommission);
			}
			// ????????????
			this.addProfit(orderCase.getOrderNum(), orderInfo.getOrderType(), orderCase.getReceivableAmount(),
					orderCase.getCarOwnerUuid(), orderCase.getCarOwnerType(), StreamTypeEnum.OUT.getType());
			TechnicianCase technicianCase = technicianCaseMapper.selectByPrimaryKey(orderCase.getCaseUuid());
			// ????????????
			String userUuid = technicianCase.getTechnicianUuid();
			if(UserTypeEnum.store.getType().equals(technicianCase.getTechnicianType())){
				StoreUserRes storeUserRes = storeUserFeign.getStoreUset(technicianCase.getTechnicianUuid()).getData();
				userUuid =storeUserRes.getUuid();
			}
			this.addProfit(orderCase.getOrderNum(), orderInfo.getOrderType(), amount, userUuid,
					technicianCase.getTechnicianType(), StreamTypeEnum.IN.getType());
			if (UserTypeEnum.technician.getType().equals(technicianCase.getTechnicianType())) {
				// ????????????????????????
				this.updateTechnicianAccount(orderCase.getTechnicianUuid(), amount);
			} else if (UserTypeEnum.store.getType().equals(technicianCase.getTechnicianType())) {
				this.updateStoreAccount(orderCase.getTechnicianUuid(), amount);
			}
		}
	}

	/**
	 * ????????????/??????????????????
	 */
	private void consultOrderBranchAccount(OrderInfo orderInfo, Integer orderType) {
		// ??????????????????????????????
		ConsultOrder consultOrderSelect = new ConsultOrder();
		consultOrderSelect.setUuid(orderInfo.getOrderUuid());
		consultOrderSelect.setSts(StsEnum.ACTIVE.getValue());
		ConsultOrder consultOrder = consultOrderMapper.selectOne(consultOrderSelect);
		if (null != consultOrder) {

			Consult consultSelect = new Consult();
			consultSelect.setUuid(consultOrder.getConsultUuid());
			Consult consult = consultMapper.selectOne(consultSelect);
			if (null != consult) {
				BigDecimal platformCommission = BigDecimal.ZERO;
				BigDecimal amount = consultOrder.getReceivableAmount();
				if (OrderTypeEnum.CONSULT.getValue().equals(orderType)) {
					// ????????????????????????
					if (ConsultTypeEnum.technician_questions.getValue().equals(consult.getConsultType())) {
						platformCommission = queryPlatformCommissionByValue(Constants.CONSULT_ZHUANJIA);
					} else {
						platformCommission = queryPlatformCommissionByValue(Constants.CONSULT_COMMISSION);
					}
					if (consultOrder.getReceivableAmount().compareTo(platformCommission) > 0) {
						// ??????????????????????????????????????????????????? = ???????????? - ????????????
						amount = DigitUtils.subtract(consultOrder.getReceivableAmount(), platformCommission);
						// ???????????????????????????
						this.addPlatfrom(consultOrder.getOrderNum(), PlatformClassifyEnum.SERVICE.getValue(), orderType,
								platformCommission);
					}
					// ?????????????????? ???????????????????????????????????????????????????
					this.addPlatfrom(consultOrder.getOrderNum(), PlatformClassifyEnum.ACCOUNT.getValue(),
							OrderTypeEnum.ANSWER.getValue(), amount);
				} else if (OrderTypeEnum.AUDITOR.getValue().equals(orderType)) {
					// ????????????????????????
					platformCommission = queryPlatformCommission(Constants.AUDIT_COMMISSION);
					if (consultOrder.getReceivableAmount().compareTo(platformCommission) > 0) {
						// ??????????????????????????????????????????????????? = ???????????? - ????????????
						amount = DigitUtils.subtract(consultOrder.getReceivableAmount(), platformCommission);
						// ???????????????????????????
						this.addPlatfrom(consultOrder.getOrderNum(), PlatformClassifyEnum.SERVICE.getValue(), orderType,
								platformCommission);
					}
					this.addPlatfrom(consultOrder.getOrderNum(), PlatformClassifyEnum.FEE.getValue(), orderType,
							amount);
				}
				// ????????????
				this.addProfit(consultOrder.getOrderNum(), orderType, consultOrder.getReceivableAmount(),
						consultOrder.getCarOwnerUuid(), consultOrder.getCarOwnerType(), StreamTypeEnum.OUT.getType());
				log.info("---------consultOrderBranchAccount-----4--");
			}
		}
	}

	/**
	 * ??????????????????
	 *
	 * @param dictUuid
	 * @return
	 */
	private BigDecimal queryPlatformCommissionByValue(String dictUuid) {
		BigDecimal platformCommission = BigDecimal.ZERO;
		if (StringUtils.isEmpty(dictUuid)) {
			return platformCommission;
		}
		// ??????????????????
		ResultRes<DictionaryRes> resResultRes = systemFeign.queryByUuid(dictUuid);
		if (resResultRes.isSuccess()) {
			if (null != resResultRes.getData()) {
				platformCommission = new BigDecimal(resResultRes.getData().getLableValue());
			}
		}
		return platformCommission;
	}

	/**
	 * ??????????????????
	 *
	 * @param dictCode
	 * @return
	 */
	private BigDecimal queryPlatformCommission(String dictCode) {
		BigDecimal platformCommission = BigDecimal.ZERO;
		if (StringUtils.isEmpty(dictCode)) {
			return platformCommission;
		}
		// ??????????????????
		ResultRes<DictionaryRes> resResultRes = systemFeign.queryByUuid(dictCode);
		if (resResultRes.isSuccess()) {
			if (null != resResultRes.getData()) {
				platformCommission = new BigDecimal(resResultRes.getData().getLableDesc());
			}
		}
		return platformCommission;
	}

	/**
	 * ????????????????????????
	 * ???????????????????????????????????????
	 * ?????????????????????????????????- ???????????????=??????????????????
	 */
	private void OrderGoodsBranchAccount(OrderInfo orderInfo) {
		// ??????????????????????????????
		OrderGoods orderGoodsSelect = new OrderGoods();
		orderGoodsSelect.setUuid(orderInfo.getOrderUuid());
		orderGoodsSelect.setSts(StsEnum.ACTIVE.getValue());
		OrderGoods orderGoods = orderGoodsMapper.selectOne(orderGoodsSelect);

		if (null != orderGoods) {

			// ???????????????
			addPlatfrom(orderGoods.getOrderNum(), PlatformClassifyEnum.SERVICE.getValue(), orderInfo.getOrderType(),
					orderGoods.getPlatformServiceMoney());

			// ???????????????
			addPlatfrom(orderGoods.getOrderNum(), PlatformClassifyEnum.ACCOUNT.getValue(), orderInfo.getOrderType(),
					orderGoods.getStoreFee());

			// ???????????????
			addProfit(orderGoods.getOrderNum(), orderInfo.getOrderType(), orderGoods.getActualAmount(),
					orderGoods.getUserUuid(), UserTypeEnum.vehicle.getType(), StreamTypeEnum.OUT.getType());

		}
	}

	/**
	 * ???????????????????????????????????????
	 *
	 * @param goodsUuid
	 * @return
	 */
	private Boolean checkGoodsShareStation(String goodsUuid) {
		Boolean flag = false;
		if (StringUtils.isEmpty(goodsUuid)) {
			return flag;
		}
		// ??????????????????
		ResultRes<GoodsRes> goodsResResultRes = goodsFeign.queryGoodsDetail(goodsUuid);
		if (goodsResResultRes.isSuccess()) {
			if (null != goodsResResultRes.getData()) {
				if (Constants.SHARE_STATION_UUID.equals(goodsResResultRes.getData().getLevelTwoUuid())) {
					flag = true;
				}

			}
		}
		return flag;
	}

	/**
	 * ????????????id??????orderInfo??????
	 */
	private OrderInfo selectOrderInfo(String orderInfoUuid) {
		OrderInfo orderInfo = orderInfoMapper.queryOrderInfo(orderInfoUuid);
		if (orderInfo == null) {
			log.error("???????????????????????????orderInfo?????????,??????id??????{}", orderInfoUuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		return orderInfo;
	}

	/**
	 * ??????orderInfo????????????????????????
	 */
	private void updateOrderInfo(String orderInfoUuid, String userName, boolean over) {
		OrderInfo orderInfoUpdate = new OrderInfo();
		orderInfoUpdate.setOrderUuid(orderInfoUuid);
		// true??????????????????????????????
		if (over) {
			orderInfoUpdate.setPaySts(OrderStsEnum.COMPLETED.getValue());
		} else {
			orderInfoUpdate.setPaySts(OrderStsEnum.HAVE_PAID.getValue());
		}
		orderInfoUpdate.setLastUpdatedBy(userName);
		orderInfoUpdate.setLastUpdatedTime(new Date());
		int orderInfoUpdateNum = orderInfoMapper.updateRefund(orderInfoUpdate);
		if (orderInfoUpdateNum <= 0) {
			log.error("??????orderInfo???????????????????????????????????????????????????{}", JSON.toJSONString(orderInfoUpdate));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ??????????????????????????????
	 */
	private void updateGoodsOrder(String goodsOrderUuid, String userName) {
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setUuid(goodsOrderUuid);
		orderGoods.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
		orderGoods.setLastUpdatedBy(userName);
		orderGoods.setLastUpdatedTime(new Date());
		orderGoods.setAfterSaleSts(0);
		int updateOrderGoodsNum = orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
		if (updateOrderGoodsNum <= 0) {
			log.error("?????????????????????????????????????????????????????????????????????{} ", JSON.toJSONString(orderGoods));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ??????????????????/????????????????????????
	 */
	private void updateConsultOrder(String consultOrderUuid, String userName, Integer orderType) {
		ConsultOrder consultOrder = consultOrderMapper.selectByPrimaryKey(consultOrderUuid);
		consultOrder.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
		consultOrder.setLastUpdatedBy(userName);
		consultOrder.setLastUpdatedTime(new Date());
		int updateOrderGoodsNum = consultOrderMapper.updateByPrimaryKeySelective(consultOrder);
		if (updateOrderGoodsNum <= 0) {
			log.error("??????????????????/???????????????????????????????????????????????????{} ", JSON.toJSONString(consultOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
		// ?????????????????????????????????????????????????????????
		if (OrderTypeEnum.CONSULT.getValue().equals(orderType)) {
			orderConsultService.pushContentCheck(consultOrderUuid);

			messageFeign.sendMsg("2014", Maps.newHashMap("amount", consultOrder.getReceivableAmount().toString()),
					consultOrder.getCarOwnerUuid(), consultOrder.getCarOwnerType(), consultOrderUuid);
		}
	}

	/**
	 * ????????????????????????
	 */
	private void updateCaseOrder(String caseOrderUuid, String userName) {
		OrderCase orderCase = new OrderCase();
		orderCase.setUuid(caseOrderUuid);
		orderCase.setOrderSts(OrderStsEnum.COMPLETED.getValue());
		orderCase.setLastUpdatedBy(userName);
		orderCase.setLastUpdatedTime(new Date());
		int updateOrderGoodsNum = orderCaseMapper.updateByPrimaryKeySelective(orderCase);
		if (updateOrderGoodsNum <= 0) {
			log.error("?????????????????????????????????????????????????????????????????????{} ", JSON.toJSONString(orderCase));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ??????DTC????????????
	 */
	private void updateDtcOrder(String dtcOrderUuid, String userName) {
		DtcOrder dtcOrder = new DtcOrder();
		dtcOrder.setUuid(dtcOrderUuid);
		dtcOrder.setOrderSts(OrderStsEnum.COMPLETED.getValue());
		dtcOrder.setLastUpdatedBy(userName);
		dtcOrder.setLastUpdatedTime(new Date());
		int updateOrderDtcNum = dtcOrderMapper.updateByPrimaryKeySelective(dtcOrder);
		if (updateOrderDtcNum <= 0) {
			log.error("??????Dtc???????????????????????????????????????????????????{} ", JSON.toJSONString(dtcOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}

	}

	/**
	 * ?????????????????????????????? ?????????
	 */
	private void updateCourseOrder(String courseOrderUuid, Integer orderType, String userName) {
		// ????????????????????????
		CourseOrder courseOrderSelect = new CourseOrder();
		courseOrderSelect.setUuid(courseOrderUuid);
		CourseOrder courseOrderInfo = courseOrderMapper.selectOne(courseOrderSelect);
		if (null != courseOrderInfo) {
			Course courseSelect = new Course();
			courseSelect.setUuid(courseOrderInfo.getUuid());
			Course course = courseMapper.selectOne(courseSelect);
			if (null != course) {
				Course updateCourse = new Course();
				updateCourse.setUuid(course.getUuid());
				int courseSalesVolume = null == course.getCourseSalesVolume() ? 0 : course.getCourseSalesVolume();
				updateCourse.setCourseSalesVolume(courseSalesVolume + 1);
				updateCourse.setLastUpdatedTime(new Date());
				updateCourse.setLastUpdatedBy(userName);
				int updateCourseNum = courseMapper.updateByPrimaryKeySelective(updateCourse);
				if (updateCourseNum <= 0) {
					log.error("???????????????????????????????????????????????????{} ", JSON.toJSONString(updateCourse));
					throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
				}
			}
		}
		CourseOrder courseOrder = new CourseOrder();
		courseOrder.setUuid(courseOrderUuid);
		courseOrder.setOrderSts(OrderStsEnum.COMPLETED.getValue());
		courseOrder.setLastUpdatedBy(userName);
		courseOrder.setLastUpdatedTime(new Date());
		int updateOrderDtcNum = courseOrderMapper.updateByPrimaryKeySelective(courseOrder);
		if (updateOrderDtcNum <= 0) {
			log.error("???????????????????????????????????????????????????????????????{} ", JSON.toJSONString(courseOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
		this.addPlatfrom(courseOrder.getOrderNum(), PlatformClassifyEnum.FEE.getValue(), orderType,
				courseOrder.getOrderAmount());
		this.addProfit(courseOrder.getOrderNum(), orderType, courseOrder.getOrderAmount(), courseOrder.getBuyerUuid(),
				null, StreamTypeEnum.OUT.getType());
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @param sceneOrderUuid
	 * @param userName
	 */
	private void updateSceneOrder(String sceneOrderUuid, String userName, Integer orderType) {
		SceneOrder sceneOrder = new SceneOrder();

		if (orderType.equals(OrderTypeEnum.SCENE.getValue())) {
			sceneOrder.setUuid(sceneOrderUuid);
			SceneOrder sceneOrder1 = sceneOrderMapper.selectOne(sceneOrder);
			sceneOrder.setOrderSts(SceneOrderStsEnum.WAIT_DOOR.getValue());
			sceneOrder.setPayDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
			try {
				Map<String, String> param = new HashMap<>();
				param.put("site", sceneOrder1.getDetailedAddr());
				param.put("pingpai", sceneOrder1.getBrandName());
				param.put("chexing", sceneOrder1.getCarModelName());
				messageFeign.sendMsg("3003", param, sceneOrder1.getIssuerUuid(), sceneOrder1.getIssuerType(),
						sceneOrderUuid);

				Map<String, String> param1 = new HashMap<>();
				sendSceneMsg(sceneOrder1, param1);
				messageFeign.sendMsg("3009", param1, sceneOrder1.getBuyerUuid(), sceneOrder1.getBuyerUserType(),
						sceneOrderUuid);

			} catch (Exception e) {
				log.info("??????????????????!!");
			}
		} else if (orderType.equals(OrderTypeEnum.SCENE_SERVICE.getValue())) {
			log.info("sceneOrderUuid----------" + sceneOrderUuid);
			SceneOrderServices sceneOrderServices = new SceneOrderServices();
			sceneOrderServices.setUuid(sceneOrderUuid);
			sceneOrderServices.setSts(StsEnum.ACTIVE.getValue());
			sceneOrderServices.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
			sceneOrderServices.setLastUpdatedBy(userName);
			sceneOrderServices.setLastUpdatedTime(new Date());
			sceneOrderServicesMapper.updateByPrimaryKeySelective(sceneOrderServices);

			SceneOrderServices sceneOrderSer = sceneOrderServicesMapper.selectByPrimaryKey(sceneOrderServices);
			SceneOrder sceneOrder1 = new SceneOrder();
			sceneOrder1.setOrderNum(sceneOrderSer.getOrderNum());
			sceneOrder1.setSts(StsEnum.ACTIVE.getValue());
			sceneOrder1 = sceneOrderMapper.selectOne(sceneOrder1);
			sceneOrder.setUuid(sceneOrder1.getUuid());
			sceneOrder.setOrderSts(SceneOrderStsEnum.IN_SERVICE.getValue());
			try {

				Map<String, String> param1 = new HashMap<>();
				sendSceneMsg(sceneOrder1, param1);
				messageFeign.sendMsg("3013", param1, sceneOrder1.getBuyerUuid(), sceneOrder1.getBuyerUserType(),
						sceneOrderUuid);

			} catch (Exception e) {
				e.printStackTrace();
				log.info("??????????????????!!");
			}
		}
		sceneOrder.setLastUpdatedBy(userName);
		sceneOrder.setLastUpdatedTime(new Date());
		int updateNum = sceneOrderMapper.updateByPrimaryKeySelective(sceneOrder);
		if (updateNum <= 0) {
			log.error("?????????????????????????????????????????????????????????????????????{} ", JSON.toJSONString(sceneOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}

	}

	private void sendSceneMsg(SceneOrder sceneOrderSelect, Map<String, String> param) {
		if (UserTypeEnum.store.getType().equals(sceneOrderSelect.getBuyerUserType())) {
			ResultRes<StoreDetailRes> storeDetailResResultRes = storeFegin
					.queryStoreDetailByUserUuid(sceneOrderSelect.getBuyerUuid());
			param.put("technicianName", storeDetailResResultRes.getData().getStoreName());
            param.put("type", UserTypeEnum.store.getDesc());
		} else {
			param.put("technicianName", sceneOrderSelect.getBuyerName());
			param.put("type", UserTypeEnum.technician.getDesc());
		}
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @param shareTechnicianOrderUuid
	 * @param userName
	 */
	private void updateShareTechnicianOrder(String shareTechnicianOrderUuid, String userName,Integer orderType) {
		ShareTechnicianOrder shareTechnicianOrder = new ShareTechnicianOrder();
		if (orderType.equals(OrderTypeEnum.SHARED_TECHNICIAN.getValue())) {

			shareTechnicianOrder.setUuid(shareTechnicianOrderUuid);
			shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.WAIT_DOOR.getValue());
			shareTechnicianOrder.setPayDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));

		}else if (orderType.equals(OrderTypeEnum.SHARED_SERVICE.getValue())){
			ShareTechnicianService shareTechnicianService = new ShareTechnicianService();
			shareTechnicianService.setUuid(shareTechnicianOrderUuid);
			shareTechnicianService.setSts(StsEnum.ACTIVE.getValue());
			ShareTechnicianService shareTechnicianSelect = shareTechnicianServiceMapper.selectOne(shareTechnicianService);
			shareTechnicianService.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
			shareTechnicianService.setLastUpdatedBy(userName);
			shareTechnicianService.setLastUpdatedTime(new Date());
			shareTechnicianServiceMapper.updateByPrimaryKeySelective(shareTechnicianService);

			ShareTechnicianOrder shareTechnicianOrderSelect = new ShareTechnicianOrder();
			shareTechnicianOrderSelect.setOrderNum(shareTechnicianSelect.getOrderNum());
			shareTechnicianOrderSelect.setSts(StsEnum.ACTIVE.getValue());
			shareTechnicianOrderSelect = shareTechnicianOrderMapper.selectOne(shareTechnicianOrderSelect);
			shareTechnicianOrder.setUuid(shareTechnicianOrderSelect.getUuid());
			shareTechnicianOrder.setOrderStatus(ShareTechnicianOrderEnum.IN_SERVICE.getValue());
		}
		shareTechnicianOrder.setLastUpdatedBy(userName);
		shareTechnicianOrder.setLastUpdatedTime(new Date());
		int updateNum = shareTechnicianOrderMapper.updateByPrimaryKeySelective(shareTechnicianOrder);
		if (updateNum <= 0) {
			log.error("?????????????????????????????????????????????????????????????????????{} ", JSON.toJSONString(shareTechnicianOrder));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

	/**
	 * ????????????????????????
	 *
	 * @param storeUuid
	 * @param orderAccount
	 */
	private void updateStoreAccount(String storeUuid, BigDecimal orderAccount) {
		UpdateStoreAccountReq req = new UpdateStoreAccountReq();
		req.setStoreUuid(storeUuid);
		req.setOrderAmount(orderAccount);
		ResultRes<String> resultRes = storeFegin.updateStoreAccount(req);
		if (!resultRes.isSuccess()) {
			log.error("?????????????????????????????????????????????{}", JSON.toJSONString(req));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}

	}

	private void addPlatfrom(String orderNo, Integer classify, Integer orderType, BigDecimal amt) {
		PlatformStreamReq platformStreamReq = new PlatformStreamReq();
		platformStreamReq.setAmt(amt);
		platformStreamReq.setClassify(classify);
		platformStreamReq.setOrderNo(orderNo);
		platformStreamReq.setOrderType(orderType);
		platformStreamReq.setStreamType(StreamTypeEnum.IN.getType());
		platformStreamFeign.addPlatfrom(platformStreamReq);
	}

	/**
	 * ??????????????????
	 * @param orderNo
	 * @param orderType
	 * @param amt
	 * @param userUid
	 * @param userType
	 * @param streamType
	 */
	private void addProfit(String orderNo, Integer orderType, BigDecimal amt, String userUid, Integer userType,
			Integer streamType) {
		AddProfitReq addProfitReq = new AddProfitReq();
		addProfitReq.setAmt(amt);
		addProfitReq.setUserType(userType);
		addProfitReq.setUserUuid(userUid);
		addProfitReq.setClassify(orderType);
		addProfitReq.setOrderNo(orderNo);
		addProfitReq.setStreamType(streamType);
		profitStreamFeign.addProfit(addProfitReq);
	}

	/**
	 * ????????????????????????
	 *
	 * @param technicianUuid
	 * @param orderAccount
	 */
	private void updateTechnicianAccount(String technicianUuid, BigDecimal orderAccount) {
		UpdateTechnicianAccountReq req = new UpdateTechnicianAccountReq();
		req.setTechnicianUuid(technicianUuid);
		req.setOrderAmount(orderAccount);
		ResultRes<String> resultRes = technicianFegin.updateTechnicianAccount(req);
		if (!resultRes.isSuccess()) {
			log.error("?????????????????????????????????????????????{}", JSON.toJSONString(req));
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
	}

}
