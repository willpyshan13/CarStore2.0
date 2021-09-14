package com.car.account.web.service.message;

import com.car.account.client.request.message.MessageTemplateReq;
import com.car.common.res.ResultRes;

public interface MessageTemplateService {


    /**
     *消息模板
     * @param
     * @return
     */
    ResultRes getMessageTemplate(String title,Integer type);

    /**
     * 添加消息模板
     * @param messageTemplateReq
     * @return
     */
    ResultRes insertMessageTemplate(MessageTemplateReq messageTemplateReq);

    /**
     * 修改消息模板
     * @param messageTemplateReq
     * @return
     */
    ResultRes updateMessageTemplate(MessageTemplateReq messageTemplateReq);
}
