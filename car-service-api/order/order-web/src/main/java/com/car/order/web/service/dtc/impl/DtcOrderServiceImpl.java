package com.car.order.web.service.dtc.impl;

import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.car.account.client.feign.*;
import com.codingapi.txlcn.common.util.Maps;
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
import com.car.account.client.response.store.StoreDetailRes;
import com.car.account.client.response.technician.TechnicianRes;
import com.car.common.enums.OrderPrefixEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.ExcelUtils;
import com.car.common.utils.ExceptionUtils;
import com.car.common.utils.OrderUtils;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.common.utils.token.LoginToken;
import com.car.order.client.enums.consult.OrderTypeEnum;
import com.car.order.client.enums.goods.OrderStsEnum;
import com.car.order.client.enums.goods.PayMethodEnum;
import com.car.order.client.request.dtc.AddDtcOrderReq;
import com.car.order.client.request.dtc.QueryDtcOrderListReq;
import com.car.order.client.request.order.order.AddOrderInfoReq;
import com.car.order.client.response.dtc.QueryDtcOrderInfoRes;
import com.car.order.client.response.dtc.QueryDtcOrderListRes;
import com.car.order.web.common.constants.ConfigConsts;
import com.car.order.web.common.constants.Constants;
import com.car.order.web.dto.dtc.DtcIssuerInfoDto;
import com.car.order.web.mapper.dtc.DtcContentMapper;
import com.car.order.web.mapper.dtc.DtcMapper;
import com.car.order.web.mapper.dtc.DtcOrderDetailMapper;
import com.car.order.web.mapper.dtc.DtcOrderMapper;
import com.car.order.web.model.dtc.Dtc;
import com.car.order.web.model.dtc.DtcContent;
import com.car.order.web.model.dtc.DtcOrder;
import com.car.order.web.model.dtc.DtcOrderDetail;
import com.car.order.web.service.dtc.DtcOrderService;
import com.car.order.web.service.order.OrderInfoService;
import com.car.system.client.feign.SysUserFeign;
import com.car.system.client.feign.SystemFeign;
import com.car.system.client.response.user.UserDetailRes;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Intellij IDEA.
 *
 * @author: ??cjw
 * Date: ??2021/2/17
 */
@Slf4j
@Service
public class DtcOrderServiceImpl implements DtcOrderService {

	@Autowired
	private DtcMapper dtcMapper;

	@Autowired
	private DtcContentMapper dtcContentMapper;

	@Autowired
	private DtcOrderMapper dtcOrderMapper;

	@Autowired
	private TechnicianFegin technicianFegin;

	@Autowired
	private StoreFegin storeFegin;

	@Autowired
	private StoreUserFeign storeUserFeign;

	@Autowired
	private SysUserFeign sysUserFeign;

	@Autowired
	private DtcOrderDetailMapper dtcOrderDetailMapper;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private ConfigConsts configConsts;

	@Autowired
	private SystemFeign systemFeign;
	@Autowired
	private VehicleFegin vehicleFegin;
	@Autowired
	private MessageFeign messageFeign;

