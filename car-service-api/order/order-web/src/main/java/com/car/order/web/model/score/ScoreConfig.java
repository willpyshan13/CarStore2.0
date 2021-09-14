package com.car.order.web.model.score;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类
 * @since jdk1.8
 */
@Data
@Table(name = "score_config")
public class ScoreConfig implements Serializable {
    /**
     * 
     */
     private Integer id ;

    /**
     * 
     */
     private BigDecimal var1 ;

    /**
     * 
     */
     private BigDecimal var2 ;

    /**
     * 
     */
     private BigDecimal score ;

    /**
     * 状态 0有效1无效
     */
     private Byte sts ;

    /**
     * 创建时间
     */
     private Date createdTime ;

    /**
     * 修改时间
     */
     private Date lastUpdatedTime ;

    /**
     * 
     */
     private String createdBy ;

    /**
     * 
     */
     private String lastUpdatedBy ;

}