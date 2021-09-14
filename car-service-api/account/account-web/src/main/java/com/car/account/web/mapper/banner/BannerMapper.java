package com.car.account.web.mapper.banner;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.car.account.client.request.banner.QueryBannerListReq;
import com.car.account.client.response.banner.BannerRes;
import com.car.account.web.model.banner.Banner;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BannerMapper extends Mapper<Banner> {

	List<BannerRes> queryBannerList(QueryBannerListReq param);

}
