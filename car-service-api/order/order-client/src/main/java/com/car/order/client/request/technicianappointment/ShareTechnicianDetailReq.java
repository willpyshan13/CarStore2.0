package com.car.order.client.request.technicianappointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 预约技师订单入参
 *
 * @author zhoujian
 * @PACKAGE_NAME: com.car.order.client.request.technicianappointment
 * @NAME: ShareTechnicianOrderReq
 * @DATE: 2021/3/4 21:22
 */
@Data
@ApiModel(value = "ShareTechnicianOrderReq", description = "预约技师订单入参")
public class ShareTechnicianDetailReq {


    @ApiModelProperty(value = "共享技师订单uuid", name = "sceneOrderUuid")
    @NotBlank(message = "请输入现场订单uuid")
    private String sceneOrderUuid;

    @ApiModelProperty(value = "描述", name = "describe")
    private String describe;

    @ApiModelProperty(value = "维修总结", name = "repairSummary")
    private String repairSummary;

    @ApiModelProperty(value = "图片", name = "imageList")
    private List<String> imageList;

    @ApiModelProperty(value = "类型1上门，2完成维修", name = "type")
    private Integer type;

}
