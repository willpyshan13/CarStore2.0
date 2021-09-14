package com.car.order.client.response.scene;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 现场订单信息VO
 * @since jdk1.8
 */
@Data
@ApiModel(description = "现场订单信息VO")
public class SceneOrderServiceRes  {

     @ApiModelProperty("主键")
     private String uuid ;


     @ApiModelProperty("订单编号")
     private String orderNum ;


     @ApiModelProperty("故障描述")
     private String faultDesc ;


     @ApiModelProperty("解决方案")
     private String solution ;


     @ApiModelProperty("基本检查费用")
     private BigDecimal basicInspectAmount ;


     @ApiModelProperty("基本检查费用Uuid")
     private String basicInspectAmountUuid ;


     @ApiModelProperty("相关线路检查费用")
     private BigDecimal lineInspectAmount ;


     @ApiModelProperty("相关线路检查费用Uuid")
     private String lineInspectAmountUuid ;


     @ApiModelProperty("诊断仪使用费")
     private BigDecimal diagnosisInstrumentAmount ;


     @ApiModelProperty("诊断仪使用费Uuid")
     private String diagnosisInstrumentAmountUuid ;


     @ApiModelProperty("车辆钣金修复费用")
     private BigDecimal carSheetMetalAmount ;


     @ApiModelProperty("车辆钣金修复费用Uuid")
     private String carSheetMetalAmountUuid ;


     @ApiModelProperty("车辆油漆修复费用")
     private BigDecimal carPaintRepairAmount ;


     @ApiModelProperty("车辆油漆修复费用Uuid")
     private String carPaintRepairAmountUuid ;


     @ApiModelProperty("其他费用费")
     private BigDecimal otherAmount ;


     @ApiModelProperty("其他费用费Uuid")
     private String otherAmountUuid ;


     @ApiModelProperty("平台订单服务费")
     private BigDecimal orderServiceAmount ;


     @ApiModelProperty("平台订单服务费Uuid")
     private String orderServiceAmountUuid ;


     @ApiModelProperty("总支付费用")
     private BigDecimal totalAmount ;


     @ApiModelProperty("支付方式 0 微信支付 1 支付宝支付")
     private Byte payType ;


     @ApiModelProperty("订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败 6：已完成")
     private Byte orderSts ;


     @ApiModelProperty("状态 0有效1无效")
     private Byte sts ;


     @ApiModelProperty("提交方案时间")
     private Date createdTime ;


     @ApiModelProperty("支付时间")
     private Date lastUpdatedTime ;


     @ApiModelProperty("创建人")
     private String createdBy ;


     @ApiModelProperty("修改人")
     private String lastUpdatedBy ;

     

}