package com.car.order.client.response.technician;

import io.swagger.annotations.Api;
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
public class CaseForTechnicianItemRes {

    @ApiModelProperty(value = "案例收益唯一标识",name = "uuid")
    private String uuid;

    @ApiModelProperty(value = "案例唯一标识",name = "uuid")
    private String caseUuid;

    @ApiModelProperty(value = "案例名称",name = "title")
    private String title;

    @ApiModelProperty(value = "故障现象",name = "faultDesc")
    private String faultDesc;

    @ApiModelProperty(value = "案例收益",name = "amt")
    private BigDecimal amt;

    @ApiModelProperty(value = "案例销量",name = "num")
    private Integer num;

    @ApiModelProperty(value = "收益类型1维修2案例3问答",name = "profitType")
    private Integer profitType;

    @ApiModelProperty(value = "owneruuid类型 1:车主，2：技师，3:店铺",name = "techniciantype")
    private Byte  techniciantype;

    @ApiModelProperty(value = "技师uuid",name = "technicianUuid")
    private String technicianUuid;

    @ApiModelProperty(value = "技师名称",name = "technicianUuid")
    private String userName;

    @ApiModelProperty(value = "级别",name = "cybAuth")
    private String cybAuth;

    @ApiModelProperty(value = "评分",name = "rank")
    private String score;


    @ApiModelProperty(value = "审核状态,(0:待审核 1:审核通过 2:审核驳回)",name = "checkStatus")
    private String checkStatus;

    @ApiModelProperty(value = "驳回原因",name = "remarks")
    private String remarks;

}
