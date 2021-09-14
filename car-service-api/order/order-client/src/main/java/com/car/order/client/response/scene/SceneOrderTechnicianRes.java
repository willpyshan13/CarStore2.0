package com.car.order.client.response.scene;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 现场订单技师相关
 */
@Data
@ApiModel(value = "SceneOrderTechnicianRes", description = "现场订单技师相关")
public class SceneOrderTechnicianRes  {

	@ApiModelProperty("主键uuid")
	private String uuid ;


	@ApiModelProperty("订单uuid")
	private String orderUuid ;


	@ApiModelProperty("技师uuid")
	private String technicianUuid ;


	@ApiModelProperty("技师姓名")
	private String technicianName ;


	@ApiModelProperty("技师手机号")
	private String technicianMobile ;


	@ApiModelProperty("描述")
	private String describes ;


	@ApiModelProperty("已检过程")
	private String alreadyInspect ;


	@ApiModelProperty("DTC故障code")
	private String dtcCode ;


	@ApiModelProperty("维修总结")
	private String repairSummary ;


	@ApiModelProperty("故障是否解决 0解决，1未解决")
	private Byte faultSolve ;


	@ApiModelProperty("状态 0有效1无效")
	private Byte sts ;


	@ApiModelProperty("上门时间")
	private Date createdTime ;


	@ApiModelProperty("提交完成时间")
	private Date lastUpdatedTime ;


	@ApiModelProperty("创建人")
	private String createdBy ;


	@ApiModelProperty("修改人")
	private String lastUpdatedBy ;

	@ApiModelProperty(value = "订单完成图片列表", name = "endImageList")
	private List<String> endImageList;

	@ApiModelProperty(value = "上门图片列表", name = "doorImageList")
	private List<String> doorImageList;


}
