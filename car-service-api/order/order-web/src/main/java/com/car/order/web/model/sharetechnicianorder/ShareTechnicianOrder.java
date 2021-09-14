package com.car.order.web.model.sharetechnicianorder;

import com.car.common.datasource.model.BaseModelInfo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhoujian
 * @PACKAGE_NAME: com.car.order.web.model.sharetechnicianorder
 * @NAME: ShareTechnicianOrder
 * @DATE: 2021/3/4 23:20
 */
@Data
@Table(name = "share_technician_order")
public class ShareTechnicianOrder extends BaseModelInfo {

    /**
     * 订单号
     */
    @Column(name = "order_num")
    private String orderNum;

    /**
     * 车主UUID
     */
    @Column(name = "owner_uuid")
    private String ownerUuid;

    /**
     * 技师UUID
     */
    @Column(name = "technician_uuid")
    private String technicianUuid;

    /**
     * 预约地点
     */
    @Column(name = "appointment_address")
    private String appointmentAddress;

    /**
     * 预约时间
     */
    @Column(name = "appointment_time")
    private Date appointmentTime;

    /**
     * 品牌
     */
    @Column(name = "brand_uuid")
    private String brandUuid;

    /**
     * 车型
     */
    @Column(name = "model_uuid")
    private String modelUuid;

    /**
     * 故障描述
     */
    @Column(name = "fault_description")
    private String faultDescription;

    /**
     * 订单预约状态
     * 1：等待接单
     * 2：接单成功
     * 3：订单取消
     * 4：订单完成
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 支付方式 0：微信 1：支付宝
     */
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 支付方式 0：微信 1：支付宝
     */
    @Column(name = "pay_date")
    private String payDate;

    /**
     * 平台服务费用
     */
    @Column(name = "platform_money")
    private BigDecimal platformMoney;

    /**
     * 平台服务费用uuid
     */
    @Column(name = "platform_money_uuid")
    private BigDecimal platformMoneyUuid;

    /**
     * 基本上门费用
     */
    @Column(name = "basic_door_amount")
    private BigDecimal basicDoorAmount;

    /**
     * 基本上门费用Uuid
     */
    @Column(name = "basic_door_amount_uuid")
    private BigDecimal basicDoorAmountUuid;

    /**
     * 总金额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 备注信息
     */
    @Column(name = "order_desc")
    private String orderDesc;

    /**
     * 关联技师id
     */
    @Column(name = "share_technician_detail_uuid")
    private String shareTechnicianDetailUuid;



    /**
     * 查询优惠券uuid
     */
    @Column(name = "coupon_uuid")
    private String couponUuid;

    /**
     * 优惠券金额
     */
    @Column(name = "coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 接单时间
     */
    @Column(name = "order_taking_date")
    private Date orderTakingDate;

    /**
     * 确认上门时间
     */
    @Column(name = "grab_update_time")
    private Date grabUpdateTime;
}
