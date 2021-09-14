package com.car.order.client.request.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/27
 */
@Data
@ApiModel(value = "SceneOrderStatisticsReq", description = "统计Bo")
public class SceneOrderStatisticsReq {

    @ApiModelProperty(value = "年", name = "year")
    private Integer year;

    @ApiModelProperty(value = "月", name = "month")
    private  Integer month;


}
