package com.car.account.web.controller.coupon;


import com.car.account.client.request.coupon.AddCouponReq;
import com.car.account.client.request.coupon.QueryCouponReq;
import com.car.account.client.request.curing.AddCuringReq;
import com.car.account.client.request.curing.QueryCuringListReq;
import com.car.account.client.response.coupon.QueryCouponRes;
import com.car.account.web.service.coupon.CouponService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(value = "CouponController", tags = "卡券接口--后台")
@RequestMapping(value = "/coupon", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class CouponController  {
    @Autowired
    private CouponService couponService;

    @PostMapping(value = "/addCoupon")
    @ApiOperation(value = "新增卡券", nickname = "addCoupon")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "卡券管理", operType = OperEnum.ADD, operDesc = "新增卡券")
    public ResultRes<String> addCoupon(@RequestBody @Valid AddCouponReq req){
        return couponService.addCoupon(req);
    }

    @PostMapping(value = "/queryCouponList")
    @ApiOperation(value = "查询卡券管理信息列表", nickname = "queryCouponList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "卡券管理", operType = OperEnum.SELECT, operDesc = "查询养护管理（爱车讲堂）信息列表")
    public PageRes<List<QueryCouponRes>> queryCouponList(@RequestBody QueryCouponReq req){
        return couponService.queryCouponList(req);
    }

    @GetMapping(value = "/queryCouponInfo/{uuid}")
    @ApiOperation(value = "查询卡券管理信息详情", nickname = "queryCuringInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "卡券管理", operType = OperEnum.SELECT, operDesc = "查询卡券管理信息详情")
    public ResultRes<QueryCouponRes> queryCouponInfo(@PathVariable("uuid") String uuid){
        return couponService.queryCouponInfo(uuid);
    }

    @PutMapping(value = "/updateCouponInfo/{uuid}")
    @ApiOperation(value = "修改卡券管理信息", nickname = "updateCuringInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "卡券管理", operType = OperEnum.UPDATE, operDesc = "修改卡券管理信息")
    public ResultRes<String> updateCouponInfo( @RequestBody AddCouponReq req){
        return couponService.updateCouponInfo(req);
    }


    @DeleteMapping(value = "/deleteCouponInfo/{uuid}")
    @ApiOperation(value = "删除卡券管理信息", nickname = "deleteCuringInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "卡券管理", operType = OperEnum.UPDATE, operDesc = "删除卡券管理信息")
    public ResultRes<String> deleteCouponInfo(@PathVariable("uuid")String uuid){
        return couponService.deleteCouponInfo(uuid);
    }

}