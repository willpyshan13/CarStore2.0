package com.car.order.client.request.technicianappointment;

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
@ApiModel(value = "SceneOrderConfirmReq", description = "确认现场订单VO")
public class ShareOrderConfirmReq {

    @ApiModelProperty(value = "现场订单uuid", name = "sceneOrderUuid")
    @NotBlank(message = "请输入现场订单uuid")
    private String shareOrderUuid;

    @ApiModelProperty(value = "类型1上门，2完成维修", name = "type")
    private Integer type;


}
