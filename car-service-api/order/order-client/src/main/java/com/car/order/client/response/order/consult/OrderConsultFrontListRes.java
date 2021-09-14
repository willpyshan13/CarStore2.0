package com.car.order.client.response.order.consult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/31
 */
@Data
@ApiModel(value="OrderConsultFrontListRes",description="查询付费咨询订单列表信息VO")
public class OrderConsultFrontListRes {

    @ApiModelProperty(value = "uuid",name = "uuid")
    private String uuid;

    @ApiModelProperty(value = "咨询标题",name = "title")
    private String title;

    @ApiModelProperty(value = "咨询描述", name = "consultDesc")
    private String consultDesc;

    @ApiModelProperty(value = "订单开始时间",name = "createdTime")
    private String createdTime;

    @ApiModelProperty(value = "订单类型 0 订单点评  1 咨询 2 回答 3 案例 4 旁听",name = "orderType")
    private Integer orderType;

    @ApiModelProperty(value = "订单金额",name = "orderAmount")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "支付方式 0 微信支付 1 支付宝支付",name = "payType")
    private Integer payType;

    @ApiModelProperty(value = "订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败",name = "orderSts")
    private Integer orderSts;

    @ApiModelProperty(value = "答复状态 0 未答复 1 已答复",name = "answerSts")
    private Integer answerSts;

    @ApiModelProperty(value = "回答审核状态 0 待审核 1 审核通过 2 审核驳回",name = "answer_check_sts")
    private Integer answerCheckSts;
    
    @ApiModelProperty(value = "咨询审核状态 0 待审核 1 审核通过 2 审核驳回",name = "consult_check_sts")
    private Integer consultCheckSts;

    @ApiModelProperty(value = "图片地址", name = "imgUrl")
    private List<String>  imgUrl;

    @ApiModelProperty(value = "图片类型 0 咨询 1 回答", name = "imgType")
    private String imgType;

    @ApiModelProperty(value = "咨询ID", name = "consultUuid")
    private String  consultUuid;

    @ApiModelProperty(value = "车辆品牌名称",name = "vehicleBrand")
    private String vehicleBrandName;

    @ApiModelProperty(value = "车型类型名称",name = "vehicleModel")
    private String vehicleModelName;

    @ApiModelProperty(value = "服务状态： 0未服务 1已服务",name = "vehicleModel")
    private Byte serviceSts;

}
