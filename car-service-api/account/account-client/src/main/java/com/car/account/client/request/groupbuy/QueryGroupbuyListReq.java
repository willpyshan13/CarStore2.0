package com.car.account.client.request.groupbuy;

import java.util.List;

import com.car.common.req.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页查询团购条件")
public class QueryGroupbuyListReq extends PageReq {

	@ApiModelProperty("状态：0=待开始;1=进行中;2=已结束")
	private List<Integer> groupSts;

	@ApiModelProperty("所属人id")
	private String userUuid;

	@ApiModelProperty("商品所属店铺")
	private String storeUuid;

	@ApiModelProperty("配送方式 0快递,1到店服务,2上门服务")
	private List<Integer> receiveMethod;

	@ApiModelProperty("商品所在城市名称")
	private String cityName;

	@ApiModelProperty("商品所在城市uuid")
	private String storeCityUuid;

	@ApiModelProperty("商品所在区县uuid")
	private String storeCountyUuid;

	@ApiModelProperty("车辆品牌")
	private String vehicleBrand;

	@ApiModelProperty("一级分类")
	private String parentType;

	@ApiModelProperty("二级分类")
	private String subType;

	@ApiModelProperty("店铺类型：101=加盟店,102=4S店,103=合作伙伴,104=旗舰店")
	private List<String> storeType;

	@ApiModelProperty("评分排序方式:null=不以此排序;0=正序(此时null值会排在最前);1=倒序")
	private Integer storeScoreDesc;
	@ApiModelProperty("距离排序方式(使用此排序方式必须传入latitude，longitude):null=不以此排序;0=正序(近);1=倒序(远)")
	private Integer distanceDesc;

	@ApiModelProperty("最大距离（单位：米。使用此参数必须传入latitude，longitude）")
	private Integer maxDistance;

	@ApiModelProperty("纬度")
	private Float latitude;
	@ApiModelProperty("经度")
	private Float longitude;

}
