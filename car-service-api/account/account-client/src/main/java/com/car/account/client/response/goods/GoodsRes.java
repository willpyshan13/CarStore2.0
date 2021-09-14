package com.car.account.client.response.goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.car.account.client.response.goods.sub.GoodsDetailRes;
import com.car.account.client.response.goods.sub.GoodsImgRes;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangyp
 * @date 2021/1/16 16:31
 */
@Data
@ApiModel
public class GoodsRes {

	@ApiModelProperty(value = "uuid", name = "uuid")
	private String uuid;

	@ApiModelProperty(value = "商品名称", name = "goodsName")
	private String goodsName;

	@ApiModelProperty(value = "店铺名称", name = "storeName")
	private String storeName;

	@ApiModelProperty(value = "店铺主键", name = "storeName")
	private String storeUuid;

	@ApiModelProperty("发布人uuid")
	private String storeUserUuid;

	@ApiModelProperty(value = "工时费", name = "manHourCost")
	private BigDecimal manHourCost;

	@ApiModelProperty(value = "材料费", name = "materialsExpenses")
	private BigDecimal materialsExpenses;

	@ApiModelProperty(value = "平台补贴", name = "platformSubsidy")
	private BigDecimal platformSubsidy;

	@ApiModelProperty(value = "库存", name = "surplusNum")
	private Integer surplusNum;

	@ApiModelProperty(value = "销量", name = "salesNum")
	private Integer salesNum;

	@ApiModelProperty(value = "销售状态:0 库存 1 在售", name = "sellSts")
	private Integer sellSts;

	@ApiModelProperty(value = "创建时间", name = "createdTime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createdTime;

	@ApiModelProperty(value = "图片列表", name = "imgList")
	private List<GoodsImgRes> imgList;

	@ApiModelProperty(value = "物料列表", name = "detailList")
	private List<GoodsDetailRes> detailList;

	@ApiModelProperty(value = "售价")
	private BigDecimal amt;

	@ApiModelProperty(value = "原价")
	private BigDecimal sourceAmt;

	@ApiModelProperty(value = "商品描述", name = "goodsDescribe")
	private String goodsDescribe;

	@ApiModelProperty(value = "一级分类", name = "levelOne")
	private String levelOne;
	@ApiModelProperty(value = "一级分类uuid", name = "levelOneUuid")
	private String levelOneUuid;
	@ApiModelProperty(value = "二级分类", name = "levelTwo")
	private String levelTwo;
	@ApiModelProperty(value = "二级分类uuid", name = "levelTwoUuid")
	private String levelTwoUuid;
	@ApiModelProperty(value = "三级类型uuid", name = "goodsType")
	private String goodsType;
	@ApiModelProperty(value = "三级类型名称", name = "goodsTypeUuid")
	private String goodsTypeName;

	@ApiModelProperty(value = "商品评分", name = "score")
	private Float score;
	@ApiModelProperty(value = "商品评论数量", name = "commentNum")
	private Integer commentNum;

	@ApiModelProperty(value = "平台服务费", name = "platformServiceMoney")
	private BigDecimal platformServiceMoney;

	@ApiModelProperty(value = "车辆品牌uuid", name = "vehicleBrand")
	private String vehicleBrand;

	@ApiModelProperty(value = "车型类型uuid", name = "vehicleModel")
	private String vehicleModel;

	/**
	 * 
	 */
	@ApiModelProperty("年款uuid")
	private String vehicleYear;

	/**
	 *
	 */
	@ApiModelProperty("排量uuid")
	private String vehicleCapacity;

	@ApiModelProperty(value = "车辆品牌", name = "vehicleBrand")
	private String vehicleBrandName;

	@ApiModelProperty(value = "车型类型", name = "vehicleModel")
	private String vehicleModelName;

	/**
	 * 
	 */
	@ApiModelProperty("年款:名字")
	private String vehicleYearName;

	/**
	 *
	 */
	@ApiModelProperty("排量:名字")
	private String vehicleCapacityName;

	@ApiModelProperty(value = "轮胎编号", name = "tyreNo")
	private String tyreNo;

	@ApiModelProperty(value = "配送方式 0快递,1到店服务,2上门服务", name = "receiveMethod")
	private Integer receiveMethod;

	@ApiModelProperty("审核状态：0待审核；1审核通过；2审核驳回")
	private Integer checkSts;

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

	@ApiModelProperty("上门服务费")
	private BigDecimal visitFee;

	/**
	 *
	 */
	@ApiModelProperty(" 购买须知")
	private String notes;

	/**
	 * 
	 */
	@ApiModelProperty("使用时长")
	private Integer useDuration;
	/**
	 * 
	 */
	@ApiModelProperty("纬度")
	private Float latitude;
	/**
	 * 
	 */
	@ApiModelProperty("经度")
	private Float longitude;
	/**
	 *
	 */
	@ApiModelProperty(" 地址文本描述")
	private String addr;
	/**
	 * 
	 */
	@ApiModelProperty("工位累计使用年限")
	private Integer yearsUse;
	/**
	 *
	 */
	@ApiModelProperty("定期维护:0=否；1=是")
	private Integer maintainSts;
	/**
	 *
	 */
	@ApiModelProperty(" 最近维护时间")
	private Date maintainDate;
	/**
	 * 
	 */
	@ApiModelProperty("维护方法:0=自行；1=第三方")
	private Integer maintainFn;
}
