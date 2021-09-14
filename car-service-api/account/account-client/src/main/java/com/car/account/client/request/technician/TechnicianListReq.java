package com.car.account.client.request.technician;

import com.car.common.req.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author linjiang.xie
 * @date 2020/12/19 18:31
 */
@Data
@ApiModel
public class TechnicianListReq extends PageReq {

    @ApiModelProperty(value = "姓名",name = "userName")
    private String userName;

    @ApiModelProperty(value = "联系方式",name = "mobile")
    private String mobile;

    @ApiModelProperty(value = "省份数据ID",name = "provinceUuid")
    private String provinceUuid;

    @ApiModelProperty(value = "城市数据ID",name = "cityUuid ")
    private String cityUuid;

    @ApiModelProperty(value = "技术类型",name = "technologyType")
    private String technologyType;



}
