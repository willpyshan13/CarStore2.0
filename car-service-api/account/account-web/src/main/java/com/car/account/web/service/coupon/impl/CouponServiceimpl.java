package com.car.account.web.service.coupon.impl;

import com.car.account.client.enums.goods.SellStatusEnum;
import com.car.account.client.request.coupon.AddCouponReq;
import com.car.account.client.request.coupon.QueryCouponReq;
import com.car.account.client.response.coupon.QueryCouponRes;
import com.car.account.client.response.curing.QueryCuringRes;
import com.car.account.web.common.utils.UuidUtils;
import com.car.account.web.mapper.coupon.CouponMapper;
import com.car.account.web.mapper.coupon.CouponSiteMapper;
import com.car.account.web.mapper.coupon.CouponUserMapper;
import com.car.account.web.model.coupon.Coupon;
import com.car.account.web.model.coupon.CouponSite;
import com.car.account.web.model.curing.Curing;
import com.car.account.web.service.coupon.CouponService;
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
public class CouponServiceimpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponSiteMapper couponSiteMapper;

    @Override
    public ResultRes<String> addCoupon(AddCouponReq req) {
        String userName = TokenHelper.getUserName();
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(req, coupon);
        coupon.setUuid(UuidUtils.getUUID());
        coupon.setSts(StsEnum.ACTIVE.getValue());
        coupon.setCouponSts(SellStatusEnum.ON_SALE.getValue());
        coupon.setCreatedTime(new Date());
        coupon.setCreatedBy(userName);
        couponMapper.insert(coupon);
        insertCouponSite(req, userName, coupon);

        return ResultRes.success(coupon.getUuid());
    }

    private void insertCouponSite(AddCouponReq req, String userName, Coupon coupon) {
        CouponSite site = new CouponSite();
        site.setCouponUuid(coupon.getUuid());
        couponSiteMapper.delete(site);
        for (String city : req.getSuitCity()){
            CouponSite couponSite = new CouponSite();
            couponSite.setCouponUuid(coupon.getUuid());
            couponSite.setCreatedBy(userName);
            couponSite.setCreatedTime(new Date());
            couponSite.setSysAreaUuid(city);
            couponSiteMapper.insertSelective(couponSite);
        }
    }

    @Override
    public PageRes<List<QueryCouponRes>> queryCouponList(QueryCouponReq req) {

        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<QueryCouponRes> queryCouponList = couponMapper.queryCouponList(req);

        PageInfo<QueryCouponRes> pageInfo = new PageInfo<>(queryCouponList);
        return PageRes.success(queryCouponList, pageInfo.getPageSize(), (int) pageInfo.getTotal(),
                pageInfo.getPages());
    }

    @Override
    public ResultRes<QueryCouponRes> queryCouponInfo(String uuid) {
        Coupon coupon = couponMapper.selectByPrimaryKey(uuid);
        QueryCouponRes queryCouponRes = new QueryCouponRes();
        BeanUtils.copyProperties(coupon,queryCouponRes);

        List<String> list = couponSiteMapper.selectSite(coupon.getUuid());
        queryCouponRes.setSuitCity(list);
        return ResultRes.success(queryCouponRes);
    }

    @Override
    public ResultRes<String> updateCouponInfo(AddCouponReq req) {
        Coupon coupon = new Coupon();
        String userName = TokenHelper.getUserName();
        BeanUtils.copyProperties(req,coupon);
        coupon.setLastUpdatedBy(userName);
        coupon.setLastUpdatedTime(new Date());
        couponMapper.updateByPrimaryKeySelective(coupon);
        insertCouponSite(req, userName, coupon);
        return ResultRes.success(coupon.getUuid());
    }

    @Override
    public ResultRes<String> deleteCouponInfo(String uuid) {
        Coupon coupon = new Coupon();
        coupon.setUuid(uuid);
        coupon.setSts(StsEnum.INVALID.getValue());
        couponMapper.updateByPrimaryKeySelective(coupon);
        return ResultRes.success();
    }
}