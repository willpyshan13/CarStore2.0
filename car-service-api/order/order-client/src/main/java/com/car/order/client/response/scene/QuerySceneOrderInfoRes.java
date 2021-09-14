package com.car.order.client.response.scene;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/3/5
 */
@Data
@ApiModel(value = "QuerySceneOrderInfoRes", description = "查询现场订单详情VO")
public class QuerySceneOrderInfoRes {

    @ApiModelProperty(value = "uuid", name = "uuid")
    private String uuid;

    @ApiModelProperty(value = "订单编号", name = "orderNum")
    private String orderNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "下单时间", name = "createdTime")
    private Date createdTime;

    @ApiModelProperty(value = "支付方式 0 微信支付 1 支付宝支付", name = "payType")
    private Integer payType;
    @ApiModelProperty(value = "发布者uuid", name = "issuerUuid")
    private String issuerUuid;
    @ApiModelProperty(value = "发布者姓名", name = "issuerName")
    private String issuerName;

    @ApiModelProperty(value = "发布者手机号码", name = "issuerMobile")
    private String issuerMobile;

    @ApiModelProperty(value = "购买者姓名", name = "buyerName")
    private String buyerName;

    @ApiModelProperty(value = "抢单者uuid", name = "buyerName")
    private String brandUuid;
    @ApiModelProperty(value = "购买者手机号码", name = "buyerMobile")
    private String buyerMobile;

    @ApiModelProperty(value = "品牌名称", name = "brandName")
    private String brandName;

    @ApiModelProperty(value = "车型uuid", name = "carModelUuid")
    private String carModelUuid;

    @ApiModelProperty(value = "车型名称", name = "carModelName")
    private String carModelName;

    @ApiModelProperty(value = "车款", name = "carStyle")
    private String carStyle;

    @ApiModelProperty(value = "VIN码", name = "vinCode")
    private String vinCode;

    @ApiModelProperty(value = "变速器一级", name = "transmissionOneLevel")
    private String transmissionOneLevel;

    @ApiModelProperty(value = "变速器一级uuid(对应字典uuid)", name = "transmissionOneLevelUuid")
    private String transmissionOneLevelUuid;

    @ApiModelProperty(value = "变速器二级", name = "transmissionTwoLevel")
    private String transmissionTwoLevel;

    @ApiModelProperty(value = "变速器二级（对应字典uuid）", name = "transmissionTwoLevelUuid")
    private String transmissionTwoLevelUuid;

    @ApiModelProperty(value = "发动机排量", name = "engineDisplacement")
    private String engineDisplacement;

    @ApiModelProperty(value = "发动机排量uuid(对应字典uuid)", name = "engineDisplacementUuid")
    private String engineDisplacementUuid;

    @ApiModelProperty(value = "驱动方式", name = "drivingMode")
    private String drivingMode;

    @ApiModelProperty(value = "驱动方式Uuid(对应字典uuid)", name = "drivingModeUuid")
    private String drivingModeUuid;

    @ApiModelProperty(value = "增压系统", name = "boosterSystem")
    private String boosterSystem;

    @ApiModelProperty(value = "增压系统uuid(对应字典uuid)", name = "boosterSystemUuid")
    private String boosterSystemUuid;

    @ApiModelProperty(value = "保修状态，0保修，1不保修", name = "warrantySts")
    private Integer warrantySts;

    @ApiModelProperty(value = "其他状态", name = "otherSts")
    private String otherSts;

    @ApiModelProperty(value = "故障描述", name = "faultDesc")
    private String faultDesc;

    @ApiModelProperty(value = "维修类型", name = "repairType")
    private String repairType;

    @ApiModelProperty(value = "维修类型uuid（对应字典表uuid）", name = "repairTypeUuid")
    private String repairTypeUuid;

    @ApiModelProperty(value = "已检过程", name = "alreadyInspect")
    private String alreadyInspect;

    @ApiModelProperty(value = "DTC故障code", name = "dtcCode")
    private String dtcCode;

    @ApiModelProperty(value = "基本上门费用", name = "basicDoorAmount")
    private BigDecimal basicDoorAmount;

    @ApiModelProperty(value = "基本上门费用Uuid", name = "basicDoorAmountUuid")
    private String basicDoorAmountUuid;

    @ApiModelProperty(value = "平台订单服务费", name = "orderServiceAmount")
    private BigDecimal orderServiceAmount;

    @ApiModelProperty(value = "平台订单服务费Uuid", name = "orderServiceAmountUuid")
    private String orderServiceAmountUuid;

    @ApiModelProperty(value = "总支付费用", name = "totalAmount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "服务时间", name = "serviceDate")
    private String  serviceDate;

    @ApiModelProperty(value = "抢单时间", name = "grabbingDate")
    private String  grabbingDate;

    @ApiModelProperty(value = "支付时间", name = "payDate")
    private String  payDate;

    @ApiModelProperty(value = "服务地点", name = "detailedAddr")
    private String  detailedAddr;

    @ApiModelProperty(value = "关联技师uuid", name = "sceneOrderTechnicianUuid")
    private String sceneOrderTechnicianUuid ;

    @ApiModelProperty(value = "关联技师对象 ", name = "sceneOrderTechnicianRes")
    private SceneOrderTechnicianRes sceneOrderTechnicianRes ;

    @ApiModelProperty(value = "服务对象 ", name = "sceneOrderServiceRes")
    private SceneOrderServiceTwoRes sceneOrderServiceRes ;

    @ApiModelProperty(value = "dtc图片", name = "dtcImageList")
    private List<String> dtcImageList;

    @ApiModelProperty(value = "故障图片图片", name = "faultImageList")
    private List<String> faultImageList;

    @ApiModelProperty(value = "订单状态 0：待抢单  1:待支付 2：待上门,3:提交方案,4:待付款,5:服务中 6:待确认,7:完成,8:退款中,9:退款成功,10:退款失败,11 已取消 12, 接单取消13, 服务取消", name = "orderSts")
    private Integer orderSts;

    @ApiModelProperty(value = "是否是本人发布，true：是， false：否", name = "isOneself")
    private Boolean isOneself;

    @ApiModelProperty(value = "抢单状态 0未抢，1已抢", name = "grabbingOrdersSts")
    private Integer grabbingOrdersSts;

    @ApiModelProperty(value = "确认上门时间", name = "grabUpdateTime")
    private String grabUpdateTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "订单完成时间/取消时间/拒绝时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdatedTime;
}
