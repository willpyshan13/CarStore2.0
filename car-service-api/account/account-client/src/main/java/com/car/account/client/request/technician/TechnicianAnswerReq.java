package com.car.account.client.request.technician;

import com.car.common.req.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技师问答列表
 * @author zhangyp
 * @date 2021/1/21 1:56
 */
@Data
@ApiModel
public class TechnicianAnswerReq extends PageReq {

    @ApiModelProperty(value = "技术类型",name = "technologyType")
    private String technologyTypeUuid;
}
