package com.car.order.client.response.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 现场订单信息VO
 * @since jdk1.8
 */
@Data
@ApiModel(description = "现场订单信息VO")
public class SceneOrderServiceTwoRes {

     @ApiModelProperty("主键")
     private String uuid ;


     @ApiModelProperty("订单编号")
     private String orderNum ;


     @ApiModelProperty("故障描述")
     private String faultDesc ;


     @ApiModelProperty("解决方案")
     private String solution ;

     @ApiModelProperty(value = "检查数据", name = "solution")
     private String checkData;

     @ApiModelProperty(value = "基本检查费用", name = "basicInspectAmount")
     private BigDecimal basicInspectAmount;

     @ApiModelProperty(value = "修复费", name = "repairAmount")
     private BigDecimal repairAmount;

     @ApiModelProperty(value = "其他费用费", name = "otherAmount")
     private BigDecimal otherAmount;

     @ApiModelProperty(value = "平台订单服务费Uuid", name = "orderServiceAmountUuid")
     private String orderServiceAmountUuid;

     @ApiModelProperty(value = "总费用", name = "totalAmount")
     private BigDecimal totalAmount;

     @ApiModelProperty(value = "检查视频/图片", name = "endImageList")
     private List<String> imageList;

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