package com.car.account.web.controller.user;

import com.car.account.client.request.user.UpdateUserImgReq;
import com.car.account.client.response.account.UserInfoRes;
import com.car.account.web.service.user.UserService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/10
 */

@Slf4j
@Api(value = "UserController", tags = "用户管理")
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/updateUserPhotoImg")
    @ApiOperation(value = "修改用户头像信息", nickname = "updateUserPhotoImg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "用户管理", operType = OperEnum.ADD, operDesc = "修改用户头像信息")
    public ResultRes<String> updateUserPhotoImg(@RequestBody @Valid UpdateUserImgReq req){
        return userService.updateUserPhotoImg(req);
    }

    @PostMapping(value = "/queryUserInfo")
    @ApiOperation(value = "用户信息", nickname = "queryUserInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "用户管理", operType = OperEnum.ADD, operDesc = "用户信息")
    public ResultRes<UserInfoRes> queryUserInfo(){
        return ResultRes.success(userService.queryUserInfo());
    }

    @PostMapping(value = "/getUserInfoRes")
    @ApiOperation(value = "用户信息", nickname = "getUserInfoRes")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "用户管理", operType = OperEnum.ADD, operDesc = "用户信息")
    public ResultRes<UserInfoRes> getUserInfoRes(String userUuid, Integer userType){
        return ResultRes.success(userService.getUserInfoRes(userUuid,userType));
    }

    /**
     *
     * @param userUuid
     * @return 1车主2技师3店铺
     */
    @PostMapping(value = "/getUserType")
    @ApiOperation(value = "根据UUID判断用户类型", nickname = "getUserInfoRes")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "用户管理", operType = OperEnum.ADD, operDesc = "根据UUID判断用户类型")
    public ResultRes<Integer> getUserType(String userUuid){
        Integer i=userService.getUserType(userUuid);

        return ResultRes.success(i);
    }


}
