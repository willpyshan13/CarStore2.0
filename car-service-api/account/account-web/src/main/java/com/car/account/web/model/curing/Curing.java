package com.car.account.web.model.curing;

import javax.persistence.Column;
import javax.persistence.Table;

import com.car.common.datasource.model.BaseModelInfo;

import lombok.Data;

/**
 * 
 * 
 * @author cjw
 * @email ${email}
 * @date 2021-05-30 16:33:46
 */
@Data
@Table(name = "curing")
public class Curing extends BaseModelInfo {

	/**
	 * 养护标题
	 */
	@Column(name = "title")
	private String title;
	/**
	 * 所属系统（对应字典所属系统uuid）
	 */
	@Column(name = "belonging_System_Uuid")
	private String belongingSystemUuid;
	/**
	 * 车辆品牌（对应车辆品牌uuid）
	 */
	@Column(name = "vehicle_Brand_Uuid")
	private String vehicleBrandUuid;
	/**
	 * 车型类型（对应车辆类型uuid）
	 */
	@Column(name = "vehicle_Model_Uuid")
	private String vehicleModelUuid;
	/**
	 * 封面图片
	 */
	@Column(name = "cover_images_url")
	private String coverImagesUrl;
	/**
	 * 养护内容
	 */
	@Column(name = "curing_Content")
	private String curingContent;
	/**
	 *  0未上架 1已上架
	 */
	@Column(name = "check_Sts")
	private Integer checkSts;

}
