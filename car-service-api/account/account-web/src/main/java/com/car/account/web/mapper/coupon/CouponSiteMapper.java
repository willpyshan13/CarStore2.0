package com.car.account.web.mapper.coupon;


import com.car.account.web.model.coupon.CouponSite;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface CouponSiteMapper extends Mapper<CouponSite> {


    List<String> selectSite(String uuid);
}