package com.car.account.web.service.message.imp;

import com.car.account.client.request.message.MessageTemplateReq;
import com.car.account.web.mapper.message.MessageTemplateMapper;
import com.car.account.web.model.message.MessageTemplate;
import com.car.account.web.service.message.MessageTemplateService;
import com.car.common.res.ResultRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageTemplateServiceImp implements MessageTemplateService {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    @Override
    public ResultRes<List<MessageTemplate>> getMessageTemplate(String title,Integer type) {
        MessageTemplate messageTemplate=new MessageTemplate();
        messageTemplate.setType(type);
        messageTemplate.setTitle(title);
        List<MessageTemplate> select = messageTemplateMapper.select(messageTemplate);
        return ResultRes.success(select);
    }

    @Override
    public ResultRes insertMessageTemplate(MessageTemplateReq messageTemplateReq) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplateMapper.insert(messageTemplate);
        return ResultRes.success();
    }

    @Override
    public ResultRes updateMessageTemplate(MessageTemplateReq messageTemplateReq) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplateMapper.updateByPrimaryKey(messageTemplate);
        return ResultRes.success();
    }
}
