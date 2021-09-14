package com.car.account.client.request.withdrawal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author niushuaixiang
 * @date 2020/12/26
 */
@Data
@ApiModel
public class AddWithdrawalReq {

    @ApiModelProperty(value = "提现金额",name = "withdrawalAmount")
    private BigDecimal withdrawalAmount;

    @ApiModelProperty(value = "开户银行名称",name = "depositBank")
    @NotBlank(message = "请输入开户银行名称！")
    private String depositBank;


    @ApiModelProperty(value = "支行名称",name = "subBranchName")
    @NotBlank(message = "请输入支行名称！")
    private String subBranchName;


    @ApiModelProperty(value = "银行卡号",name = "cardNumbers")
    @NotBlank(message = "请输入银行卡号！")
    private String cardNumbers;


}
