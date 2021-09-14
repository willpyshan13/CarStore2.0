package com.car.order.web.service.consult.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.car.account.client.response.store.StoreBrandRes;
import com.car.account.client.response.technician.TechnicianBrandRes;
import org.apache.commons.collections4.map.HashedMap;
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
import com.car.account.client.feign.MessageFeign;
import com.car.account.client.feign.StoreFegin;
import com.car.account.client.feign.StoreUserFeign;
import com.car.account.client.feign.TechnicianFegin;
import com.car.account.client.feign.VehicleFegin;
import com.car.account.client.request.store.UpdateStoreAccountReq;
import com.car.account.client.request.technician.UpdateTechnicianAccountReq;
import com.car.account.client.response.platform.PlatformStreamRes;
import com.car.account.client.response.store.StoreDetailRes;
import com.car.account.client.response.store.StoreUserRes;
import com.car.account.client.response.technician.TechnicianRes;
import com.car.account.client.response.vehicle.config.ConfigRes;
import com.car.account.client.response.vehicle.vehicleUser.VehicleUserRes;
import com.car.common.enums.CheckStatusEnum;
import com.car.common.enums.OrderPrefixEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StreamTypeEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.ExcelUtils;
import com.car.common.utils.ExceptionUtils;
import com.car.common.utils.OrderUtils;
import com.car.common.utils.RequestUtil;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.common.utils.token.Baggages;
import com.car.order.client.enums.consult.AcceptResultEnum;
import com.car.order.client.enums.consult.AnswerStsEnum;
import com.car.order.client.enums.consult.ConsultImgTypenum;
import com.car.order.client.enums.consult.ConsultOrderTypeEnum;
import com.car.order.client.enums.consult.ConsultTypeEnum;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.goods.EvaluateStsEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.PayMethodEnum;
import com.car.order.client.enums.order.WhetherAgreeRefundEnum;
import com.car.order.client.request.content.AddContentReq;
import com.car.order.client.request.order.consult.AddAnswerConsultReq;
import com.car.order.client.request.order.consult.AddAuditorReq;
import com.car.order.client.request.order.consult.AddConsultReq;
import com.car.order.client.request.order.consult.PayConsultReq;
import com.car.order.client.request.order.consult.QueryConsultListReq;
import com.car.order.client.request.order.consult.QueryOrderConsultListReq;
import com.car.order.client.request.order.consult.UpdateAcceptResultReq;
import com.car.order.client.request.order.order.AddOrderInfoReq;
import com.car.order.client.request.order.order.OrderWhetherAgreeRefundReq;
import com.car.order.client.response.order.consult.ConsultInfoListRes;
import com.car.order.client.response.order.consult.ConsultOrderDetailRes;
import com.car.order.client.response.order.consult.ConsultRes;
import com.car.order.client.response.order.consult.OrderConsultInfoListRes;
import com.car.order.web.common.constants.ConfigConsts;
import com.car.order.web.common.constants.Constants;
import com.car.order.web.mapper.consult.ConsultDetailMapper;
import com.car.order.web.mapper.consult.ConsultImagesMapper;
import com.car.order.web.mapper.consult.ConsultMapper;
import com.car.order.web.mapper.consult.ConsultOrderMapper;
import com.car.order.web.model.consult.Consult;
import com.car.order.web.model.consult.ConsultDetail;
import com.car.order.web.model.consult.ConsultImages;
import com.car.order.web.model.consult.ConsultOrder;
import com.car.order.web.service.consult.OrderConsultService;
import com.car.order.web.service.content.ContentService;
import com.car.order.web.service.order.OrderAccountService;
import com.car.order.web.service.order.OrderInfoService;
import com.car.system.client.feign.SystemFeign;
import com.car.system.client.response.dict.DictionaryRes;
import com.car.utility.client.feign.PayFeign;
import com.codingapi.txlcn.common.util.Maps;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

/**
 * @author zhouz
 * @date 2021/1/1
 */
@Slf4j
@Service
public class OrderConsultServiceImpl implements OrderConsultService {

	private static final int SUB_STR_LENGTH = 46;
	@Autowired
	ConsultMapper consultMapper;
	@Autowired
	ConsultDetailMapper consultDetailMapper;
	@Autowired
	ConsultImagesMapper consultImagesMapper;
	@Autowired
	ConsultOrderMapper consultOrderMapper;

	@Autowired
	ContentService contentService;

	@Autowired
	ConfigConsts configConsts;
	@Resource
	private TechnicianFegin technicianFegin;
	@Resource
	private VehicleFegin vehicleFegin;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	SystemFeign systemFeign;

	@Autowired
	private StoreFegin storeFegin;
	@Autowired
	private OrderAccountService orderAccountService;

	@Autowired
	private MessageFeign messageFeign;
	@Autowired
	private StoreUserFeign storeUserFeign;
	@Autowired
	private PayFeign payFeign;

	/**
	 * 咨询订单金额字典编码
	 */
	private static final String consultMoneyCode = "consult_amount";

