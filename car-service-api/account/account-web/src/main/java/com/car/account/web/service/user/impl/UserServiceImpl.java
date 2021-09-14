package com.car.account.web.service.user.impl;

import com.car.account.client.request.user.UpdateUserImgReq;
import com.car.account.client.response.account.UserInfoRes;
import com.car.account.web.mapper.store.StoreMapper;
import com.car.account.web.mapper.store.StoreUserMapper;
import com.car.account.web.mapper.technician.TechnicianMapper;
import com.car.account.web.mapper.vehicle.VehicleUserMapper;
import com.car.account.web.model.store.Store;
import com.car.account.web.model.store.StoreUser;
import com.car.account.web.model.technician.Technician;
import com.car.account.web.model.vehicle.Vehicle;
import com.car.account.web.model.vehicle.VehicleUser;
import com.car.account.web.service.dict.SysDictService;
import com.car.account.web.service.user.UserService;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.enums.UserTypeEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.ResultRes;
import com.car.common.utils.TokenHelper;
import com.car.system.client.response.dict.DictionaryRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/10
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TechnicianMapper technicianMapper;

    @Autowired
    private VehicleUserMapper vehicleUserMapper;

    @Autowired
    private StoreUserMapper storeUserMapper;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private StoreMapper storeMapper;
    /**
     * 修改用户头像
     * @param req
     * @return
     */
    @Override
    public ResultRes<String> updateUserPhotoImg(UpdateUserImgReq req) {
        //用户uuid
        String userUuid = TokenHelper.getUserUuid();
        //用户类型
        Integer userType = TokenHelper.getUserType();

        int updateUserPhotoImgNum = 0;
        if (UserTypeEnum.technician.getType().equals(userType)) {
            //修改技师头像信息
            Technician technician = new Technician();
            technician.setUuid(userUuid);
            technician.setPhotoImgUrl(req.getUserPhotoImg());
            updateUserPhotoImgNum =  technicianMapper.updateByPrimaryKeySelective(technician);

        } else if (UserTypeEnum.store.getType().equals(userType)) {
            //修改店铺头像url
            StoreUser storeUser = new StoreUser();
            storeUser.setUuid(userUuid);
            storeUser.setPhotoImgUrl(req.getUserPhotoImg());
            updateUserPhotoImgNum = storeUserMapper.updateByPrimaryKeySelective(storeUser);

        } else {
            //修改车主头像url
            VehicleUser vehicleUser = new VehicleUser();
            vehicleUser.setUuid(userUuid);
            vehicleUser.setPhotoImgUrl(req.getUserPhotoImg());
            updateUserPhotoImgNum =  vehicleUserMapper.updateByPrimaryKeySelective(vehicleUser);
        }
        if (updateUserPhotoImgNum <= 0) {
            log.error("修改用户头像失败");
            throw new BusinessException(ResEnum.UPDATE_DB_ERROR);
        }
        return ResultRes.success(userUuid);
    }

    /**
     * 查询用户头像信息
     * @return
     */
    @Override
    public UserInfoRes queryUserInfo() {
        //用户uuid
        String userUuid = TokenHelper.getUserUuid();
        //用户类型
        Integer userType = TokenHelper.getUserType();
        //用户头像url

        return getUserInfoRes(userUuid, userType);
    }

    @Override
    public UserInfoRes getUserInfoRes(String userUuid, Integer userType) {
        UserInfoRes userInfoRes = new UserInfoRes();
        if (UserTypeEnum.technician.getType().equals(userType)) {
            //修改技师头像信息
            Technician technicianSelect = new Technician();
            technicianSelect.setUuid(userUuid);
            technicianSelect.setSts(StsEnum.ACTIVE.getValue());
            Technician technician = technicianMapper.selectOne(technicianSelect);
            if (null != technician) {

                userInfoRes.setName(technician.getUserName());
                userInfoRes.setHeadImage(technician.getPhotoImgUrl());
                if(null!=technician.getScore()){
                    userInfoRes.setScore(technician.getScore());
                }
                DictionaryRes dictionaryRes = sysDictService.querySysDict(technician.getTechnologyType());
                userInfoRes.setType(dictionaryRes.getLableDesc());
            }
        } else if (UserTypeEnum.store.getType().equals(userType)) {
            //修改店铺头像url
            StoreUser storeUserSelect = new StoreUser();
            storeUserSelect.setUuid(userUuid);
            storeUserSelect.setSts(StsEnum.ACTIVE.getValue());
            StoreUser storeUser = storeUserMapper.selectOne(storeUserSelect);
            if (null != storeUser) {
                Store store = storeMapper.selectByPrimaryKey(storeUser.getStoreUuid());
                userInfoRes.setName(storeUser.getUserName());
                userInfoRes.setHeadImage(storeUser.getPhotoImgUrl());
                if(null!=store.getScore()){
                    userInfoRes.setScore(store.getScore());
                }
                DictionaryRes dictionaryRes = sysDictService.querySysDict(store.getStoreType());
                userInfoRes.setType(dictionaryRes.getLableDesc());
            }
        } else {
            //修改车主头像url
            VehicleUser vehicleUserSelect = new VehicleUser();
            vehicleUserSelect.setUuid(userUuid);
            vehicleUserSelect.setSts(StsEnum.ACTIVE.getValue());
            VehicleUser vehicleUser = vehicleUserMapper.selectOne(vehicleUserSelect);
            if (null != vehicleUser) {
                userInfoRes.setName(vehicleUser.getPhotoImgUrl());
                userInfoRes.setHeadImage(vehicleUser.getUserName());
            }
        }
        userInfoRes.setUserType(userType);

        return userInfoRes;
    }

    @Override
    public Integer getUserType(String userUuid) {
        Technician technician=new Technician();
        technician.setUuid(userUuid);
        Technician technician1 = technicianMapper.selectByPrimaryKey(technician);
        if(technician1==null){
            StoreUser storeUser=new StoreUser();
            storeUser.setUuid(userUuid);
            StoreUser storeUser1 = storeUserMapper.selectByPrimaryKey(technician);
            if(storeUser1==null){
                Vehicle vehicle=new Vehicle();
                vehicle.setUuid(userUuid);
                VehicleUser vehicleUser = vehicleUserMapper.selectByPrimaryKey(vehicle);
                if(vehicleUser!=null){
                    return UserTypeEnum.vehicle.getType();
                }
            }else{
                return  UserTypeEnum.store.getType();
            }
        }else{
            return UserTypeEnum.technician.getType();
        }
        return null;
    }
}
