package com.car.order.client.request.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/27
 */
@Data
@ApiModel(value = "addSceneOrderReq", description = "新增现场下单订单VO")
public class AddSceneOrderReq {

    @ApiModelProperty(value = "dtc故障uuid", name = "dtcUuid")
    private String dtcUuid;

    @ApiModelProperty(value = "品牌uuid", name = "brandUuid")
    @NotBlank(message = "请选择车辆品牌！")
    private String brandUuid;

    @ApiModelProperty(value = "车型uuid", name = "carModelUuid")
    @NotBlank(message = "请选择车型！")
    private String carModelUuid;

    @ApiModelProperty(value = "车款", name = "carStyle")
    @NotBlank(message = "请选择车款！")
    private String carStyle;

    @ApiModelProperty(value = "VIN码", name = "vinCode")
    private String vinCode;

    @ApiModelProperty(value = "变速器一级uuid(对应字典uuid)", name = "transmissionOneLevelUuid")
    @NotBlank(message = "请选择变速器一级！")
    private String transmissionOneLevelUuid;

    @ApiModelProperty(value = "变速器二级（对应字典uuid）", name = "transmissionTwoLevelUuid")
    @NotBlank(message = "请选择变速器二级！")
    private String transmissionTwoLevelUuid;

    @ApiModelProperty(value = "发动机排量uuid(对应字典uuid)", name = "engineDisplacementUuid")
    @NotBlank(message = "请选择发动机排量！")
    private String engineDisplacementUuid;

    @ApiModelProperty(value = "驱动方式Uuid(对应字典uuid)", name = "drivingModeUuid")
    @NotBlank(message = "请选择驱动方式Uuid！")
    private String drivingModeUuid;

    @ApiModelProperty(value = "增压系统uuid(对应字典uuid)", name = "boosterSystemUuid")
    @NotBlank(message = "请选择增压系统！")
    private String boosterSystemUuid;

    @ApiModelProperty(value = "保修状态0保修，1不保修", name = "warrantySts")
    private Integer warrantySts;

    @ApiModelProperty(value = "其他状态", name = "otherSts")
    private String otherSts;

    @ApiModelProperty(value = "故障描述", name = "faultDesc")
    @NotBlank(message = "请输入故障描述！")
    private String faultDesc;

    @ApiModelProperty(value = "维修类型uuid（对应字典表uuid）", name = "repairTypeUuid")
    @NotBlank(message = "请选择维修类型！")
    private String repairTypeUuid;

    @ApiModelProperty(value = "已检过程", name = "alreadyInspect")
    @NotBlank(message = "请输入已检过程！")
    private String alreadyInspect;

    @ApiModelProperty(value = "DTC故障code", name = "dtcCode")
    private String dtcCode;

    @ApiModelProperty(value = "基本上门费用Uuid", name = "basicDoorAmountUuid")
    @NotBlank(message = "请选择基本上门费用！")
    private String basicDoorAmountUuid;


    @ApiModelProperty(value = "平台订单服务费Uuid", name = "orderServiceAmountUuid")
    @NotBlank(message = "请输入平台订单服务费！")
    private String orderServiceAmountUuid;

    @ApiModelProperty(value = "总费用", name = "totalAmount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "详细地址", name = "detailedAddr")
    private String detailedAddr;

    @NotBlank(message = "请选择城市！")
    @ApiModelProperty(value = "城市",name = "addressCity")
    private String addressCity;

    @NotBlank(message = "请选择省份！")
    @ApiModelProperty(value = "省份",name = "addressProvince")
    private String addressProvince;

    @ApiModelProperty(value = "县镇",name = "addressCounty")
    private String addressCounty;

    @ApiModelProperty(value = "dtc图片", name = "dtcImageList")
    private List<String> dtcImageList;

    @ApiModelProperty(value = "故障描述图片", name = "dtcImageList")
    private List<String> faultDescImageList;

    @ApiModelProperty(value = "服务时间", name = "dtcImageList")
    private String serviceDate ;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度", name = "longitude")
    private Float longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度", name = "latitude")
    private Float latitude;
}
