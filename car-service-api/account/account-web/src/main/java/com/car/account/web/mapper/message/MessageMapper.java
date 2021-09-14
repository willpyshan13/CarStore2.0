package com.car.account.web.mapper.message;

import com.car.account.client.response.message.MessageRes;
import com.car.account.web.model.message.Message;
import com.car.account.web.model.message.MessageTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MessageMapper extends Mapper<Message> {

    List<MessageRes> selectByCondition(Message message);

    Integer updateMessageStatus(@Param("userUuid") String userUuid, @Param("status") int status);

    Integer selectmesssageTypeNumber(@Param("userUuid") String userUuid, @Param("type") int type);
}