	/**
	 * ??????id????????????
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<QueryDtcOrderInfoRes> getById(String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			log.error("uuid????????????");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		QueryDtcOrderInfoRes res = dtcOrderMapper.getById(uuid);
		QueryDtcOrderInfoRes queryRes = new QueryDtcOrderInfoRes();
		// ????????????????????????
		Integer userType = TokenHelper.getUserType();
		if (!StringUtils.isEmpty(userType)) {
			if (null != res) {
				// ??????????????????
				DtcOrder dtcOrder = new DtcOrder();
				dtcOrder.setUuid(uuid);
				Integer readCount = null == res.getReadCount() ? 0 : res.getReadCount();
				dtcOrder.setReadCount(readCount + 1);
				int updateDtcOrder = dtcOrderMapper.updateByPrimaryKeySelective(dtcOrder);
				if (updateDtcOrder <= 0) {
					log.error("??????dtcOrder?????????????????????????????????{}", uuid);
					throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
				}
				// ???????????????????????????????????????
				queryRes = checkDtcOrder(res);
			}
		} else {
			BeanUtils.copyProperties(res, queryRes);
		}
		return ResultRes.success(queryRes);
	}

	/**
	 * ??????DTC????????????
	 * @param req
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> addOrder(AddDtcOrderReq req) {
		// ????????????
		Integer userType = TokenHelper.getUserType();
		// ??????uuid
		String userUuid = TokenHelper.getUserUuid();
		// ???????????????????????????????????????????????????3
		DtcOrder dtcOrder = dtcOrderMapper.queryPurchaseOrder(userUuid, req.getDtcUuid());
		if (null != dtcOrder) {
			log.error("??????????????????????????????");
			return ResultRes.success(dtcOrder.getUuid());
		}
		// ??????Dtc????????????
		Dtc dtc = queryDtcInfo(req.getDtcUuid());
		// ??????dtc????????????
		String dtcOrderUuid = insertDtcOrder(req, dtc);
		// ??????dtc??????????????????
		insertDtcOrderDetail(dtcOrderUuid, dtc);

		//???????????????????????????????????????DTC
		Integer usetDtcNumber = dtcOrderMapper.getUsetDtcNumber(userUuid, req.getDtcUuid());
		//??????DTC---------????????????
		if(usetDtcNumber==0){
			Map param =new HashMap();
			param.put("amount", dtc.getDtcAmount());
			param.put("dtcName", dtc.getDtcCode());
			messageFeign.sendMsg("5008", param, userUuid , userType ,dtcOrderUuid);
		}else{
			Map param =new HashMap();
			param.put("dtcName", dtc.getDtcCode());
			param.put("amount", dtc.getDtcAmount());
			messageFeign.sendMsg("5009", param, userUuid , userType ,dtcOrderUuid);
		}

		return ResultRes.success(dtcOrderUuid);
	}

	/**
	 * ????????????
	 * @param req
	 * @return
	 */
	@Override
	public PageRes<List<QueryDtcOrderListRes>> list(QueryDtcOrderListReq req) {
		PageHelper.startPage(req.getPageNum(), req.getPageSize());

		// ??????????????????userUuid
		String userUuid = TokenHelper.getUserUuid();

		// ????????????????????????
		Integer userType = TokenHelper.getUserType();
		List<QueryDtcOrderListRes> list = new ArrayList<>();
		if (null == userType) {
			// ????????????
			list = dtcOrderMapper.list(req, null);
		} else {
			// ????????????
			list = dtcOrderMapper.list(req, userUuid);
		}
		PageInfo<QueryDtcOrderListRes> pageInfo = new PageInfo<>(list);
		return PageRes.success(list, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
	}

	/**
	 * ??????????????????dtc????????????
	 * @param req
	 * @return
	 */
	@Override
	public PageRes<List<QueryDtcOrderListRes>> myList(QueryDtcOrderListReq req) {
		// ??????????????????userUuid
		String userUuid = TokenHelper.getUserUuid();
		log.info("??????????????????userUuid" + userUuid);
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		if (req.getType() == 2) {
			List<QueryDtcOrderListRes> recording = dtcOrderMapper.getAdditionalRecording(userUuid);
			PageInfo<QueryDtcOrderListRes> pageInfo = new PageInfo<>(recording);
			return PageRes.success(recording, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
		}
		if (req.getType() == 1) {
			// ????????????????????????
			Integer userType = TokenHelper.getUserType();
			List<QueryDtcOrderListRes> list = new ArrayList<>();
			if (null == userType) {
				// ????????????
				list = dtcOrderMapper.overdueDtcList(req, null);
			} else {
				// ????????????
				list = dtcOrderMapper.overdueDtcList(req, userUuid);
			}
			if (!CollectionUtils.isEmpty(list)) {
				for (QueryDtcOrderListRes res : list) {
					Integer readCount = null == res.getReadCount() ? 0 : res.getReadCount();
					res.setReadCount(configConsts.getDtcOrderNum() - readCount);
				}
			}
			PageInfo<QueryDtcOrderListRes> pageInfo = new PageInfo<>(list);
			return PageRes.success(list, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
		}

		if (req.getType() == 0) {
			// ????????????????????????
			Integer userType = TokenHelper.getUserType();
			List<QueryDtcOrderListRes> list = new ArrayList<>();
			if (null == userType) {
				// ????????????
				list = dtcOrderMapper.myList(req, null);
			} else {
				// ????????????
				list = dtcOrderMapper.myList(req, userUuid);
			}
			if (!CollectionUtils.isEmpty(list)) {
				for (QueryDtcOrderListRes res : list) {
					Integer readCount = null == res.getReadCount() ? 0 : res.getReadCount();
					res.setReadCount(configConsts.getDtcOrderNum() - readCount);
				}
			}
			PageInfo<QueryDtcOrderListRes> pageInfo = new PageInfo<>(list);
			return PageRes.success(list, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
		}
		return null;
	}

	/**
	 * DTC??????????????????
	 * @param exportReq
	 * @param response
	 */
	@Override
	public void exportOrderDtcList(QueryDtcOrderListReq exportReq, HttpServletResponse response) {
		log.debug("DTC??????????????????");
		try {
			List<QueryDtcOrderListRes> list = dtcOrderMapper.list(exportReq, null);
			// ??????????????????
			InputStream resourceAsStream = getClass().getClassLoader()
					.getResourceAsStream(Constants.ORDER_DTC_INFO_EXPORT_TEMPLATE);
			// ????????????????????????
			List<QueryDtcOrderListRes> excelList = ExcelUtils.setFieldValue(list);
			Workbook wb = new XSSFWorkbook(resourceAsStream);
			Sheet sheet = wb.getSheetAt(0);
			// ????????????????????????
			int firstRowIndex = sheet.getFirstRowNum() + 2;
			for (int rowIndex = firstRowIndex; rowIndex < excelList.size() + 2; rowIndex++) {
				// ?????????
				Row rowStyle = (rowIndex % 2) == 0 ? sheet.getRow(2) : sheet.getRow(3);
				// ????????????
				CellStyle cellStyle = ExcelUtils.getExcelFormat(rowStyle.getCell(1));
				CellStyle cellStyle1 = ExcelUtils.getExcelFormat(rowStyle.getCell(0));
				Row row = sheet.getRow(rowIndex);
				if (row == null) {
					row = sheet.createRow(rowIndex);
				}
				row.setHeight(rowStyle.getHeight());
				QueryDtcOrderListRes exportDto = excelList.get(rowIndex - 2);
				ExcelUtils.setCell(row, cellStyle1, 0, rowIndex - 1);
				ExcelUtils.setCell(row, cellStyle, 1, exportDto.getBuyerName());
				ExcelUtils.setCell(row, cellStyle, 2, exportDto.getBuyerMobile());
				ExcelUtils.setCell(row, cellStyle, 3, exportDto.getDtcCode());

				ExcelUtils.setCell(row, cellStyle, 4, exportDto.getCreatedTime());
				ExcelUtils.setCell(row, cellStyle, 5, OrderStsEnum.enumOfDesc(exportDto.getOrderSts()));
				ExcelUtils.setCell(row, cellStyle, 6, "?? " + exportDto.getOrderAmount());
				ExcelUtils.setCell(row, cellStyle, 7, "DTC??????");
				ExcelUtils.setCell(row, cellStyle, 8, PayMethodEnum.enumOfDesc(exportDto.getPayType()));
			}
			ExcelUtils.responseWrite(wb, response, Constants.ORDER_DTC_INFO_EXPORT_TEMPLATE);
		} catch (Exception ex) {
			log.error("????????????????????????????????????????????????{}", ExceptionUtils.stackTraceToString(ex));
		}
	}

	/**
	 * ??????id??????????????????
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<Boolean> queryOrderSts(String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			log.error("??????id?????????????????????uuid???????????????{}", uuid);
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		Boolean flag = false;
		DtcOrder dtcOrderSelect = new DtcOrder();
		dtcOrderSelect.setUuid(uuid);
		DtcOrder dtcOrder = dtcOrderMapper.selectOne(dtcOrderSelect);
		if (null == dtcOrder) {
			log.error("?????????????????????????????????uuid??????{}", uuid);
			throw new BusinessException(ResEnum.NON_EXISTENT.getValue());
		}
		if (OrderStsEnum.COMPLETED.getValue().equals(dtcOrder.getOrderSts())) {
			flag = true;
		}
		return ResultRes.success(flag);
	}

	/**
	 * ??????????????????DTC??????????????????
	 * @return
	 */
	@Override
	public ResultRes<HashMap> getNumber() {
		// ?????????
		Integer DtcNumber = dtcOrderMapper.getDtcNumber(TokenHelper.getUserUuid());
		// ?????????
		Integer overdueDtcNumber = dtcOrderMapper.getOverdueDtcNumber(TokenHelper.getUserUuid());
		HashMap hashMap = new HashMap();
		hashMap.put("kecha", DtcNumber);
		hashMap.put("guoqi", overdueDtcNumber);
		return ResultRes.success(hashMap);
	}

	/**
	 * ??????????????????DTC??????????????????
	 * @return
	 */
	@Override
	public ResultRes<HashMap> getNumber(String uuid) {
		// ?????????
		Integer DtcNumber = dtcOrderMapper.getDtcNumber(uuid);
		// ?????????
		Integer overdueDtcNumber = dtcOrderMapper.getOverdueDtcNumber(uuid);
		HashMap hashMap = new HashMap();
		hashMap.put("kecha", DtcNumber);
		hashMap.put("guoqi", overdueDtcNumber);
		return ResultRes.success(hashMap);
	}

	/**
	 * ??????dtc??????uuid??????????????????
	 * @param dtcUuid
	 * @return
	 */
	private Dtc queryDtcInfo(String dtcUuid) {
		Dtc dtcSelect = new Dtc();
		dtcSelect.setUuid(dtcUuid);
		dtcSelect.setSts(StsEnum.ACTIVE.getValue());
		Dtc dtc = dtcMapper.selectOne(dtcSelect);
		if (null == dtc) {
			log.error("??????dtc??????????????????");
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		return dtc;
	}

	/**
	 * ?????????????????????
	 * @param dtcIssuerUuid
	 * @param userType
	 */
	private DtcIssuerInfoDto queryDtcIssuerInfo(String dtcIssuerUuid, Integer userType) {
		if (StringUtils.isEmpty(dtcIssuerUuid)) {
			log.error("?????????uuid????????????");
			throw new BusinessException(ResEnum.LACK_PARAMETER);
		}
		DtcIssuerInfoDto dtcIssuerInfoDto = new DtcIssuerInfoDto();
		if (UserTypeEnum.technician.getType().equals(userType)) {
			// ????????????????????????
			ResultRes<TechnicianRes> resResultRes = technicianFegin.queryTechnicianDetail(dtcIssuerUuid);
			if (resResultRes.isSuccess()) {
				TechnicianRes technicianRes = resResultRes.getData();
				dtcIssuerInfoDto.setDtcIssuerName(technicianRes.getUserName());
				dtcIssuerInfoDto.setDtcIssuerMobile(technicianRes.getMobile());
			}
		} else if (UserTypeEnum.store.getType().equals(userType)) {
			// ????????????????????????
			ResultRes<StoreDetailRes> resResultRes = storeFegin.queryStoreDetail(dtcIssuerUuid);
			if (resResultRes.isSuccess()) {
				StoreDetailRes storeDetailRes = resResultRes.getData();
				dtcIssuerInfoDto.setDtcIssuerName(storeDetailRes.getStoreName());
			}
		} else {
			// ????????????????????????
			ResultRes<UserDetailRes> resResultRes = sysUserFeign.queryUserDetail(dtcIssuerUuid);
			if (resResultRes.isSuccess()) {
				UserDetailRes userDetailRes = resResultRes.getData();
				dtcIssuerInfoDto.setDtcIssuerName(userDetailRes.getName());
				dtcIssuerInfoDto.setDtcIssuerMobile(userDetailRes.getPhone());
			}
		}
		return dtcIssuerInfoDto;
	}

	/**
	 * ??????????????????
	 * @param req
	 * @param dtc
	 * @return
	 */
	private String insertDtcOrder(AddDtcOrderReq req, Dtc dtc) {
		// ????????????
		Integer userType = TokenHelper.getUserType();
		// ??????uuid
		String userUuid = TokenHelper.getUserUuid();

		// ??????user
		String userName = TokenHelper.getUserName();
		// ???????????????
		String mobile = null;
		LoginToken loginToken = TokenHelper.getLoginToken();
		if (null != loginToken) {
			mobile = loginToken.getUserMobile();
		}
		// ?????????????????????
		DtcIssuerInfoDto dtcIssuerInfoDto = queryDtcIssuerInfo(dtc.getDtcIssuerUuid(), userType);
		DtcOrder dtcOrderInsert = new DtcOrder();
		dtcOrderInsert.setUuid(UuidUtils.getUuid());
		dtcOrderInsert.setDtcUuid(dtc.getUuid());
		dtcOrderInsert.setDtcIssuerType(dtc.getDtcIssuerType());
		dtcOrderInsert.setOrderNum(OrderUtils.GenOrderNo(OrderPrefixEnum.DTC));
		dtcOrderInsert.setOrderAmount(dtc.getDtcAmount());
		dtcOrderInsert.setOrderSts(OrderStsEnum.UNPAID.getValue());
		dtcOrderInsert.setBuyerUuid(userUuid);
		dtcOrderInsert.setBuyerType(userType);
		dtcOrderInsert.setBuyerName(userName);
		dtcOrderInsert.setBuyerMobile(mobile);
		dtcOrderInsert.setIssuerUuid(dtc.getDtcIssuerUuid());
		dtcOrderInsert.setIssuerName(dtcIssuerInfoDto.getDtcIssuerName());
		dtcOrderInsert.setIssuerMobile(dtcIssuerInfoDto.getDtcIssuerMobile());
		dtcOrderInsert.setReadCount(0);
		dtcOrderInsert.setSts(StsEnum.ACTIVE.getValue());
		dtcOrderInsert.setCreatedBy(userName);
		dtcOrderInsert.setCreatedTime(new Date());
		int insertNum = dtcOrderMapper.insert(dtcOrderInsert);
		if (insertNum <= 0) {
			log.error("??????dtc?????????????????????????????????{}", JSON.toJSONString(dtcOrderInsert));
			throw new BusinessException(ResEnum.INSERT_DB_ERROR);
		}
		// ??????order_info??????
		AddOrderInfoReq addOrderInfoReq = new AddOrderInfoReq();
		addOrderInfoReq.setOrderType(OrderTypeEnum.DTC.getValue());
		addOrderInfoReq.setOrderUuid(dtcOrderInsert.getUuid());
		orderInfoService.addOrder(addOrderInfoReq);
		return dtcOrderInsert.getUuid();
	}

	/**
	 * ??????dtc??????????????????
	 * @param dtcOrderUuid
	 * @param dtc
	 * @return
	 */
	private void insertDtcOrderDetail(String dtcOrderUuid, Dtc dtc) {
		// ??????user
		String userName = TokenHelper.getUserName();
		// ??????dtc????????????
		DtcContent dtcContent = dtcContentMapper.queryDtcUuidInfo(dtc.getUuid());
		DtcOrderDetail dtcOrderDetail = new DtcOrderDetail();
		dtcOrderDetail.setUuid(UuidUtils.getUuid());
		dtcOrderDetail.setOrderUuid(dtcOrderUuid);
		dtcOrderDetail.setDtcCode(dtc.getDtcCode());
		dtcOrderDetail.setDtcCode2(dtc.getDtcCode2());
		dtcOrderDetail.setDtcCode3(dtc.getDtcCode3());
		dtcOrderDetail.setDtcBrandUuid(dtc.getDtcBrandUuid());
		dtcOrderDetail.setDtcDefinition(dtc.getDtcDefinition());
		dtcOrderDetail.setDtcExplain(dtcContent.getDtcExplain());
		dtcOrderDetail.setDtcReasons(dtcContent.getDtcReasons());
		dtcOrderDetail.setDtcDiagnose(dtcContent.getDtcDiagnose());
		dtcOrderDetail.setSts(StsEnum.ACTIVE.getValue());
		dtcOrderDetail.setCreatedBy(userName);
		dtcOrderDetail.setCreatedTime(new Date());
		int insertNum = dtcOrderDetailMapper.insert(dtcOrderDetail);
		if (insertNum <= 0) {
			log.error("??????dtc?????????????????????????????????{}", JSON.toJSONString(dtcOrderDetail));
			throw new BusinessException(ResEnum.INSERT_DB_ERROR);
		}
	}

	/**
	 * ???????????????????????????????????????
	 * @param res
	 */
	private QueryDtcOrderInfoRes checkDtcOrder(QueryDtcOrderInfoRes res) {
		QueryDtcOrderInfoRes queryRes = new QueryDtcOrderInfoRes();
		// ??????????????????
		QueryDtcOrderInfoRes queryDtcOrderInfoRes = dtcOrderMapper.getById(res.getUuid());
		if (configConsts.getDtcOrderNum() <= queryDtcOrderInfoRes.getReadCount()) {
			queryRes.setIsPay(false);

		} else {
			res.setIsPay(true);
			BeanUtils.copyProperties(res, queryRes);
		}
		return queryRes;
	}

}
