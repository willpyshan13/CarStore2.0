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
public class AccountRes {
    @ApiModelProperty("账户余额")
    private BigDecimal accountAmt;
    @ApiModelProperty("可提现金额")
    private BigDecimal waitAmount;
    @ApiModelProperty("提现累计金额")
    private BigDecimal withdrawAmount;
    @ApiModelProperty("冻结金额")
    private BigDecimal frozenAmt;

    @ApiModelProperty("开户名")
    private String accountName;

    @ApiModelProperty("开户银行,对应字典表code")
    private String depositBank;

    @ApiModelProperty("支行名称")
    private String subBranchName;

    @ApiModelProperty("银行卡号")
    private String cardNumbers;

    @ApiModelProperty("订单数")
    private Integer orderNum;

    @ApiModelProperty("基本信息")
    private Double score;

    @ApiModelProperty("基本信息")
    private UserInfoRes userInfoRes;
}
