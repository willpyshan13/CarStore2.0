package com.car.account.web.service.technician.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.car.account.client.feign.MessageFeign;
import com.car.account.client.feign.StoreUserFeign;
import com.car.account.client.feign.VehicleFegin;
import com.car.account.client.request.vehicle.VehicleUserListReq;
import com.car.account.client.response.vehicle.vehicleUser.VehicleUserRes;
import com.car.account.web.service.addr.AddrService;
import com.car.account.web.service.store.impl.StoreServiceImpl;
import com.car.common.enums.UserTypeEnum;
import com.car.system.client.feign.SystemFeign;
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

import com.car.account.client.enums.technician.TechnicianCertificateImgTypeEnum;
import com.car.account.client.enums.technician.TechnicianTypeEnum;
import com.car.account.client.request.technician.TechnicianAccountReq;
import com.car.account.client.request.technician.TechnicianAnswerReq;
import com.car.account.client.request.technician.TechnicianListReq;
import com.car.account.client.request.technician.TechnicianLocationListReq;
import com.car.account.client.request.technician.TechnicianReq;
import com.car.account.client.response.technician.TechnicianAccountRes;
import com.car.account.client.response.technician.TechnicianAnswerRes;
import com.car.account.client.response.technician.TechnicianBrandRes;
import com.car.account.client.response.technician.TechnicianCountRes;
import com.car.account.client.response.technician.TechnicianListRes;
import com.car.account.client.response.technician.TechnicianLocationListRes;
import com.car.account.client.response.technician.TechnicianRes;
import com.car.account.client.response.technician.caset.CaseStatisticsRes;
import com.car.account.web.common.constants.TechnicianConstants;
import com.car.account.web.mapper.technician.TechnicianAccountMapper;
import com.car.account.web.mapper.technician.TechnicianBrandMapper;
import com.car.account.web.mapper.technician.TechnicianCertificateImagesMapper;
import com.car.account.web.mapper.technician.TechnicianMapper;
import com.car.account.web.model.technician.Technician;
import com.car.account.web.model.technician.TechnicianAccount;
import com.car.account.web.model.technician.TechnicianBrand;
import com.car.account.web.model.technician.TechnicianCertificateImages;
import com.car.account.web.service.dict.SysDictService;
import com.car.account.web.service.person.PersonService;
import com.car.account.web.service.technician.TechnicianService;
import com.car.common.enums.CheckStatusEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.exception.BusinessException;
import com.car.common.req.PageReq;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.Constants;
import com.car.common.utils.DateUtil;
import com.car.common.utils.ExcelUtils;
import com.car.common.utils.ExceptionUtils;
import com.car.common.utils.StringUtil;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.common.utils.token.LoginToken;
import com.car.system.client.response.dict.DictionaryRes;
import com.car.utility.client.feign.BaiduFeign;
import com.car.utility.client.feign.SmsFeign;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xlj
 */
@Slf4j
@Service
public class TechnicianServiceImpl implements TechnicianService {

	@Autowired
	TechnicianMapper technicianMapper;

	@Autowired
	TechnicianAccountMapper technicianAccountMapper;

	@Autowired
	TechnicianBrandMapper technicianBrandMapper;

	@Autowired
	TechnicianCertificateImagesMapper technicianCertificateImagesMapper;

	@Resource
	SmsFeign smsFeign;

	@Resource
	SysDictService sysDictService;

	@Autowired
	private PersonService personService;

	@Autowired
	private StoreServiceImpl storeService;

	@Autowired
	BaiduFeign baiduFeign;

	@Autowired
	private StoreUserFeign storeUserFeign;

