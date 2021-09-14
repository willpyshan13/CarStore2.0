package com.car.account.web.service.banner.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.car.account.client.request.banner.BannerReq;
import com.car.account.client.request.banner.QueryBannerListReq;
import com.car.account.client.response.banner.BannerRes;
import com.car.account.web.mapper.banner.BannerMapper;
import com.car.account.web.model.banner.Banner;
import com.car.account.web.service.banner.BannerService;
import com.car.common.enums.ResEnum;
import com.car.common.enums.StsEnum;
import com.car.common.exception.BusinessException;
import com.car.common.res.PageRes;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerMapper bannerMapper;

	@Override
	public BannerRes addBanner(BannerReq params) {
		Banner data = new Banner();
		BeanUtils.copyProperties(params, data);

		String userName = org.springframework.util.StringUtils.isEmpty(TokenHelper.getUserName())
				? TokenHelper.getLoginToken().getUserMobile()
				: TokenHelper.getUserName();

		data.setUuid(UuidUtils.getUuid());
		data.setSts(StsEnum.ACTIVE.getValue());
		data.setCreatedTime(new Date());
		data.setCreatedBy(userName);

		int insert = bannerMapper.insert(data);
		if (0 == insert) {
			log.error("新增地址失败>>>params:{}", JSON.toJSONString(params));
			throw new BusinessException(ResEnum.DB_ERROR);
		}

		BannerRes res = new BannerRes();
		BeanUtils.copyProperties(data, res);
		return res;

	}

	@Override
	public void disableBanner(String uuid) {

		Banner data = new Banner();
		data.setUuid(uuid);
		data.setSts(StsEnum.INVALID.getValue());
		data.setLastUpdatedTime(new Date());
		data.setLastUpdatedBy(TokenHelper.getUserName());
		bannerMapper.updateByPrimaryKeySelective(data);

	}

	@Override
	public BannerRes updateBanner(BannerReq params) {

		Banner data = new Banner();
		BeanUtils.copyProperties(params, data);
		data.setLastUpdatedTime(new Date());
		data.setLastUpdatedBy(TokenHelper.getUserName());
		bannerMapper.updateByPrimaryKeySelective(data);

		BannerRes res = new BannerRes();
		BeanUtils.copyProperties(bannerMapper.selectByPrimaryKey(data.getUuid()), res);
		return res;
	}

	@Override
	public PageRes<List<BannerRes>> queryBannerList(QueryBannerListReq param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<BannerRes> bannerResList = bannerMapper.queryBannerList(param);
		PageInfo<BannerRes> pageInfo = new PageInfo<>(bannerResList);
		return PageRes.success(bannerResList, pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getPages());
	}

	@Override
	public BannerRes queryBannerDetail(String uuid) {
		BannerRes res = new BannerRes();
		BeanUtils.copyProperties(bannerMapper.selectByPrimaryKey(uuid), res);
		return res;
	}

}
