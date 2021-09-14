package com.car.account.web.service.coupon;

import com.car.account.client.request.coupon.AddCouponReq;
import com.car.account.client.request.coupon.QueryCouponReq;
import com.car.account.client.response.coupon.QueryCouponRes;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;

import java.util.List;

/**
 * @program: car-service
 * @description:
 * @author: niushuaixiang
 * @create: 2021-07-23 16:37
 */
public interface CouponService {

    /**
     * 新增卡券信息
     * @param req
     * @return
     */
    ResultRes<String> addCoupon(AddCouponReq req);

    /**
     * 查询卡券信息
     * @param req
     * @return
     */
    PageRes<List<QueryCouponRes>> queryCouponList(QueryCouponReq req);

    /**
     * 查询卡券详情
     * @param uuid
     * @return
     */
    ResultRes<QueryCouponRes> queryCouponInfo(String uuid);

    /**
     * 修改卡券信息
     * @param req
     * @param uuid
     * @return
     */
    ResultRes<String> updateCouponInfo( AddCouponReq req);


    /**
     * 删除卡券信息
     * @param uuid
     * @return
     */
    ResultRes<String> deleteCouponInfo(String uuid);
}