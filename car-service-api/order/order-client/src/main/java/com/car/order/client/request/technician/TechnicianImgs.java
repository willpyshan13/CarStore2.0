package com.car.order.client.request.technician;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class TechnicianImgs {

    @ApiModelProperty(value="路径",name="url")
    private String url;
    @ApiModelProperty(value="名称",name="fileName")
    private String fileName;
}
