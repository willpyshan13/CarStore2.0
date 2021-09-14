package com.car.order.client.request.order.goods;

import java.util.List;

import com.car.common.req.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhouz
 * @date 2020/12/30
 */
@ApiModel(value = "QueryOrderGoodsListReq", description = "查询商品订单列表请求VO对象")
@Data
public class QueryOrderGoodsListReq extends PageReq {

	@ApiModelProperty(value = "商品名称", name = "goodsName")
	private String goodsName;

	@ApiModelProperty(value = "开始时间 yyyy-MM-dd", name = "startDate", example = "2020-12-30 21:35:00")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String startDate;

	@ApiModelProperty(value = "结束时间 yyyy-MM-dd", name = "endDate", example = "2020-12-30 21:35:00")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private String endDate;

	@ApiModelProperty(value = "订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败 6：已完成", name = "orderSts")
	private List<Integer> orderSts;

	@ApiModelProperty(value = "售后状态:0 待预约; 1买家申请退款; 2已预约待上门(使用); 3退款中 ;4卖家标记已上门; 5已取消 ;6买家标记已上门; 7卖家标记已完成; 8买家标记已完成", name = "afterSaleSts")
	private List<Integer> afterSaleSts;

	@ApiModelProperty(value = "评价状态 0 未评论  1 已评论 2 好评 3 中评 4 差评", name = "evaluateSts")
	private List<Integer> evaluateSts;

	@ApiModelProperty(value = "订单号", name = "orderNum")
	private String orderNum;

	@ApiModelProperty(value = "联系人", name = "contacts")
	private String contacts;

	@ApiModelProperty(value = "手机号", name = "mobile")
	private String mobile;

	@ApiModelProperty(value = "商铺账户uuid", name = "mobile")
	private String storeUserUuid;

	@ApiModelProperty(value = "下单人uuid", name = "userUuid")
	private String userUuid;
	
	@ApiModelProperty(value = "0=单品订单；1=工位订单", name = "station")
	private Integer station;

	@ApiModelProperty(value = "配送方式 0快递,1到店服务,2上门服务", name = "receiveMethod")
	private List<Integer> receiveMethod;
}
