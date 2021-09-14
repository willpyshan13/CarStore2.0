package com.car.order.client.request.order.consult;

import com.car.common.req.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhouz
 * @date 2020/12/30
 */
@ApiModel(value="QueryOrderConsultFrontListReq",description="查询付费咨询订单列表请求VO对象")
@Data
public class QueryOrderConsultFrontListReq extends PageReq {

    @ApiModelProperty(value = "状态 0 全部 1 待付款 2: 待服务 3:待评价  4:退款/售后",name = "state")
    private Integer state;

    @ApiModelProperty(value = " 向我提问---1/我提问题的----2/已抢答---3",name = "state")
    //我提问题的----2  暂无
    private Integer quizzer;

    @ApiModelProperty(value = "1全部 2进行 3完成",name = "answer_check_sts")
    private Integer answerCheckSts;
}
