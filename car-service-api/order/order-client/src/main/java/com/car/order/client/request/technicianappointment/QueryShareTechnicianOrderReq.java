package com.car.order.client.request.technicianappointment;

import com.car.common.req.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhoujian
 * @PACKAGE_NAME: com.car.order.client.request.technicianappointment
 * @NAME: QueryShareTechnicianOrderReq
 * @DATE: 2021/3/5 0:12
 */
@Data
@ApiModel(value = "QueryShareTechnicianOrderReq", description = "查询预约订单入参")
public class QueryShareTechnicianOrderReq extends PageReq {



    /**
     * 订单预约状态
     */
    @ApiModelProperty(value = "预约技师查询状态 1 待接单，2已接单（）")
    private Integer type ;

    /**
     * 订单预约状态
     */
    @ApiModelProperty(value = "预约技师查询状态 1进行中，2.已完成 null，全部")
    private Integer status ;

}