	/**
	 * 新增咨询订单
	 * @param addConsultReq
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> addConsultOrder(AddConsultReq addConsultReq) {
		log.debug("新增咨询订单");
		log.info("addConsultReq:" + JSON.toJSONString(addConsultReq));
		Consult consult = new Consult();
		String technicianUuid = addConsultReq.getTechnicianUuid();
		BigDecimal answerAmt = null;
		if (StringUtils.isEmpty(technicianUuid)) {
			// 不存在技师ID，全国提问订单，从字典中取标准资费订单
			ResultRes<DictionaryRes> dictionaryRes = systemFeign.queryByCode(consultMoneyCode);
			if (!dictionaryRes.isSuccess() || dictionaryRes.getData() == null) {
				throw new BusinessException(ResEnum.NON_EXISTENT);
			}
			answerAmt = BigDecimal.valueOf(Double.valueOf(dictionaryRes.getData().getLableValue()));
			consult.setConsultType(ConsultTypeEnum.national_questions.getValue());

		} else {
			ResultRes<TechnicianRes> res = technicianFegin.queryTechnicianDetail(technicianUuid);
			if (!res.isSuccess()) {
				log.error("未定位到技师信息>>>res:{}", JSON.toJSONString(res));
				throw new BusinessException(ResEnum.TECHNICIAN_NOT_EXIST);
			}
			TechnicianRes data = res.getData();
			String photoImgUrl = data.getPhotoImgUrl();
			String mobile = data.getMobile();
			String name = data.getUserName();
			answerAmt = data.getAnswerAmt();
			consult.setTechnicianImgUrl(photoImgUrl);
			consult.setTechnicianName(name);
			consult.setTechnicianMobile(mobile);
			consult.setTechnicianUuid(technicianUuid);
			consult.setConsultType(ConsultTypeEnum.technician_questions.getValue());
			consult.setTechnicianType(UserTypeEnum.technician.getType());

			/*//消息
			MessageReq messageReq =new MessageReq();
			messageReq.setContent("客户向我提出了问题，请前往处理接单作答。");
			messageReq.setPushMessageContent("您有一个新的共享技师订单，等待接单，请前往【订单】—【共享技师订单】查看");
			messageFeign.insertMessagePush(messageReq);*/
		}

		String consultTitle = addConsultReq.getConsultTitle();
		String carOwnerUuid = TokenHelper.getUserUuid();

		String ehicleUuid = "";// 数据ID
		String PhotoImgUrl = "";// 头像图片链接地址
		String Mobile = "";// 联系方式
		String Name = "";// 姓名
		if (UserTypeEnum.vehicle.getType().equals(TokenHelper.getUserType())) {
			ResultRes<VehicleUserRes> rst = vehicleFegin.queryDetail(carOwnerUuid);
			if (!rst.isSuccess()) {
				log.error("未定位到车主信息>>>rst:{}", JSON.toJSONString(rst));
				log.error("未定位到车主信息》》》token0：{}", RequestUtil.getRequestHeaderParam("token"));
				log.error("未定位到车主信息》》》token1：{}", Baggages.getToken());
				throw new BusinessException(ResEnum.VEHICLE_OWNER_NOT_EXIST);
			}

			VehicleUserRes vehicle = rst.getData();
			ehicleUuid = vehicle.getUuid();
			PhotoImgUrl = vehicle.getPhotoImgUrl();
			Mobile = vehicle.getMobile();
			Name = vehicle.getUserName();
		} else if (UserTypeEnum.store.getType().equals(TokenHelper.getUserType())) {
			// 如果新增案例人员为店铺联系人,查询店铺信息
			StoreDetailRes storeDetailRes = queryStoreDetailByUser();
			ehicleUuid = TokenHelper.getUserUuid();
			if (storeDetailRes.getShopImgList() != null) {
				PhotoImgUrl = storeDetailRes.getShopImgList().get(0);
			}
			Mobile = TokenHelper.getLoginToken().getUserMobile();
			Name = storeDetailRes.getStoreName();
		} else if (UserTypeEnum.technician.getType().equals(TokenHelper.getUserType())) {
			ehicleUuid = TokenHelper.getUserUuid();
			ResultRes<TechnicianRes> res = technicianFegin.queryTechnicianDetail(ehicleUuid);
			if (!res.isSuccess()) {
				log.error("未定位到技师信息>>>res:{}", JSON.toJSONString(res));
				throw new BusinessException(ResEnum.TECHNICIAN_NOT_EXIST);
			}
			TechnicianRes data = res.getData();
			PhotoImgUrl = data.getPhotoImgUrl();
			Mobile = data.getMobile();
			Name = data.getUserName();
		}

		// 新增咨询
		String consultUuid = UuidUtils.getUuid();
		consult.setUuid(consultUuid);
		consult.setCreatedTime(new Date());
		consult.setCreatedBy(TokenHelper.getUserName());
		consult.setSts(StsEnum.ACTIVE.getValue());
		consult.setCarOwnerImgUrl(PhotoImgUrl);
		consult.setCarOwnerName(Name);
		consult.setCarOwnerMobile(Mobile);
		consult.setCarOwnerUuid(ehicleUuid);
		consult.setConsultCheckSts(CheckStatusEnum.CHECK_PENDING.getValue());
		consult.setAnswerCheckSts(CheckStatusEnum.CHECK_PENDING.getValue());
		consult.setAnswerSts(AnswerStsEnum.UNANSWERED.getValue());
		consult.setCarOwnerType(TokenHelper.getUserType());
		consult.setTitle(consultTitle);
		consult.setVehicleBrand(addConsultReq.getVehicleBrand());
		consult.setVehicleModel(addConsultReq.getVehicleModel());
		consult.setTechnicalTypeUuid(addConsultReq.getTechnicalTypeUuid());

		// 订单咨询信息入库
		consultMapper.insert(consult);

		// 咨询详情入库
		ConsultDetail consultDetail = new ConsultDetail();
		consultDetail.setUuid(consultUuid);
		consultDetail.setConsultUuid(consult.getUuid());
		consultDetail.setCreatedTime(new Date());
		consultDetail.setCreatedBy(TokenHelper.getUserName());
		consultDetail.setSts(StsEnum.ACTIVE.getValue());

		consultDetail.setConsultDesc(addConsultReq.getConsultContent());
		consultDetailMapper.insert(consultDetail);

		// 新增咨询相关图片
		List<String> consultImgList = addConsultReq.getConsultImgList();

		addConsultImg(consultImgList, consult.getUuid(), ConsultImgTypenum.CONSULT_IMG.getValue());

		// 新增咨询订单信息
		String consultOrderUuid = UuidUtils.getUuid();
		ConsultOrder consultOrder = new ConsultOrder();
		consultOrder.setUuid(consultOrderUuid);
		consultOrder.setCreatedTime(new Date());
		consultOrder.setCreatedBy(TokenHelper.getUserName());
		consultOrder.setSts(StsEnum.ACTIVE.getValue());
		consultOrder.setConsultUuid(consultUuid);
		consultOrder.setOrderAmount(answerAmt);
		consultOrder.setReceivableAmount(answerAmt);
		consultOrder.setCarOwnerImgUrl(PhotoImgUrl);
		consultOrder.setCarOwnerName(Name);
		consultOrder.setCarOwnerMobile(Mobile);
		consultOrder.setCarOwnerUuid(ehicleUuid);
		consultOrder.setCarOwnerType(TokenHelper.getUserType());
		consultOrder.setEvaluateSts(EvaluateStsEnum.NO_COMMENT.getValue());
		String orderNo = OrderUtils.GenOrderNo(OrderPrefixEnum.ZX);
		consultOrder.setOrderNum(orderNo);
		consultOrder.setOrderSts(OrderStsEnum.UNPAID.getValue());
		consultOrder.setServiceSts(ConsultOrderTypeEnum.No_SERVICE.getValue());
		consultOrder.setOrderType(OrderTypeEnum.CONSULT.getValue());

		// 新增咨询订单
		consultOrderMapper.insert(consultOrder);

