package com.car.order.client.request.technicianappointment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/27
 */
@Data
@ApiModel(value = "AddShareOrderServiceReq", description = "共享技师服务订单VO")
public class AddShareOrderServiceReq {


    @ApiModelProperty(value = "现场订单uuid", name = "sceneOrderUuid")
    @NotBlank(message = "请输入现场订单uuid")
    private String shareOrderUuid;

    @ApiModelProperty(value = "故障描述", name = "faultDesc")
    private String faultDesc;

    @ApiModelProperty(value = "解决方案", name = "solution")
    private String solution;

    @ApiModelProperty(value = "检查数据", name = "solution")
    private String checkData;

    @NotNull(message = "请输入基本检查费用！")
    @ApiModelProperty(value = "基本检查费用", name = "basicInspectAmount")
    private BigDecimal basicInspectAmount;

    @NotNull(message = "请输入修复费用！")
    @ApiModelProperty(value = "修复费", name = "repairAmount")
    private BigDecimal repairAmount;

    @NotNull(message = "请输入其他费用费！")
    @ApiModelProperty(value = "其他费用费", name = "otherAmount")
    private BigDecimal otherAmount;

    @ApiModelProperty(value = "平台订单服务费Uuid", name = "orderServiceAmountUuid")
    private String orderServiceAmountUuid;

    @ApiModelProperty(value = "图片/视频", name = "imageList")
    private List<String> imageList;

    @ApiModelProperty(value = "总费用", name = "totalAmount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "优惠券uuid", name = "couponUuid")
    private String couponUuid;
}
