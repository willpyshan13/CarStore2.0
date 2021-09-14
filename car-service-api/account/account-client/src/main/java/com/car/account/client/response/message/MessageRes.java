package com.car.account.client.response.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel
public class MessageRes {

    @ApiModelProperty("")
    private String uuid ;


    @ApiModelProperty("内容")
    private String content ;


    @ApiModelProperty("消息类型 1工位通知，2发现消息，3现场支持，4商城消息，5钱包消息，6账户信息")
    private Byte type ;


    @ApiModelProperty("是否已读 1：已读  2：未读")
    private Byte status ;

    @ApiModelProperty("用户uuid")
    private String technicianUuid;

    @ApiModelProperty("客户类型 ")
    private Byte clientType;


    @ApiModelProperty(value = "创建时间",example = "yyyy-MM-dd HH:mi:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime ;


    @ApiModelProperty(value = "修改时间",example = "yyyy-MM-dd HH:mi:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdatedTime ;
    /**
     * 订单uuid
     */
    @ApiModelProperty("订单号")
    private String orderUuid;

    @ApiModelProperty("创建人")
    private String createdBy ;


    @ApiModelProperty("修改人")
    private String lastUpdatedBy ;

}
