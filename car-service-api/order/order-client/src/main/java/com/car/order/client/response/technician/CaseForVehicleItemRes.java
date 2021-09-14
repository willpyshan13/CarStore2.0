package com.car.order.client.response.technician;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhouz
 * @date 2021/1/23
 */
@Data
@ApiModel
public class CaseForVehicleItemRes {


    @ApiModelProperty(value = "案例唯一标识",name = "uuid")
    private String uuid;

    @ApiModelProperty(value = "案例唯名称",name = "title")
    private String title;

    @ApiModelProperty(value = "技师uuid",name = "technicianUuid")
    private String technicianUuid;

    @ApiModelProperty(value = "技师名称",name = "technicianName")
    private String technicianName;

    @ApiModelProperty(value = "案例金额",name = "amt")
    private BigDecimal amt;

    @ApiModelProperty(value = "工龄",name = "workingYear")
    private Integer workingYear;

    @ApiModelProperty(value = "案例图片",name = "imgUrl")
    private String imgUrl;

    @ApiModelProperty(value = "级别",name = "rank")
    private String cybAuth;

    @ApiModelProperty(value = "级别名称",name = "rank")
    private String cybAuthName;

    @ApiModelProperty(value = "评分",name = "score")
    private String score;

    @ApiModelProperty(value = "owneruuid类型 1:车主，2：技师，3:店铺",name = "carOwnertype")
    private Integer technicianType;

    @ApiModelProperty(value = "购买次数",name = "purchaseNumber")
    private Long purchaseNumber;

    @ApiModelProperty(value = "省",name = "addressProvince")
    private String addressProvinceName;

    @ApiModelProperty(value = "市",name = "addressCity")
    private String addressCityName;

    @ApiModelProperty(value = "区",name = "addressCounty")
    private String addressCountyName;


}
