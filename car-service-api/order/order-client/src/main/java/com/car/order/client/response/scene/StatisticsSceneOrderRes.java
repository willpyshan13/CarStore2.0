package com.car.order.client.response.scene;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/3/5
 */
@Data
@ApiModel(value = "StatisticsSceneOrderRes", description = "统计vo")
public class StatisticsSceneOrderRes {



    @ApiModelProperty(value = "总数", name = "total")
    private Integer total;

    @ApiModelProperty(value = "总金额", name = "totalAmount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "金额折线图", name = "numList")
    private List<Map> numList;

    @ApiModelProperty(value = "数量折线图", name = "amountList")
    private List<Map> amountList;
}
