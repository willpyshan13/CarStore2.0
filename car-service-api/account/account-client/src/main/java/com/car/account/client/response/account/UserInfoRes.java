package com.car.account.client.response.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
 *
 * 账户信息
 * @author zhangyp
 * @date 2021/1/28 22:06
 */
@Data
@ApiModel
public class UserInfoRes {
    @ApiModelProperty("头像")
    private  String headImage;
    @ApiModelProperty("姓名")
    private String  name;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("用户类型/1:车主，2：技师，3:店铺',")
    private Integer userType;
    @ApiModelProperty("评分")
    private BigDecimal score;
}
