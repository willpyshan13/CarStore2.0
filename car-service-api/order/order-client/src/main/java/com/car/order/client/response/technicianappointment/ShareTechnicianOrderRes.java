package com.car.order.client.response.technicianappointment;

import com.car.common.res.BaseRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预约技师订单出参
 *
 * @author zhoujian
 * @PACKAGE_NAME: com.car.order.client.request.technicianappointment
 * @NAME: ShareTechnicianOrderRes
 * @DATE: 2021/3/4 21:22
 */
@Data
@ApiModel(value = "ShareTechnicianOrderRes", description = "预约技师订单出参")
public class ShareTechnicianOrderRes {

    @ApiModelProperty(value = "数据记录UUID")
    private String uuid;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    /**
     * 车主UUID
     */
    @ApiModelProperty(value = "车主UUID")
    private String ownerUuid;

    /**
     * 技师UUID
     */
    @ApiModelProperty(value = "技师UUID")
    private String technicianUuid;

    /**
     * 预约地点
     */
    @ApiModelProperty(value = "预约地点")
    private String appointmentAddress;

    /**
     * 预约时间
     */
    @ApiModelProperty(value = "预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date appointmentTime;

    /**
     * 品牌UUID
     */
    @ApiModelProperty(value = "品牌UUID")
    private String brandUuid;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 车型UUID
     */
    @ApiModelProperty(value = "车型UUID")
    private String modelUuid;

    /**
     * 车型名称
     */
    @ApiModelProperty(value = "车型名称")
    private String modelName;

    /**
     * 故障描述
     */
    @ApiModelProperty(value = "故障描述")
    private String faultDescription;

    /**
     * 订单预约状态
     * 1：待付款
     * 2：待接单
     * 3：待服务
     * 4：已完成
     * 5：退款中
     * 6：已退款
     */
    @ApiModelProperty(value = "订单状态 0：待抢单  1:待支付 2：待上门,3:提交方案,4:待付款,5:服务中 6:待确认,7:完成,8:退款中,9:退款成功,10:退款失败,11 已取消12, 已拒绝")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单预约状态名称")
    private String orderStatusName;

    @ApiModelProperty(value = "支付方式 0：微信 1：支付宝")
    private Integer payType;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private BigDecimal payNum;

    /**
     * 平台服务费用
     */
    @ApiModelProperty(value = "平台服务费用")
    private BigDecimal platformMoney;

    /**
     * 技师预约费用
     */
    @ApiModelProperty(value = "技师预约费用")
    private BigDecimal reservationMoney;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String orderDesc;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "订单完成时间/取消时间/拒绝时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdatedTime;

}
