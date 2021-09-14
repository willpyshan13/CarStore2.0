package com.car.order.web.service.dtc.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.car.account.client.feign.MessageFeign;
import com.car.account.client.feign.StoreFegin;
import com.car.account.client.feign.StoreUserFeign;
import com.car.common.enums.CheckStatusEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.ExcelUtils;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.order.DtcIssuerTypeEnum;
import com.car.order.client.request.dtc.AddDtcReq;
import com.car.order.client.request.dtc.CheckDtcReq;
import com.car.order.client.request.dtc.QueryDtcListReq;
import com.car.order.client.response.dtc.QueryDtcInfoRes;
import com.car.order.client.response.dtc.QueryDtcListRes;
import com.car.order.client.response.dtc.QueryDtcOrderInfoRes;
import com.car.order.client.response.dtc.QueryDtcOrderListRes;
import com.car.order.web.common.constants.ConfigConsts;
import com.car.order.web.mapper.dtc.DtcContentMapper;
import com.car.order.web.mapper.dtc.DtcMapper;
import com.car.order.web.mapper.dtc.DtcOrderMapper;
import com.car.order.web.model.dtc.Dtc;
import com.car.order.web.model.dtc.DtcContent;
import com.car.order.web.model.dtc.DtcOrder;
import com.car.order.web.service.dtc.DtcService;
import com.codingapi.txlcn.common.util.Maps;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/17
 */
@Slf4j
@Service
public class DtcServiceImpl implements DtcService {

	@Autowired
	private DtcMapper dtcMapper;

	@Autowired
	private DtcContentMapper dtcContentMapper;

	@Autowired
	private ConfigConsts configConsts;

	@Autowired
	private StoreUserFeign storeUserFeign;

	@Autowired
	StoreFegin storeFegin;

	@Autowired
	private DtcOrderMapper dtcOrderMapper;
	@Autowired
	private MessageFeign messageFeign;

