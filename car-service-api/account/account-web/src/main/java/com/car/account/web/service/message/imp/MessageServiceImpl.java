package com.car.account.web.service.message.imp;

import com.car.account.client.enums.message.MessageTypeEnum;
import com.car.account.client.request.message.MessageReq;
import com.car.account.client.response.message.MessageRes;
import com.car.account.web.mapper.message.MessageMapper;
import com.car.account.web.mapper.message.MessageTemplateMapper;
import com.car.account.web.model.message.Message;
import com.car.account.web.model.message.MessageTemplate;
import com.car.account.web.service.message.MessageService;
import com.car.common.enums.StsEnum;
import com.car.common.res.ResultRes;
import com.car.common.utils.StringUtil;
import com.car.common.utils.TokenHelper;
import com.car.common.utils.UuidUtils;
import com.car.utility.client.feign.JiguangSmsFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private JiguangSmsFeign jiguangSmsFeign;
    @Autowired
    private MessageTemplateMapper messageTemplateMapper;
    /**
     *消息列表
     * @param
     * @return
     */
    @Override
    public ResultRes<List<MessageRes>> messageList(Integer type) {
        List<MessageRes> messageRes=new ArrayList<>();
        Message message = new Message();
        message.setType(type);
        //用户uuid
        String userUuid = TokenHelper.getUserUuid();
        //用户类型
        Integer userType = TokenHelper.getUserType();
        if(StringUtils.isNotEmpty(userUuid)){
            message.setTechnicianUuid(userUuid);
            message.setClientType(userType);
        }

        messageRes = messageMapper.selectByCondition(message);

        return ResultRes.success(messageRes);
    }

    /**
     * 一键已读
     * @param
     * @return
     */
    @Override
    public ResultRes updateMessageStatus() {

        messageMapper.updateMessageStatus(TokenHelper.getUserUuid(),2);
        return ResultRes.success();
    }

    /**
     * 根据id修改变为已读状态
     * @param uuid
     * @return
     */
    @Override
    public ResultRes updateMessageStatusById(String uuid) {
        Message message = new Message();
        message.setUuid(uuid);
        message.setStatus(2);
        messageMapper.updateByPrimaryKeySelective(message);
        return ResultRes.success();
    }

    /**
     * 删除
     * @param uuid
     * @return
     */
    @Override
    public ResultRes deleteMessageById(String uuid){
        Message message = new Message();
        message.setUuid(uuid);
        message.setSts(StsEnum.INVALID.getValue());
        messageMapper.updateByPrimaryKeySelective(message);
        return ResultRes.success();
    }

    /**
     * 发送消息并推送x
     * @param messageReq
     * @return
     */
    @Override
    public ResultRes insertMessagePush(MessageReq messageReq) {
        Message message = new Message();
        BeanUtils.copyProperties(messageReq,message);
        message.setCreatedTime(new Date());
        message.setStatus(1);
        message.setSts(StsEnum.ACTIVE.getValue());
        message.setCreatedBy(TokenHelper.getUserName());
        messageMapper.insert(message);
        //jiguangSmsFeign.jpushAll(messageReq.getPushMessageContent(),message.getType()+"",message.getTechnicianUuid(),messageReq.getType()+"");
        return ResultRes.success();
    }

    /**
     * 发送消息
     * @param messageReq
     * @return
     */
    @Override
    public ResultRes insertMessage(MessageReq messageReq) {
        Message message = new Message();
        BeanUtils.copyProperties(messageReq,message);
        message.setCreatedTime(new Date());
        message.setStatus(1);
        messageMapper.insertSelective(message);
        return ResultRes.success(message.getUuid());
    }

    @Override
    public ResultRes sendMsg(String id, Map<String,String> param,String userUuid,Integer userType,String orderUuid) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setUuid(id);
        messageTemplate = messageTemplateMapper.selectOne(messageTemplate);
        String pushContent = messageTemplate.getPushContent();
        String content = messageTemplate.getContent();
        if (!param.isEmpty()) {
            for (String key : param.keySet()) {
                String value = param.get(key);
                content = content.replace("#{" + key + "}", value);
                System.out.println("Key = " + key + ", Value = " + value);
            }
        }
        Message message = new Message();
        message.setUuid(UuidUtils.getUuid());
        message.setContent(content);
        message.setClientType(userType);
        message.setTechnicianUuid(userUuid);
        message.setType(messageTemplate.getType());
        message.setCreatedTime(new Date());
        message.setStatus(1);
        message.setSts(0);
        message.setOrderUuid(orderUuid);
        messageMapper.insertSelective(message);
        log.info("sasdadasdasdadasd"+pushContent);
        if (messageTemplate.getNeedPush().equals(1)) {
            jiguangSmsFeign.jpushAll(id,pushContent, message.getType() + "", message.getTechnicianUuid(), message.getType() + "",orderUuid);
        }
        return ResultRes.success();
    }

    /**
     * 消息数量
     * @return
     */
    @Override
    public HashMap messageNumber() {
        HashMap hashMap=new HashMap();
        //用户uuid
        String userUuid = TokenHelper.getUserUuid();
        for(int i=1;i<=7;i++){
            Integer number=messageMapper.selectmesssageTypeNumber(userUuid,i);
            hashMap.put(i,number);
        }
        return hashMap;
    }



}
