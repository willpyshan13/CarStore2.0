package com.car.account.web.mapper.coupon;

import com.car.account.client.request.coupon.QueryCouponReq;
import com.car.account.client.response.coupon.QueryCouponRes;
import com.car.account.web.model.coupon.Coupon;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CouponMapper extends Mapper<Coupon>{


    List<QueryCouponRes> queryCouponList(QueryCouponReq req);
}