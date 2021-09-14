package com.car.account.web.service.vehicle.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.car.account.client.response.vehicle.config.ConfigRes;
import com.car.account.web.common.utils.UuidUtils;
import com.car.account.web.mapper.vehicle.VehicleConfigMapper;
import com.car.account.web.model.vehicle.VehicleConfig;
import com.car.account.web.service.vehicle.VehicleConfigService;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.ResultRes;
import com.car.common.utils.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xlj
 */
@Slf4j
@Service
public class VehicleConfigServiceImpl implements VehicleConfigService {

	@Autowired
	VehicleConfigMapper vehicleConfigMapper;

	/**
	 * 查询所有车辆配置信息
	 * @return
	 */
	@Override
	public ResultRes<List<ConfigRes>> queryAllList() {
		// 查询所有有效的数据信息
		VehicleConfig search = new VehicleConfig();
		search.setSts(StsEnum.ACTIVE.getValue());
		List<VehicleConfig> vehicleConfigList = vehicleConfigMapper.selectConfigByExample(search);
		// 对象转化输出
		return ResultRes.success(convertToConfigRes(vehicleConfigList));
	}

	/**
	 * 查询父节点下车辆子节点
	 * @param parentUuid
	 * @return
	 */
	@Override
	public ResultRes<List<ConfigRes>> queryListByParent(String parentUuid) {
		// 查询所有有效的数据信息
		VehicleConfig search = new VehicleConfig();
		search.setSts(StsEnum.ACTIVE.getValue());
		search.setParentCode(parentUuid);
		List<VehicleConfig> vehicleConfigList = vehicleConfigMapper.selectConfigByExample(search);
		// 对象转化输出
		return ResultRes.success(convertToConfigRes(vehicleConfigList));
	}

	/**
	 * 查询父节点所有孙子节点信息
	 * @param parentUuid
	 * @return
	 */
	@Override
	public ResultRes<List<ConfigRes>> queryListNextByParent(String parentUuid) {
		ResultRes<List<ConfigRes>> zi = queryListByParent(parentUuid);
		List<ConfigRes> sun = new ArrayList<>();

		for (ConfigRes configRes : zi.getData()) {
			sun.addAll(queryListByParent(configRes.getUuid()).getData());
		}
		return ResultRes.success(sun);
	}

	/**
	 * 对象转化
	 * @param vehicleConfigList
	 * @return
	 */
	private List<ConfigRes> convertToConfigRes(List<VehicleConfig> vehicleConfigList) {
		List<ConfigRes> listRes = new ArrayList<>();
		if (!CollectionUtils.isEmpty(vehicleConfigList)) {
			for (VehicleConfig config : vehicleConfigList) {
				ConfigRes res = new ConfigRes();
				BeanUtils.copyProperties(config, res);
				listRes.add(res);
			}
		}
		return listRes;
	}

	/**
	 * 根据uuid查询车辆节点信息
	 * @param uuid
	 * @return
	 */
	@Override
	public ResultRes<ConfigRes> queryConfig(String uuid) {
		VehicleConfig config = vehicleConfigMapper.selectByPrimaryKey(uuid);
		if (StringUtils.isEmpty(config)) {
			throw new BusinessException(ResEnum.NON_EXISTENT);
		}
		ConfigRes res = new ConfigRes();
		BeanUtils.copyProperties(config, res);
		return ResultRes.success(res);
	}

