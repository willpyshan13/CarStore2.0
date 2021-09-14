package com.car.order.client.response.technicianappointment;


import com.car.common.res.BaseRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 共享订单服务订单
 * @since jdk1.8
 */
@Data
@ApiModel(value = "ShareTechnicianServiceRes", description = "共享订单服务订单出参")
public class ShareTechnicianServiceRes  {


    @ApiModelProperty(value = "数据记录UUID")
    private String uuid;

    @ApiModelProperty(value = "订单编号")
    private String orderNum;

    /**
     * 故障描述
     */
    @ApiModelProperty(value = "故障描述")
    private String faultDesc;

    /**
     * 解决方案
     */
    @ApiModelProperty(value = "解决方案")
    private String solution;


    @ApiModelProperty(value = "检查数据", name = "solution")
    private String checkData;


    @ApiModelProperty(value = "基本检查费用", name = "basicInspectAmount")
    private BigDecimal basicInspectAmount;


    @ApiModelProperty(value = "修复费", name = "repairAmount")
    private BigDecimal repairAmount;


    @ApiModelProperty(value = "其他费用费", name = "otherAmount")
    private BigDecimal otherAmount;

    /**
     * 平台订单服务费
     */
    @ApiModelProperty(value = "平台订单服务费")
    private BigDecimal orderServiceAmount;

    /**
     * 平台订单服务费Uuid
     */
    @ApiModelProperty(value = " 平台订单服务费Uuid")
    private String orderServiceAmountUuid;

    /**
     * 总支付费用
     */
    @ApiModelProperty(value = "总支付费用")
    private BigDecimal totalAmount;

    /**
     * 支付方式 0 微信支付 1 支付宝支付
     */
    @ApiModelProperty(value = "支付方式 0 微信支付 1 支付宝支付")
    private Integer payType;

    /**
     * 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败 6：已完成
     */
    @ApiModelProperty(value = "订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败 6：已完成")
    private Integer orderSts;

    /**
     * 查询优惠券uuid
     */
    @ApiModelProperty(value = "查询优惠券uuid")
    private String couponUuid;

    /**
     * 优惠券金额
     */
    @ApiModelProperty(value = "优惠券金额")
    private BigDecimal couponAmount;

    /**
     * 是否有效 0:生效 1：无效
     */
    @ApiModelProperty(value = "是否有效 0:生效 1：无效 ")
    private Integer sts;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "提交维修方案时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "支付维修方案时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdatedTime;

    @ApiModelProperty(value = "检查视频/图片", name = "endImageList")
    private List<String> imageList;
}