	/**
	 * 根据id查询详情
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<QueryDtcInfoRes> getById(String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			log.error("uuid不能为空");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		QueryDtcInfoRes queryDtcInfoRes = dtcMapper.getById(uuid);

		QueryDtcInfoRes res = new QueryDtcInfoRes();
		// 获取dtc发布人类型
		Pair<Integer, String> pair = getDtcIssuerType();
		if (!StringUtils.isEmpty(queryDtcInfoRes)) {
			if (pair.getFirst().equals(DtcIssuerTypeEnum.ADMIN.getType())) {
				BeanUtils.copyProperties(queryDtcInfoRes, res);
			} else if (pair.getFirst().equals(DtcIssuerTypeEnum.TECHNICIAN.getType())) {

				// 检查是否可以查看dtc信息
				checkDtcInfoExaminePermission(pair, queryDtcInfoRes, res);
			} else if (pair.getFirst().equals(DtcIssuerTypeEnum.STORE.getType())) {
				// 查询当前登录人所属店铺信息
				checkDtcInfoExaminePermission(pair, queryDtcInfoRes, res);
			}
			res.setUuid(uuid);
		}
		return ResultRes.success(res);
	}

	private QueryDtcInfoRes checkDtcInfoExaminePermission(Pair<Integer, String> pair, QueryDtcInfoRes queryDtcInfoRes,
			QueryDtcInfoRes res) {
		// 根据token判断查看人是否为技师用户
		if (pair.getSecond().equals(queryDtcInfoRes.getDtcIssuerUuid())) {
			// 根据token判断查看人是技师用户并且是当前dtc的发布者 直接返回详情
			BeanUtils.copyProperties(queryDtcInfoRes, res);
			res.setIsOneself(true);
		} else {
			// 根据token判断查看人是技师用户但不是当前dtc的发布者 需要购买查看
			// 查询是否购买并且订单查看次数不大于3
			DtcOrder dtcOrder = dtcOrderMapper.queryPurchaseOrder(pair.getSecond(), queryDtcInfoRes.getUuid());
			if (!StringUtils.isEmpty(dtcOrder)) {
				if (OrderStsEnum.COMPLETED.getValue().equals(dtcOrder.getOrderSts())) {
					// 已支付修改订单查看次数信息
					updateDtcOrder(dtcOrder.getUuid());
					BeanUtils.copyProperties(queryDtcInfoRes, res);
				} else {
					res.setDtcAmount(dtcOrder.getOrderAmount());
					res.setOrderUuid(dtcOrder.getUuid());
				}
				res.setOrderSts(dtcOrder.getOrderSts());
			} else {
				res.setDtcAmount(queryDtcInfoRes.getDtcAmount());
			}
			res.setIsOneself(false);
		}
		return res;
	}

	/**
	 * 检测用户是否需要购买此dtc
	 * @param pair
	 * @param queryDtcInfoRes
	 * @return
	 */
	private boolean checkDtcInfoExaminePermissionBuy(Pair<Integer, String> pair, QueryDtcListRes queryDtcInfoRes) {

		if (pair == null || queryDtcInfoRes == null) {
			return false;
		}

		if (pair.getFirst().equals(DtcIssuerTypeEnum.ADMIN.getType())) {
			return false;
		} else if (pair.getFirst().equals(DtcIssuerTypeEnum.TECHNICIAN.getType())
				|| pair.getFirst().equals(DtcIssuerTypeEnum.STORE.getType())) {

			// 根据token判断查看人是否为技师用户
			if (pair.getSecond().equals(queryDtcInfoRes.getDtcIssuerUuid())) {
				// 根据token判断查看人是技师用户并且是当前dtc的发布者 直接返回详情
				return false;
			} else {
				// 根据token判断查看人是技师用户但不是当前dtc的发布者 需要购买查看
				// 查询是否购买并且订单查看次数不大于3
				DtcOrder dtcOrder = dtcOrderMapper.queryPurchaseOrder(pair.getSecond(), queryDtcInfoRes.getUuid());
				if (!StringUtils.isEmpty(dtcOrder)) {
					if (OrderStsEnum.COMPLETED.getValue().equals(dtcOrder.getOrderSts())) {
						// 已支付修改订单查看次数信息
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 *修改订单查看次数信息
	 */
	private void updateDtcOrder(String dtcOrderUuid) {
		// 查看订单详情
		QueryDtcOrderInfoRes db = dtcOrderMapper.getById(dtcOrderUuid);
		// 修改订单查看次数信息
		DtcOrder dtcOrderUpdate = new DtcOrder();
		dtcOrderUpdate.setUuid(db.getUuid());
		dtcOrderUpdate.setReadCount(db.getReadCount() + 1);
		int updateDtcOrder = dtcOrderMapper.updateByPrimaryKeySelective(dtcOrderUpdate);
		if (updateDtcOrder <= 0) {
			log.error("修改dtcOrder信息失败，修改订单为：{}", db.getUuid());
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
		if (db.getReadCount() == 2) {
			messageFeign.sendMsg("2201",
					Maps.newHashMap("dtcName",
							Objects.toString(db.getDtcCode(), "") + Objects.toString(db.getDtcCode2(), "")
									+ Objects.toString(db.getDtcCode3(), "")),
					TokenHelper.getUserUuid(), TokenHelper.getUserType(), dtcOrderUuid);
		}
	}

	/**
	 * 新增DTC信息
	 * @param req
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> add(AddDtcReq req) {
		// 获取用户名称
		String userName = TokenHelper.getUserName();
		// 判断dtc金额是否为空
		if (StringUtils.isEmpty(req.getDtcAmount())) {
			req.setDtcAmount(configConsts.getDtcOrderMoney());
		}

		// 获取dtc发布人类型
		Pair<Integer, String> pair = getDtcIssuerType();

		Dtc dtc = new Dtc();
		dtc.setUuid(UuidUtils.getUuid());
		dtc.setDtcIssuerUuid(pair.getSecond());
		dtc.setDtcCode(req.getDtcCode());
		dtc.setDtcCode2(req.getDtcCode2());
		dtc.setDtcCode3(req.getDtcCode3());
		dtc.setDtcType(req.getDtcType());
		dtc.setDtcDefinition(req.getDtcDefinition());
		dtc.setDtcBrandUuid(req.getDtcBrandUuid());
		dtc.setDtcModelUuid(req.getDtcModelUuid());
		dtc.setDtcAmount(new BigDecimal(req.getDtcAmount()));
		dtc.setDtcIssuerType(pair.getFirst());
		dtc.setSts(StsEnum.ACTIVE.getValue());
		dtc.setCreatedTime(new Date());
		dtc.setDtcCheckSts(CheckStatusEnum.CHECK_PENDING.getValue());
		dtc.setCreatedBy(userName);
		int insertDtcNum = dtcMapper.insert(dtc);
		if (insertDtcNum <= 0) {
			log.error("新增dtc故障信息失败，请求参数为：{}", JSON.toJSONString(dtc));
			throw new BusinessException(ResEnum.INSERT_DB_ERROR);
		}
		DtcContent dtcContent = new DtcContent();
		dtcContent.setUuid(UuidUtils.getUuid());
		dtcContent.setDtcUuid(dtc.getUuid());
		dtcContent.setDtcExplain(req.getDtcExplain());
		dtcContent.setDtcReasons(req.getDtcReasons());
		dtcContent.setDtcDiagnose(req.getDtcDiagnose());
		dtcContent.setSts(StsEnum.ACTIVE.getValue());
		dtcContent.setCreatedBy(userName);
		dtcContent.setCreatedTime(new Date());
		int insertDtcContent = dtcContentMapper.insert(dtcContent);
		if (insertDtcContent <= 0) {
			log.error("新增dtc故障内容详情失败，请求参数为：{}", JSON.toJSONString(dtcContent));
			throw new BusinessException(ResEnum.INSERT_DB_ERROR);
		}
		return ResultRes.success(dtc.getUuid());
	}

	/**
	 * 根据id修改dtc信息
	 * @param req
	 * @param uuid
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> updateById(AddDtcReq req, String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			log.error("修改dtc信息，uuid不能为空");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		// 判断dtc金额是否为空
		if (StringUtils.isEmpty(req.getDtcAmount())) {
			req.setDtcAmount(configConsts.getDtcOrderMoney());
		}
		// 获取dtc发布人类型
		Pair<Integer, String> pair = getDtcIssuerType();
		// 用户名称
		String userName = TokenHelper.getUserName();
		Dtc dtcUpdate = new Dtc();
		dtcUpdate.setUuid(uuid);
		dtcUpdate.setDtcCode(req.getDtcCode());
		dtcUpdate.setDtcCode2(req.getDtcCode2());
		dtcUpdate.setDtcCode3(req.getDtcCode3());
		dtcUpdate.setDtcDefinition(req.getDtcDefinition());
		dtcUpdate.setDtcBrandUuid(req.getDtcBrandUuid());
		dtcUpdate.setDtcModelUuid(req.getDtcModelUuid());
		dtcUpdate.setDtcIssuerType(pair.getFirst());
		dtcUpdate.setDtcType(req.getDtcType());
		dtcUpdate.setDtcAmount(new BigDecimal(req.getDtcAmount()));
		dtcUpdate.setLastUpdatedTime(new Date());
		dtcUpdate.setLastUpdatedBy(userName);
		dtcUpdate.setDtcCheckSts(req.getDtcCheckSts());
		dtcUpdate.setDtcRemarks(req.getDtcRemarks());
		// 修改dtc相关信息
		int dtcUpdateNum = dtcMapper.updateByPrimaryKeySelective(dtcUpdate);
		if (dtcUpdateNum <= 0) {
			log.error("修改dtc故障信息失败，请求参数为：{}, uuid:{}", JSON.toJSONString(dtcUpdate), uuid);
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
		// 修改dtc详情表信息
		int updateDtcContentNum = dtcContentMapper.updateDtcContentInfo(req, uuid, userName);
		if (updateDtcContentNum <= 0) {
			log.error("修改dtc详情信息，请求参数为：{}, uuid:{}", JSON.toJSONString(req), uuid);
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}
		return ResultRes.success(uuid);
	}

	/**
	 * 删除dtc信息
	 * @param uuid
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> deleteById(String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			log.error("删除dtc信息，uuid不能为空！");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		// 用户信息uuid
		String userUuid = TokenHelper.getUserUuid();
		// 用户信息姓名
		String userName = TokenHelper.getUserName();
		// 删除dtc信息
		int deleteDtcNum = dtcMapper.deleteDtcInfo(uuid, userName);
		if (deleteDtcNum <= 0) {
			log.error("删除dtc信息失败");
			throw new BusinessException(ResEnum.PAY_ERROR);
		}
		// 删除dtc内容详情信息
		int deleteDtcContent = dtcContentMapper.deleteDtcContentInfo(uuid, userName);
		if (deleteDtcContent <= 0) {
			log.error("删除dtc内容详情失败");
			throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
		}

		return ResultRes.success(uuid);
	}

	/**
	 * 分页列表
	 * @param req
	 * @return
	 */
	@Override
	public PageRes<List<QueryDtcListRes>> list(QueryDtcListReq req) {
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		req.setUserId(TokenHelper.getUserUuid());
		List<QueryDtcListRes> list = dtcMapper.queryDtcList(req);
		Pair<Integer, String> pair = getDtcIssuerType();

		list.forEach(o -> o.setNeedBuy(checkDtcInfoExaminePermissionBuy(pair, o) ? 1 : 0));

		PageInfo<QueryDtcListRes> pageInfo = new PageInfo<>(list);
		return PageRes.success(list, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
	}

	@Override
	public ResultRes<String> checkDtc(CheckDtcReq req) {
		log.debug("审核DTC");
		Dtc d = new Dtc();
		d.setUuid(req.getDtcUuid());
		d = dtcMapper.selectByPrimaryKey(d);
		if (StringUtils.isEmpty(d) || StsEnum.INVALID.getValue().equals(d.getSts())) {
			log.error("审核DTC 未匹配到对应数据 uuid {}", d.getUuid());
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		String checkDesc = CheckStatusEnum.enumOfDesc(req.getDtcCheckSts());
		if (StringUtils.isEmpty(checkDesc)) {
			log.error("审核DTC 无效的审核状态入参 checkSt:{}", req.getDtcCheckSts());
			throw new BusinessException(ResEnum.INVALID_CHECK_STS);
		}
		if (!d.getDtcCheckSts().equals(req.getDtcCheckSts())) {
			d.setDtcCheckSts(req.getDtcCheckSts());
			d.setDtcRemarks(req.getDtcRemarks());
			dtcMapper.updateByPrimaryKey(d);
		}
		return ResultRes.success(d.getUuid());
	}

	/**
	 * 批量导入
	 * @param file
	 * @return
	 */
	@Override
	public ResultRes batchImport(MultipartFile file, String brandUuid, String modelUuid, String dtcTypeUuid) {
		try {
			// 建立输入流
			InputStream input = file.getInputStream();
			Workbook wb;
			// 根据文件格式(2003或者2007)来初始化
			if (file.getOriginalFilename().endsWith("xlsx")) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			// 获得第一个表单
			wb.getNumberOfSheets();
			Sheet sheet = wb.getSheetAt(0);
			int rowCount = sheet.getLastRowNum() + 1;
			for (int i = 1; i < rowCount; i++) {
				dtcTypeUuid = StringUtils.trimWhitespace(ExcelUtils.getSheetValue(sheet, i, 1));
				String dtcCode = StringUtils.trimWhitespace(ExcelUtils.getSheetValue(sheet, i, 2));
				String dtcCode2 = StringUtils.trimWhitespace(ExcelUtils.getSheetValue(sheet, i, 3));
				String dtcCode3 = StringUtils.trimWhitespace(ExcelUtils.getSheetValue(sheet, i, 4));
				if (StringUtils.isEmpty(dtcCode)) {
					continue;
				}
				// 定义名称
				String dtcDefinition = StringUtils.trimWhitespace(ExcelUtils.getSheetValue(sheet, i, 5));
				// 故障说明
				String dtcExplain = StringUtils.trimWhitespace(ExcelUtils.getSheetValue(sheet, i, 6));
				// 可能原因
				String dtcReasons = StringUtils.trimWhitespace(ExcelUtils.getSheetValue(sheet, i, 7));
				// 诊断辅助
				String dtcDiagnose = StringUtils.trimWhitespace(ExcelUtils.getSheetValue(sheet, i, 8));

				Dtc dtc = new Dtc();
				dtc.setUuid(UuidUtils.getUuid());
				dtc.setDtcIssuerUuid("0");
				dtc.setDtcCode(dtcCode);
				dtc.setDtcCode2(dtcCode2);
				dtc.setDtcCode3(dtcCode3);
				dtc.setDtcDefinition(dtcDefinition);
				dtc.setDtcBrandUuid(brandUuid);
				dtc.setDtcModelUuid(modelUuid);
				dtc.setDtcAmount(new BigDecimal(0.5));
				dtc.setDtcIssuerType(0);
				dtc.setSts(StsEnum.ACTIVE.getValue());
				dtc.setDtcType(dtcTypeUuid);
				dtc.setCreatedTime(new Date());
				dtc.setCreatedBy("admin");
				dtc.setDtcCheckSts(1);
				int insertDtcNum = dtcMapper.insert(dtc);
				if (insertDtcNum <= 0) {
					log.error("新增dtc故障信息失败，请求参数为：{}", JSON.toJSONString(dtc));
					throw new BusinessException(ResEnum.INSERT_DB_ERROR);
				}
				DtcContent dtcContent = new DtcContent();
				dtcContent.setUuid(UuidUtils.getUuid());
				dtcContent.setDtcUuid(dtc.getUuid());
				dtcContent.setDtcExplain(dtcExplain);
				dtcContent.setDtcReasons(dtcReasons);
				dtcContent.setDtcDiagnose(dtcDiagnose);
				dtcContent.setSts(StsEnum.ACTIVE.getValue());
				dtcContent.setCreatedBy("admin");
				dtcContent.setCreatedTime(new Date());
				dtcContentMapper.insert(dtcContent);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResultRes.success();
	}

	/**
	 * dtc类型
	 * @return
	 */
	@Override
	public ResultRes<List<AddDtcReq>> dtcGetType() {
		List<AddDtcReq> stringList = dtcContentMapper.dtcGetType();
		return ResultRes.success(stringList);
	}

	/**
	 * 我购买的DTC列表
	 * @return
	 */
	@Override
	public ResultRes<List<QueryDtcOrderListRes>> dtcOrderList() {
		List<QueryDtcOrderListRes> addDtcReqList = dtcOrderMapper.dtcOrderList(TokenHelper.getUserUuid());
		return ResultRes.success(addDtcReqList);
	}

	/**
	 * 根据token获取dtc发布人类型
	 * @return
	 */
	private Pair<Integer, String> getDtcIssuerType() {
		// 获取用户uuid
		String issuerUuid = TokenHelper.getUserUuid();
		// 默认后台发布
		Integer dtcIssuerType = DtcIssuerTypeEnum.ADMIN.getType();

		Integer userType = TokenHelper.getUserType();
		if (UserTypeEnum.vehicle.getType().equals(userType)) {
			// 车主
			throw new BusinessException(ResEnum.DTC_NOT_VEHICLE_ISSUER);
		} else if (UserTypeEnum.technician.getType().equals(userType)) {
			// 技师
			dtcIssuerType = DtcIssuerTypeEnum.TECHNICIAN.getType();
		} else if (UserTypeEnum.store.getType().equals(userType)) {
			// 店铺
			dtcIssuerType = DtcIssuerTypeEnum.STORE.getType();
		}
		return Pair.of(dtcIssuerType, issuerUuid);
	}

	/**
	 * 查询当前登录人信是否为该Dtc故障信息发布人
	 * @param userUuid
	 */
	private void checkUserDtc(QueryDtcInfoRes res, String userUuid) {
	}
}
