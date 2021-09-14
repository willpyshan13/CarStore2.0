package com.car.order.client.response.order.instance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhouz
 * @date 2020/12/31
 */
@Data
@ApiModel(value="OrderCaseFrontListRes",description="前端-案例订单列表信息VO")
public class OrderCaseFrontListRes {

    @ApiModelProperty(value = "uuid",name = "uuid")
    private String uuid;

    @ApiModelProperty(value = "订单编号",name = "orderNum")
    private String orderNum;

    @ApiModelProperty(value = "订单开始时间",name = "createdTime")
    private String createdTime;

    @ApiModelProperty(value = "订单金额",name = "orderAmount")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "支付方式 0 微信支付 1 支付宝支付",name = "payType")
    private Integer payType;

    @ApiModelProperty(value = "订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败",name = "orderSts")
    private Integer orderSts;

    @ApiModelProperty(value = "案例名称",name = "caseName")
    private String caseName;

    @ApiModelProperty(value = "案例资源地址",name = "caseImgUrl")
    private String caseImgUrl;

    @ApiModelProperty(value = "案例数量",name = "caseNum")
    private Integer caseNum;

    @ApiModelProperty(value = "故障现象",name = "faultDesc")
    private String faultDesc;

    @ApiModelProperty(value = "诊断思路和过程",name = "ideaProcess")
    private String ideaProcess;

    @ApiModelProperty(value = "结论总结",name = "summary")
    private String summary;

    @ApiModelProperty(value = "车主姓名",name = "carOwnerName")
    private String carOwnerName;

    @ApiModelProperty(value = "技师姓名",name = "technicianName")
    private String technicianName;

    @ApiModelProperty(value = "级别",name = "cybAuth")
    private String cybAuth;

    @ApiModelProperty(value = "评分",name = "rank")
    private String score;

    @ApiModelProperty(value = "owneruuid类型 1:车主，2：技师，3:店铺",name = "techniciantype")
    private Byte  techniciantype;

    @ApiModelProperty(value = "技师uuid",name = "technicianUuid")
    private String technicianUuid;

    @ApiModelProperty(value = "技师名称",name = "technicianUuid")
    private String userName;

    @ApiModelProperty(value = "购买次数",name = "purchaseNumber")
    private Long purchaseNumber;

    @ApiModelProperty(value = "案例UUID",name = "purchaseNumber")
    private String caseUuid;
}