	/**
	 * 批量导入车辆配置信息
	 * @param file
	 * @return
	 */
	@Override
	public ResultRes batchImport(MultipartFile file) {
		boolean isE2007 = false;
		// 判断是否是excel2007格式
		if (file.getOriginalFilename().endsWith("xlsx")) {
			isE2007 = true;
		}
		int rowIndex = 0;
		try {
			// 建立输入流
			InputStream input = file.getInputStream();
			Workbook wb;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			// 获得第一个表单
			Sheet sheet = wb.getSheetAt(0);
			int rowCount = sheet.getLastRowNum() + 1;
			for (int i = 1; i < rowCount; i++) {
				rowIndex = i;
				String vehiclePinPai = ExcelUtils.getSheetValue(sheet, i, 0);
				String vehicleXingHao = ExcelUtils.getSheetValue(sheet, i, 1);
				String vehicleXingHao2 = ExcelUtils.getSheetValue(sheet, i, 2);
				String vehicleXingHao3 = ExcelUtils.getSheetValue(sheet, i, 3);
				String vehicleXingHao4 = ExcelUtils.getSheetValue(sheet, i, 4);
				String vehicleXingHao5 = ExcelUtils.getSheetValue(sheet, i, 5);

				vehiclePinPai = StringUtils.isEmpty(vehiclePinPai) ? "无" : vehiclePinPai;
				vehicleXingHao = StringUtils.isEmpty(vehicleXingHao) ? "无" : vehicleXingHao;
				vehicleXingHao2 = StringUtils.isEmpty(vehicleXingHao2) ? "无" : vehicleXingHao2;
				vehicleXingHao3 = StringUtils.isEmpty(vehicleXingHao3) ? "无" : vehicleXingHao3;
				vehicleXingHao4 = StringUtils.isEmpty(vehicleXingHao4) ? "无" : vehicleXingHao4;
				vehicleXingHao5 = StringUtils.isEmpty(vehicleXingHao5) ? "无" : vehicleXingHao5;

				VehicleConfig model = checkSave(StringUtils.trimWhitespace(vehiclePinPai), "0001", "2");
				model = checkSave(StringUtils.trimWhitespace(vehicleXingHao), model.getUuid(), "3");
				model = checkSave(StringUtils.trimWhitespace(vehicleXingHao2), model.getUuid(), "4");
				model = checkSave(StringUtils.trimWhitespace(vehicleXingHao3), model.getUuid(), "5");
				model = checkSave(StringUtils.trimWhitespace(vehicleXingHao4), model.getUuid(), "6");
				model = checkSave(StringUtils.trimWhitespace(vehicleXingHao5), model.getUuid(), "7");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResultRes.success();
	}

	private VehicleConfig checkSave(String name, String pCode, String type) {
		// 检查车辆型号是否存在
		VehicleConfig vehicleModel1 = new VehicleConfig();
		vehicleModel1.setConfigName(name);
		vehicleModel1.setConfigType(type);
		vehicleModel1.setSts(0);
		vehicleModel1.setParentCode(pCode);
		vehicleModel1 = vehicleConfigMapper.selectOne(vehicleModel1);
		if (StringUtils.isEmpty(vehicleModel1)) {
			// 新增当前车辆
			vehicleModel1 = new VehicleConfig();
			vehicleModel1.setUuid(UuidUtils.getUUID());
			vehicleModel1.setConfigName(name);
			vehicleModel1.setParentCode(pCode);
			vehicleModel1.setSts(StsEnum.ACTIVE.getValue());
			vehicleModel1.setConfigType(type);
			vehicleModel1.setCreatedTime(new Date());
			// 查询排序最大的一个型号
			Integer maxSort = vehicleConfigMapper.selectMaxSortByVehicleConfigModel(pCode);
			vehicleModel1.setSortNum(maxSort + 1);
			vehicleConfigMapper.insert(vehicleModel1);
		}

		return vehicleModel1;
	}

	@Override
	public List<ConfigRes> queryListByUuid(List<String> uuidList) {
		List<ConfigRes> reslist = new ArrayList<>();
		List<VehicleConfig> list = vehicleConfigMapper.queryListByUuid(uuidList);
		for (VehicleConfig vehicleConfig : list) {
			ConfigRes re = new ConfigRes();
			BeanUtils.copyProperties(vehicleConfig, re);
			reslist.add(re);
		}
		return reslist;
	}

	@Override
	public void logoRenameToJpg() {

		File dir = new File("D:/合格LOEO/");
		String todir = "D:/pinpai_logo/";
		String ossPath = "https://dl-car.oss-cn-beijing.aliyuncs.com/other/pinpai_logo/";

		File[] logos = dir.listFiles();
		for (File file : logos) {
			String nameAll = file.getName();
			String name = StringUtils.trimWhitespace(nameAll.substring(0, nameAll.lastIndexOf(".")));
			String ext = StringUtils.trimWhitespace(nameAll.substring(nameAll.lastIndexOf(".") + 1));

			VehicleConfig query = new VehicleConfig();
			query.setConfigName(name);
			query.setSts(0);
			query.setConfigType("2");
			List<VehicleConfig> dblist = vehicleConfigMapper.select(query);
			if (dblist != null && dblist.size() == 1) {
				VehicleConfig vc = dblist.get(0);
				vc.setLogoUrl(ossPath + vc.getUuid() + "." + ext);

				try {
					FileUtils.copyFile(file, new File(todir + vc.getUuid() + "." + ext));
					FileUtils.deleteQuietly(file);
					vehicleConfigMapper.updateByPrimaryKey(vc);
				} catch (IOException e) {
				}
			}

		}
	}
}
