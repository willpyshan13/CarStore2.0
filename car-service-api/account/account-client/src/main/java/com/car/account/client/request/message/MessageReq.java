package com.car.account.client.request.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class MessageReq {

    @ApiModelProperty("")
    private String uuid ;


    @ApiModelProperty("内容")
    private String content ;

    @ApiModelProperty("推送内容")
    private String pushMessageContent ;

    @ApiModelProperty("消息类型 1工位通知，2发现消息，3现场支持，4商城消息，5钱包消息，6账户信息，7 共享技师")
    private Integer type;

    @ApiModelProperty("用户uuid")
    private String technicianUuid ;

    @ApiModelProperty("客户类型 ")
    private Integer clientType ;

    @ApiModelProperty("是否已读 1：未读 2：已读")
    private Integer status ;


    @ApiModelProperty("创建时间")
    private Date createdTime ;


    @ApiModelProperty("修改时间")
    private Date lastUpdatedTime ;


    @ApiModelProperty("创建人")
    private String createdBy ;


    @ApiModelProperty("修改人")
    private String lastUpdatedBy ;


}
