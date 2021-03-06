package com.car.account.web.service.vehicle.impl;


import com.car.account.client.enums.vehicle.VehicleUserTypeEnum;
import com.car.account.client.request.vehicle.VehicleReq;
import com.car.account.client.request.vehicle.VehicleUserListReq;
import com.car.account.client.request.vehicle.VehicleUserReq;
import com.car.account.client.response.vehicle.vehicleUser.QueryOrderAmountRes;
import com.car.account.client.response.vehicle.vehicleUser.QueryVehicleUserCountRes;
import com.car.account.client.response.vehicle.vehicleUser.VehicleRes;
import com.car.account.client.response.vehicle.vehicleUser.VehicleUserRes;
import com.car.account.web.common.constants.VehicleConstants;
import com.car.account.web.mapper.vehicle.VehicleMapper;
import com.car.account.web.mapper.vehicle.VehicleUserMapper;
import com.car.account.web.model.vehicle.Vehicle;
import com.car.account.web.model.vehicle.VehicleUser;
import com.car.account.web.service.person.PersonService;
import com.car.account.web.service.vehicle.VehicleService;
import com.car.account.web.service.vehicle.VehicleUserService;
import com.car.common.enums.CheckStatusEnum;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.DateUtil;
import com.car.common.utils.ExcelUtils;
import com.car.common.utils.ExceptionUtils;
import com.car.common.utils.TokenHelper;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author xlj
 */
@Slf4j
@Service
public class VehicleUserServiceImpl implements VehicleUserService {

    @Autowired
    VehicleUserMapper vehicleUserMapper;

    @Autowired
    VehicleMapper vehicleMapper;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    private PersonService personService;

    /**
     * ??????????????????????????????/????????????
     * @return
     */
    @Override
    public ResultRes<QueryVehicleUserCountRes> queryVehicleUserCount() {
        QueryVehicleUserCountRes res = new QueryVehicleUserCountRes();
        //?????????????????????
        VehicleUser user = new VehicleUser();
        user.setSts(StsEnum.ACTIVE.getValue());
        user.setUserType(VehicleUserTypeEnum.REGISTER.getValue());
        res.setRegisterCount(vehicleUserMapper.selectCount(user));
        //???????????????
        user.setUserType(VehicleUserTypeEnum.OWNER.getValue());
        res.setOwnerCount(vehicleUserMapper.selectCount(user));
        return ResultRes.success(res);
    }

    /**
     * ??????????????????
     * @param param
     * @return
     */
    @Override
    public PageRes<List<VehicleUserRes>> queryList(VehicleUserListReq param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<VehicleUser> userList = vehicleUserMapper.queryList(param);
        PageInfo<VehicleUser> pageInfo = new PageInfo<>(userList);
        //????????????
        List<VehicleUserRes> resList = convertToRes(userList);
        return PageRes.success(resList,pageInfo.getPageSize(),(int) pageInfo.getTotal(),pageInfo.getPages());
    }

    /**
     * ?????????????????????????????????
     * @return
     */
    @Override
    public ResultRes<VehicleUserRes> queryDetailByToken() {
        return queryDetail(TokenHelper.getUserUuid());
    }

