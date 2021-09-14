package com.car.order.client.request.order.score;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/1/30
 */
@Data
@ApiModel(value="AddScoreInfoReq",description="新增评分信息")
public class AddScoreInfoReq {

    @ApiModelProperty(value = "订单id", name = "orderUuid")
    private String orderUuid;

    @ApiModelProperty(value = "打分类型  1 咨询 2 回答 3 案例 4 旁听 5 维修保养 6 代驾 7 dtc故障 8 课程 9:现场下单 10：共享技师 11现场服务12, 共享服务 13, 工位14, 拼团", name = "scoreType")
    @NotNull(message = "请输入评分类型！")
    private Integer orderType;

    @ApiModelProperty(value = "星值eg:4.5", name = "scoreStar")
    private BigDecimal scoreStar;

    @ApiModelProperty(value = "订单被评价人的uuid", name = "relationUuid")
    private String relationUuid;

    @ApiModelProperty(value = "订单被评价人的类型", name = "relationType")
    private Integer relationType;

}
