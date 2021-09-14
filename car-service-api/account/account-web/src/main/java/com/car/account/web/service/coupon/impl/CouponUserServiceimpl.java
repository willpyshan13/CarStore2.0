package com.car.account.web.service.coupon.impl;

import com.car.account.client.enums.goods.SellStatusEnum;
import com.car.account.client.request.coupon.AddCouponReq;
import com.car.account.client.request.coupon.QueryCouponReq;
import com.car.account.client.response.coupon.QueryCouponRes;
import com.car.account.web.common.utils.UuidUtils;
import com.car.account.web.mapper.coupon.CouponMapper;
import com.car.account.web.model.coupon.Coupon;
import com.car.account.web.service.coupon.CouponService;
import com.car.account.web.service.coupon.CouponUserService;
import com.car.common.enums.StsEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import com.car.common.utils.TokenHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: car-service
 * @description:
 * @author: niushuaixiang
 * @create: 2021-07-23 16:37
 */
@Service
public class CouponUserServiceimpl implements CouponUserService {


}