    /**
     * ??????????????????
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<VehicleUserRes> queryDetail(String uuid) {
        VehicleUser vehicleUser = vehicleUserMapper.selectByPrimaryKey(uuid);
        if(StringUtils.isEmpty(vehicleUser)){
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        VehicleUserRes vehicleUserRes = new VehicleUserRes();
        BeanUtils.copyProperties(vehicleUser,vehicleUserRes);
        //????????????????????????????????????
        List<Vehicle> vehicleList = vehicleMapper.queryListByUser(vehicleUser.getUuid());
        List<VehicleRes> vehicleResList = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            VehicleRes vehicleRes = new VehicleRes();
            BeanUtils.copyProperties(vehicle,vehicleRes);
            vehicleResList.add(vehicleRes);
        }
        vehicleUserRes.setVehicleList(vehicleResList);
        return ResultRes.success(vehicleUserRes);
    }

    /**
     * ??????????????????
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @TxcTransaction
    public ResultRes editVehicleUser(VehicleUserReq param) {
        VehicleUser vehicleUser = vehicleUserMapper.selectByPrimaryKey(param.getUuid());
        if(StringUtils.isEmpty(vehicleUser)){
            throw new BusinessException(ResEnum.NON_EXISTENT);
        }
        //??????????????????????????????
        boolean mobileFlag = checkMobile(param.getUuid(),param.getMobile());
        if(!mobileFlag){
            throw new BusinessException(ResEnum.MOBILE_EXIST_ERROR);
        }
        //??????????????????????????????
        boolean certificateFlag = checkCertificateNum(param.getUuid(),param.getCertificateNum());
        if(!certificateFlag){
            throw new BusinessException(ResEnum.ID_CARD_EXIST_ERROR);
        }
        //????????????????????????????????????????????????????????????????????????token
        if (!(vehicleUser.getMobile().equals(param.getMobile()))) {
            personService.exitLoginByUserUuid(param.getUuid());
        }
        //?????????????????????????????????
        updateVehicleUser(vehicleUser,param);
        //??????????????????????????????
        updateBoundCar(vehicleUser,param);
        return ResultRes.success(vehicleUser.getUuid());
    }

    /**
     * ??????????????????
     * @param exportReq
     * @param response
     */
    @Override
    public void exportVehicleList(VehicleUserListReq exportReq, HttpServletResponse response) {
        try{
            List<VehicleUser> userList = vehicleUserMapper.queryList(exportReq);
            //????????????
            List<VehicleUserRes> resList = convertToRes(userList);
            //??????????????????
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(VehicleConstants.VEHICLE_USER_EXPORT_TEMPLATE);
            //????????????????????????
            List<VehicleUserRes> excelList = ExcelUtils.setFieldValue(resList);
            Workbook wb = new XSSFWorkbook(resourceAsStream);
            Sheet sheet = wb.getSheetAt(0);
            //????????????????????????
            int firstRowIndex = sheet.getFirstRowNum()+2;
            for (int rowIndex = firstRowIndex; rowIndex < excelList.size()+2; rowIndex++) {
                //?????????
                Row rowStyle = (rowIndex % 2) == 0?sheet.getRow(2): sheet.getRow(3);
                //????????????
                CellStyle cellStyle = ExcelUtils.getExcelFormat(rowStyle.getCell(1));
                CellStyle cellStyle1 = ExcelUtils.getExcelFormat(rowStyle.getCell(0));
                Row row = sheet.getRow(rowIndex);
                if(row == null){
                    row = sheet.createRow(rowIndex);
                }
                row.setHeight(rowStyle.getHeight());
                VehicleUserRes exportDto = excelList.get(rowIndex - 2);
                ExcelUtils.setCell(row,cellStyle1,0,rowIndex-1);
                ExcelUtils.setCell(row,cellStyle,1,exportDto.getUserName());
                ExcelUtils.setCell(row,cellStyle,2,exportDto.getMobile());
                ExcelUtils.setCell(row,cellStyle,3,exportDto.getAddressProvinceName());
                ExcelUtils.setCell(row,cellStyle,4,exportDto.getAddressCityName());
                ExcelUtils.setCell(row,cellStyle,5,exportDto.getAddressDetail());
                ExcelUtils.setCell(row,cellStyle,6,exportDto.getVehicleList().size());
                //?????????
                StringBuffer plateNumbers = new StringBuffer();
                StringBuffer vehicleBrandNames = new StringBuffer();
                StringBuffer vehicleModelNames =new StringBuffer();
                StringBuffer licenseRegisterDates = new StringBuffer();
                StringBuffer emissionLevelsNames = new StringBuffer();
                StringBuffer fuelTypeNames = new StringBuffer();
                List<VehicleRes> vehicleResList = exportDto.getVehicleList();
                for (VehicleRes vehicle : vehicleResList) {
                    plateNumbers.append(vehicle.getPlateNumber()).append("/");
                    vehicleBrandNames.append(vehicle.getVehicleBrandName()).append("/");
                    vehicleModelNames.append(vehicle.getVehicleModelName()).append("/");
                    licenseRegisterDates.append(vehicle.getLicenseRegisterDate()).append("/");
                    emissionLevelsNames.append(vehicle.getEmissionLevelsName()).append("/");
                    fuelTypeNames.append(vehicle.getFuelTypeName()).append("/");
                }
                ExcelUtils.setCell(row,cellStyle,7,plateNumbers.toString());
                ExcelUtils.setCell(row,cellStyle,8,vehicleBrandNames.toString());
                ExcelUtils.setCell(row,cellStyle,9,vehicleModelNames.toString());
                ExcelUtils.setCell(row,cellStyle,10,licenseRegisterDates.toString());
                ExcelUtils.setCell(row,cellStyle,11,emissionLevelsNames.toString());
                ExcelUtils.setCell(row,cellStyle,12,fuelTypeNames.toString());
                ExcelUtils.setCell(row,cellStyle,13, DateUtil.dateToStr(exportDto.getCreatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            }
            ExcelUtils.responseWrite(wb,response, VehicleConstants.VEHICLE_USER_EXPORT_TEMPLATE);
        }catch (Exception ex){
            log.error("??????????????????????????????????????????{}", ExceptionUtils.stackTraceToString(ex));
        }
    }

    /**
     * ?????????????????????
     * @param userList
     * @return
     */
    private List<VehicleUserRes> convertToRes(List<VehicleUser> userList){
        List<VehicleUserRes> resList = new ArrayList<>();
        for (VehicleUser user : userList) {
            VehicleUserRes vehicleUserRes = new VehicleUserRes();
            BeanUtils.copyProperties(user,vehicleUserRes);
            //????????????????????????????????????
            List<Vehicle> vehicleList = vehicleMapper.queryListByUser(user.getUuid());
            List<VehicleRes> vehicleResList = new ArrayList<>();
            for (Vehicle vehicle : vehicleList) {
                VehicleRes vehicleRes = new VehicleRes();
                BeanUtils.copyProperties(vehicle,vehicleRes);
                vehicleResList.add(vehicleRes);
            }
            vehicleUserRes.setVehicleList(vehicleResList);
            resList.add(vehicleUserRes);
        }
        return resList;
    }

    /**
     * ??????????????????
     * @param vehicleUser
     * @param param
     */
    private void updateBoundCar(VehicleUser vehicleUser,VehicleUserReq param){
        List<VehicleReq> vehicleList = param.getVehicleList();
        for (VehicleReq vehicleReq : vehicleList ) {
            //??????ID??????????????????
            Vehicle vehicle = vehicleMapper.selectByPrimaryKey(vehicleReq.getUuid());
            if(StringUtils.isEmpty(vehicle)){
                log.error("????????????ID???{}????????????????????????",vehicleReq.getUuid());
                throw new BusinessException(ResEnum.NON_EXISTENT);
            }
            //???????????????????????????????????????????????????????????????????????????
            if(!vehicle.getVehicleUserUuid().equals(vehicleUser.getUuid())){
                log.error("????????????ID???{}???????????????ID:{}?????????????????????",vehicle.getUuid(),vehicleUser.getUuid());
                throw new BusinessException(ResEnum.VEHICLE_CROSS_RIGHTS_ERROR);
            }
            //????????????????????????
            boolean plateNumberFlag = vehicleService.checkPlateNumber(vehicle.getUuid(),vehicleReq.getPlateNumber());
            if(!plateNumberFlag){
                throw new BusinessException(ResEnum.PLATE_NUMBER_EXIST_ERROR);
            }
            //????????????????????????
            this.updateVehicle(vehicle,vehicleReq);
            BeanUtils.copyProperties(param,vehicle);
            vehicleMapper.updateByPrimaryKey(vehicle);
        }
    }

    /**
     * ????????????????????????
     * @param vehicle
     * @param vehicleReq
     */
    private void updateVehicle(Vehicle vehicle, VehicleReq vehicleReq) {
        BeanUtils.copyProperties(vehicleReq,vehicle);
        vehicle.setLastUpdatedBy(TokenHelper.getUserName());
        vehicle.setLastUpdatedTime(new Date());
        vehicleMapper.updateByPrimaryKey(vehicle);
    }

    /**
     * ????????????????????????
     * @param vehicleUser
     * @param param
     */
    private void updateVehicleUser(VehicleUser vehicleUser,VehicleUserReq param){
        vehicleUser.setAccountName(param.getAccountName());
        vehicleUser.setAddressCity(param.getAddressCity());
        vehicleUser.setAddressDetail(param.getAddressDetail());
        vehicleUser.setAddressProvince(param.getAddressProvince());
        vehicleUser.setAlipayAccount(param.getAlipayAccount());
        vehicleUser.setCardNumbers(param.getCardNumbers());
        vehicleUser.setCertificateNum(param.getCertificateNum());
        vehicleUser.setCertificateType(param.getCertificateType());
        vehicleUser.setDepositBank(param.getDepositBank());
        vehicleUser.setMobile(param.getMobile());
        vehicleUser.setSubBranchName(param.getSubBranchName());
        vehicleUser.setUserName(param.getUserName());
        vehicleUser.setLastUpdatedBy(TokenHelper.getUserName());
        vehicleUser.setLastUpdatedTime(new Date());
        vehicleUserMapper.updateByPrimaryKey(vehicleUser);
    }



    /**
     * ??????????????????????????????
     * @param uuid
     * @return
     */
    private boolean checkCertificateNum(String uuid,String certificateNum) {
        //?????????????????????????????????????????????
        VehicleUser search = new VehicleUser();
        search.setCertificateNum(certificateNum);
        search.setSts(StsEnum.ACTIVE.getValue());
        VehicleUser user = vehicleUserMapper.selectOne(search);
        return checkUserIsExist(uuid,user);
    }

    /**
     * ??????????????????????????????
     * @param uuid
     * @return
     */
    private boolean checkMobile(String uuid,String mobile) {
        //?????????????????????????????????????????????
        VehicleUser search = new VehicleUser();
        search.setMobile(mobile);
        search.setSts(StsEnum.ACTIVE.getValue());
        VehicleUser user = vehicleUserMapper.selectOne(search);
        return checkUserIsExist(uuid,user);
    }

    /**
     * ????????????????????????
     * @param uuid
     * @param user
     * @return
     */
    private boolean checkUserIsExist(String uuid,VehicleUser user){
        if(!StringUtils.isEmpty(user)){
            if(StringUtils.isEmpty(uuid)){
                //?????????????????????
                return false;
            }else{
                //??????????????????????????????????????????ID
                if(!user.getUuid().equals(uuid)){
                    //??????????????????
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ????????????????????????????????????
     * @param uuid
     * @return
     */
    @Override
    public ResultRes<QueryOrderAmountRes> queryOrderAmount(String uuid) {
        //TODO ??????????????????????????????????????????->????????????->????????????
        QueryOrderAmountRes res = new QueryOrderAmountRes();
        res.setOrderCount(5);
        res.setPayTotalMoney("7431.52");
        res.setRemainingMoney("52.66");
        return ResultRes.success(res);
    }
}
