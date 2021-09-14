package com.car.order.client.response.technicianappointment;


import com.car.common.res.BaseRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 现场订单技师相关实体类
 * @since jdk1.8
 */
@Data
@ApiModel(value = "ShareTechnicianDetailRes", description = "共享订单确认对象")
public class ShareTechnicianDetailRes {

    @ApiModelProperty(value = "数据记录UUID")
    private String uuid;
    /**
     * 订单uuid
     */
    @ApiModelProperty(value = "共享技师订单uuid")
     private String orderUuid ;

    /**
     * 技师uuid
     */
    @ApiModelProperty(value = "技师uuid")
     private String technicianUuid ;

    /**
     * 技师姓名
     */
    @ApiModelProperty(value = "技师姓名")
     private String technicianName ;

    /**
     * 技师手机号
     */
    @ApiModelProperty(value = "技师手机号")
     private String technicianMobile ;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
     private String desc ;

    /**
     * 已检过程
     */
    @ApiModelProperty(value = "已检过程")
     private String alreadyInspect ;

    /**
     * DTC故障code
     */
    @ApiModelProperty(value = "DTC故障code")
     private String dtcCode ;

    /**
     * 维修总结
     */
    @ApiModelProperty(value = "维修总结")
     private String repairSummary ;

    /**
     * 故障是否解决 0解决，1未解决
     */
    @ApiModelProperty(value = " 故障是否解决 0解决，1未解决")
     private Integer faultSolve ;

    @ApiModelProperty(value = "订单完成图片列表", name = "endImageList")
    private List<String> endImageList;

    @ApiModelProperty(value = "上门图片列表", name = "doorImageList")
    private List<String> doorImageList;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "上门时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "完成服务时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdatedTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String lastUpdatedBy;
}