package com.car.account.client.request.goods;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.car.common.req.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhouz
 * @date 2020/12/22
 */
@Data
@ApiModel(description = "查询商品列表请求VO")
public class QueryGoodsListReq extends PageReq {

	@ApiModelProperty("商品名称")
	private String goodsName;

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

	@ApiModelProperty("商品类型,对应字典表 uuid")
	private String goodsType;

	@ApiModelProperty("店铺名称")
	private String storeName;

	@ApiModelProperty("店铺主键")
	private String storeUuid;

	@ApiModelProperty("店铺类型：101=加盟店,102=4S店,103=合作伙伴,104=旗舰店")
	private List<String> storeType;

	@Min(value = 0, message = "状态取值区间[0,1]")
	@Max(value = 1, message = "状态取值区间[0,1]")
	@ApiModelProperty("销售状态0 下架 ;1上架")
	private Integer sellSts;

	@ApiModelProperty("最大销量")
	private Integer maxSalesNum;

	@ApiModelProperty("最小销量")
	private Integer minSalesNum;

	@ApiModelProperty("最大价格")
	private String maxPrice;

	@ApiModelProperty("最小价格")
	private String minPrice;

	@ApiModelProperty("审核状态：0待审核；1审核通过；2审核驳回")
	private List<Integer> checkSts;

	@ApiModelProperty("补贴额排序方式:null=不以此排序;0=正序(此时null值会排在最前);1=倒序")
	private Integer subsidyDesc;
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

	@ApiModelProperty("最小补贴额度")
	private BigDecimal minPlatformSubsidy;

	/**
	 * 
	 */
	@ApiModelProperty("上午可预约数")
	private Integer amServeNum;
	/**
	 * 
	 */
	@ApiModelProperty("下午可预约数")
	private Integer pmServeNum;
	/**
	 * 
	 */
	@ApiModelProperty("晚间可预约数")
	private Integer nmServeNum;

}
