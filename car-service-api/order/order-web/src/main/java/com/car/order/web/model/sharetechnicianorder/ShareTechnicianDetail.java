package com.car.order.web.model.sharetechnicianorder;

import com.car.common.datasource.model.BaseModelInfo;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 现场订单技师相关实体类
 * @since jdk1.8
 */
@Data
@Table(name = "share_technician_order")
public class ShareTechnicianDetail extends BaseModelInfo {


    /**
     * 订单uuid
     */
     private String orderUuid ;

    /**
     * 技师uuid
     */
     private String technicianUuid ;

    /**
     * 技师姓名
     */
     private String technicianName ;

    /**
     * 技师手机号
     */
     private String technicianMobile ;

    /**
     * 描述
     */
     private String desc ;

    /**
     * 已检过程
     */
     private String alreadyInspect ;

    /**
     * DTC故障code
     */
     private String dtcCode ;

    /**
     * 维修总结
     */
     private String repairSummary ;

    /**
     * 故障是否解决 0解决，1未解决
     */
     private Integer faultSolve ;


}