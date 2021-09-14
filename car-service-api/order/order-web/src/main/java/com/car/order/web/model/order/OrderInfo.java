package com.car.order.web.model.order;

import com.car.common.datasource.model.BaseModelInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/2/8
 */
@Data
@Table(name = "order_info")
public class OrderInfo extends BaseModelInfo {

    @Column(name = "order_type")
    private Integer orderType;

    @Column(name = "order_uuid")
    private String orderUuid;

    @Column(name = "pay_sts")
    private Integer paySts;

    @Column(name = "evaluate_sts")
    private Integer evaluateSts;

    @Column(name = "drawee_uuid")
    private String draweeUuid;

    @Column(name = "deawee_type")
    private Integer deaweeType;
}
