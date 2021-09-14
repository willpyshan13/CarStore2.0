package com.car.account.client.request.goods;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.car.common.req.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangyp
 * @date 2021/1/17 0:40
 */
@Data
@ApiModel
public class QueryStoreGoodsListReq extends PageReq {

	@ApiModelProperty("销售状态:0库存(下架)1在售(上架)")
	@Max(value = 1)
	@Min(value = 0)
	private Integer sellSts;

	@ApiModelProperty("审核状态：0待审核；1审核通过；2审核驳回")
	private List<Integer> checkSts;
}
