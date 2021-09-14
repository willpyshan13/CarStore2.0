package com.car.order.client.response.technician;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangyp
 * @date 2021/1/21 0:17
 */
@Data
@ApiModel
public class TechnicianCaseRes {

    /**
     * 案例唯一标识必填
     */
    @ApiModelProperty(value = "案例唯一标识",name = "uuid")
    private String uuid;

    /**
     * 行驶里程单位km
     */
    @ApiModelProperty(value="行驶里程单位km",name="mileage")
    private Integer mileage;

    /**
     * 制造日期yyyy/MM/dd
     */
    @ApiModelProperty(value = "开始时间 yyyy-MM-dd",name="startDate",example = "2020-12-30 21:35:00")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String madeTime;

    /**
     * 车架号
     */
    @ApiModelProperty(value="车架号",name="vin")
    private String vin;

    /**
     * 所属系统字典表attach_sys
     */
    @ApiModelProperty(value="所属系统字典表attach_sys",name="attachSys")
    private String attachSys;

    /**
     * 所属系统字典表attach_sys
     */
    @ApiModelProperty(value="系统名称",name="attachSysName")
    private String attachSysName;

    /**
     * 所属系统字典表attach_sys
     */
    @ApiModelProperty(value="所属系统描述",name="describe")
    private String describe;

    /**
     * 金额
     */
    @ApiModelProperty(value="金额",name="amt")
    private BigDecimal amt;

    /**
     * 品牌字典表repair_brand
     */
    @ApiModelProperty(value="品牌字典表repair_brand",name="brandUuid")
    private String brandUuid;

    /**
     * 型号
     */
    @ApiModelProperty(value="型号",name="model")
    private String model;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题",name="title")
    private String title;

    /**
     * 动力信息
     */
    @ApiModelProperty(value="动力信息",name="powerInfo")
    private String powerInfo;

    /**
     * 故障现象
     */
    @ApiModelProperty(value="故障现象",name="faultDesc")
    private String faultDesc;

    /**
     * 诊断思路和过程
     */
    @ApiModelProperty(value="诊断思路和过程",name="ideaProcess")
    private String ideaProcess;

    /**
     * 结论总结
     */
    @ApiModelProperty(value="结论总结",name="summary")
    private String summary;

    /**
     * 案例资源列表
     */
    @ApiModelProperty(value="案例资源列表 图片/文件",name="caseImgList")
    private List<String> caseImgList;

    /**
     * 收益
     */
    @ApiModelProperty(value="收益",name="earnings")
    private BigDecimal earnings;

    /**
     * 销量
     */
    @ApiModelProperty(value="销量",name="salesVolume")
    private Integer salesVolume;

    @ApiModelProperty(value="订单uuid",name="orderUuid")
    private String orderUuid;

    @ApiModelProperty(value="订单状态0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败",name="orderSts")
    private Integer orderSts;

    @ApiModelProperty(value = "技师名称", name = "userName")
    private String userName;

    @ApiModelProperty(value = "技师工龄", name = "workingYear")
    private String workingYear;

    @ApiModelProperty(value = "发布时间", name = "createdTime")
    private String createdTime;

    @ApiModelProperty(value = "品牌", name = "brandName")
    private String brandName;

    @ApiModelProperty(value = "型号名称", name = "modelName")
    private String modelName;

    @ApiModelProperty(value = "评分", name = "score")
    private Integer score=5;

    @ApiModelProperty(value = "级别", name = "cybAuth")
    private Integer cybAuth;

    @ApiModelProperty(value = "owneruuid类型 1:车主，2：技师，3:店铺", name = "technicianType")
    private Byte technicianType;

    @ApiModelProperty(value = "技师uuid", name = "userType")
    private String technicianUuid;

    @ApiModelProperty(value = "店铺名", name = "storeName")
    private String storeName;

    @ApiModelProperty(value = "店铺类型,对应字典表 uuid",name = "storeType")
    private String storeType;

    @ApiModelProperty(value = "是否购买过 0否 1是",name = "purchase")
    private Byte  purchase;

    @ApiModelProperty(value = "审核状态,(0:待审核 1:审核通过 2:审核驳回)",name = "checkStatus")
    private Byte  checkStatus;
}
