package com.car.account.web.controller.message;


import com.car.account.client.request.message.MessageReq;
import com.car.account.client.response.message.MessageRes;
import com.car.account.web.model.message.Message;
import com.car.account.web.service.message.MessageService;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(value = "MessageController", tags = "消息管理--前段调用")
@RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/messageList")
    @ApiOperation(value = "消息列表", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "消息列表")
    public ResultRes<List<MessageRes>> messageList(Integer type){
        return messageService.messageList(type);
    }


    @GetMapping(value = "/updateMessageStatus")
    @ApiOperation(value = "一键已读", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "一键已读")
    public ResultRes updateMessageStatus(){
        return messageService.updateMessageStatus();
    }

    @GetMapping(value = "/updateMessageStatusById")
    @ApiOperation(value = "单个已读", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "单个已读")
    public ResultRes updateMessageStatusById(String id){
        return messageService.updateMessageStatusById(id);
    }

    @GetMapping(value = "/deleteMessageById")
    @ApiOperation(value = "删除", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "删除")
    public ResultRes deleteMessageById(String id){
        return messageService.deleteMessageById(id);
    }

    @PostMapping(value = "/insertMessagePush")
    @ApiOperation(value = "发送消息并推送", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "发送消息并推送")
    public ResultRes insertMessagePush(@RequestBody @Valid MessageReq messageReq){
        return messageService.insertMessagePush(messageReq);
    }

    @PostMapping(value = "/insertMessage")
    @ApiOperation(value = "发送消息", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "发送消息并推送")
    public ResultRes insertMessage(@RequestBody @Valid MessageReq messageReq){
        return messageService.insertMessage(messageReq);
    }

    @PostMapping(value = "/messageNumber")
    @ApiOperation(value = "消息数量", nickname = "account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "消息数量")
    public ResultRes<Map> messageNumber(){
        return ResultRes.success(messageService.messageNumber());
    }

    @PostMapping(value = "/sendMsg")
    @ApiOperation(value = "消息发送", nickname = "sendMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String" ,paramType="header")
    })
    @SysOperLog(operModul = "消息管理", operType = OperEnum.SELECT, operDesc = "消息发送")
    public ResultRes sendMsg(@RequestParam("id") String id, @RequestBody Map<String,String> param,
                             @RequestParam("userUuid")String  userUuid,@RequestParam("userType")Integer userType,@RequestParam(name = "orderUuid")String orderUuid){
        ResultRes resultRes = ResultRes.error();
        try {
            resultRes =  messageService.sendMsg(id,param,userUuid,userType,orderUuid);
        }catch (Exception e){
            e.printStackTrace();
            log.info("消息发送失败!");
        }
        return resultRes;
    }

}
