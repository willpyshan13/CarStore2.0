package com.car.account.web.controller.message;


import com.car.account.client.request.message.MessageTemplateReq;
import com.car.account.web.model.message.MessageTemplate;
import com.car.account.web.service.message.MessageTemplateService;
import com.car.common.annotation.SysOperLog;
import com.car.common.enums.OperEnum;
import com.car.common.res.ResultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "MessageTemplateController", tags = "消息模板管理---后端")
@RequestMapping(value = "/messageTemplate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class MessageTemplateController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @GetMapping(value = "/getMessageTemplate")
    @ApiOperation(value = "消息模板", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "消息模板")
    public ResultRes getMessageTemplate(String title,Integer type){
        return messageTemplateService.getMessageTemplate(title,type);
    }

    @PostMapping(value = "/insertMessageTemplate")
    @ApiOperation(value = "消息模板", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "添加消息模板")
    public ResultRes insertMessageTemplate(@RequestBody MessageTemplateReq messageTemplateReq){
        log.info("添加消息模板传入参数："+messageTemplateReq.toString());
        return messageTemplateService.insertMessageTemplate(messageTemplateReq);
    }

    @PostMapping(value = "/updateMessageTemplate")
    @ApiOperation(value = "消息模板", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "修改消息模板")
    public ResultRes updateMessageTemplate(@RequestBody MessageTemplateReq messageTemplateReq){
        log.info("添加消息模板传入参数："+messageTemplateReq.toString());
        return messageTemplateService.updateMessageTemplate(messageTemplateReq);
    }

}
