package com.car.account.client.response.groupbuy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.car.account.client.response.goods.GoodsRes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "团购VO")
public class GroupbuyRes {

	@ApiModelProperty("uuid")
	private String uuid;

	@ApiModelProperty("售价")
	private BigDecimal price;

	@ApiModelProperty("原价")
	private BigDecimal sourcePrice;

	@ApiModelProperty("成团数量")
	private Integer userNum;

	@ApiModelProperty("团购开始时间")
	private Date startTime;

	@ApiModelProperty("团购结束时间")
	private Date endTime;

	@ApiModelProperty("当前参与人数")
	private Integer participateNum;

	@ApiModelProperty("所属人id")
	private String userUuid;
	@ApiModelProperty("商品所属店铺")
	private String storeUuid;

	@ApiModelProperty("状态：0=待开始;1=进行中;2=已结束")
	private Integer groupSts;

	@ApiModelProperty("成团时间")
	private Date intoGroupTime;

	@ApiModelProperty("配送方式 0快递,1到店服务,2上门服务")
	private Integer receiveMethod;

	@ApiModelProperty("平台服务费")
	private BigDecimal platformServiceMoney;

	@ApiModelProperty("平台补贴")
	private BigDecimal platformSubsidy;

	@ApiModelProperty("商品集合")
	private List<GoodsRes> goodsRes;

	@ApiModelProperty("店铺名字")
	private String storeName;

	@ApiModelProperty("店铺评分")
	private String storeScore;
	
	@ApiModelProperty("店铺类型")
	private String storeType;
}
