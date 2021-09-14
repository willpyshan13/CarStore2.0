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
@ApiModel(value="OrderCaseInfoListRes",description="案例订单列表信息VO")
public class OrderCaseInfoListRes {

    @ApiModelProperty(value = "uuid",name = "uuid")
    private String uuid;

    @ApiModelProperty(value = "案例UUID",name = "caseUuid")
    private String caseUuid;

    @ApiModelProperty(value = "订单编号",name = "orderNum")
    private String orderNum;

    @ApiModelProperty(value = "订单开始时间",name = "createdTime")
    private String createdTime;

    @ApiModelProperty(value = "技师姓名",name = "technicianName")
    private String technicianName;

    @ApiModelProperty(value = "技师手机号",name = "technicianMobile")
    private String technicianMobile;

    @ApiModelProperty(value = "订单金额",name = "orderAmount")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "车主姓名",name = "carOwnerName")
    private String carOwnerName;

    @ApiModelProperty(value = "车主手机号",name = "carOwnerMobile")
    private String carOwnerMobile;

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

    @ApiModelProperty(value = "购买次数",name = "purchaseNumber")
    private Long purchaseNumber;

    @ApiModelProperty(value = "车友邦技能等级标签(0=普通,1=专家)",name = "cybAuth")
    private Byte cybAuth;

    @ApiModelProperty(value = "工龄",name = "workingYear")
    private Integer workingYear;

    @ApiModelProperty(value = "店铺类型,对应字典表 uuid",name = "storeType")
    private String storeType;

    @ApiModelProperty(value = "省份", name = "addressProvince")
    private String addressProvince;

    @ApiModelProperty(value = "评分", name = "score")
    private Integer score=5;

    @ApiModelProperty(value = "owneruuid类型 1:车主，2：技师，3:店铺", name = "technicianType")
    private Byte technicianType;
}
