package com.car.account.web.model.banner;

import javax.persistence.Column;
import javax.persistence.Table;

import com.car.common.datasource.model.BaseModelInfo;

import lombok.Data;

@Data
@Table(name = "banner")
public class Banner extends BaseModelInfo {

	/**
	 * 名称
	 */
	@Column(name = "cname")
	private String cname;

	/**
	 * 显示端：0商家端；1客户端
	 */
	@Column(name = "banner_type")
	private Integer bannerType;

	/**
	 * 链接
	 */
	@Column(name = "url")
	private String url;

	/**
	 * 状态：0下架；1上架
	 */
	@Column(name = "banner_sts")
	private Integer bannerSts;

	/**
	 * 主图
	 */
	@Column(name = "img")
	private String img;
}
