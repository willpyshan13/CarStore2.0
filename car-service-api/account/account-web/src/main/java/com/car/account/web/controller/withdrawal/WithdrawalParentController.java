package com.car.account.web.controller.withdrawal;

import com.car.account.client.request.withdrawal.AddWithdrawalReq;
import com.car.account.client.request.withdrawal.QueryWithdrawalListReq;
import com.car.account.client.request.withdrawal.UpdateWithdrawalReq;
import com.car.account.client.response.withdrawal.QueryWithdrawalListRes;
import com.car.account.client.response.withdrawal.WithdrawalRes;
import com.car.account.web.service.withdrawal.WithdrawalService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.PageRes;
import com.car.common.res.ResultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


/**
 * @author zhouz
 * @date 2020/12/26
 */
@Slf4j
@Api(value = "WithdrawalParentController", tags = "提现管理")
@RequestMapping(value = "/withdrawalparent", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class WithdrawalParentController {

    @Autowired
    WithdrawalService withdrawalService;


    /**
     * 提现
     * @param addWithdrawalReq
     * @return
     */
    @PostMapping(value = "/withdrawal")
    @ApiOperation(value = "提现", nickname = "withdrawal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "提现", operType = OperEnum.ADD, operDesc = "提现")
    public ResultRes<String> withdrawal(@RequestBody @Valid AddWithdrawalReq addWithdrawalReq){
        return withdrawalService.addWithdrawal(addWithdrawalReq);
    }

}
