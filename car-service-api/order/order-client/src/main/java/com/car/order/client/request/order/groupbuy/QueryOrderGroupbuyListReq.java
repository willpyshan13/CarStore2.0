package com.car.order.client.request.order.groupbuy;

import java.util.List;

import com.car.common.req.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class QueryOrderGroupbuyListReq extends PageReq {

	@ApiModelProperty("主键")
	private String uuid;

	@ApiModelProperty("订单状态 0 待支付; 1 已支付; 2 已取消; 3退款中; 4退款成功;  5退款失败; 6已完成;7已成团")
	private List<Integer> orderSts;

	@ApiModelProperty("售后状态:0 待预约;  1买家申请退款 ; 2已预约待上门(使用);  3退款中;  4卖家标记已上门;  5已取消;  6买家标记已上门 ; 7卖家标记已完成;  8买家标记已完成")
	private List<Integer> afterSaleSts;

	@ApiModelProperty("退款状态:0 同意退款; 1 拒绝退款; 2 取消退款")
	private List<Integer> refundSts;

	@ApiModelProperty("团购uuid")
	private String groupbuyUuid;

	@ApiModelProperty("购买人")
	private String userUuid;

	@ApiModelProperty("订单编号")
	private String orderNum;

	@ApiModelProperty("支付方式 0 微信支付 1 支付宝支付")
	private List<Integer> payType;

	@ApiModelProperty("团配送方式 0快递,1到店服务,2上门服务")
	private List<Integer> receiveMethod;

	@ApiModelProperty("团状态：0=待开始;1=进行中;2=已结束")
	private List<Integer> groupSts;

	@ApiModelProperty("团的创建人id：服务提供方Id")
	private String groupUserUuid;

	@ApiModelProperty("服务提供方店铺uuid")
	private String storeUuid;

	@ApiModelProperty("店铺名称模糊条件")
	private String storeNameLike;
}
