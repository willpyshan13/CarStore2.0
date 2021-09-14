package com.car.account.client.request.goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.car.account.client.request.goods.sub.GoodsDetailReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhouz
 * @date 2020/12/22
 */
@Data
@ApiModel(value = "UpdateGoodsReq", description = "修改商品请求VO")
public class UpdateGoodsReq {

	@NotBlank(message = "[UpdateGoodsReq] uuid is required！")
	@ApiModelProperty(value = "uuid", name = "uuid")
	private String uuid;

	@ApiModelProperty(value = "商品名称", name = "goodsName")
	private String goodsName;

	@ApiModelProperty(value = "店铺uuid", name = "storeUuid")
	private String storeUuid;

	@ApiModelProperty(value = "三级分类", name = "goodsType")
	private String goodsType;

	@ApiModelProperty(value = "一级分类", name = "parentType")
	private String parentType;

	@ApiModelProperty(value = "二级分类", name = "parentType")
	private String subType;

	@ApiModelProperty(value = "工时费", name = "manHourCost")
	private BigDecimal manHourCost;

	@ApiModelProperty(value = "材料费", name = "materialsExpenses")
	private BigDecimal materialsExpenses;

	@ApiModelProperty(value = "库存", name = "surplusNum")
	private Integer surplusNum;

	@ApiModelProperty(value = "商品图片", name = "goodsImgListReq")
	private List<GoodsImgReq> goodsImgListReq;

	@ApiModelProperty(value = "销售状态:0 下架 1上架", name = "sellSts")
	@Max(value = 1, message = "销售状态数值不能大于1")
	@Min(value = 0, message = "销售状态数值不能小于0")
	private Integer sellSts;

	@ApiModelProperty(value = "描述", name = "goodsDescribe")
	private String goodsDescribe;

	List<GoodsDetailReq> detailList;

	@ApiModelProperty(value = "平台服务费", name = "platformServiceMoney")
	private BigDecimal platformServiceMoney;

	@ApiModelProperty(value = "平台补贴", name = "platformSubsidy")
	private BigDecimal platformSubsidy;

	@ApiModelProperty(value = "车辆品牌", name = "vehicleBrand")
	private String vehicleBrand;

	@ApiModelProperty(value = "车型类型", name = "vehicleModel")
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

	@ApiModelProperty(value = "轮胎编号", name = "tyreNo")
	private String tyreNo;

	@ApiModelProperty("审核状态：0待审核；1审核通过；2审核驳回")
	private Integer checkSts;

	@ApiModelProperty(value = "配送方式 0快递,1到店服务,2上门服务", name = "receiveMethod")
	private Integer receiveMethod;
	@ApiModelProperty(" 购买须知")
	private String notes;

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
