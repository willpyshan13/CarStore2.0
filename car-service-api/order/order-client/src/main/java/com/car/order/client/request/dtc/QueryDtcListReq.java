package com.car.order.client.request.dtc;

import com.car.common.req.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/17
 */
@Data
@ApiModel(value = "QueryDtcListReq", description = "查询dtc故障列表请求信息，接收参数VO")
public class QueryDtcListReq extends PageReq {

	@ApiModelProperty(value = "dtc故障代码_前缀", name = "dtcCode")
	private String dtcCode;
	@ApiModelProperty(value = "dtc故障代码_中缀", name = "dtcCode2")
	private String dtcCode2;
	@ApiModelProperty(value = "dtc故障代码_后缀", name = "dtcCode3")
	private String dtcCode3;

	@ApiModelProperty(value = "审核状态:0 待审核 ;1 审核通过; 2 审核驳回", name = "dtcCheckSts")
	private Integer dtcCheckSts;

	@ApiModelProperty(value = "用户ID", name = "userId")
	private String userId;

	@ApiModelProperty(value = "品牌", name = "dtcBrandUuid")
	private String dtcBrandUuid;

}
