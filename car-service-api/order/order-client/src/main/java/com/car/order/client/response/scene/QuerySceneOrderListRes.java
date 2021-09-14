package com.car.order.client.response.scene;

import com.car.common.res.PageRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/3/5
 */
@Data
@ApiModel(value = "QuerySceneOrderListRes", description = "查询现场订单列表返回VO")
public class QuerySceneOrderListRes{

    @ApiModelProperty(value = "uuid", name = "uuid")
    private String uuid;

    @ApiModelProperty(value = "发布者uuid", name = "issuerUuid")
    private String issuerUuid;

    @ApiModelProperty(value = "发布者姓名", name = "issuerName")
    private String issuerName;

    @ApiModelProperty(value = "发布者手机号码", name = "issuerMobile")
    private String issuerMobile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", name = "createdTime")
    private Date createdTime;

    @ApiModelProperty(value = "抢单者uuid", name = "buyerName")
    private String brandUuid;

    @ApiModelProperty(value = "抢单者姓名", name = "buyerName")
    private String buyerName;

    @ApiModelProperty(value = "抢单者手机号码", name = "buyerMobile")
    private String buyerMobile;

    @ApiModelProperty(value = "品牌名称", name = "brandName")
    private String brandName;

    @ApiModelProperty(value = "车型名称", name = "carModelName")
    private String carModelName;

    @ApiModelProperty(value = "故障描述", name = "faultDesc")
    private String faultDesc;

    @ApiModelProperty(value = "支付方式 0 微信支付 1 支付宝支付", name = "payType")
    private Integer payType;

    @ApiModelProperty(value = "订单状态 0：待抢单  1:待支付 2：待上门,3:提交方案,4:待付款,5:服务中 6:待确认,7:完成,8:退款中,9:退款成功,10:退款失败,11 已取消 12, 接单取消13, 服务取消", name = "orderSts")
    private Integer orderSts;

    @ApiModelProperty(value = "基础上门费", name = "totalAmount")
    private BigDecimal basicDoorAmount;

    @ApiModelProperty(value = "总支付费用", name = "totalAmount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "抢单状态 0未抢，1已抢", name = "grabbingOrdersSts")
    private Integer grabbingOrdersSts;

    @ApiModelProperty(value = "是否是本人发布，true：是， false：否", name = "isOneself")
    private Boolean isOneself;

    @ApiModelProperty(value = "距离(km)", name = "distance")
    private Float distance;

    @ApiModelProperty(value = "是否评分0:未评分 1:已评分", name = "isScore")
    private Integer isScore;
}
