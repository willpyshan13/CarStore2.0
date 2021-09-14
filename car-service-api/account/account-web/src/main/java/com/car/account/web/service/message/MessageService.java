package com.car.account.web.service.message;

import com.car.account.client.request.message.MessageReq;
import com.car.account.client.response.message.MessageRes;
import com.car.account.web.model.message.Message;
import com.car.common.res.ResultRes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MessageService {
    ResultRes<List<MessageRes>> messageList(Integer type);

    ResultRes updateMessageStatus();

    ResultRes updateMessageStatusById (String uuid);

    ResultRes deleteMessageById (String uuid);

    ResultRes insertMessagePush(MessageReq messageReq);

    ResultRes insertMessage(MessageReq messageReq);

    HashMap messageNumber();

    ResultRes sendMsg(String id, Map<String,String>  param,String userUuid,Integer userType,String orderUuid);
}
