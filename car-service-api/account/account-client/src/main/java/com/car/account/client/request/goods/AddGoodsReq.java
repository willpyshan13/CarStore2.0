package com.car.account.client.request.goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.car.account.client.request.goods.sub.GoodsDetailReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhouz
 * @date 2020/12/22
 */
@Data
@ApiModel(value = "AddGoodsReq", description = "新增商品请求VO")
public class AddGoodsReq {

	@NotBlank(message = "请输入商品名称！")
	@ApiModelProperty(value = "商品名称", name = "goodsName")
	private String goodsName;

	@ApiModelProperty(value = "店铺uuid", name = "storeUuid")
	private String storeUuid;

	@NotBlank(message = "请输入商品类型！")
	@ApiModelProperty(value = "三级分类", name = "goodsType")
	private String goodsType;

	@NotBlank(message = "请输入一级分类！")
	@ApiModelProperty(value = "一级分类", name = "parentType")
	private String parentType;

	@NotBlank(message = "请输入二级分类！")
	@ApiModelProperty(value = "二级分类", name = "subType")
	private String subType;

	@Min(value = 0, message = "工时费不合法")
	@ApiModelProperty(value = "工时费", name = "manHourCost")
	private BigDecimal manHourCost;

	@Min(value = 0, message = "快递费不合法")
	@ApiModelProperty(value = "快递费", name = "materialsExpenses")
	private BigDecimal materialsExpenses;

	@NotNull(message = "请输入库存数量！")
	@ApiModelProperty(value = "库存", name = "surplusNum")
	@Min(value = 0, message = "库存数量不能小于0")
	private Integer surplusNum;

	@NotEmpty(message = "请输入商品图片！")
	@ApiModelProperty(value = "商品图片", name = "goodsImgListReq")
	private List<GoodsImgReq> goodsImgListReq;

	@NotNull(message = "请输入销售状态！")
	@ApiModelProperty(value = "销售状态:0 库存 1 在售", name = "sellSts")
	@Max(value = 1, message = "销售状态数值不能大于1")
	@Min(value = 0, message = "销售状态数值不能小于0")
	private Integer sellSts;

	@NotBlank(message = "请输入商品描述！")
	@ApiModelProperty(value = "描述", name = "goodsDescribe")
	private String goodsDescribe;

	@ApiModelProperty(value = "物料明细", name = "detailList")
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
