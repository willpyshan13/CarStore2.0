package com.car.order.client.request.dtc;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value="AddDtcReq",description="DTC新增故障请求VO")
public class dtcType {
    @ApiModelProperty(value = "uuid", name = "dtcCode")
    @NotBlank(message = "uuid")
    private String uuid;

    @ApiModelProperty(value = "dtcType", name = "dtcDefinition")
    @NotBlank(message = "dtc类型")
    private String dtcType;
}
