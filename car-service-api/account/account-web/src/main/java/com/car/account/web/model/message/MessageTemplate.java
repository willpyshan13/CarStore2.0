package com.car.account.web.model.message;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息模板实体类
 * @since jdk1.8
 */
@Data
public class MessageTemplate implements Serializable {
    /**
     * 
     */
     private String uuid ;

    /**
     * 消息类型消息类型 1工位通知，2发现消息，3现场支持，4商城消息，5钱包消息，6账户信息，7 共享技师
     */
     private Integer type ;

    /**
     * 是否需要推送 0否 1是
     */
     private Integer needPush ;

    /**
     * 标题
     */
     private String title ;

    /**
     * 内容
     */
     private String content ;

    /**
     * 推送内容
     */
     private String pushContent ;

    /**
     * 用户ID
     */
     private String userUuid ;

    /**
     * 创建时间
     */
     private Date createdTime ;

    /**
     * 修改时间
     */
     private Date lastUpdatedTime ;

    /**
     * 创建人
     */
     private String createdBy ;

    /**
     * 修改人
     */
     private String lastUpdatedBy ;

}