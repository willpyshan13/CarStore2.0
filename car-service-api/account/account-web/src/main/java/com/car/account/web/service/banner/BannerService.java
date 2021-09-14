package com.car.account.web.service.banner;

import java.util.List;

import com.car.account.client.request.banner.BannerReq;
import com.car.account.client.request.banner.QueryBannerListReq;
import com.car.account.client.response.banner.BannerRes;
import com.car.common.res.PageRes;

public interface BannerService {
	BannerRes addBanner(BannerReq params);

	void disableBanner(String uuid);

	BannerRes updateBanner(BannerReq params);

	PageRes<List<BannerRes>> queryBannerList(QueryBannerListReq queryBannerListReq);

	BannerRes queryBannerDetail(String uuid);
}
