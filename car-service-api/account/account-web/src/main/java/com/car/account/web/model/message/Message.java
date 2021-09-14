package com.car.account.web.model.message;

import com.car.common.datasource.model.BaseModelInfo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "message")
public class Message extends BaseModelInfo {



    /**
     * 内容
     */
    @Column(name = "content")
    private String content ;

    /**
     * 消息类型
     */
    @Column(name = "type")
    private Integer type ;

    /**
     * 是否已读 1：未读  2：已读
     */
    @Column(name = "status")
    private Integer status ;

    /**
     * 技师ID
     */
    @Column(name = "technician_uuid")
    private String technicianUuid;

    /**
     * 订单uuid
     */
    @Column(name = "order_uuid")
    private String orderUuid;

    /**
     * 客户类型
     */
    @Column(name = "client_type")
    private Integer clientType;

}