/*		if (org.apache.commons.lang3.StringUtils.isNotBlank(consult.getTechnicianUuid())) {
			messageFeign.sendMsg("2001", Maps.newHashMap("userName", TokenHelper.getUserName()), technicianUuid,
					UserTypeEnum.technician.getType(), consultOrderUuid);
		}*/

		// 新增order_info信息
		AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
		addOrderInfoReq.setOrderType(OrderTypeEnum.CONSULT.getValue());
		addOrderInfoReq.setOrderUuid(consultOrderUuid);
		orderInfoService.addOrder(addOrderInfoReq);

		return ResultRes.success(consultOrder.getUuid());
	}

	/**
	 * 删除咨询订单
	 * @param orderUuid
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> deleteConsultOrder(String orderUuid) {
		log.debug("删除咨询订单");
		ConsultOrder consultOrder = checkOrderIsActive(orderUuid);
		consultOrder.setSts(StsEnum.INVALID.getValue());
		consultOrderMapper.updateByPrimaryKeySelective(consultOrder);
		return ResultRes.success(orderUuid);
	}

	/**
	 * 回答咨询订单
	 * @param addAnswerConsultReq
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> updateConsultAnswer(AddAnswerConsultReq addAnswerConsultReq) {
		log.info("回答咨询参数:" + addAnswerConsultReq.toString());
		log.debug("回答咨询订单");
		ConsultOrder consultOrder = checkOrderIsActive(addAnswerConsultReq.getOrderUuid());

		log.debug("查询咨询详情");
		Consult consult = checkConsult(consultOrder.getConsultUuid());
		// 验证技师 是否存在与订单技师是否匹配 addAnswerConsultReq.getTechnicianUuid()
		String technicianUuid = TokenHelper.getUserUuid();
		if (!technicianUuid.equals(consult.getTechnicianUuid())) {
			log.error("回答咨询订单 技师uuid与咨询订单技师uuid不匹配 technicianUuid:{}", technicianUuid);
			throw new BusinessException(ResEnum.TECHNICIAN_NO_CONSUL_ORDER);
		}

		ConsultDetail consultDetail = new ConsultDetail();
		consultDetail.setConsultUuid(consultOrder.getConsultUuid());
		consultDetail.setSts(StsEnum.ACTIVE.getValue());
		consultDetail = consultDetailMapper.selectOne(consultDetail);
		if (StringUtils.isEmpty(consultDetail)) {
			log.error("回答咨询订单 咨询详情不存在 consultUuid:{}", consultOrder.getConsultUuid());
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		consultDetail.setAnswerDesc(addAnswerConsultReq.getAnswerContent());
		consultDetailMapper.updateByPrimaryKeySelective(consultDetail);

		consult.setAnswerSts(AnswerStsEnum.ALREADY_ANSWERED.getValue());
		consult.setAnswerTime(new Date());
		consultMapper.updateByPrimaryKeySelective(consult);

		// 新增咨询相关图片
		addConsultImg(addAnswerConsultReq.getAnswerImgList(), consult.getUuid(),
				ConsultImgTypenum.ANSWER_IMG.getValue());

		// 增加技师的咨询次数
		if (!StringUtils.isEmpty(consult.getTechnicianUuid())
				&& UserTypeEnum.technician.getType().equals(consult.getTechnicianType())) {
			technicianFegin.updateQaCount(consult.getTechnicianUuid());
		}
		ConsultOrder consultOrder1 = new ConsultOrder();
		consultOrder1.setUuid(addAnswerConsultReq.getOrderUuid());
		consultOrder1.setServiceSts(1);
		consultOrderMapper.updateByPrimaryKeySelective(consultOrder1);
		// 增加内容审核
		addContentCheck(consultOrder.getCarOwnerUuid(), consultOrder.getCarOwnerMobile(),
				OrderTypeEnum.ANSWER.getValue(), consult.getTitle(), addAnswerConsultReq.getAnswerContent(),
				consultOrder.getUuid(), addAnswerConsultReq.getAnswerImgList());

		messageFeign.sendMsg("2023", Maps.newHashMap("technicianName", consult.getTechnicianName()), consultOrder.getCarOwnerUuid(),
				consultOrder.getCarOwnerType(), consultOrder.getUuid());

		return ResultRes.success(consultOrder.getUuid());
	}

	/**
	 * 是否采纳回答结果
	 * @param updateAcceptResultReq
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> updateAcceptResult(UpdateAcceptResultReq updateAcceptResultReq) {
		log.debug("更改回答结果采纳状态");
		// 验证订单是否有效
		ConsultOrder consultOrder = checkOrderIsActive(updateAcceptResultReq.getOrderUuid());
		// 验证咨询信息是否有效
		Consult consult = checkConsult(consultOrder.getConsultUuid());

		if (!(consult.getCarOwnerType().equals(TokenHelper.getUserType())
				&& consult.getCarOwnerUuid().equals(TokenHelper.getUserUuid()))) {
			log.error("越权操作,当前用户类型:{},当前用户ID:{},欲将问答UUID:{} 置为满意/不满意回答", TokenHelper.getUserType(),
					TokenHelper.getUserUuid(), consultOrder.getConsultUuid());
			throw new BusinessException(ResEnum.INVALID_ACCEPT_RESULT);
		}

		String desc = AcceptResultEnum.enumOfDesc(updateAcceptResultReq.getAcceptResult());
		if (StringUtils.isEmpty(desc)) {
			log.error("无效的回答结果采纳状态 acceptResult: {}", updateAcceptResultReq.getAcceptResult());
			throw new BusinessException(ResEnum.INVALID_ACCEPT_RESULT);
		}
		consult.setAcceptResult(updateAcceptResultReq.getAcceptResult());
		consultMapper.updateByPrimaryKeySelective(consult);

		return ResultRes.success(consultOrder.getUuid());
	}

	/**
	 * 推送内容审核
	 * @param orderUuid
	 */
	@Override
	public ResultRes pushContentCheck(String orderUuid) {
		ConsultOrder consultOrder = checkOrderIsActive(orderUuid);
		log.debug("查询咨询详情");
		Consult consult = checkConsult(consultOrder.getConsultUuid());

		log.debug("查询咨询描述");
		ConsultDetail consultDetail = new ConsultDetail();
		consultDetail.setConsultUuid(consult.getUuid());
		consultDetail.setSts(StsEnum.ACTIVE.getValue());
		consultDetail = consultDetailMapper.selectOne(consultDetail);
		if (StringUtils.isEmpty(consultDetail)) {
			log.error("未查询到咨询描述 consultUuid:{}", consultOrder.getConsultUuid());
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		ConsultImages consultImages = new ConsultImages();
		consultImages.setConsultUuid(consult.getUuid());
		consultImages.setSts(StsEnum.ACTIVE.getValue());
		consultImages.setImgType(ConsultImgTypenum.CONSULT_IMG.getValue());
		List<ConsultImages> consultImagesList = consultImagesMapper.select(consultImages);

		List<String> resourcesList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(consultImagesList)) {
			for (ConsultImages item : consultImagesList) {
				resourcesList.add(item.getImgUrl());
			}
		}
		// 增加内容审核 支付成功后添加
		addContentCheck(consultOrder.getCarOwnerUuid(), consultOrder.getCarOwnerMobile(),
				OrderTypeEnum.CONSULT.getValue(), consult.getTitle(), consultDetail.getConsultDesc(),
				consultOrder.getUuid(), resourcesList);

		return ResultRes.success(orderUuid);
	}

	/**
	 * 新增旁听订单
	 * @param addAuditorReq
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> addAuditorOrder(AddAuditorReq addAuditorReq) {
		log.debug("新增旁听订单");
		ConsultOrder consultOrder = checkOrderIsActive(addAuditorReq.getOrderUuid());

		ConsultOrder auditorOrder = new ConsultOrder();
		auditorOrder.setUuid(UuidUtils.getUuid());
		auditorOrder.setCreatedTime(new Date());
		auditorOrder.setCreatedBy(TokenHelper.getUserName());
		auditorOrder.setSts(StsEnum.ACTIVE.getValue());
		auditorOrder.setConsultUuid(consultOrder.getConsultUuid());
		auditorOrder.setOrderType(OrderTypeEnum.AUDITOR.getValue());
		String orderNum = OrderUtils.GenOrderNo(OrderPrefixEnum.PT);
		BigDecimal money = new BigDecimal(configConsts.getAuditorOrderMoney());
		auditorOrder.setOrderAmount(money);
		auditorOrder.setReceivableAmount(money);
		auditorOrder.setOrderNum(orderNum);
		auditorOrder.setEvaluateSts(EvaluateStsEnum.NO_COMMENT.getValue());

		String userUuid = TokenHelper.getUserUuid();
		ResultRes<VehicleUserRes> res = vehicleFegin.queryDetail(userUuid);
		if (res.isSuccess()) {
			VehicleUserRes data = res.getData();
			String userName = data.getUserName();
			String mobile = data.getMobile();
			String photoImgUrl = data.getPhotoImgUrl();
			String uuid = data.getUuid();

			auditorOrder.setCarOwnerType(UserTypeEnum.vehicle.getType());
			auditorOrder.setCarOwnerImgUrl(photoImgUrl);
			auditorOrder.setCarOwnerName(userName);
			auditorOrder.setCarOwnerMobile(mobile);
			auditorOrder.setCarOwnerUuid(uuid);
		} else {

			throw new BusinessException(res.getCode(), res.getMsg());
		}

		auditorOrder.setOrderSts(OrderStsEnum.UNPAID.getValue());
		consultOrderMapper.insert(auditorOrder);

		// 新增order_info信息
		AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
		addOrderInfoReq.setOrderUuid(auditorOrder.getUuid());
		addOrderInfoReq.setOrderType(OrderTypeEnum.AUDITOR.getValue());
		orderInfoService.addOrder(addOrderInfoReq);

		return ResultRes.success(auditorOrder.getUuid());
	}

	/**
	 * 新增旁听订单
	 * @param addAuditorReq
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> addAuditorOrderTwo(AddAuditorReq addAuditorReq) {
		log.debug("新增技师/商铺旁听订单");
		ConsultOrder consultOrder = checkOrderIsActive(addAuditorReq.getOrderUuid());

		ConsultOrder auditorOrder = new ConsultOrder();
		auditorOrder.setUuid(UuidUtils.getUuid());
		auditorOrder.setCreatedTime(new Date());
		auditorOrder.setCreatedBy(TokenHelper.getUserName());
		auditorOrder.setSts(StsEnum.ACTIVE.getValue());
		auditorOrder.setConsultUuid(consultOrder.getConsultUuid());
		auditorOrder.setOrderType(OrderTypeEnum.AUDITOR.getValue());
		String orderNum = OrderUtils.GenOrderNo(OrderPrefixEnum.PT);
		BigDecimal money = new BigDecimal(configConsts.getAuditorOrderMoney());
		auditorOrder.setOrderAmount(money);
		auditorOrder.setReceivableAmount(money);
		auditorOrder.setOrderNum(orderNum);
		auditorOrder.setEvaluateSts(EvaluateStsEnum.NO_COMMENT.getValue());
		Integer userType = TokenHelper.getUserType();
		String userUuid = TokenHelper.getUserUuid();

		if (UserTypeEnum.technician.getType().equals(userType)) {
			ResultRes<TechnicianRes> resT = technicianFegin.queryTechnicianDetail(userUuid);
			if (!resT.isSuccess()) {
				log.error("未定位到技师信息>>>oederuuId:{}", addAuditorReq.getOrderUuid());
				throw new BusinessException(ResEnum.NON_EXISTENT);
			}
			TechnicianRes technicianRes = resT.getData();
			auditorOrder.setCarOwnerImgUrl(technicianRes.getPhotoImgUrl());
			auditorOrder.setCarOwnerName(technicianRes.getUserName());
			auditorOrder.setCarOwnerMobile(technicianRes.getMobile());
			auditorOrder.setCarOwnerUuid(technicianRes.getUuid());
		} else if (UserTypeEnum.store.getType().equals(userType)) {
			ResultRes<StoreDetailRes> resS = storeFegin.queryStoreDetailByUser();
			if (resS.getData() != null) {
				resS = storeFegin.queryStoreDetail(resS.getData().getUuid());
				StoreDetailRes storeDetailRes = resS.getData();
				auditorOrder.setCarOwnerImgUrl(storeDetailRes.getShopImgList().get(0));
				auditorOrder.setCarOwnerName(TokenHelper.getUserName());
				auditorOrder.setCarOwnerMobile(TokenHelper.getLoginToken().getUserMobile());
				auditorOrder.setCarOwnerUuid(TokenHelper.getUserUuid());
			}

		}

		auditorOrder.setOrderSts(OrderStsEnum.UNPAID.getValue());
		auditorOrder.setCarOwnerType(userType);
		auditorOrder.setServiceSts(ConsultOrderTypeEnum.AKREADY_SERVICE.getValue());
		consultOrderMapper.insert(auditorOrder);

		// 新增order_info信息
		AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
		addOrderInfoReq.setOrderUuid(auditorOrder.getUuid());
		addOrderInfoReq.setOrderType(OrderTypeEnum.AUDITOR.getValue());
		orderInfoService.addOrder(addOrderInfoReq);

		return ResultRes.success(auditorOrder.getUuid());
	}

	/**
	 * 支付旁听订单
	 * @param payConsultReq
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> payAuditorOrder(PayConsultReq payConsultReq) {
		log.debug("支付旁听订单");
		ConsultOrder consultOrder = checkOrderIsActive(payConsultReq.getOrderUuid());
		// 验证支付类型是否有效
		checkPayType(payConsultReq.getPayType());
		consultOrder.setPayType(payConsultReq.getPayType());
		// TODO
		// 开始支付 支付成功后修改支付状态 插入数据
		consultOrder.setOrderSts(OrderStsEnum.HAVE_PAID.getValue());
		consultOrderMapper.updateByPrimaryKeySelective(consultOrder);

		return ResultRes.success(consultOrder.getUuid());
	}

	/**
	 * 验证支付类型是否有效
	 * @param payType
	 */
	private void checkPayType(Integer payType) {
		String desc = PayMethodEnum.enumOfDesc(payType);
		if (StringUtils.isEmpty(desc)) {
			log.error("支付订单 无效的支付类型 payType:{}", payType);
			throw new BusinessException(ResEnum.INVALID_CHECK_STS);
		}
	}

	/**
	 * 修改咨询状态
	 * @param orderUuid
	 * @param orderType
	 * @param checkSts
	 */
	@Override
	public void updateConsultCheckSts(String orderUuid, Integer orderType, Integer checkSts) {
		log.debug("修改咨询状态");
		ConsultOrder consultOrder = checkOrderIsActive(orderUuid);
		log.debug("查询咨询详情");
		Consult consult = checkConsult(consultOrder.getConsultUuid());

		if (OrderTypeEnum.CONSULT.getValue().equals(orderType)) {
			consult.setConsultCheckSts(checkSts);

			/*if (checkSts == 1 || checkSts == 2) {
				messageFeign.sendMsg(checkSts == 1 ? "2016" : "2015", new HashedMap<>(), consult.getCarOwnerUuid(),
						consult.getCarOwnerType(), orderUuid);
			}*/

		} else if (OrderTypeEnum.ANSWER.getValue().equals(orderType)) {
			consult.setAnswerCheckSts(checkSts);

			if (checkSts == 1 || checkSts == 2) {
				if (consult.getConsultType().equals(ConsultTypeEnum.technician_questions.getValue())) {
					/*messageFeign.sendMsg(checkSts == 1 ? "2002" : "2003",
							Maps.newHashMap("userName", consult.getCarOwnerName()), consult.getTechnicianUuid(),
							UserTypeEnum.technician.getType(), orderUuid);*/
				} else {
					messageFeign.sendMsg(checkSts == 1 ? "2007" : "2008",
							Maps.newHashMap("userName", consult.getCarOwnerName()), consult.getTechnicianUuid(),
							consult.getTechnicianType(), orderUuid);
				}
			/*
				if (checkSts == 1  && consult.getConsultType().equals(ConsultTypeEnum.technician_questions.getValue())) {
					messageFeign.sendMsg("2018", Maps.newHashMap("technicianName", consult.getTechnicianName()),
							consult.getCarOwnerUuid(), consult.getCarOwnerType(), orderUuid);
				} else if (checkSts == 2  && consult.getConsultType().equals(ConsultTypeEnum.technician_questions.getValue())) {
					messageFeign.sendMsg("2020",
							Maps.newHashMap("technicianName", consult.getTechnicianName(), "amount",
									consultOrder.getReceivableAmount().toString()),
							consult.getCarOwnerUuid(), consult.getCarOwnerType(), orderUuid);
				}
			}*/
			}
			if (CheckStatusEnum.APPROVE.getValue().equals(checkSts)) {

				PlatformStreamRes platformStreamRes = orderAccountService.getPlatformStreamRes(orderType,
						consultOrder.getOrderNum());

				if (UserTypeEnum.store.getType().equals(consult.getTechnicianType())) {
					UpdateStoreAccountReq req = new UpdateStoreAccountReq();
					req.setStoreUuid(consult.getTechnicianUuid());
					req.setOrderAmount(platformStreamRes.getAmt());
					ResultRes<String> resultRes = storeFegin.updateStoreAccount(req);
/*					if (!resultRes.isSuccess()) {
						log.error("修改店铺账户信息，请求参数为：{}", JSON.toJSONString(req));
						throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
					}*/
				} else if (UserTypeEnum.technician.getType().equals(consult.getTechnicianType())) {

					UpdateTechnicianAccountReq req = new UpdateTechnicianAccountReq();
					req.setTechnicianUuid(consult.getTechnicianUuid());
					req.setOrderAmount(platformStreamRes.getAmt());
					ResultRes<String> resultRes = technicianFegin.updateTechnicianAccount(req);
					if (!resultRes.isSuccess()) {
						log.error("查询技师账户信息，请求参数为：{}", JSON.toJSONString(req));
						throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
					}
				}

				orderAccountService.addProfit(consultOrder.getOrderNum(), orderType, platformStreamRes.getAmt(),
						consult.getTechnicianUuid(), consult.getTechnicianType(), StreamTypeEnum.IN.getType());
				consultOrder.setOrderSts(OrderStsEnum.COMPLETED.getValue());
				consultOrder.setLastUpdatedTime(new Date());
				int updateOrderGoodsNum = consultOrderMapper.updateByPrimaryKeySelective(consultOrder);
				if (updateOrderGoodsNum <= 0) {
					log.error("修改线上咨询/旁听订单支付状态失败，请求参数为：{} ", JSON.toJSONString(consultOrder));
					throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
				}

			} else if (CheckStatusEnum.CHECK_REJECTED.getValue().equals(checkSts)) {
				// 回答在审核时不通过，直接退钱
				orderRefund(consult);
				return;
			}
		}
		consultMapper.updateByPrimaryKeySelective(consult);
	}

	/**
	 * 查询咨询列表
	 * @param param
	 * @return
	 */
	@Override
	public PageRes<List<ConsultInfoListRes>> queryConsultList(QueryConsultListReq param) {
		log.debug("查询咨询列表");
		log.info("查询咨询列表车主ID" + param.getCarOwnerUuid());
		 BigDecimal price=new BigDecimal(0);
		String userUuid = TokenHelper.getUserUuid();
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<ConsultInfoListRes> consultInfoList = consultOrderMapper.queryConsultList(param);
		if(param.getConsultType()==4){
			price = new BigDecimal(systemFeign.queryByUuid("6220").getData().getLableValue());
		}
		for (ConsultInfoListRes e : consultInfoList) {
			if(price.compareTo(BigDecimal.valueOf(0))==1){
				e.setConsultAmt(price);
			}
			e.setVehicleBrandName(vehicleFegin.queryConfig(e.getVehicleBrand()).getData().getConfigName());
			e.setVehicleModelName(vehicleFegin.queryConfig(e.getVehicleModel()).getData().getConfigName());
			List<String> typeImgList = consultImagesMapper.getTypeImgList(e.getConsultUuid(), "0");
			e.setImgs(typeImgList);
			if (e.getCarOwnerUuid().equals(userUuid)) {
				e.setYesOrNo(1);
			}
		}
		PageInfo<ConsultInfoListRes> pageInfo = new PageInfo<>(consultInfoList);
		return PageRes.success(consultInfoList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
	}

	/**
	 * 查询咨询订单列表
	 * @param param
	 * @return
	 */
	@Override
	public PageRes<List<OrderConsultInfoListRes>> queryOrderConsultList(QueryOrderConsultListReq param) {
		log.debug("查询咨询订单列表");
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		param.setTechnicianUuid(TokenHelper.getUserUuid());
		List<OrderConsultInfoListRes> orderConsultInfoList = consultOrderMapper.queryOrderConsultList(param);
		PageInfo<OrderConsultInfoListRes> pageInfo = new PageInfo<>(orderConsultInfoList);
		return PageRes.success(orderConsultInfoList, pageInfo.getPageSize(), (int) pageInfo.getTotal(),
				pageInfo.getPages());
	}

	/**
	 * 查询咨询订单详情
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<ConsultOrderDetailRes> queryOrderConsultDetail(String uuid) {
		List<String> list=new ArrayList<>();
		log.debug("查询咨询订单详情");
		ConsultOrderDetailRes consultOrderDetailRes = null;
		ConsultOrder consultOrder = new ConsultOrder();
		consultOrder.setSts(StsEnum.ACTIVE.getValue());
		consultOrder.setUuid(uuid);
		// consultOrder.setOrderType(OrderTypeEnum.CONSULT.getValue());
		consultOrder = consultOrderMapper.selectOne(consultOrder);
		if (!StringUtils.isEmpty(consultOrder)) {
			consultOrderDetailRes = new ConsultOrderDetailRes();
			BeanUtils.copyProperties(consultOrder, consultOrderDetailRes);

			log.debug("查询咨询详情");
			Consult consult = new Consult();
			consult.setUuid(consultOrder.getConsultUuid());
			consult.setSts(StsEnum.ACTIVE.getValue());
			consult = consultMapper.selectOne(consult);

			if (!StringUtils.isEmpty(consult)) {
				ConsultRes consultRes = new ConsultRes();
				BeanUtils.copyProperties(consult, consultRes);
				consultRes.setConsultAmt(consultOrder.getOrderAmount());
				consultOrderDetailRes.setAcceptResult(consult.getAcceptResult());
				consultOrderDetailRes.setConsultRes(consultRes);
				consultOrderDetailRes.setVehicleBrand(consult.getVehicleBrand());
				consultOrderDetailRes.setVehicleModel(consult.getVehicleModel());
				if (TokenHelper.getUserType() != null) {
					consultOrderDetailRes.getConsultRes().setAnswerCheckSts(consult.getAnswerCheckSts());
				}
				log.debug("查询咨询内容");
				ConsultDetail consultDetail = new ConsultDetail();
				consultDetail.setConsultUuid(consult.getUuid());
				consultDetail.setSts(StsEnum.ACTIVE.getValue());
				consultDetail = consultDetailMapper.selectOne(consultDetail);
				boolean answerRet = CheckStatusEnum.APPROVE.getValue().equals(consult.getAnswerCheckSts())
						|| TokenHelper.getUserUuid().equals(consult.getTechnicianUuid());

				if (!StringUtils.isEmpty(consultDetail)) {
					consultRes.setConsultDesc(consultDetail.getConsultDesc());
					// 回答内容审核通过才给返回结果
					if (TokenHelper.getUserType() != null) {
						if (answerRet) {
							consultRes.setAnswerDesc(consultDetail.getAnswerDesc());
						}
					}else{
						consultRes.setAnswerDesc(consultDetail.getAnswerDesc());
					}
				}

				ConsultImages consultImages = new ConsultImages();
				consultImages.setConsultUuid(consult.getUuid());
				consultImages.setSts(StsEnum.ACTIVE.getValue());
				// 回答内容审核通过才给返回结果
//                if (!CheckStatusEnum.APPROVE.getValue().equals(consult.getAnswerCheckSts())) {
//                    log.debug("技师回答审核不通过或则暂未回答");
//                    consultImages.setImgType(ConsultImgTypenum.CONSULT_IMG.getValue());
//                }
				log.debug("查询咨询相关图片");
				List<ConsultImages> consultImagesList = consultImagesMapper.select(consultImages);
				if (!CollectionUtils.isEmpty(consultImagesList)) {
					List<String> consultImgList = new ArrayList<>();
					List<String> answerImgList = new ArrayList<>();

					for (ConsultImages item : consultImagesList) {
						if (ConsultImgTypenum.CONSULT_IMG.getValue().equals(item.getImgType())) {
							consultImgList.add(item.getImgUrl());
						} else if (ConsultImgTypenum.ANSWER_IMG.getValue().equals(item.getImgType()) ) {
							if(TokenHelper.getUserType() == null) {
								answerImgList.add(item.getImgUrl());
							}
							if(answerRet&&TokenHelper.getUserType() != null){
								answerImgList.add(item.getImgUrl());
							}
						}

					}
					consultRes.setConsultImgList(consultImgList);
					consultRes.setAnswerImgList(answerImgList);
				}
				consultOrderDetailRes.setConsultRes(consultRes);
			}

			//技师擅长品牌
			if(consult.getTechnicianType()!=null && consult.getTechnicianType()==2){
				TechnicianRes data1 = technicianFegin.queryTechnicianDetail(consultOrderDetailRes.getConsultRes().getTechnicianUuid()).getData();
				if(data1!=null){
					consultOrderDetailRes.setTechnicianScore(data1.getScore());//技师评分
				}
				Integer integer = consultOrderMapper.byQueryQuizCount(data1.getUuid());
				//技师 被提问次数
				consultOrderDetailRes.setByConsultNumber(integer);//被提问次数
				if(data1!=null){
					List<TechnicianBrandRes> brandList = data1.getBrandList();
					brandList.stream().forEach(e->{
						list.add(e.getBrandName());
					});
					consultOrderDetailRes.setBrandList(list);
				}
			}else if(consult.getTechnicianType()!=null && consult.getTechnicianType()==3){
				StoreUserRes data = storeUserFeign.queryStoreUserInfo(consultOrderDetailRes.getConsultRes().getTechnicianUuid()).getData();
				StoreDetailRes data1 = storeFegin.queryStoreDetail(data.getStoreUuid()).getData();
				List<StoreBrandRes> storeBrandUuidList = data1.getStoreBrandUuidList();
				if(storeBrandUuidList!=null){
					storeBrandUuidList.stream().forEach(e->{
						list.add(e.getConfigName());
					});
				}
			}

		}
		consultMapper.queryConsultByUuid(consultOrderDetailRes.getConsultRes().getUuid());
		DictionaryRes data = systemFeign.queryByUuid(consultOrderDetailRes.getConsultRes().getTechnicalTypeUuid())
				.getData();
		ConfigRes data2 = vehicleFegin.queryConfig(consultOrderDetailRes.getVehicleBrand()).getData();
		ConfigRes data3 = vehicleFegin.queryConfig(consultOrderDetailRes.getVehicleModel()).getData();
		consultOrderDetailRes.getConsultRes().setTechnicalTypeName(data.getLableDesc());
		consultOrderDetailRes.setBrandName(data2.getConfigName());
		consultOrderDetailRes.setModelName(data3.getConfigName());

		return ResultRes.success(consultOrderDetailRes);
	}

	/**
	 * 咨询订单信息导出
	 * @param exportReq
	 * @param response
	 */
	@Override
	public void exportOrderConsultList(QueryOrderConsultListReq exportReq, HttpServletResponse response) {
		log.debug("咨询订单信息导出");
		try {
			List<OrderConsultInfoListRes> orderConsultInfoList = consultOrderMapper.queryOrderConsultList(exportReq);
			// 读取模板文件
			InputStream resourceAsStream = getClass().getClassLoader()
					.getResourceAsStream(Constants.ORDER_CONSULT_INFO_EXPORT_TEMPLATE);
			// 设置空行默认属性
			List<OrderConsultInfoListRes> excelList = ExcelUtils.setFieldValue(orderConsultInfoList);
			Workbook wb = new XSSFWorkbook(resourceAsStream);
			Sheet sheet = wb.getSheetAt(0);
			// 从第三行开始写入
			int firstRowIndex = sheet.getFirstRowNum() + 2;
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
				OrderConsultInfoListRes exportDto = excelList.get(rowIndex - 2);
				ExcelUtils.setCell(row, cellStyle1, 0, rowIndex - 1);
				ExcelUtils.setCell(row, cellStyle, 1, exportDto.getCarOwnerName());
				ExcelUtils.setCell(row, cellStyle, 2, exportDto.getCreatedTime());
				ExcelUtils.setCell(row, cellStyle, 3, exportDto.getTechnicianName());

				ExcelUtils.setCell(row, cellStyle, 4, exportDto.getTechnicianMobile());
				ExcelUtils.setCell(row, cellStyle, 5, OrderTypeEnum.enumOfDesc(exportDto.getOrderType()));
				ExcelUtils.setCell(row, cellStyle, 6, "¥ " + exportDto.getOrderAmount());
				ExcelUtils.setCell(row, cellStyle, 7, exportDto.getCarOwnerMobile());
				ExcelUtils.setCell(row, cellStyle, 8, PayMethodEnum.enumOfDesc(exportDto.getPayType()));
				ExcelUtils.setCell(row, cellStyle, 9, OrderStsEnum.enumOfDesc(exportDto.getOrderSts()));
				ExcelUtils.setCell(row, cellStyle, 10, AnswerStsEnum.enumOfDesc(exportDto.getAnswerSts()));
			}
			ExcelUtils.responseWrite(wb, response, Constants.ORDER_CONSULT_INFO_EXPORT_TEMPLATE);
		} catch (Exception ex) {
			log.error("咨询订单信息导出异常，异常原因：{}", ExceptionUtils.stackTraceToString(ex));
		}
	}

	/**
	 * 验证订单是否有效
	 * @param orderUuid
	 */
	private ConsultOrder checkOrderIsActive(String orderUuid) {
		ConsultOrder consultOrder = new ConsultOrder();
		consultOrder.setUuid(orderUuid);
		consultOrder.setSts(StsEnum.ACTIVE.getValue());
		consultOrder = consultOrderMapper.selectOne(consultOrder);
		if (StringUtils.isEmpty(consultOrder)) {
			log.error("订单不存在 orderUuid:{}", orderUuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		return consultOrder;
	}

	/**
	 * 增加内容审核
	 * @param carOwnerUuid
	 * @param carOwnerMobile
	 * @param orderType
	 * @param orderName
	 * @param checkContentDesc
	 * @param orderUuid
	 * @param resourcesList
	 */
	private void addContentCheck(String carOwnerUuid, String carOwnerMobile, Integer orderType, String orderName,
			String checkContentDesc, String orderUuid, List<String> resourcesList) {
		AddContentReq addContentReq = new AddContentReq();
		addContentReq.setUserUuid(carOwnerUuid);
		addContentReq.setMobile(carOwnerMobile);
		addContentReq.setOrderType(orderType);
		addContentReq.setOrderName(orderName);
		addContentReq.setContentDetail(checkContentDesc);
		addContentReq.setOrderUuid(orderUuid);
		addContentReq.setResourcesList(resourcesList);
		contentService.addContent(addContentReq);
	}

	/**
	 * 新增咨询相关图片
	 * @param consultImgList
	 * @param consultUuid
	 * @param imgType
	 */
	private void addConsultImg(List<String> consultImgList, String consultUuid, Integer imgType) {
//        if (ConsultImgTypenum.ANSWER_IMG.getValue().equals(imgType)) {
//            // 如果是回答删除之前保留的图片
//            ConsultImages deleteConsultImages = new ConsultImages();
//            deleteConsultImages.setImgType(imgType);
//            deleteConsultImages.setConsultUuid(consultUuid);
//            consultImagesMapper.delete(deleteConsultImages);
//        }
		if (!CollectionUtils.isEmpty(consultImgList)) {
			List<ConsultImages> consultImagesList = new ArrayList<>();
			ConsultImages consultImages;
			for (String imgUrl : consultImgList) {
				consultImages = new ConsultImages();
				consultImages.setUuid(UuidUtils.getUuid());
				consultImages.setCreatedTime(new Date());
				consultImages.setCreatedBy(TokenHelper.getUserName());
				consultImages.setSts(StsEnum.ACTIVE.getValue());
				consultImages.setConsultUuid(consultUuid);
				consultImages.setImgUrl(imgUrl);
				consultImages.setImgType(imgType);
				consultImagesList.add(consultImages);
			}

			log.info("consultImagesList:{}", JSON.toJSONString(consultImagesList));
			consultImagesMapper.batchInsertConsultImages(consultImagesList);
		}
	}

	/**
	 * 根据咨询ID查询咨询信息
	 * @param consultUuid
	 * @return consult
	 */
	@Override
	public Consult checkConsult(String consultUuid) {
		Consult consult = new Consult();
		consult.setUuid(consultUuid);
		consult.setSts(StsEnum.ACTIVE.getValue());
		consult = consultMapper.selectOne(consult);
		if (StringUtils.isEmpty(consult)) {
			log.error("未查询到咨询详情 consultUuid:{}", consultUuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		return consult;
	}

	/**
	 * 国咨询订单枪单（技师）
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<String> consultOrderSnapUp(String uuid) {
		if (UserTypeEnum.vehicle.getType().equals(TokenHelper.getUserType())) {
			throw new BusinessException(ResEnum.SUPPORT_ONLY_TECHNICIAN_ERROR);
		}
		Consult consult = consultMapper.selectByPrimaryKey(uuid);
		if (StringUtils.isEmpty(consult)) {
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}

		ConsultOrder consultOrder = new ConsultOrder();
		consultOrder.setConsultUuid(consult.getUuid());
		consultOrder.setSts(StsEnum.ACTIVE.getValue());
		consultOrder = consultOrderMapper.selectOne(consultOrder);

		if (!StringUtils.isEmpty(consult.getTechnicianUuid())) {
			// 当前订单已被抢单
			throw new BusinessException(ResEnum.CONSULT_ORDER_SNAP_UP_ERROR);
		}
		if (UserTypeEnum.technician.getType().equals(TokenHelper.getUserType())) {

			// 修改数据状态
			ResultRes<TechnicianRes> technicianRes = technicianFegin.queryTechnicianDetail(TokenHelper.getUserUuid());
			if (!technicianRes.isSuccess()) {
				throw new BusinessException(ResEnum.NON_EXISTENT);
			}
			TechnicianRes technician = technicianRes.getData();
			consult.setTechnicianUuid(technician.getUuid());
			consult.setTechnicianImgUrl(technician.getPhotoImgUrl());
			consult.setTechnicianMobile(technician.getMobile());
			consult.setTechnicianName(technician.getUserName());
			consult.setTechnicianType(TokenHelper.getUserType());

		} else if (UserTypeEnum.store.getType().equals(TokenHelper.getUserType())) {
			ResultRes<StoreUserRes> stRes = storeUserFeign.queryStoreUserInfo(TokenHelper.getUserUuid());
			if (!stRes.isSuccess()) {
				throw new BusinessException(ResEnum.NON_EXISTENT);
			}
			StoreUserRes res = stRes.getData();
			StoreDetailRes data = storeFegin.queryStoreDetail(res.getStoreUuid()).getData();
			consult.setTechnicianUuid(res.getUuid());
			consult.setTechnicianImgUrl(res.getPhotoImgUrl());
			consult.setTechnicianMobile(res.getMobile());
			consult.setTechnicianName(data.getStoreName());
			consult.setTechnicianType(TokenHelper.getUserType());
		}
		log.info("*****consult:"+JSON.toJSONString(consult)+"consultOrder:"+JSON.toJSONString(consultOrder));
		messageFeign.sendMsg("2006", Maps.newHashMap("userName", consult.getCarOwnerName()), TokenHelper.getUserUuid(),
				TokenHelper.getUserType(), consultOrder.getUuid());
		messageFeign.sendMsg("2017", Maps.newHashMap("technicianName", consult.getTechnicianName()),
				consult.getCarOwnerUuid(), consult.getCarOwnerType(), consultOrder.getUuid());
		consultMapper.updateByPrimaryKey(consult);
		return ResultRes.success();
	}

	/**
	 * 拒绝回答
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes updateAnswerSts(String uuid) {

		Consult consult = consultMapper.selectByPrimaryKey(uuid);
		ConsultOrder co=new ConsultOrder();
		co.setConsultUuid(uuid);
		co.setCarOwnerUuid(consult.getCarOwnerUuid());
		ConsultOrder consultOrder = consultOrderMapper.selectOne(co);
		if (!TokenHelper.getUserUuid().equals(consult.getTechnicianUuid())) {
			return ResultRes.error("9999", "不是你的单据，禁止操作");
		}

		if (!consult.getConsultType().equals(ConsultTypeEnum.technician_questions.getValue())) {
			return ResultRes.error("9999", "非指向性提问，禁止拒绝");
		}

		if (!consult.getAnswerSts().equals(AnswerStsEnum.UNANSWERED.getValue())) {
			return ResultRes.error("9999", "状态错误，禁止操作");
		}

		consult.setAnswerSts(AnswerStsEnum.REFUSED_TO_ANSWER.getValue());
		consult.setAnswerTime(new Date());

		messageFeign.sendMsg("2019",Maps.newHashMap("technicianName", consult.getTechnicianName(), "amount",
							consultOrder.getOrderAmount().toString()),
							consult.getCarOwnerUuid(), consult.getCarOwnerType(), consultOrder.getUuid());
		return orderRefund(consult);
	}

	/**
	 * 给问答订单退款
	 * @param consult
	 * @return
	 */
	private ResultRes orderRefund(Consult consult) {

		ConsultOrder consultOrder = new ConsultOrder();
		consultOrder.setConsultUuid(consult.getUuid());
		consultOrder.setSts(StsEnum.ACTIVE.getValue());
		consultOrder = consultOrderMapper.selectOne(consultOrder);

		ResultRes<String> res = orderInfoService.orderRefund(consultOrder.getUuid());
		if (!res.isSuccess()) {
			return res;
		}

		res = payFeign.orderRefund(consultOrder.getUuid());
		log.info(res + "----------------");
		log.info("----------------" + res.toString());
		if (res.isSuccess()) {
			OrderWhetherAgreeRefundReq req = new OrderWhetherAgreeRefundReq();
			req.setOrderUuid(consultOrder.getUuid());
			req.setWhetherRefund(WhetherAgreeRefundEnum.AGREE.getValue());
			orderInfoService.orderWhetherAgreeRefund(req);
			consultMapper.updateByPrimaryKeySelective(consult);
		}

		return res;
	}

	/**
	 * 根据店铺登录用户查询店铺信息
	 * @return
	 */
	private StoreDetailRes queryStoreDetailByUser() {
		ResultRes<StoreDetailRes> storeDetailResResultRes = storeFegin.queryStoreDetailByUser();
		if (!storeDetailResResultRes.isSuccess()) {
			log.error("根据店铺联系人未定位到店铺信息 >>>storeDetailResResultRes:{}", JSON.toJSONString(storeDetailResResultRes));
			throw new BusinessException(ResEnum.STORE_INFO_NOT_BY_USER);
		}
		StoreDetailRes storeDetailRes = storeDetailResResultRes.getData();
		return storeDetailRes;
	}

}
