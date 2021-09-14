package com.car.account.client.request.curing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/5/30
 */
@Data
@ApiModel(value = "AddCuringReq", description = "新增养护管理请求VO")
public class AddCuringReq {

    @ApiModelProperty(value = "养护标题", name = "title")
    private String title;

    @ApiModelProperty(value = "所属系统（对应字典所属系统uuid）", name = "belongingSystem")
    private String belongingSystemUuid;

    @ApiModelProperty(value = "车辆品牌（对应车辆品牌uuid）", name = "vehicleBrand")
    private String vehicleBrandUuid;

    @ApiModelProperty(value = "车型类型（对应车辆类型uuid）", name = "vehicleModel")
    private String vehicleModelUuid;

    @ApiModelProperty(value = "封面图片", name = "coverImagesUrl")
    private String coverImagesUrl;

    @ApiModelProperty(value = "养护内容", name = "curingContent")
    private String curingContent;

    @ApiModelProperty(value = "0未上架 1已上架", name = "checkSts")
    private Integer checkSts;
}
