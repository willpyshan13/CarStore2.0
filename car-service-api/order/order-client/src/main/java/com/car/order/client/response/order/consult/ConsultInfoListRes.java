package com.car.order.client.response.order.consult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/31
 */
@Data
@ApiModel(value = "ConsultInfoListRes", description = "咨询列表信息VO")
public class ConsultInfoListRes {

	@ApiModelProperty(value = "订单uuid", name = "uuid")
	private String uuid;

	@ApiModelProperty(value = "咨询标题", name = "title")
	private String title;

	@ApiModelProperty(value = "技师姓名", name = "technicianName")
	private String technicianName;

	@ApiModelProperty(value = "技师头像地址", name = "technicianImgUrl")
	private String technicianImgUrl;

	@ApiModelProperty(value = "订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败", name = "orderSts")
	private Integer orderSts;

	@ApiModelProperty(value = "答复状态 0 未答复 1 已答复", name = "answerSts")
	private Integer answerSts;

	@ApiModelProperty(value = "车辆品牌", name = "vehicleBrand")
	private String vehicleBrand;

	@ApiModelProperty(value = "车辆品牌名称", name = "vehicleBrandName")
	private String vehicleBrandName;

	@ApiModelProperty(value = "车型类型", name = "vehicleModel")
	private String vehicleModel;

	@ApiModelProperty(value = "车型类型名称", name = "vehicleModelName")
	private String vehicleModelName;

	@ApiModelProperty(value = "图片地址", name = "imgs")
	private List<String> imgs;

    @ApiModelProperty(value = "咨询描述",name = "consultDesc")
    private String consultDesc;

	@ApiModelProperty(value = "咨询uuid",name = "consultUuid")
	private String consultUuid;


	@ApiModelProperty(value = "1：已经旁听过  2：未被旁听",name = "imgs")
	private Integer yesOrNo ;

	@ApiModelProperty(value = "购买人uuid",name = "imgs")
	private String carOwnerUuid;

	@ApiModelProperty(value = "咨询金额",name = "consultAmt")
	private BigDecimal consultAmt;
/*
	@ApiModelProperty(value = "咨询描述",name = "consultDesc")
	private String consultDesc;

	@ApiModelProperty(value = "回答描述",name = "answerDesc")
	private String answerDesc;

	@ApiModelProperty(value = "咨询时间",name = "createdTime")
	private Date createdTime;

	@ApiModelProperty(value = "咨询金额",name = "consultAmt")
	private BigDecimal consultAmt;

	@ApiModelProperty(value = "订单uuid",name = "uuid")
	private String orderUuid;

	@ApiModelProperty(value = "车主姓名",name = "carOwnerName")
	private String carOwnerName;*/

}
