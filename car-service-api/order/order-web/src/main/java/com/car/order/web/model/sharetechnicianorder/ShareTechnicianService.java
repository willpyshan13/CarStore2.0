package com.car.order.web.model.sharetechnicianorder;

import com.car.common.datasource.model.BaseModelInfo;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 共享订单信息实体类
 * @since jdk1.8
 */
@Data
@Table(name = "share_technician_service")
public class ShareTechnicianService extends BaseModelInfo {

    /**
     * 订单编号
     */
     private String orderNum ;

    /**
     * 故障描述
     */
     private String faultDesc ;

    /**
     * 解决方案
     */
     private String solution ;

    /**
     * 检查数据
     */
    private String checkData;

    /**
     * 基本检查费用
     */
    private BigDecimal basicInspectAmount;
    /**
     * 其他费用费
     */
    private BigDecimal otherAmount;

    /**
     * 修复费用
     */
    private BigDecimal repairAmount;

    /**
     * 平台订单服务费
     */
     private BigDecimal orderServiceAmount ;

    /**
     * 平台订单服务费Uuid
     */
     private String orderServiceAmountUuid ;

    /**
     * 总支付费用
     */
     private BigDecimal totalAmount ;

    /**
     * 支付方式 0 微信支付 1 支付宝支付
     */
     private Integer payType ;

    /**
     * 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中  4:退款成功  5:退款失败 6：已完成
     */
     private Integer orderSts ;

    /**
     * 查询优惠券uuid
     */
    private String couponUuid;

    /**
     * 优惠券金额
     */
    private BigDecimal couponAmount;




}