	@Autowired
	private VehicleFegin vehicleFegin;
	@Autowired
	private MessageFeign messageFeign;
	@Autowired
	private AddrService addrService;
	@Autowired
	private SystemFeign systemFeign;

	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> addTechnician(TechnicianReq params) {

		// ????????????????????????
		/*if (null == params.getAnswerAmt() || params.getAnswerAmt().compareTo(BigDecimal.ZERO) == 0) {
		    log.error("???????????????????????????0");
		    throw new BusinessException(ResEnum.TECHNICIAN_NO_ANSWER_AMT);
		}*/
		String userName = (StringUtil.isNotBlank(TokenHelper.getUserName())) ? TokenHelper.getUserName()
				: params.getUserName();
		// ??????????????????????????????
		Technician technician = new Technician();
		technician.setMobile(params.getMobile());
		technician.setSts(StsEnum.ACTIVE.getValue());
		technician = technicianMapper.selectOne(technician);
		if (!StringUtils.isEmpty(technician)) {
			throw new BusinessException(ResEnum.MOBILE_EXIST_ERROR);
		}

		// ????????????????????????????????????
		storeService.checkMobileAlreadyExist(params.getMobile(), null);

		// ????????????????????????????????????
		VehicleUserListReq vehicleUserListReq = new VehicleUserListReq();
		vehicleUserListReq.setMobile(params.getMobile());
		List<VehicleUserRes> data1 = vehicleFegin.queryList(vehicleUserListReq).getData();
		System.out.println("data1:" + data1.toString());
		if (!data1.isEmpty()) {
			throw new BusinessException(ResEnum.MOBILE_EXIST_ERROR);
		}

		// ??????????????????,???????????? ??????????????????????????????????????? ??????????????????????????????
		checkIsTechnicianType(params);

		Date curr = new Date();
		String uuid = UuidUtils.getUuid();

		// ??????????????????????????????
		batchInsertCertificateImages(params, uuid, userName, false);

		Technician data = new Technician();
		BeanUtils.copyProperties(params, data);

		data.setUuid(uuid);
		data.setSts(StsEnum.ACTIVE.getValue());
		data.setCreatedBy(userName);
		data.setCreatedTime(curr);
		data.setCheckSts(CheckStatusEnum.CHECK_PENDING.getValue());
		data.setCybAuth(0);

		DictionaryRes dictionaryRes = sysDictService
				.querySysDict(TechnicianConstants.TECHNICIAN_CONSULT_AMOUNT_DICT_UUID);
		if (!StringUtils.isEmpty(dictionaryRes)) {
			data.setAnswerAmt(new BigDecimal(dictionaryRes.getLableValue()));
		}
		technicianMapper.insert(data);

		// ??????????????????
		List<String> brandUuidList = params.getBrandUuidList();
		if (!CollectionUtils.isEmpty(brandUuidList)) {

			brandUuidList.stream().forEach(s -> {

				insertTechnicianBrand(s, uuid);
			});
		}

		// ??????????????????
		TechnicianAccountReq technicianAccount = params.getTechnicianAccount();
		if (null != technicianAccount) {

			insertTechnicianAccount(uuid, technicianAccount);
		}
		return ResultRes.success(uuid);
	}

	/**
	 * ????????????
	 *
	 * @param uuid
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes<String> deleteTechnician(String uuid) {
		Technician deleteTechnician = new Technician();
		deleteTechnician.setSts(StsEnum.ACTIVE.getValue());
		deleteTechnician.setUuid(uuid);
		deleteTechnician = technicianMapper.selectOne(deleteTechnician);
		if (StringUtils.isEmpty(deleteTechnician)) {
			log.error("????????????,???????????????????????? uuid:{}", uuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		deleteTechnician.setSts(StsEnum.INVALID.getValue());
		deleteTechnician.setLastUpdatedBy(TokenHelper.getUserName());
		deleteTechnician.setLastUpdatedTime(new Date());
		int deleteNum = technicianMapper.updateByPrimaryKeySelective(deleteTechnician);
		if (deleteNum <= 0) {
			log.error("????????????????????????");
			throw new BusinessException(ResEnum.DELETE_DB_ERROR);
		}
		ResultRes resultRes = personService.exitLoginByUserUuid(uuid);
		if (!resultRes.isSuccess()) {
			log.error("??????????????????");
			throw new BusinessException(ResEnum.EXIT_ERROR);
		}
		return ResultRes.success(uuid);
	}

	/**
	 * ?????????????????????????????????/??????/??????/??????/?????????
	 *
	 * @param param
	 * @return
	 */
	@Override
	public ResultRes<TechnicianCountRes> queryTechnicianCount(TechnicianListReq param) {
		return ResultRes.success(technicianMapper.queryTechnicianCount(param));
	}

