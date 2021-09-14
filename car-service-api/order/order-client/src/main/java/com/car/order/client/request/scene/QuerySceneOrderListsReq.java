package com.car.order.client.request.scene;

import com.car.common.req.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/3/5
 */
@Data
@ApiModel(value = "QuerySceneOrderListsReq", description = "查询现场订单列表详情VO")
public class QuerySceneOrderListsReq extends PageReq {

    @ApiModelProperty(value = "订单状态 0：待抢单  1:待支付 2：待上门,3:提交方案,4:待付款,5:服务中 6:待确认,7:完成,8:退款中,9:退款成功,10:退款失败,", name = "orderSts")
    private Integer orderSts;

}
