package com.car.account.web.controller.coupon;


import com.car.account.client.request.coupon.AddCouponReq;
import com.car.account.client.request.coupon.QueryCouponReq;
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
@Api(value = "CouponController", tags = "卡券接口--前段")
@RequestMapping(value = "/coupon", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class CouponUserController {
    @Autowired
    private CouponService couponService;

    @PostMapping(value = "/sendCoupon")
    @ApiOperation(value = "卡券 ----发券（内部调用）", nickname = "sendCoupon")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "卡券", operType = OperEnum.SELECT, operDesc = "卡券 ----发券（内部调用）")
    public void sendCoupon(){

    }


}