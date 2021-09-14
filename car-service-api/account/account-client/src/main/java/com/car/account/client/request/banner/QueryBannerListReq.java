package com.car.account.client.request.banner;

import com.car.common.req.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页查询banner条件")
public class QueryBannerListReq extends PageReq {

	@ApiModelProperty("名称like查询")
	private String cnameLike;

	/**
	 * 
	 */
	@ApiModelProperty("显示端：0商家端；1客户端")
	private Integer bannerType;

	/**
	 * 
	 */
	@ApiModelProperty("状态：0下架；1上架")
	private Integer bannerSts;
}
