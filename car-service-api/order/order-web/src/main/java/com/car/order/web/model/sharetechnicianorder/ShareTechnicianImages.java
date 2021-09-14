package com.car.order.web.model.sharetechnicianorder;

import com.car.common.datasource.model.BaseModelInfo;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 现场订单图片实体类
 * @since jdk1.8
 */
@Data
public class ShareTechnicianImages extends BaseModelInfo {


    /**
     * 关联uuid
     */
     private String relationUuid ;

    /**
     * dtc图片
     */
     private String imageUrl ;

    /**
     * 2:故障描述 3技师到场，4 完成服务
     */
     private Integer type ;


}