package com.car.account.client.response.banner;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Banner VO")
public class BannerRes {

	@ApiModelProperty("uuid")
	private String uuid;

	@ApiModelProperty("创建时间")
	private Date createdTime;

	@ApiModelProperty("最近一次更新时间")
	private Date lastUpdatedTime;

	/**
	 * 
	 */
	@ApiModelProperty("名称")
	private String cname;

	/**
	 * 
	 */
	@ApiModelProperty("显示端：0商家端；1客户端")
	private Integer bannerType;

	/**
	 * 
	 */
	@ApiModelProperty("链接")
	private String url;

	/**
	 * 
	 */
	@ApiModelProperty("状态：0下架；1上架")
	private Integer bannerSts;

	/**
	 * 
	 */
	@ApiModelProperty("主图")
	private String img;
}
