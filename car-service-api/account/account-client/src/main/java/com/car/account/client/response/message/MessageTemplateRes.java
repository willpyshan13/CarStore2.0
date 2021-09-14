package com.car.account.client.response.message;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息模板VO
 * @since jdk1.8
 */
@Data
@ApiModel(description = "消息模板VO")
public class MessageTemplateRes implements Serializable {

     @ApiModelProperty("")
     private String uuid ;


     @ApiModelProperty("消息类型")
     private String type ;


     @ApiModelProperty("是否需要推送 0否 1是")
     private Byte needPush ;


     @ApiModelProperty("标题")
     private String title ;


     @ApiModelProperty("内容")
     private String content ;


     @ApiModelProperty("推送内容")
     private String pushContent ;


     @ApiModelProperty("用户ID")
     private String userUuid ;


     @ApiModelProperty("创建时间")
     private Date createdTime ;


     @ApiModelProperty("修改时间")
     private Date lastUpdatedTime ;


     @ApiModelProperty("创建人")
     private String createdBy ;


     @ApiModelProperty("修改人")
     private String lastUpdatedBy ;

}