	/**
	 * ????????????????????????
	 *
	 * @param param
	 * @return
	 */
	@Override
	public PageRes<List<TechnicianListRes>> queryTechnicianList(TechnicianListReq param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<TechnicianListRes> technicianResList = technicianMapper.queryTechnicianList(param);
		PageInfo<TechnicianListRes> pageInfo = new PageInfo<>(technicianResList);
		return PageRes.success(technicianResList, pageInfo.getPageSize(), (int) pageInfo.getTotal(),
				pageInfo.getPages());
	}

	/**
	 * ??????????????????
	 *
	 * @param exportReq
	 * @param response
	 */
	@Override
	public void exportTechnicianList(TechnicianListReq exportReq, HttpServletResponse response) {
		try {
			List<TechnicianListRes> technicianResList = technicianMapper.queryTechnicianList(exportReq);
			// ??????????????????
			InputStream resourceAsStream = getClass().getClassLoader()
					.getResourceAsStream(TechnicianConstants.TECHNICIAN_EXPORT_TEMPLATE);
			// ????????????????????????
			List<TechnicianListRes> excelList = ExcelUtils.setFieldValue(technicianResList);
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
				TechnicianListRes exportDto = excelList.get(rowIndex - 2);
				ExcelUtils.setCell(row, cellStyle1, 0, rowIndex - 1);
				ExcelUtils.setCell(row, cellStyle, 1, exportDto.getUserName());
				ExcelUtils.setCell(row, cellStyle, 2, exportDto.getMobile());
				ExcelUtils.setCell(row, cellStyle, 3, exportDto.getAddressProvinceName());
				ExcelUtils.setCell(row, cellStyle, 4, exportDto.getAddressCityName());
				ExcelUtils.setCell(row, cellStyle, 5, exportDto.getAddressDetail());
				ExcelUtils.setCell(row, cellStyle, 6, exportDto.getTechnologyTypeName());
				ExcelUtils.setCell(row, cellStyle, 7,
						StringUtils.isEmpty(exportDto.getWorkingYear()) ? 0 : exportDto.getWorkingYear());
				ExcelUtils.setCell(row, cellStyle, 8, exportDto.getTechnicianBrandName());
				ExcelUtils.setCell(row, cellStyle, 9,
						StringUtils.isEmpty(exportDto.getOrderCount()) ? 0 : exportDto.getOrderCount());
				ExcelUtils.setCell(row, cellStyle, 10,
						StringUtils.isEmpty(exportDto.getCaseCount()) ? 0 : exportDto.getCaseCount());
				ExcelUtils.setCell(row, cellStyle, 11,
						StringUtils.isEmpty(exportDto.getQaCount()) ? 0 : exportDto.getQaCount());
				ExcelUtils.setCell(row, cellStyle, 12,
						StringUtils.isEmpty(exportDto.getSupportCount()) ? 0 : exportDto.getSupportCount());
				ExcelUtils.setCell(row, cellStyle, 13,
						StringUtils.isEmpty(exportDto.getScore()) ? "" : exportDto.getScore().toString());
				ExcelUtils.setCell(row, cellStyle, 14, CheckStatusEnum.enumOfDesc(exportDto.getCheckSts()));
				ExcelUtils.setCell(row, cellStyle, 15, exportDto.getStateVerification());
				ExcelUtils.setCell(row, cellStyle, 16, exportDto.getHostAuthentication());
				ExcelUtils.setCell(row, cellStyle, 17,
						DateUtil.dateToStr(exportDto.getCreatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
			}
			ExcelUtils.responseWrite(wb, response, TechnicianConstants.TECHNICIAN_EXPORT_TEMPLATE);
		} catch (Exception ex) {
			log.error("??????????????????????????????????????????{}", ExceptionUtils.stackTraceToString(ex));
		}
	}

	/**
	 * ??????????????????
	 *
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<TechnicianRes> queryTechnicianDetail(String uuid) {
		Technician technician = technicianMapper.selectByPrimaryKey(uuid);

		if (StringUtils.isEmpty(technician)) {
			log.error("????????????ID???{}??????????????????????????????", uuid);
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		// ?????????????????????????????????
		TechnicianRes res = new TechnicianRes();
		BeanUtils.copyProperties(technician, res);

		// ?????????????????????????????????
		TechnicianAccount account = new TechnicianAccount();
		account.setTechnicianUuid(technician.getUuid());
		account.setSts(StsEnum.ACTIVE.getValue());
		account = technicianAccountMapper.selectOne(account);
		if (!StringUtils.isEmpty(account)) {
			TechnicianAccountRes technicianAccount = new TechnicianAccountRes();
			BeanUtils.copyProperties(account, technicianAccount);
			res.setTechnicianAccount(technicianAccount);
		}
		// ?????????????????????????????????????????????
		List<TechnicianBrandRes> technicianBrandResList = technicianBrandMapper
				.selectBrandByTechnicianUuid(technician.getUuid());
		res.setBrandList(technicianBrandResList);

		// ?????????????????????????????????
		int shareNum = technicianBrandMapper.queryShareNum(technician.getUuid());
		res.setShareNum(shareNum);

		DictionaryRes dictionaryRes = sysDictService.querySysDict(technician.getTechnologyType());
		// ??????????????????????????????
		res.setTechnologyTypeName(dictionaryRes.getLableDesc());

		// ???????????????????????????
		DictionaryRes shareTechnicianMoney = sysDictService.querySysDict("6007");
		// ??????????????????
		res.setPlatformMoney(Double.valueOf(shareTechnicianMoney.getLableValue()));

		res.setAddressProvinceName(addrService.locationVal(res.getAddressProvince()));
		res.setAddressCityName(addrService.locationVal(res.getAddressCity()));
		res.setAddressCountyName(addrService.locationVal(res.getAddressCounty()));
		DictionaryRes certificate = sysDictService.querySysDict(technician.getCertificateType());
		res.setCertificateTypeName(certificate.getLableDesc());
		// ??????????????????????????????
		TechnicianCertificateImages query = new TechnicianCertificateImages();
		query.setTechnicianUuid(technician.getUuid());
		List<TechnicianCertificateImages> list = technicianCertificateImagesMapper.select(query);
		if (!CollectionUtils.isEmpty(list)) {
			List<String> stateImgList = list.stream()
					.filter(images -> TechnicianCertificateImgTypeEnum.STATE_VERIFICATION.getValue()
							.equals(images.getImgType()))
					.map(TechnicianCertificateImages::getImgUrl).collect(Collectors.toList());
			List<String> hostImgList = list.stream()
					.filter(images -> TechnicianCertificateImgTypeEnum.HOST_AUTHENTICATION.getValue()
							.equals(images.getImgType()))
					.map(TechnicianCertificateImages::getImgUrl).collect(Collectors.toList());
			res.setStateImgList(stateImgList);
			res.setHostImgList(hostImgList);
		}
		res.setAddressProvinceName(systemFeign.queryArea(technician.getAddressProvince()).getData().getAreaName());
		res.setAddressCityName(systemFeign.queryArea(technician.getAddressCity()).getData().getAreaName());
		res.setAddressCountyName(systemFeign.queryArea(technician.getAddressCounty()).getData().getAreaName());
		return ResultRes.success(res);
	}

	/**
	 * ??????????????????
	 *
	 * @param param
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@TxcTransaction
	public ResultRes updateTechnician(TechnicianReq param) {
		Technician technician = technicianMapper.selectByPrimaryKey(param.getUuid());
		if (StringUtils.isEmpty(technician)) {
			log.error("????????????ID???{}??????????????????????????????", param.getUuid());
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		// ????????????????????????????????????????????????????????????????????????token
		if (!(technician.getMobile().equals(param.getMobile()))) {
			personService.exitLoginByUserUuid(param.getUuid());
		}
		// ?????????????????????????????????????????????????????????token
		if (!(CheckStatusEnum.APPROVE.getValue().equals(param.getCheckSts()))) {
			personService.exitLoginByUserUuid(param.getUuid());
		}
		// ??????????????????????????????
		boolean mobileFlag = checkMobileExist(technician, param.getMobile());
		if (mobileFlag) {
			throw new BusinessException(ResEnum.MOBILE_EXIST_ERROR);
		}
		// ??????????????????,???????????? ??????????????????????????????????????? ??????????????????????????????
		checkIsTechnicianType(param);

		// ????????????????????????????????????????????????????????????
		Integer newCheckSts = param.getCheckSts();
		Integer oldCheckSts = technician.getCheckSts();
		String newMobile = param.getMobile();
		String oldMobile = technician.getMobile();

		BeanUtils.copyProperties(param, technician);
		technician.setLastUpdatedBy(TokenHelper.getUserName());
		technician.setLastUpdatedTime(new Date());

		// ???????????????????????????(0=??????2???,1=??????5???)
		DictionaryRes dictionaryRes = sysDictService
				.querySysDict(technician.getCybAuth() == 0 ? TechnicianConstants.TECHNICIAN_CYB_GEN_AMOUNT_DICT_UUID
						: TechnicianConstants.TECHNICIAN_CYB_EXP_AMOUNT_DICT_UUID);
		if (!StringUtils.isEmpty(dictionaryRes)) {
			technician.setAnswerAmt(new BigDecimal(dictionaryRes.getLableValue()));
		}

		technicianMapper.updateByPrimaryKey(technician);

		TechnicianAccountReq technicianAccountReq = param.getTechnicianAccount();
		String uuid = technician.getUuid();
		if (!StringUtils.isEmpty(technicianAccountReq)) {
			// ??????????????????????????????
			TechnicianAccount account = new TechnicianAccount();
			account.setTechnicianUuid(uuid);
			account.setSts(StsEnum.ACTIVE.getValue());
			account = technicianAccountMapper.selectOne(account);
			if (StringUtils.isEmpty(account)) {
				// ????????????????????????
				insertTechnicianAccount(uuid, technicianAccountReq);
			} else {
				// ????????????????????????
				BeanUtils.copyProperties(technicianAccountReq, account);
				account.setLastUpdatedBy(TokenHelper.getUserName());
				account.setLastUpdatedTime(new Date());
				technicianAccountMapper.updateByPrimaryKeySelective(account);
			}
		}

		// ??????????????????????????????
		batchInsertCertificateImages(param, uuid, technician.getUserName(), true);

		// ????????????????????????
		TechnicianBrand technicianBrand = new TechnicianBrand();
		technicianBrand.setTechnicianUuid(param.getUuid());
		technicianBrandMapper.delete(technicianBrand);
		// ????????????
		List<String> brandReqs = param.getBrandUuidList();
		if (!CollectionUtils.isEmpty(brandReqs)) {
			brandReqs.stream().forEach(s -> {
				insertTechnicianBrand(s, uuid);
			});
		}

		// ???????????????????????????????????????????????????,????????????????????????????????????
		if (!oldMobile.equals(newMobile)) {
			ResultRes resultRes = personService.exitLoginByUserUuid(uuid);
			if (!resultRes.isSuccess()) {
				log.error("???????????????????????????????????????");
				throw new BusinessException(ResEnum.EXIT_ERROR);
			}
		}
		String id = "";
		if (CheckStatusEnum.APPROVE.getValue().equals(newCheckSts)) {
			id = "6002";
		} else if (CheckStatusEnum.CHECK_REJECTED.getValue().equals(newCheckSts)) {
			id = "6001";
		}
		try {
			messageFeign.sendMsg(id,new HashMap(), technician.getUuid(), UserTypeEnum.technician.getType(),
					param.getUuid());
		} catch (Exception e) {
			log.info("??????????????????!!");
		}
		// ??????????????????
		sendExamineSms(technician.getMobile(), newCheckSts, oldCheckSts, technician.getRejectDetail());
		return ResultRes.success();
	}

	/**
	 * ????????????????????????
	 *
	 * @param technicianUuid
	 * @param technicianAccount
	 */
	private void insertTechnicianAccount(String technicianUuid, TechnicianAccountReq technicianAccount) {
		// ????????????????????????
		TechnicianAccount account = new TechnicianAccount();
		BeanUtils.copyProperties(technicianAccount, account);
		account.setUuid(UuidUtils.getUuid());
		account.setSts(StsEnum.ACTIVE.getValue());
		account.setTechnicianUuid(technicianUuid);
		account.setCreatedBy(TokenHelper.getUserName());
		account.setCreatedTime(new Date());
		account.setAccountAmount(BigDecimal.ZERO);
		account.setFrozenAmt(BigDecimal.ZERO);
		account.setWaitAmount(BigDecimal.ZERO);
		account.setTotalAmount(BigDecimal.ZERO);
		account.setWithdrawAmount(BigDecimal.ZERO);
		technicianAccountMapper.insert(account);
	}

	/**
	 * ????????????????????????
	 *
	 * @param brandUuid
	 * @param technicianUuid
	 */
	private void insertTechnicianBrand(String brandUuid, String technicianUuid) {
		TechnicianBrand insert = new TechnicianBrand();
		insert.setUuid(UuidUtils.getUuid());
		insert.setSts(StsEnum.ACTIVE.getValue());
		insert.setBrandUuid(brandUuid);
		insert.setTechnicianUuid(technicianUuid);
		insert.setCreatedBy(TokenHelper.getUserName());
		insert.setCreatedTime(new Date());
		technicianBrandMapper.insert(insert);
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param technician
	 * @param mobile
	 * @return
	 */
	private boolean checkMobileExist(Technician technician, String mobile) {
		if (technician.getMobile().equals(mobile)) {
			// ?????????????????????????????????????????????????????????
			return false;
		}
		Technician search = new Technician();
		search.setMobile(mobile);
		search.setSts(StsEnum.ACTIVE.getValue());
		Technician checkTechnician = technicianMapper.selectOne(search);
		if (StringUtils.isEmpty(checkTechnician)) {
			// ??????????????????????????????????????????
			return false;
		}
		return true;
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param mobile
	 * @param newCheckSts
	 * @param oldCheckSts
	 */
	private void sendExamineSms(String mobile, Integer newCheckSts, Integer oldCheckSts, String content) {
		try {
			if (!newCheckSts.equals(oldCheckSts)) {
				if (CheckStatusEnum.APPROVE.getValue().equals(newCheckSts)) {
					// ????????????
					smsFeign.sendTechnicianCheckSuccess(mobile);

				} else if (CheckStatusEnum.CHECK_REJECTED.getValue().equals(newCheckSts)) {
					// ????????????
					smsFeign.sendTechnicianCheckReject(mobile, content);

				}
			}
		} catch (Exception ex) {
			log.error("????????????????????????????????????????????????????????????{}", ExceptionUtils.stackTraceToString(ex));
		}
	}

	@Override
	public PageRes<List<TechnicianAnswerRes>> queryTechnicianAnswerList(TechnicianAnswerReq params) {

		Integer pageNum = params.getPageNum();
		Integer pageSize = params.getPageSize();

		PageHelper.startPage(pageNum, pageSize);
		List<Technician> list = technicianMapper.queryTechnicianAnswerList(params);
		PageInfo<Technician> pageInfo = new PageInfo<>(list);
		if (!CollectionUtils.isEmpty(list)) {
			List<TechnicianAnswerRes> dst = new ArrayList<>();

			boolean isApple = "1".equals(sysDictService.querySysDict(Constants.PRICE_ON_OFF_UUID).getLableValue());

			for (Technician s : list) {

				String uuid = s.getUuid();
				String userName = s.getUserName();
				if (StringUtils.isEmpty(userName)) {
					userName = TechnicianConstants.TECHNICIAN_JOINT_STRING;
				} else {
					userName = userName.substring(0, 1) + TechnicianConstants.TECHNICIAN_JOINT_STRING;
				}
				// ???????????? ????????????????????????
				String technologyType = s.getTechnologyType();
				DictionaryRes dictionaryRes = sysDictService.querySysDict(technologyType);
				if (null != dictionaryRes) {
					s.setTechnologyType(dictionaryRes.getLableDesc());
				}
				TechnicianAnswerRes res = new TechnicianAnswerRes();
				//??????
				if(s.getAddressProvince()!=null && s.getAddressCity()!=null && s.getAddressCounty()!=null){
					String province = systemFeign.queryArea(s.getAddressProvince()).getData().getAreaName();
					String city =systemFeign.queryArea(s.getAddressCity()).getData().getAreaName();
					String district = systemFeign.queryArea(s.getAddressCounty()).getData().getAreaName();
					res.setAddressProvinceName(province);
					res.setAddressCityName(city);
					res.setAddressCountyName(district);
				}
				BeanUtils.copyProperties(s, res);
				res.setName(userName);
				res.setUuid(uuid);
				res.setTechnicalTypeUuid(technologyType);

				// ???????????????????????????????????????
				List<TechnicianBrandRes> technicianBrandRes = technicianBrandMapper.selectBrandByTechnicianUuid(uuid);
				if (!CollectionUtils.isEmpty(technicianBrandRes)) {
					res.setBrandResList(technicianBrandRes);
				}

				if (isApple) {
					res.setAnswerAmt(BigDecimal.ZERO);
				}
				dst.add(res);
			}
			dst = dst.stream().filter(e -> !e.getUuid().equals(TokenHelper.getUserUuid())).collect(Collectors.toList());
			return PageRes.success(dst, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
		}

		return PageRes.success(Collections.emptyList(), pageInfo.getPageSize(), (int) pageInfo.getTotal(),
				pageInfo.getPages());
	}

	@Override
	public PageRes<List<CaseStatisticsRes>> queryCaseStatisticsList(PageReq pageReq) {
		Integer pageNum = pageReq.getPageNum();
		Integer pageSize = pageReq.getPageSize();
		PageHelper.startPage(pageNum, pageSize);

		return null;
	}

	@Override
	public ResultRes<List<TechnicianLocationListRes>> queryTechnicianLocationList(TechnicianLocationListReq req) {

		Integer distance = req.getDistance();
		if (distance == null) {
			DictionaryRes dictionaryRes = sysDictService.querySysDict("10101");
			distance = Integer.parseInt(dictionaryRes.getLableDesc());
		} else {
			distance = distance * 1000;
		}

		List<Technician> list = technicianMapper.queryTechnicianLocationList(req.getLatitude(), req.getLongitude(),
				distance, req.getBrandUuid(), req.getTechnologyType());
		List<TechnicianLocationListRes> resList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(list)) {
			list.stream().forEach(data -> {
				TechnicianLocationListRes res = new TechnicianLocationListRes();
				BeanUtils.copyProperties(data, res);
				resList.add(res);
			});
		}
		return ResultRes.success(resList);
	}

	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<String> updateQaCount(String uuid) {
		Technician technician = technicianMapper.selectByPrimaryKey(uuid);
		if (StringUtils.isEmpty(technician)) {
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		technician.setQaCount((technician.getQaCount() == null ? 0 : technician.getQaCount()) + 1);
		technician.setLastUpdatedTime(new Date());
		technicianMapper.updateByPrimaryKey(technician);
		return ResultRes.success(uuid);
	}

	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<String> updateScore(String uuid, BigDecimal score){
		Technician technician = technicianMapper.selectByPrimaryKey(uuid);
		if (StringUtils.isEmpty(technician)) {
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		technician.setScore(score);
		technician.setScoreCount((technician.getScoreCount() == null ? 0 : technician.getQaCount()) + 1);
		technician.setLastUpdatedTime(new Date());
		technicianMapper.updateByPrimaryKey(technician);
		return ResultRes.success(uuid);
	}

	@Override
	public ResultRes<List<String>> queryTechnicianBrand(String uuid) {
		List<TechnicianBrandRes> res = technicianBrandMapper.selectBrandByTechnicianUuid(uuid);
		List<String> list = new ArrayList<>();
		for (TechnicianBrandRes technicianBrandRes: res){
			list.add(technicianBrandRes.getBrandUuid());
		}
		return ResultRes.success(list);
	}

	/**
	 * ??????????????????,???????????? ??????????????????????????????????????? ??????????????????????????????
	 *
	 * @param params
	 */
	private void checkIsTechnicianType(TechnicianReq params) {
		String technologyType = params.getTechnologyType();
		if (TechnicianTypeEnum.DRIVING_TECHNICIAN.getValue().equals(technologyType)) {
			String driverLicenseUrl = params.getDriverLicenseUrl();
			String driverLicenseBackUrl = params.getDriverLicenseBackUrl();
			String noCrimeUrl = params.getNoCrimeUrl();
			String healthCheckUrl = params.getHealthCheckUrl();

			// ????????????????????????????????????????????????????????????????????????????????????
			if (StringUtils.isEmpty(driverLicenseUrl) || StringUtils.isEmpty(driverLicenseBackUrl)
					|| StringUtils.isEmpty(noCrimeUrl) || StringUtils.isEmpty(healthCheckUrl)) {
				log.error("????????????????????????????????????????????????????????????????????????????????????");
				throw new BusinessException(ResEnum.TECHNICIAN_DRIVING_NO_IMG);
			}
		}
	}

	/**
	 * ????????????????????????
	 *
	 * @param params
	 * @param technicianUuid
	 * @param name
	 */
	private void batchInsertCertificateImages(TechnicianReq params, String technicianUuid, String name,
			boolean isUpdate) {

		String stateVerification = params.getStateVerification();
		String hostAuthentication = params.getHostAuthentication();

		/*if (StringUtils.isEmpty(stateVerification) && StringUtils.isEmpty(hostAuthentication)) {
			log.error("?????????????????????");
			throw new BusinessException(ResEnum.TECHNICIAN_NO_LEVEL);
		}*/

		if (!StringUtils.isEmpty(stateVerification) && !StringUtils.isEmpty(hostAuthentication)) {
			List<String> stateImgList = params.getStateImgList();
			List<String> hostImgList = params.getHostImgList();
			if (CollectionUtils.isEmpty(stateImgList) && CollectionUtils.isEmpty(hostImgList)) {
				log.error("?????????  ?????????????????? ??? ???????????????");
				throw new BusinessException(ResEnum.TECHNICIAN_NO_STATE_LEVEL_IMG);
			}
		}

		/*		if (!StringUtils.isEmpty(hostAuthentication)) {
					List<String> hostImgList = params.getHostImgList();
					if (CollectionUtils.isEmpty(hostImgList)) {
						log.error("????????????????????????????????????");
						throw new BusinessException(ResEnum.TECHNICIAN_NO_HOST_LEVEL_IMG);
					}
				}*/

		List<TechnicianCertificateImages> list = new ArrayList<>();
		TechnicianCertificateImages technicianCertificateImages;
		if (!CollectionUtils.isEmpty(params.getStateImgList())) {
			if (isUpdate) {
				TechnicianCertificateImages deleteImg = new TechnicianCertificateImages();
				deleteImg.setTechnicianUuid(technicianUuid);
				deleteImg.setImgType(TechnicianCertificateImgTypeEnum.STATE_VERIFICATION.getValue());
				technicianCertificateImagesMapper.delete(deleteImg);
			}

			for (String url : params.getStateImgList()) {
				technicianCertificateImages = new TechnicianCertificateImages();
				technicianCertificateImages.setImgType(TechnicianCertificateImgTypeEnum.STATE_VERIFICATION.getValue());
				technicianCertificateImages.setImgUrl(url);
				technicianCertificateImages.setTechnicianUuid(technicianUuid);
				technicianCertificateImages.setUuid(UuidUtils.getUuid());
				technicianCertificateImages.setSts(StsEnum.ACTIVE.getValue());
				technicianCertificateImages.setCreatedBy(name);
				technicianCertificateImages.setCreatedTime(new Date());
				list.add(technicianCertificateImages);
			}
		}
		if (!CollectionUtils.isEmpty(params.getHostImgList())) {
			if (isUpdate) {
				TechnicianCertificateImages deleteImg = new TechnicianCertificateImages();
				deleteImg.setTechnicianUuid(technicianUuid);
				deleteImg.setImgType(TechnicianCertificateImgTypeEnum.HOST_AUTHENTICATION.getValue());
				technicianCertificateImagesMapper.delete(deleteImg);
			}
			for (String url : params.getHostImgList()) {
				technicianCertificateImages = new TechnicianCertificateImages();
				technicianCertificateImages.setImgType(TechnicianCertificateImgTypeEnum.HOST_AUTHENTICATION.getValue());
				technicianCertificateImages.setImgUrl(url);
				technicianCertificateImages.setTechnicianUuid(technicianUuid);
				technicianCertificateImages.setUuid(UuidUtils.getUuid());
				technicianCertificateImages.setSts(StsEnum.ACTIVE.getValue());
				technicianCertificateImages.setCreatedBy(name);
				technicianCertificateImages.setCreatedTime(new Date());
				list.add(technicianCertificateImages);
			}
		}

		technicianCertificateImagesMapper.batchInsertCertificateImages(list);

	}

}
