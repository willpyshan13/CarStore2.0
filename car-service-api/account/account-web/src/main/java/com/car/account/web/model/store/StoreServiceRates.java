package com.car.account.web.model.store;

import com.car.common.datasource.model.BaseModelInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺-平台服务-收费比率-补贴比率实体类
 * @since jdk1.8
 */
@Data
@Table(name = "store_service_rates")
public class StoreServiceRates extends BaseModelInfo {


    /**
     * 店铺主键
     */
    @Column(name = "store_uuid")
     private String storeUuid ;

    /**
     * 服务类型 ：1洗车   2:维修   3：美容  4:保养 5.其他  6.工位
     */
    @Column(name = "service_type")
     private Byte serviceType ;

    /**
     * 是否启用
     */
    @Column(name = "status")
    private Byte status ;


    /**
     * 服务费收费比率
     */
    @Column(name = "service_rates")
     private BigDecimal serviceRates ;

    /**
     * goods_parent表uuid
     */
    @Column(name = "goods_parent_uuid")
     private String goodsParentUuid ;

    /**
     * 补贴比率
     */
    @Column(name = "subsidies_rates")
     private BigDecimal subsidiesRates ;


}